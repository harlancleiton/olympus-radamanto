package com.olympus.radamanto.infrastructure.adapters.output.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.olympus.radamanto.domain.events.DomainEvent
import com.olympus.radamanto.domain.exceptions.ConcurrencyAggregateException
import com.olympus.radamanto.domain.ports.output.EventStore
import com.olympus.radamanto.domain.valueobjects.EntityId
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.*
import org.springframework.stereotype.Repository

@Repository
class MongoEventStore(
    private val mongoTemplate: MongoTemplate,
    private val objectMapper: ObjectMapper
) : EventStore {

    override fun saveEvents(aggregateId: EntityId, events: List<DomainEvent>, expectedVersion: Long): Result<Unit> {
        return runCatching {
            val lastEvent = mongoTemplate.findOne(
                Query(Criteria.where("aggregateId").isEqualTo(aggregateId.value))
                    .with(Sort.by(Sort.Direction.DESC, "version"))
                    .limit(1),
                EventDocument::class.java
            )

            if (lastEvent != null && lastEvent.version != expectedVersion) {
                throw ConcurrencyAggregateException(aggregateId)
            }

            events.forEachIndexed { index, event ->
                val eventData = objectMapper.convertValue(event, Map::class.java) as Map<String, Any>
                val eventDocument = EventDocument(
                    aggregateId = aggregateId.value.toString(),
                    eventName = event.name,
                    eventType = event.javaClass.simpleName,
                    eventData = eventData,
                    version = expectedVersion + index + 1,
                    timestamp = event.occurredAt
                )
                mongoTemplate.save(eventDocument)
            }
        }
    }

    override fun getEvents(aggregateId: EntityId): Result<List<DomainEvent>> {
        return runCatching {
            mongoTemplate.find(
                Query(Criteria.where("aggregateId").isEqualTo(aggregateId.value))
                    .with(Sort.by(Sort.Direction.ASC, "version")),
                EventDocument::class.java
            ).map { it.toDomainEvent(objectMapper) }
        }
    }
}

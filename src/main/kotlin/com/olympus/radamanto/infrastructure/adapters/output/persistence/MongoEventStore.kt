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

    override fun saveEvents(aggregateId: EntityId, events: List<DomainEvent>, expectedVersion: Long) {
        val lastEvent = mongoTemplate.findOne(
            Query(Criteria.where("aggregateId").isEqualTo(aggregateId.value))
                .with(Sort.by(Sort.Direction.DESC, "version"))
                .limit(1),
            MongoDomainEventDocument::class.java
        )

        if (lastEvent != null && lastEvent.version != expectedVersion) {
            throw ConcurrencyAggregateException(aggregateId)
        }

        events.forEachIndexed { index, event ->
            val eventDocument = MongoDomainEventDocument(
                aggregateId = aggregateId.value.toString(),
                eventName = event.name,
                eventType = event.javaClass.simpleName,
                eventData = objectMapper.writeValueAsString(event),
                version = expectedVersion + index + 1,
                timestamp = event.occurredAt
            )
            mongoTemplate.save(eventDocument)
        }
    }

    override fun getEvents(aggregateId: EntityId): List<DomainEvent> {
        return mongoTemplate.find(
            Query(Criteria.where("aggregateId").isEqualTo(aggregateId.value))
                .with(Sort.by(Sort.Direction.ASC, "version")),
            MongoDomainEventDocument::class.java
        ).map { it.toDomainEvent(objectMapper) }
    }
}

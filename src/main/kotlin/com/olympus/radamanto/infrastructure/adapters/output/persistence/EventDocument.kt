package com.olympus.radamanto.infrastructure.adapters.output.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.olympus.radamanto.domain.events.DomainEvent
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.*
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "events")
@CompoundIndexes(
    CompoundIndex(name = "aggregateIdVersion", def = "{ 'aggregateId' : 1, 'version': 1 }"),
    CompoundIndex(name = "aggregateIdTimestamp", def = "{ 'aggregateId' : 1, 'timestamp': 1 }")
)
data class EventDocument(
    @Id val id: String = ObjectId().toHexString(),
    @Indexed val aggregateId: String,
    @Indexed val eventName: String,
    val eventType: String,
    val eventData: String,
    @Indexed val version: Long,
    @Indexed val timestamp: Instant
) {
    fun toDomainEvent(objectMapper: ObjectMapper): DomainEvent {
        val eventClass = Class.forName(eventType).kotlin
        return objectMapper.readValue(eventData, eventClass.java) as DomainEvent
    }
}

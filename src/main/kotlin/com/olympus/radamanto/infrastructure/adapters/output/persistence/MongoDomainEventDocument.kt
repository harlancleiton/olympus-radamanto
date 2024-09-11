package com.olympus.radamanto.infrastructure.adapters.output.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.olympus.radamanto.domain.events.DomainEvent
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "domain_events")
data class MongoDomainEventDocument(
    @Id val id: String = ObjectId().toHexString(),
    val aggregateId: String,
    val eventName: String,
    val eventType: String,
    val eventData: String,
    val version: Long,
    val timestamp: Instant
) {
    fun toDomainEvent(objectMapper: ObjectMapper): DomainEvent {
        val eventClass = Class.forName(eventType).kotlin
        return objectMapper.readValue(eventData, eventClass.java) as DomainEvent
    }
}

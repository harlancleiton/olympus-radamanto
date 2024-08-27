package com.olympus.radamanto.domain.events

import com.olympus.radamanto.domain.valueobjects.EntityId
import java.time.Instant

/**
 * Represents a domain event in the system.
 * Domain events are immutable records of something that happened in the domain.
 */
interface DomainEvent {
    /**
     * The unique identifier of the event.
     */
    val id: EntityId


    /**
     * The identifier of the aggregate that this event is associated with.
     */
    val aggregateId: EntityId


    /**
     * The name of the event, used for identification and logging purposes.
     */
    val name: String


    /**
     * The timestamp when the event occurred.
     */
    val occurredAt: Instant


    /**
     * The version of the aggregate after this event was applied.
     * This is used for optimistic concurrency control and event ordering.
     */
    val version: Long
}

package com.olympus.radamanto.domain.ports.output

import com.olympus.radamanto.domain.events.DomainEvent
import com.olympus.radamanto.domain.valueobjects.EntityId

interface EventStore {
    fun saveEvents(aggregateId: EntityId, events: List<DomainEvent>, expectedVersion: Long)
    fun getEvents(aggregateId: EntityId): List<DomainEvent>
}

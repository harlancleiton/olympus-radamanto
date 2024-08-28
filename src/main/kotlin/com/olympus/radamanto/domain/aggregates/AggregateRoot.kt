package com.olympus.radamanto.domain.aggregates

import com.olympus.radamanto.domain.entities.BaseEntity
import com.olympus.radamanto.domain.events.DomainEvent
import com.olympus.radamanto.domain.valueobjects.EntityId

abstract class AggregateRoot<T : DomainEvent>(id: EntityId) : BaseEntity(id) {
    private val uncommittedEvents = mutableListOf<T>()


    /**
     * Applies an event to the aggregate, updating its state.
     *
     * @param event The event to apply.
     */
    protected open fun applyEvent(event: T) {
        this.version = event.version
        this.updatedAt = event.occurredAt
        uncommittedEvents.add(event)
    }


    /**
     * Returns a list of all uncommitted events.
     *
     * @return A list of uncommitted UserEvents.
     */
    fun getUncommittedEvents(): List<T> = uncommittedEvents.toList()


    /**
     * Clears the list of uncommitted events.
     */
    fun clearUncommittedEvents() {
        uncommittedEvents.clear()
    }
}
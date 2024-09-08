package com.olympus.radamanto.domain.aggregates

import com.olympus.radamanto.domain.entities.BaseEntity
import com.olympus.radamanto.domain.events.DomainEvent
import com.olympus.radamanto.domain.events.EventPublisher
import com.olympus.radamanto.domain.valueobjects.EntityId

/**
 * Abstract base class for all aggregate roots in the domain.
 *
 * An aggregate root is an entity that encapsulates and controls access to a cluster of related objects,
 * ensuring consistency and enforcing invariants across the entire aggregate.
 *
 * @param T The type of domain events this aggregate root can handle.
 * @property id The unique identifier of the aggregate root.
 * @property publisher The publisher responsible for publishing domain events.
 */
abstract class AggregateRoot<T : DomainEvent>(
    id: EntityId,
    private val publisher: EventPublisher
) : BaseEntity(id) {

    private val uncommittedEvents = mutableListOf<T>()


    /**
     * Applies an event to the aggregate root, updating its state.
     *
     * This method should be overridden by subclasses to define how specific events affect the aggregate's state.
     * It also adds the event to the list of uncommitted events.
     *
     * @param event The event to apply.
     */
    protected open fun applyEvent(event: T) {
        this.version = event.version
        this.updatedAt = event.occurredAt
        uncommittedEvents.add(event)
    }


    /**
     * Commits all uncommitted events by publishing them and then clearing the list.
     *
     * This method should be called after all changes to the aggregate have been made,
     * typically at the end of a use case or service method.
     *
     * @return A Result indicating success or failure of the commit operation.
     */
    fun commit(): Result<Unit> {
        // TODO change return to void
        return runCatching {
            publisher.publishAll(uncommittedEvents)
            clearUncommittedEvents()
        }
    }


    /**
     * Returns a list of all uncommitted events.
     *
     * Uncommitted events are events that have been applied to the aggregate but not yet persisted or published.
     *
     * @return A list of uncommitted events.
     */
    fun getUncommittedEvents(): List<T> = uncommittedEvents.toList()


    /**
     * Clears the list of uncommitted events.
     *
     * This method should be called after the events have been successfully persisted or published.
     */
    fun clearUncommittedEvents() {
        uncommittedEvents.clear()
    }
}

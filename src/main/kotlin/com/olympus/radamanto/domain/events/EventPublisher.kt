package com.olympus.radamanto.domain.events

/**
 * Interface for publishing domain events.
 */
interface EventPublisher {
    /**
     * Publishes a single domain event.
     *
     * @param event The domain event to be published.
     * @return A Result indicating success or failure of the publish operation.
     */
    fun publish(event: DomainEvent): Result<Unit>


    /**
     * Publishes a collection of domain events.
     *
     * @param events The collection of domain events to be published.
     * @return A Result indicating success or failure of the publish operation for all events.
     */
    fun publishAll(events: Collection<DomainEvent>): Result<Unit>
}

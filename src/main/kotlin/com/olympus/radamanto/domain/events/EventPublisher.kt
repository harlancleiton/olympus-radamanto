package com.olympus.radamanto.domain.events

/**
 * Interface for publishing domain events.
 * This interface is used by domain aggregates to publish events
 * without depending on the specific implementation of the event publishing mechanism.
 */
interface EventPublisher {

    /**
     * Publishes a single domain event.
     *
     * @param event The domain event to be published.
     */
    fun publish(event: DomainEvent)


    /**
     * Publishes a collection of domain events.
     *
     * @param events The collection of domain events to be published.
     */
    fun publishAll(events: Collection<DomainEvent>)
}

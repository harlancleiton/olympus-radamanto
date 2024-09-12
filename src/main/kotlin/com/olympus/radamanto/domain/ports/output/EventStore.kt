package com.olympus.radamanto.domain.ports.output

import com.olympus.radamanto.domain.events.DomainEvent
import com.olympus.radamanto.domain.valueobjects.EntityId

/**
 * Interface for storing domain events.
 *
 * An EventStore is responsible for persisting events that represent changes in the state of aggregates
 * and for retrieving historical events to reconstruct the current state of aggregates.
 */
interface EventStore {

    /**
     * Persists a list of events for a specific aggregate.
     *
     * @param aggregateId The ID of the aggregate to which the events belong.
     * @param events The list of events to be persisted.
     * @param expectedVersion The expected version of the aggregate before applying the new events.
     *                        Used to ensure optimistic concurrency control.
     * @return A Result containing Unit if the operation was successful, or an error if the operation failed.
     */
    fun saveEvents(aggregateId: EntityId, events: List<DomainEvent>, expectedVersion: Long): Result<Unit>


    /**
     * Retrieves the list of historical events for a specific aggregate.
     *
     * @param aggregateId The ID of the aggregate whose events need to be retrieved.
     * @return A Result containing a list of all historical events for the aggregate, ordered by version
     *         if the operation was successful, or an error if the operation failed.
     */
    fun getEvents(aggregateId: EntityId): Result<List<DomainEvent>>
}

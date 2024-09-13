package com.olympus.radamanto.domain.events

import com.olympus.radamanto.domain.valueobjects.EntityId
import java.time.Instant

/**
 * Factory responsible for creating UserEvent instances.
 * This object provides methods to create various types of user-related events
 * in a consistent manner, ensuring all necessary fields are properly set.
 */
object UserEventFactory {

    /**
     * Creates a UserCreatedEvent.
     *
     * @param aggregateId The ID of the user aggregate.
     * @param username The username of the newly created user.
     * @param email The email address of the newly created user.
     * @return A new instance of UserEvent.UserCreated.
     */
    fun createUserCreatedEvent(
        aggregateId: EntityId,
        username: String,
        email: String,
        password: String
    ): UserEvent.UserCreated {
        return UserEvent.UserCreated(
            id = EntityId.generate(),
            aggregateId = aggregateId,
            username = username,
            email = email,
            password = password,
            occurredAt = Instant.now(),
            version = 1
        )
    }


    /**
     * Creates an EmailChangedEvent.
     *
     * @param aggregateId The ID of the user aggregate.
     * @param newEmail The new email address of the user.
     * @param version The version of the event.
     * @return A new instance of UserEvent.EmailChanged.
     */
    fun createEmailChangedEvent(
        aggregateId: EntityId,
        newEmail: String,
        version: Long
    ): UserEvent.EmailChanged {
        return UserEvent.EmailChanged(
            id = EntityId.generate(),
            aggregateId = aggregateId,
            newEmail = newEmail,
            occurredAt = Instant.now(),
            version = version
        )
    }


    /**
     * Creates an UsernameChangedEvent.
     *
     * @param aggregateId The ID of the user aggregate.
     * @param newUsername The new username of the user.
     * @param version The version of the event.
     * @return A new instance of UserEvent.UsernameChanged.
     */
    fun createUsernameChangedEvent(
        aggregateId: EntityId,
        newUsername: String,
        version: Long
    ): UserEvent.UsernameChanged {
        return UserEvent.UsernameChanged(
            id = EntityId.generate(),
            aggregateId = aggregateId,
            newUsername = newUsername,
            occurredAt = Instant.now(),
            version = version
        )
    }


    /**
     * Creates a UserDisabledEvent.
     *
     * @param aggregateId The ID of the user aggregate.
     * @param reason The reason for disabling the user.
     * @param version The version of the event.
     * @return A new instance of UserEvent.UserDisabled.
     */
    fun createUserDisabledEvent(
        aggregateId: EntityId,
        reason: String,
        version: Long
    ): UserEvent.UserDisabled {
        return UserEvent.UserDisabled(
            id = EntityId.generate(),
            aggregateId = aggregateId,
            reason = reason,
            occurredAt = Instant.now(),
            version = version
        )
    }


    /**
     * Creates a UserEnabledEvent.
     *
     * @param aggregateId The ID of the user aggregate.
     * @param version The version of the event.
     * @return A new instance of UserEvent.UserEnabled.
     */
    fun createUserEnabledEvent(
        aggregateId: EntityId,
        version: Long
    ): UserEvent.UserEnabled {
        return UserEvent.UserEnabled(
            id = EntityId.generate(),
            aggregateId = aggregateId,
            occurredAt = Instant.now(),
            version = version
        )
    }
}

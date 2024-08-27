package com.olympus.radamanto.domain.events

import com.olympus.radamanto.domain.valueobjects.EntityId
import java.time.Instant

/**
 * Represents events related to user operations in the system.
 * This sealed class contains all possible user-related events.
 */
sealed class UserEvent : DomainEvent {

    /**
     * Represents the event of a new user being created in the system.
     *
     * @property id The unique identifier of the event.
     * @property aggregateId The identifier of the user aggregate.
     * @property username The username of the newly created user.
     * @property email The email address of the newly created user.
     * @property occurredAt The timestamp when the user was created.
     * @property version The version of the user aggregate after creation (typically 1).
     */
    data class UserCreated(
        override val id: EntityId,
        override val aggregateId: EntityId,
        val username: String,
        val email: String,
        override val occurredAt: Instant,
        override val version: Long
    ) : UserEvent() {
        override val name: String = "USER_CREATED"
    }


    /**
     * Represents the event of a user's password being changed.
     *
     * @property id The unique identifier of the event.
     * @property aggregateId The identifier of the user aggregate.
     * @property newPasswordHash The new password hash.
     * @property occurredAt The timestamp when the password was changed.
     * @property version The version of the user aggregate after the password change.
     */
    data class PasswordChanged(
        override val id: EntityId,
        override val aggregateId: EntityId,
        val newPasswordHash: String,
        override val occurredAt: Instant,
        override val version: Long
    ) : UserEvent() {
        override val name: String = "PASSWORD_CHANGED"
    }


    /**
     * Represents the event of a user's email being changed.
     *
     * @property id The unique identifier of the event.
     * @property aggregateId The identifier of the user aggregate.
     * @property newEmail The new email address.
     * @property occurredAt The timestamp when the email was changed.
     * @property version The version of the user aggregate after the email change.
     */
    data class EmailChanged(
        override val id: EntityId,
        override val aggregateId: EntityId,
        val newEmail: String,
        override val occurredAt: Instant,
        override val version: Long
    ) : UserEvent() {
        override val name: String = "EMAIL_CHANGED"
    }


    /**
     * Represents the event of a user being disabled in the system.
     *
     * @property id The unique identifier of the event.
     * @property aggregateId The identifier of the user aggregate.
     * @property reason The reason for disabling the user.
     * @property occurredAt The timestamp when the user was disabled.
     * @property version The version of the user aggregate after being disabled.
     */
    data class UserDisabled(
        override val id: EntityId,
        override val aggregateId: EntityId,
        val reason: String,
        override val occurredAt: Instant,
        override val version: Long
    ) : UserEvent() {
        override val name: String = "USER_DISABLED"
    }


    /**
     * Represents the event of a user being enabled in the system.
     *
     * @property id The unique identifier of the event.
     * @property aggregateId The identifier of the user aggregate.
     * @property occurredAt The timestamp when the user was enabled.
     * @property version The version of the user aggregate after being enabled.
     */
    data class UserEnabled(
        override val id: EntityId,
        override val aggregateId: EntityId,
        override val occurredAt: Instant,
        override val version: Long
    ) : UserEvent() {
        override val name: String = "USER_ENABLED"
    }
}

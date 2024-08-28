package com.olympus.radamanto.domain.aggregates

import com.olympus.radamanto.domain.events.UserEvent
import com.olympus.radamanto.domain.valueobjects.EntityId
import java.time.Instant

/**
 * Represents a User aggregate in the system.
 * This class encapsulates all the business logic and state related to a user.
 *
 * @property username The username of the user.
 * @property email The email address of the user.
 * @property isEnabled Indicates whether the user account is enabled or disabled.
 */
class User private constructor(
    id: EntityId,
    var username: String,
    var email: String,
    var isEnabled: Boolean
) : AggregateRoot<UserEvent>(id) {

    /**
     * Changes the user's email address.
     *
     * @param newEmail The new email address.
     * @return A Result indicating success or failure of the operation.
     */
    fun changeEmail(newEmail: String): Result<Unit> {
        return runCatching {
            val event = UserEvent.EmailChanged(
                id = EntityId.generate(),
                aggregateId = this.id,
                newEmail = newEmail,
                occurredAt = Instant.now(),
                version = this.version + 1
            )
            applyEvent(event)
        }
    }


    /**
     * Disables the user account.
     *
     * @param reason The reason for disabling the account.
     * @return A Result indicating success or failure of the operation.
     */
    fun disable(reason: String): Result<Unit> {
        return runCatching {
            if (!isEnabled) return@runCatching
            val event = UserEvent.UserDisabled(
                id = EntityId.generate(),
                aggregateId = this.id,
                reason = reason,
                occurredAt = Instant.now(),
                version = this.version + 1
            )
            applyEvent(event)
        }
    }


    /**
     * Enables the user account.
     *
     * @return A Result indicating success or failure of the operation.
     */
    fun enable(): Result<Unit> {
        return runCatching {
            if (isEnabled) return@runCatching
            val event = UserEvent.UserEnabled(
                id = EntityId.generate(),
                aggregateId = this.id,
                occurredAt = Instant.now(),
                version = this.version + 1
            )
            applyEvent(event)
        }
    }


    /**
     * Applies an event to the aggregate, updating its state.
     *
     * @param event The event to apply.
     */
    override fun applyEvent(event: UserEvent) {
        when (event) {
            is UserEvent.UserCreated -> {
                this.username = event.username
                this.email = event.email
            }


            is UserEvent.EmailChanged -> {
                this.email = event.newEmail
            }


            is UserEvent.UserDisabled -> {
                this.isEnabled = false
            }


            is UserEvent.UserEnabled -> {
                this.isEnabled = true
            }
        }
        super.applyEvent(event)
    }

    companion object {
        /**
         * Creates a User instance from a UserCreated event.
         * This method should only be called by the UserFactory.
         *
         * @param event The UserCreated event.
         * @return A new User instance.
         */
        internal fun createFromEvent(event: UserEvent.UserCreated): User {
            val user = User(
                id = event.aggregateId,
                username = event.username,
                email = event.email,
                isEnabled = true
            )
            user.applyEvent(event)
            return user
        }
    }
}

package com.olympus.radamanto.domain.factories

import com.olympus.radamanto.domain.aggregates.User
import com.olympus.radamanto.domain.events.UserEvent
import com.olympus.radamanto.domain.valueobjects.EntityId
import java.time.Instant

/**
 * Factory for creating and reconstituting User aggregates.
 */
object UserFactory {
    /**
     * Creates a new User aggregate.
     *
     * @param username The username for the new user.
     * @param email The email address for the new user.
     * @param passwordHash The hashed password for the new user.
     * @return A Result containing the new User instance if successful, or an error if creation fails.
     */
    fun create(username: String, email: String, passwordHash: String): Result<User> {
        return runCatching {
            val event = UserEvent.UserCreated(
                id = EntityId.generate(),
                aggregateId = EntityId.generate(),
                username = username,
                email = email,
                occurredAt = Instant.now(),
                version = 1
            )
            User.createFromEvent(event)
        }
    }


    /**
     * Reconstitutes a User aggregate from a list of events.
     *
     * @param events The list of UserEvents to apply.
     * @return A Result containing the reconstituted User instance if successful, or an error if reconstitution fails.
     * @throws IllegalArgumentException if the event list is empty or doesn't start with a UserCreated event.
     */
    fun reconstituteFromEvents(events: List<UserEvent>): Result<User> {
        return runCatching {
            require(events.isNotEmpty()) { "Cannot reconstitute User from an empty event list" }
            val firstEvent = events.first() as? UserEvent.UserCreated
                ?: throw IllegalArgumentException("First event must be UserCreated")

            val user = User.createFromEvent(firstEvent)
            events.drop(1).forEach { event ->
                when (event) {
                    is UserEvent.EmailChanged -> user.changeEmail(event.newEmail)
                    is UserEvent.PasswordChanged -> user.changePassword(event.newPasswordHash)
                    is UserEvent.UserDisabled -> user.disable("Reconstituted from event")
                    is UserEvent.UserEnabled -> user.enable()
                    is UserEvent.UserCreated -> throw IllegalArgumentException("Duplicate UserCreated event")
                }
            }
            user
        }
    }
}

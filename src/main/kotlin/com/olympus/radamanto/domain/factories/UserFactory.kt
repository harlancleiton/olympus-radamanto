package com.olympus.radamanto.domain.factories

import com.olympus.radamanto.domain.aggregates.User
import com.olympus.radamanto.domain.events.EventPublisher
import com.olympus.radamanto.domain.events.UserEvent
import com.olympus.radamanto.domain.valueobjects.*
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
     * @return A Result containing the new User instance if successful, or an error if creation fails.
     */
    fun create(username: String, email: String, publisher: EventPublisher): Result<User> {
        val usernameResult = Username.create(username)
        val emailResult = Email.create(email)

        return when {
            usernameResult.isFailure -> Result.failure(usernameResult.exceptionOrNull()!!)
            emailResult.isFailure -> Result.failure(emailResult.exceptionOrNull()!!)


            else -> {
                val event = UserEvent.UserCreated(
                    id = EntityId.generate(),
                    aggregateId = EntityId.generate(),
                    username = usernameResult.getOrThrow().value,
                    email = emailResult.getOrThrow().value,
                    occurredAt = Instant.now(),
                    version = 1
                )
                Result.success(User.createFromEvent(event, publisher))
            }
        }
    }


    /**
     * Reconstitutes a User aggregate from a list of events.
     *
     * @param events The list of UserEvents to apply.
     * @return A Result containing the reconstituted User instance if successful, or an error if reconstitution fails.
     * @throws IllegalArgumentException if the event list is empty or doesn't start with a UserCreated event.
     */
    fun reconstituteFromEvents(events: List<UserEvent>, publisher: EventPublisher): Result<User> {
        return runCatching {
            require(events.isNotEmpty()) { "Cannot reconstitute User from an empty event list" }
            val firstEvent = events.first() as? UserEvent.UserCreated
                ?: throw IllegalArgumentException("First event must be UserCreated")

            val user = User.createFromEvent(firstEvent, publisher)
            events.drop(1).forEach { event ->
                when (event) {
                    is UserEvent.EmailChanged -> user.changeEmail(Email.create(event.newEmail).getOrThrow())
                    is UserEvent.UsernameChanged -> user.changeUsername(Username.create(event.newUsername).getOrThrow())
                    is UserEvent.UserDisabled -> user.disable(event.reason)
                    is UserEvent.UserEnabled -> user.enable()
                    is UserEvent.UserCreated -> throw IllegalArgumentException("Duplicate UserCreated event")
                }
            }
            user
        }
    }
}

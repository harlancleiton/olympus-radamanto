package com.olympus.radamanto.application.commands

import com.olympus.radamanto.application.ports.output.UserCommandRepository
import com.olympus.radamanto.domain.events.EventPublisher
import com.olympus.radamanto.domain.factories.UserFactory
import com.olympus.radamanto.domain.valueobjects.EntityId
import com.olympus.radamanto.utils.flatMap

/**
 * Handler for processing CreateUserCommand.
 */
class CreateUserCommandHandler(
    private val userCommandRepository: UserCommandRepository,
    private val eventPublisher: EventPublisher
) : CommandHandler<CreateUserCommand, EntityId> {


    /**
     * Handles the CreateUserCommand by creating a new user and saving it to the repository.
     *
     * @param command The CreateUserCommand to be processed.
     * @return A Result containing the EntityId of the newly created user if successful, or an error if failed.
     */
    override fun handle(command: CreateUserCommand): Result<EntityId> {
        return UserFactory.create(command.username, command.email, eventPublisher)
            .flatMap { user ->
                userCommandRepository.save(user)
            }
            .flatMap { savedUser ->
                savedUser.commit().map { savedUser.id }
            }
    }
}

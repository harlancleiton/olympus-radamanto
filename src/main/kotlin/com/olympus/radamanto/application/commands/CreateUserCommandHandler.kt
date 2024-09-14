package com.olympus.radamanto.application.commands

import com.olympus.radamanto.application.ports.input.QueryBus
import com.olympus.radamanto.application.ports.output.UserCommandRepository
import com.olympus.radamanto.application.queries.CheckEmailExistenceQuery
import com.olympus.radamanto.application.queries.CheckUsernameExistenceQuery
import com.olympus.radamanto.domain.events.EventPublisher
import com.olympus.radamanto.domain.factories.UserFactory
import com.olympus.radamanto.domain.valueobjects.EntityId
import com.olympus.radamanto.utils.flatMap

/**
 * Handler for processing CreateUserCommand.
 */
class CreateUserCommandHandler(
    private val queryBus: QueryBus,
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
        val emailExists = queryBus.dispatch(CheckEmailExistenceQuery(command.email))
        if (emailExists.isSuccess && emailExists.getOrThrow()) {
            return Result.failure(IllegalArgumentException("Email already in use"))
        }

        val usernameExists = queryBus.dispatch(CheckUsernameExistenceQuery(command.username))
        if (usernameExists.isSuccess && usernameExists.getOrThrow()) {
            return Result.failure(IllegalArgumentException("Username already in use"))
        }

        return UserFactory.create(command.username, command.email, command.password, eventPublisher)
            .flatMap { user ->
                userCommandRepository.save(user)
            }
            .flatMap { savedUser ->
                savedUser.commit().map { savedUser.id }
            }

    }
}

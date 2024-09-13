package com.olympus.radamanto.application.commands

import com.olympus.radamanto.application.ports.input.QueryBus
import com.olympus.radamanto.application.ports.output.UserCommandRepository
import com.olympus.radamanto.application.queries.CheckEmailExistenceQuery
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
        return emailExists.flatMap {
            runCatching {
                if (it.getOrThrow()) {
                    throw IllegalArgumentException("Email already in use")
                }

                UserFactory.create(
                    command.username,
                    command.email,
                    command.password,
                    eventPublisher
                )
            }.flatMap {
                userCommandRepository.save(it.getOrThrow())
            }.flatMap { savedUser ->
                savedUser.commit().map { savedUser.id }
            }
        }
    }
}

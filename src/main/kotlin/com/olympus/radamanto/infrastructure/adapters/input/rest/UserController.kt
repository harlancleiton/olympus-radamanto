package com.olympus.radamanto.infrastructure.adapters.input.rest

import com.olympus.radamanto.application.commands.CreateUserCommand
import com.olympus.radamanto.application.ports.input.CommandBus
import com.olympus.radamanto.domain.valueobjects.EntityId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * REST controller for user-related operations.
 *
 * @property commandBus The command bus used to dispatch commands.
 */
@RestController
@RequestMapping("/public/users")
class UserController(private val commandBus: CommandBus) {

    /**
     * Handles the HTTP POST request to create a new user.
     *
     * @param request The CreateUserRequest containing the user details.
     * @return ResponseEntity with the appropriate HTTP status and, if successful, the location of the new resource.
     */
    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<Any> {
        val command = CreateUserCommand(request.username, request.email, request.password)
        val result = commandBus.dispatch(command)

        return when (result.isSuccess) {
            true -> {
                val userId = result.getOrThrow() as EntityId
                ResponseEntity.created(URI.create("/api/users/${userId.value}")).build()
            }


            false -> {
                ResponseEntity.badRequest()
                    .body(ErrorResponse(result.exceptionOrNull()?.message ?: "An error occurred"))
            }
        }
    }
}


/**
 * Data class representing the request body for creating a user.
 *
 * @property username The username of the new user.
 * @property email The email address of the new user.
 * @property password The password of the new user.
 */
data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String
)


/**
 * Data class representing an error response.
 *
 * @property message The error message.
 */
data class ErrorResponse(
    val message: String
)

package com.olympus.radamanto.application.commands


/**
 * Command for creating a new user.
 *
 * @property username The username of the new user.
 * @property email The email address of the new user.
 */
data class CreateUserCommand(
    val username: String,
    val email: String,
    val password: String
) : Command

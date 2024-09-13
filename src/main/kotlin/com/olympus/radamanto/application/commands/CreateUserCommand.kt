package com.olympus.radamanto.application.commands

import com.olympus.radamanto.domain.valueobjects.*


/**
 * Command for creating a new user.
 *
 * @property username The username of the new user.
 * @property email The email address of the new user.
 * @property password The password of the new user.
 */
data class CreateUserCommand(
    val username: Username,
    val email: Email,
    val password: Password
) : Command

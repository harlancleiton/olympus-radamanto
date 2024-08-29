package com.olympus.radamanto.domain.exceptions

/**
 * Represents errors that can occur during Username creation or validation.
 *
 * @property message The error message.
 */
sealed class UsernameException(message: String) : BaseException(message) {
    /**
     * Error for when the username is blank.
     */
    data object BlankUsername : UsernameException("Username cannot be blank")


    /**
     * Error for when the username is too short.
     */
    data class UsernameTooShort(val n: Int) : UsernameException("Username must be at least $n characters long")


    /**
     * Error for when the username is too long.
     */
    data class UsernameTooLong(val n: Int) : UsernameException("Username cannot be longer than $n characters")


    /**
     * Error for when the username contains invalid characters.
     */
    data object InvalidUsernameFormat :
        UsernameException("Username can only contain letters, numbers, dots, underscores, and hyphens")


    override fun isClientError(): Boolean = true
    override fun isServerError(): Boolean = false
}

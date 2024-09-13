package com.olympus.radamanto.domain.exceptions

import com.olympus.radamanto.domain.valueobjects.Password

/**
 * Sealed class representing exceptions related to password validation.
 */
sealed class PasswordException(message: String) : BaseException(message) {

    /**
     * Exception thrown when the password is too short.
     *
     * @property minLength The minimum required length for the password.
     */
    data class TooShort(val actualLength: Int, val minLength: Int = Password.MIN_LENGTH) :
        PasswordException("Password must be at least $minLength characters long. Provided length: $actualLength")


    /**
     * Exception thrown when the password is too long.
     *
     * @property maxLength The maximum allowed length for the password.
     */
    data class TooLong(val actualLength: Int, val maxLength: Int = Password.MAX_LENGTH) :
        PasswordException("Password cannot be longer than $maxLength characters. Provided length: $actualLength")


    /**
     * Exception thrown when the password does not meet the complexity requirements.
     */
    data object InvalidFormat :
        PasswordException("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.") {
        private fun readResolve(): Any = InvalidFormat
    }

    override fun isClientError(): Boolean = true
    override fun isServerError(): Boolean = false
}

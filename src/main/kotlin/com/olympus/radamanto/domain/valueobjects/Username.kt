package com.olympus.radamanto.domain.valueobjects

import com.olympus.radamanto.domain.exceptions.UsernameException


/**
 * Represents a username in the system.
 *
 * @property value The string value of the username.
 */
data class Username(val value: String) {
    
    companion object {
        /**
         * The minimum allowed length for a username.
         */
        private const val MIN_LENGTH = 3


        /**
         * The maximum allowed length for a username.
         */
        private const val MAX_LENGTH = 50


        /**
         * The regular expression pattern for valid username characters.
         */
        private val VALID_USERNAME_PATTERN = Regex("^[a-zA-Z0-9._-]+$")


        /**
         * Creates a new Username instance.
         *
         * @param value The string value for the username.
         * @return A Result containing the new Username if valid, or an error if invalid.
         */
        fun create(value: String): Result<Username> {
            return when {
                value.isBlank() -> Result.failure(UsernameException.BlankUsername)
                value.length < MIN_LENGTH -> Result.failure(UsernameException.UsernameTooShort(MIN_LENGTH))
                value.length > MAX_LENGTH -> Result.failure(UsernameException.UsernameTooLong(MAX_LENGTH))
                !value.matches(VALID_USERNAME_PATTERN) -> Result.failure(UsernameException.InvalidUsernameFormat)
                else -> Result.success(Username(value))
            }
        }
    }

    override fun toString(): String = value
}
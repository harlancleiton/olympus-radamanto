package com.olympus.radamanto.domain.valueobjects

import com.olympus.radamanto.domain.exceptions.EmailException

/**
 * Represents an email address in the system.
 *
 * @property value The string value of the email address.
 */
data class Email(val value: String) {

    companion object {
        private val VALID_EMAIL_PATTERN = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")


        /**
         * Creates a new Email instance.
         *
         * @param value The string value for the email address.
         * @return A Result containing the new Email if valid, or an error if invalid.
         */
        fun create(value: String): Result<Email> {
            return when {
                value.isBlank() -> Result.failure(EmailException.BlankEmail)
                !value.matches(VALID_EMAIL_PATTERN) -> Result.failure(EmailException.InvalidEmailFormat)
                else -> Result.success(Email(value))
            }
        }
    }

    override fun toString(): String = value
}
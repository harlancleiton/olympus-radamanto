// EmailError.kt
package com.olympus.radamanto.domain.exceptions

/**
 * Represents errors that can occur during Email creation or validation.
 *
 * * @property message The error message.
 */
sealed class EmailException(message: String) : BaseException(message) {
    /**
     * Error for when the email is blank.
     */
    data object BlankEmail : EmailException("Email cannot be blank")


    /**
     * Error for when the email format is invalid.
     */
    data object InvalidEmailFormat : EmailException("Invalid email format")


    override fun isClientError(): Boolean = true
    override fun isServerError(): Boolean = false
}

package com.olympus.radamanto.domain.valueobjects

import com.olympus.radamanto.domain.exceptions.PasswordException

/**
 * Value object representing a Password in the system.
 *
 * @property value The plain text password.
 */
data class Password internal constructor(val value: String) {

    companion object {
        const val MIN_LENGTH = 8
        const val MAX_LENGTH = 64
        private val HAS_UPPERCASE = Regex(".*[A-Z].*")
        private val HAS_LOWERCASE = Regex(".*[a-z].*")
        private val HAS_DIGIT = Regex(".*\\d.*")
        private val HAS_SPECIAL_CHAR = Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")


        /**
         * Creates a new Password instance after validating the provided plain text password.
         *
         * @param plainPassword The plain text password to be validated.
         * @return A Result containing the Password instance if validation is successful, or a PasswordException if validation fails.
         */
        fun create(plainPassword: String): Result<Password> {
            return when {
                plainPassword.length < MIN_LENGTH -> Result.failure(PasswordException.TooShort(plainPassword.length))
                plainPassword.length > MAX_LENGTH -> Result.failure(PasswordException.TooLong(plainPassword.length))


                !plainPassword.matches(HAS_UPPERCASE) ||
                        !plainPassword.matches(HAS_LOWERCASE) ||
                        !plainPassword.matches(HAS_DIGIT) ||
                        !plainPassword.matches(HAS_SPECIAL_CHAR) -> {
                    Result.failure(PasswordException.InvalidFormat)
                }


                else -> Result.success(Password(plainPassword))
            }
        }
    }


    /**
     * Returns a string representation of the Password object.
     * Note: The actual password is not exposed in the string representation for security reasons.
     *
     * @return A string representing the Password object.
     */
    override fun toString(): String {
        return "Password(********)"
    }
}

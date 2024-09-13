package com.olympus.radamanto.domain.exceptions

/**
 * Represents exceptions related to EntityId operations in the Radamanto system.
 * This sealed class extends BaseException and provides specific exception types for EntityId issues.
 *
 * @property message A descriptive message for the exception.
 */
sealed class EntityIdException(message: String) : BaseException(message) {

    /**
     * Represents an exception for an invalid UUID format.
     *
     * @property value The string value that failed to parse as a valid UUID.
     */
    data class InvalidUUID(val value: String) : EntityIdException("$value isn't a valid uuid")


    /**
     * Indicates whether this exception represents a client error.
     * For EntityIdException, this always returns false as it's considered a server-side issue.
     *
     * @return false, indicating that this is not a client error.
     */
    override fun isClientError() = false


    /**
     * Indicates whether this exception represents a server error.
     * For EntityIdException, this always returns true as it's considered a server-side issue.
     *
     * @return true, indicating that this is a server error.
     */
    override fun isServerError() = true
}

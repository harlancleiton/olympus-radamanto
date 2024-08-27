package com.olympus.radamanto.domain.valueobjects

import com.olympus.radamanto.domain.exceptions.EntityIdException
import java.util.*

/**
 * Represents a unique identifier for entities in the system.
 * This class wraps a UUID and provides methods for creating and comparing EntityIds.
 *
 * @property value The underlying UUID that this EntityId represents.
 */
class EntityId private constructor(val value: UUID) {
    companion object {
        /**
         * Generates a new EntityId with a random UUID.
         *
         * @return A new EntityId instance.
         */
        fun generate(): EntityId = EntityId(UUID.randomUUID())


        /**
         * Creates an EntityId from an existing UUID.
         *
         * @param value The UUID to create the EntityId from.
         * @return An EntityId instance representing the given UUID.
         */
        fun from(value: UUID): EntityId = EntityId(value)


        /**
         * Attempts to create an EntityId from a string representation of a UUID.
         *
         * @param value The string to parse as a UUID.
         * @return A Result containing the EntityId if successful, or a failure with EntityIdException.InvalidUUID if parsing fails.
         */
        fun from(value: String): Result<EntityId> {
            val result = runCatching { UUID.fromString(value) }
            return when (result.isSuccess) {
                true -> result.map { EntityId(it) }
                false -> Result.failure(EntityIdException.InvalidUUID(value))
            }
        }
    }


    /**
     * Compares this EntityId with another object for equality.
     * EntityIds are considered equal if they wrap the same UUID value.
     *
     * @param other The object to compare with this EntityId.
     * @return true if the other object is an EntityId with the same UUID value, false otherwise.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EntityId) return false
        return value == other.value
    }


    /**
     * Generates a hash code for this EntityId.
     * The hash code is based on the underlying UUID's hash code.
     *
     * @return The hash code value for this EntityId.
     */
    override fun hashCode(): Int = value.hashCode()


    /**
     * Returns a string representation of this EntityId.
     * The string representation is the same as the underlying UUID's string representation.
     *
     * @return A string representation of this EntityId.
     */
    override fun toString(): String = value.toString()
}

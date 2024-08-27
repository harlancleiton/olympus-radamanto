package com.olympus.radamanto.domain.entities

import com.olympus.radamanto.domain.valueobjects.EntityId
import java.time.Instant

/**
 * Abstract base class for all entities in the domain.
 * Provides common properties that all entities should have.
 */
abstract class BaseEntity(val id: EntityId = EntityId.generate()) {
    
    val createdAt: Instant = Instant.now()
    var updatedAt: Instant = createdAt
        protected set
    var version: Long = 0
        protected set


    /**
     * Determines whether this entity is equal to another object.
     * Two entities are considered equal if they have the same ID.
     *
     * @param other The object to compare with this entity.
     * @return true if the other object is an entity with the same ID, false otherwise.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseEntity) return false
        return id == other.id
    }


    /**
     * Generates a hash code for this entity.
     * The hash code is based solely on the entity's ID.
     *
     * @return The hash code value for this entity.
     */
    override fun hashCode(): Int {
        return id.hashCode()
    }


    /**
     * Returns a string representation of this entity.
     *
     * @return A string that includes the class name and ID of this entity.
     */
    override fun toString(): String {
        return "${this::class.simpleName}(id=$id)"
    }
}

package com.olympus.radamanto.domain.valueobjects

import com.olympus.radamanto.domain.exceptions.EntityIdException
import java.util.*

class EntityId private constructor(val value: UUID) {
    companion object {
        fun generate(): EntityId = EntityId(UUID.randomUUID())

        fun from(value: UUID): EntityId = EntityId(value)

        fun from(value: String): Result<EntityId> {
            val result = runCatching { UUID.fromString(value) }
            return when (result.isSuccess) {
                true -> result.map { EntityId(it) }
                false -> Result.failure(EntityIdException.InvalidUUID(value))
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityId

        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value.toString()
}

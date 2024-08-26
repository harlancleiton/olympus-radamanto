package com.olympus.radamanto.domain.exceptions

sealed class EntityIdException(message: String, cause: Throwable? = null) : BaseException(message, cause) {

    data class InvalidUUID(val value: String) : EntityIdException("$value isn't a valid uuid", null)

    override fun isClientError() = true

    override fun isServerError() = false
}

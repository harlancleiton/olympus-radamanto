package com.olympus.radamanto.domain.exceptions

import com.olympus.radamanto.domain.valueobjects.EntityId
import java.time.Instant

abstract class BaseException(message: String, cause: Throwable? = null) : Exception(message, cause) {

    val id = EntityId.generate()

    val name: String
        get() = this.javaClass.simpleName

    val timestamp = Instant.now()

    abstract fun isClientError(): Boolean

    abstract fun isServerError(): Boolean
}

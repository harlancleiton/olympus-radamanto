package com.olympus.radamanto.domain.exceptions

import com.olympus.radamanto.domain.valueobjects.EntityId
import java.time.Instant

/**
 * Base class for all custom exceptions in the Radamanto system.
 * This abstract class provides common properties and methods for exception handling and categorization.
 *
 * @property message A descriptive message for the exception.
 * @property cause The underlying cause of the exception, if any.
 */
abstract class BaseException(message: String, cause: Throwable? = null) : Exception(message, cause) {

    /**
     * A unique identifier for this exception instance.
     */
    val id: EntityId = EntityId.generate()


    /**
     * The simple name of the exception class.
     * This property returns the class name without the package.
     */
    val name: String
        get() = this.javaClass.simpleName


    /**
     * The timestamp when this exception was created.
     */
    val timestamp: Instant = Instant.now()


    /**
     * Determines if this exception represents a client error.
     *
     * @return true if the exception is due to a client error, false otherwise.
     */
    abstract fun isClientError(): Boolean


    /**
     * Determines if this exception represents a server error.
     *
     * @return true if the exception is due to a server error, false otherwise.
     */
    abstract fun isServerError(): Boolean
}

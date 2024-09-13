package com.olympus.radamanto.infrastructure.adapters.input

import com.olympus.radamanto.application.ports.input.QueryBus
import com.olympus.radamanto.application.queries.Query
import com.olympus.radamanto.application.queries.QueryHandler

@Suppress("UNCHECKED_CAST")
class SimpleQueryBus(
    private val handlers: Map<Class<out Query<*>>, QueryHandler<*, *>>
) : QueryBus {


    override fun <R> dispatch(query: Query<R>): Result<R> {
        val handler = handlers[query::class.java] as? QueryHandler<Query<R>, R>
            ?: return Result.failure(IllegalArgumentException("No handler found for ${query::class.simpleName}"))

        return runCatching { handler.handle(query) }
    }
}

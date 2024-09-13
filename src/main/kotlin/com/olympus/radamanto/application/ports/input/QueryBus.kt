package com.olympus.radamanto.application.ports.input

import com.olympus.radamanto.application.queries.Query

interface QueryBus {
    fun <R> dispatch(query: Query<R>): Result<R>
}

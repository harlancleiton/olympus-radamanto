package com.olympus.radamanto.application.queries

interface QueryHandler<Q : Query<R>, R> {
    fun handle(query: Q): R
}

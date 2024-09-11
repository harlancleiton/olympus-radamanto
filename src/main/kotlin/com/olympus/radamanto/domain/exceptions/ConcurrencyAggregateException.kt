package com.olympus.radamanto.domain.exceptions

import com.olympus.radamanto.domain.valueobjects.EntityId

class ConcurrencyAggregateException(aggregateId: EntityId) :
    BaseException("Unexpected version for aggregate ${aggregateId.value}") {

    override fun isClientError(): Boolean = false

    override fun isServerError(): Boolean = true
}

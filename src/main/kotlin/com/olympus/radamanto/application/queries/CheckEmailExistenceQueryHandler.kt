package com.olympus.radamanto.application.queries

import com.olympus.radamanto.application.ports.output.UserQueryRepository

class CheckEmailExistenceQueryHandler(
    private val userQueryRepository: UserQueryRepository
) :
    QueryHandler<CheckEmailExistenceQuery, Result<Boolean>> {


    override fun handle(query: CheckEmailExistenceQuery): Result<Boolean> {
        return userQueryRepository.existsByEmail(query.email)
    }
}

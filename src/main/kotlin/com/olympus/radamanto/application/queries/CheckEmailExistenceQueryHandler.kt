package com.olympus.radamanto.application.queries

import com.olympus.radamanto.application.ports.output.UserQueryRepository

class CheckEmailExistenceQueryHandler(
    private val userQueryRepository: UserQueryRepository
) :
    QueryHandler<CheckEmailExistenceQuery, Boolean> {


    override fun handle(query: CheckEmailExistenceQuery): Boolean {
        return userQueryRepository.existsByEmail(query.email).getOrThrow()
    }
}

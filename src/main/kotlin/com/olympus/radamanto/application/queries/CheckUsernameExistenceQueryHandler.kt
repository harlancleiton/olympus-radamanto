package com.olympus.radamanto.application.queries

import com.olympus.radamanto.application.ports.output.UserQueryRepository

class CheckUsernameExistenceQueryHandler(
    private val userQueryRepository: UserQueryRepository
) :
    QueryHandler<CheckUsernameExistenceQuery, Boolean> {


    override fun handle(query: CheckUsernameExistenceQuery): Boolean {
        return userQueryRepository.existsByUsername(query.username).getOrThrow()
    }
}

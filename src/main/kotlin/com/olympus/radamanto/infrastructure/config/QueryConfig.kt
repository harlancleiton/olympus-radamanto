package com.olympus.radamanto.infrastructure.config

import com.olympus.radamanto.application.ports.input.QueryBus
import com.olympus.radamanto.application.ports.output.UserQueryRepository
import com.olympus.radamanto.application.queries.*
import com.olympus.radamanto.infrastructure.adapters.input.SimpleQueryBus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryConfig {

    @Bean
    fun queryBus(
        checkEmailExistenceQueryHandler: CheckEmailExistenceQueryHandler,
        checkUsernameExistenceQueryHandler: CheckUsernameExistenceQueryHandler
    ): QueryBus {
        val handlers: Map<Class<out Query<*>>, QueryHandler<*, *>> = mapOf(
            CheckEmailExistenceQuery::class.java to checkEmailExistenceQueryHandler,
            CheckUsernameExistenceQuery::class.java to checkUsernameExistenceQueryHandler
        )
        return SimpleQueryBus(handlers)
    }


    @Bean
    fun checkEmailExistenceQueryHandler(userQueryRepository: UserQueryRepository): CheckEmailExistenceQueryHandler {
        return CheckEmailExistenceQueryHandler(userQueryRepository)
    }


    @Bean
    fun checkUsernameExistenceQueryHandler(userQueryRepository: UserQueryRepository): CheckUsernameExistenceQueryHandler {
        return CheckUsernameExistenceQueryHandler(userQueryRepository)
    }
}

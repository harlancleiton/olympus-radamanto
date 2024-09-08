package com.olympus.radamanto.infrastructure.config

import com.olympus.radamanto.application.commands.*
import com.olympus.radamanto.application.ports.input.CommandBus
import com.olympus.radamanto.application.ports.output.UserCommandRepository
import com.olympus.radamanto.domain.events.EventPublisher
import com.olympus.radamanto.infrastructure.adapters.input.CommandBusImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

@Configuration
class CommandConfig {

    @Bean
    @Suppress("UNCHECKED_CAST")
    fun commandBus(handlers: List<CommandHandler<*, *>>): CommandBus {
        val handlerMap: Map<KClass<out Command>, CommandHandler<*, *>> = handlers.associateBy { handler ->
            val commandType = (handler.javaClass.genericInterfaces
                .filterIsInstance<ParameterizedType>()
                .first { it.rawType == CommandHandler::class.java }
                .actualTypeArguments[0] as Class<*>).kotlin

            commandType as KClass<out Command>
        }
        
        return CommandBusImpl(handlerMap)
    }


    @Bean
    fun createUserCommandHandler(
        userCommandRepository: UserCommandRepository,
        eventPublisher: EventPublisher
    ): CommandHandler<*, *> {
        return CreateUserCommandHandler(userCommandRepository, eventPublisher)
    }
}
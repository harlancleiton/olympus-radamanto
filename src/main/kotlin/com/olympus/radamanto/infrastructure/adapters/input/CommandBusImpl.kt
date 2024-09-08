package com.olympus.radamanto.infrastructure.adapters.input

import com.olympus.radamanto.application.commands.Command
import com.olympus.radamanto.application.commands.CommandHandler
import com.olympus.radamanto.application.ports.input.CommandBus
import kotlin.reflect.KClass

/**
 * Implementation of the CommandBus interface.
 *
 * @property handlers A map of command types to their respective handlers.
 */
@Suppress("UNCHECKED_CAST")
class CommandBusImpl(
    private val handlers: Map<KClass<out Command>, CommandHandler<*, *>>
) : CommandBus {


    /**
     * Dispatches a command to its appropriate handler.
     *
     * @param command The command to be dispatched.
     * @return A Result containing the output of the command handler, or an error if the command couldn't be processed.
     */
    override fun dispatch(command: Command): Result<Any> {
        val handler = handlers[command::class] as? CommandHandler<Command, Any>
            ?: return Result.failure(IllegalArgumentException("No handler found for ${command::class.simpleName}"))

        return handler.handle(command)
    }
}

package com.olympus.radamanto.application.ports.input

import com.olympus.radamanto.application.commands.Command

/**
 * Interface for the command bus, responsible for dispatching commands to their appropriate handlers.
 */
interface CommandBus {
    /**
     * Dispatches a command to its appropriate handler.
     *
     * @param command The command to be dispatched.
     * @return A Result containing the output of the command handler, or an error if the command couldn't be processed.
     */
    fun dispatch(command: Command): Result<Any>
}

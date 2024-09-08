package com.olympus.radamanto.application.commands

/**
 * Base interface for all command handlers in the application.
 * Command handlers are responsible for processing commands and producing results.
 *
 * @param C The type of command this handler can process.
 * @param R The type of result this handler produces.
 */
interface CommandHandler<C : Command, R> {
    
    /**
     * Handles the given command and produces a result.
     *
     * @param command The command to be handled.
     * @return A Result containing either the successful result of type R or an error.
     */
    fun handle(command: C): Result<R>
}

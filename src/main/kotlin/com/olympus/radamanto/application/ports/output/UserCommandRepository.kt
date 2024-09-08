package com.olympus.radamanto.application.ports.output

import com.olympus.radamanto.domain.aggregates.User

/**
 * Repository interface for User command operations.
 */
interface UserCommandRepository {

    /**
     * Saves a User to the repository.
     *
     * @param user The User to be saved.
     * @return A Result containing the saved User if successful, or an error if the operation failed.
     */
    fun save(user: User): Result<User>
}

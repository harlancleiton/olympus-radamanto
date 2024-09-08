package com.olympus.radamanto.infrastructure.external.keycloak

import com.olympus.radamanto.domain.aggregates.User

/**
 * Interface for interacting with the Keycloak authentication and authorization server.
 * This client provides methods to manage users in Keycloak.
 */
interface KeycloakClient {

    /**
     * Creates a new user in Keycloak based on the provided User aggregate.
     *
     * @param user The User aggregate containing the user information to be created in Keycloak.
     * @return A Result indicating the success or failure of the user creation operation.
     *         Returns Result.success(Unit) if the user was created successfully.
     *         Returns Result.failure with an appropriate exception if the operation failed.
     */
    fun createUser(user: User): Result<Unit>
}

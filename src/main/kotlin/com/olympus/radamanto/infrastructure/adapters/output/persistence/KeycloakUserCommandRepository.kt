package com.olympus.radamanto.infrastructure.adapters.output.persistence

import com.olympus.radamanto.application.ports.output.UserCommandRepository
import com.olympus.radamanto.domain.aggregates.User
import com.olympus.radamanto.infrastructure.external.keycloak.KeycloakClient
import org.springframework.stereotype.Component

@Component
class KeycloakUserCommandRepository(private val keycloakClient: KeycloakClient) : UserCommandRepository {

    override fun save(user: User): Result<User> {
        return keycloakClient.createUser(user).map { user }
    }
}

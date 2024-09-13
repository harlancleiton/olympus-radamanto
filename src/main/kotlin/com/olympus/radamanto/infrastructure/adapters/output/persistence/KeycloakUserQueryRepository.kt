package com.olympus.radamanto.infrastructure.adapters.output.persistence

import com.olympus.radamanto.application.ports.output.UserQueryRepository
import com.olympus.radamanto.domain.valueobjects.Email
import com.olympus.radamanto.infrastructure.external.keycloak.KeycloakClient
import org.springframework.stereotype.Component

@Component
class KeycloakUserQueryRepository(private val keycloakClient: KeycloakClient) : UserQueryRepository {
    override fun existsByEmail(email: Email): Result<Boolean> {
        return keycloakClient.existsByEmail(email)
    }
}
package com.olympus.radamanto.infrastructure.external.keycloak

import com.olympus.radamanto.domain.aggregates.User
import com.olympus.radamanto.domain.exceptions.PasswordException
import com.olympus.radamanto.domain.valueobjects.Email
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.ws.rs.core.Response

@Component
class KeycloakClientImpl(
    private val keycloak: Keycloak,
) : KeycloakClient {
    private val logger = LoggerFactory.getLogger(KeycloakClientImpl::class.java)


    @Value("\${keycloak.realm}")
    private lateinit var realm: String

    override fun createUser(user: User): Result<Unit> {
        logger.debug("Creating the user ${user.username} – ${user.email}")
        if (user.password == null) return Result.failure(PasswordException.InvalidFormat)
        return runCatching {
            val credentials = listOf(preparePasswordRepresentation(user.password!!.value))
            val ur = UserRepresentation().apply {
                this.id = user.id.value.toString()
                this.username = user.username.value
                this.email = user.email.value
                this.credentials = credentials
                this.isEmailVerified = false
                isEnabled = true
            }
            val response = keycloak.realm(realm).users().create(ur)
            if (response.status != Response.Status.CREATED.statusCode) {
                // TODO handle error appropriately
                throw Exception("Failed to create user in Keycloak. Status: ${response.status}")
            }
        }
    }

    override fun existsByEmail(email: Email): Result<Boolean> {
        return runCatching {
            val users = keycloak.realm(realm).users().searchByEmail(email.value, true)
            users.isNotEmpty()
        }
    }

    private fun preparePasswordRepresentation(
        password: String
    ): CredentialRepresentation {
        val credentials = CredentialRepresentation()
        credentials.isTemporary = false
        credentials.type = CredentialRepresentation.PASSWORD
        credentials.value = password
        return credentials
    }
}

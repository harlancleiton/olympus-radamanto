package com.olympus.radamanto.infrastructure.external.keycloak

/**
 * Data class representing a user in Keycloak.
 */
data class KeycloakUser(
    val id: String,
    val username: String,
    val email: String,
    val isEnabled: Boolean
)
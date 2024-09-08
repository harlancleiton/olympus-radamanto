package com.olympus.radamanto.infrastructure.config

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig {

    @Value("\${keycloak.auth-server-url}")
    private lateinit var authServerUrl: String


    @Value("\${keycloak.realm}")
    private lateinit var realm: String


    @Value("\${keycloak.resource}")
    private lateinit var clientId: String


    @Value("\${keycloak.credentials.secret}")
    private lateinit var clientSecret: String


    @Bean
    fun keycloak(): Keycloak {
        return KeycloakBuilder.builder()
            .serverUrl(authServerUrl)
            .realm(realm)
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .build()
    }


    @Bean
    fun keycloakRealm(): String = realm
}

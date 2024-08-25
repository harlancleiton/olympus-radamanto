package com.olympus.radamanto.infrastructure.adapters.input.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class AuthController {

    @GetMapping("/public")
    fun publicEndpoint(): String = "This is a public endpoint"

    @GetMapping("/private")
    fun privateEndpoint(principal: Principal): String = "Hello, ${principal.name}! This is a private endpoint"
}

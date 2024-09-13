package com.olympus.radamanto.application.ports.output

import com.olympus.radamanto.domain.valueobjects.Email

interface UserQueryRepository {
    fun existsByEmail(email: Email): Result<Boolean>
}
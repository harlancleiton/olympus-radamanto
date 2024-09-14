package com.olympus.radamanto.application.ports.output

import com.olympus.radamanto.domain.valueobjects.Email
import com.olympus.radamanto.domain.valueobjects.Username

interface UserQueryRepository {
    fun existsByEmail(email: Email): Result<Boolean>
    fun existsByUsername(username: Username): Result<Boolean>
}
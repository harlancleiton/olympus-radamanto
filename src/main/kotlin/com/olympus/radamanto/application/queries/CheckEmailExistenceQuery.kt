package com.olympus.radamanto.application.queries

import com.olympus.radamanto.domain.valueobjects.Email

data class CheckEmailExistenceQuery(val email: Email) : Query<Boolean>

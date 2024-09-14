package com.olympus.radamanto.application.queries

import com.olympus.radamanto.domain.valueobjects.Username

data class CheckUsernameExistenceQuery(val username: Username) : Query<Boolean>

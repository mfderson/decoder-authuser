package com.ead.authuser.services

import com.ead.authuser.enums.RoleType
import com.ead.authuser.models.RoleModel

interface RoleService {

    fun findByType(type: RoleType): RoleModel?
}
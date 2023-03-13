package com.ead.authuser.repositories

import com.ead.authuser.enums.RoleType
import com.ead.authuser.models.RoleModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<RoleModel, UUID> {
    fun findByType(type: RoleType): RoleModel?
}
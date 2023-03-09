package com.ead.authuser.services.impl

import com.ead.authuser.enums.RoleType
import com.ead.authuser.models.RoleModel
import com.ead.authuser.repositories.RoleRepository
import com.ead.authuser.services.RoleService
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(private val roleRepository: RoleRepository): RoleService {

    override fun findByType(type: RoleType) =
        roleRepository.findByType(type)

}
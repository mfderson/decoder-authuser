package com.ead.authuser.models

import com.ead.authuser.enums.RoleType
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "ROLES")
data class RoleModel(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    val type: RoleType = RoleType.ROLE_STUDENT
): GrantedAuthority, Serializable {
    override fun getAuthority() = this.type.toString()
}

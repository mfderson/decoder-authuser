package com.ead.authuser.configs.security

import com.ead.authuser.models.UserModel
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

data class UserDetailsImpl(
    val userId: UUID = UUID.randomUUID(),
    val fullName: String = "",
    @get:JvmName("getUsernameDetails")
    val username: String = "",
    @field:JsonIgnore
    @get:JvmName("getPasswordDetails")
    val password: String = "",
    val email: String = "",
    @get:JvmName("getAuthoritiesDetails")
    val authorities: MutableSet<GrantedAuthority> = mutableSetOf()
): UserDetails {

    fun build(userModel: UserModel): UserDetailsImpl {
        val authorities = userModel.roles.map {
            SimpleGrantedAuthority(it.authority)
        }.toMutableSet<GrantedAuthority>()

        return UserDetailsImpl(
            userModel.id,
            userModel.fullName,
            userModel.username,
            userModel.password,
            userModel.email,
            authorities
        )
    }
    override fun getAuthorities() = this.authorities

    override fun getPassword() = this.password

    override fun getUsername() = this.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
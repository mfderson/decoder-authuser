package com.ead.authuser.configs.security

import com.ead.authuser.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = username?.let {
            userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User not found with username: $username")
        } ?: throw RuntimeException("Username cannot be null")

        return UserDetailsImpl().build(user)
    }
}
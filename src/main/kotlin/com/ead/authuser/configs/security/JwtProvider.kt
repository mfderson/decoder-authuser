package com.ead.authuser.configs.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider {

    @Value("\${ead.auth.jwtSecret}")
    lateinit var jwtSecret: String

    @Value("\${ead.auth.jwtExpirationMs}")
    lateinit var jwtExpirationMs: Number

    fun generateJwt(authentication: Authentication): String {
        val userPrincipal: UserDetails = authentication.principal as UserDetailsImpl

        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs.toInt()))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }
}
package com.ead.authuser.configs.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider {

    companion object {
        val LOGGER: Logger = LogManager.getLogger()
    }

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

    fun getUsernameJwt(token: String): String =
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject

    fun validateJwt(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            LOGGER.error("Invalid JWT signature: ${e.message}", e)
        } catch (e: MalformedJwtException) {
            LOGGER.error("Invalid JWT token: ${e.message}", e)
        } catch (e: ExpiredJwtException) {
            LOGGER.error("Invalid JWT is expired: ${e.message}", e)
        } catch (e: UnsupportedJwtException) {
            LOGGER.error("JWT token is unsupported: ${e.message}", e)
        } catch (e: IllegalArgumentException) {
            LOGGER.error("JWT claims string is empty: ${e.message}", e)
        } catch (e: Exception) {
            LOGGER.error("Unexpected exception in validate JWT: ${e.message}", e)
        }
        return false
    }
}
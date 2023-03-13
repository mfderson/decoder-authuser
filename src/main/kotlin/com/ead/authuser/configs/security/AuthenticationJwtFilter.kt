package com.ead.authuser.configs.security

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationJwtFilter(
    val jwtProvider: JwtProvider,
    val userDetailsServiceImpl: UserDetailsServiceImpl
): OncePerRequestFilter() {

    companion object {
        val LOGGER: Logger = LogManager.getLogger()
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwtStr = getTokenHeader(request)
            jwtStr.let {
                if (jwtProvider.validateJwt(jwtStr)) {
                    val username = jwtProvider.getUsernameJwt(jwtStr)
                    val userDetails = userDetailsServiceImpl.loadUserByUsername(username)
                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (e: Exception) {
            LOGGER.error("Cannot set User Authentication: $e")
        }
        filterChain.doFilter(request, response)
    }

    fun getTokenHeader(request: HttpServletRequest): String {
        val headerAuth = request.getHeader("Authorization")
        return headerAuth?.startsWith("Bearer ").let {
            headerAuth.substring(7, headerAuth.length)
        }
    }


}
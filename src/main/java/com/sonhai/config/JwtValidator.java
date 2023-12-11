package com.sonhai.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

/* Validate the JWT (Json Web Token in incoming Http requests) */
public class JwtValidator extends OncePerRequestFilter {
  /**
   * Filter and get executed for each Http requests
   * Perform JWT validation and sets the authentication.
   **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        /* Get Jwt from Https Header */
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null) {
            /* Remove the title substring from jwt */
            jwt = jwt.substring(7);
            try {
                /*
                    Generates a secret key from the constant JwtConstant.SECRET_KEY
                    using the HMAC SHA algorithm.
                **/
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

                /*
                    Parse the JWT,
                    Verifies its signatures using the key - "key",
                    Extract the claim (payload) from jwt.
                **/
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                /*
                    Get the information and authentication of User from JWT
                **/
                String email = String.valueOf(claims.get("email"));

                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token From JWT Validator");
            }
        }
        /* Allow the requests to proceed the next filter of the chain */
        filterChain.doFilter(request, response);
    }
}

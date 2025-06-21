package com.sanem.share.ong.infra.security;

import com.sanem.share.ong.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository repository;

    public SecurityFilter(JwtUtils jwtUtils, UserRepository repository) {
        this.jwtUtils = jwtUtils;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtils.retrieveToken(request);
        if (token != null) {
            String subject = jwtUtils.validate(token);
            repository.findByEmail(subject).ifPresent(user -> {
                var auth = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }

        filterChain.doFilter(request, response);
    }
}

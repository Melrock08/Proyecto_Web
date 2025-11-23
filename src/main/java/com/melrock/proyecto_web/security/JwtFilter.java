package com.melrock.proyecto_web.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Lógica del filtro JWT
        String path = request.getRequestURI();

        System.out.println("JWTFilter - el path es: " + path);

        if (path.startsWith("/auth/swagger-ui") || path.equals("/auth/swagger-ui.html") || path.equals("/auth/v3/api-docs") || path.startsWith("/auth/v3/api-docs")  || (path.startsWith("/auth/auth")  && !path.startsWith("/auth/auth/renew-token")) ) {
            System.out.println("JwtFilter - Sobrepasado el JWT para el path: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer")){
            String token = authHeader.substring(7);

            if(!this.jwtUtil.validarToken(token)){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String email = this.jwtUtil.extraerEmail(token);
            String rol = this.jwtUtil.extraerRol(token);

            UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(
                email, token, java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol)));
            
            SecurityContextHolder.getContext().setAuthentication(autenticacion);

        }

        filterChain.doFilter(request, response);
        
    }
}

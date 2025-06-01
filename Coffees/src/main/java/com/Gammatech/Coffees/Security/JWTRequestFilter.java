/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Gammatech.Coffees.Utilities.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * 
 * JWTRequestFilter es un filtro que intercepta las solicitudes HTTP para
 * verificar la validez del token JWT y establecer el contexto de seguridad
 * en función de la autenticación del usuario.
 * Este filtro se ejecuta una vez por solicitud y se encarga de extraer el token
 * de la cabecera de autorización, validar el token y cargar los detalles del usuario
 * en el contexto de seguridad de Spring.
 * @author Aaron
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter{
    /**
     * CustomUserDetailsService es un servicio que implementa UserDetailsService
     * para cargar los detalles del usuario desde la base de datos.
     * Es utilizado por Spring Security para autenticar usuarios.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;
    /**
     * JWTUtils es una clase que proporciona utilidades para generar, validar y extraer información de tokens JWT (JSON Web Tokens).
     * Utiliza una clave secreta para firmar y verificar los tokens, y maneja la creación de reclamaciones como el nombre de usuario y el rol del usuario.
     */
    @Autowired
    private JWTUtils jwtUtil;

    /**
     * Constructor for JWTRequestFilter.
     * 
     * @param userDetailsService the service to fetch user details
     * @param jwtUtil the utility class for JWT operations
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }

}

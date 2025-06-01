/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Gammatech.Coffees.Security.CustomUserDetailsService;
import com.Gammatech.Coffees.Security.JWTRequestFilter;

/**
 *
 * * Configuración de seguridad para la aplicación.
 * * Esta clase configura la seguridad web de la aplicación, incluyendo la autenticación
 * * basada en JWT, la gestión de sesiones y los filtros de seguridad.
 * @author Aaron
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    // Filtro para manejar las solicitudes JWT
    @Autowired
    private JWTRequestFilter jwtRequestFilter;
    // Servicio personalizado para obtener detalles del usuario
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configura la cadena de filtros de seguridad.
     * 
     * @param http la configuración de seguridad HTTP
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/auth/**").permitAll()
                        //.requestMatchers("/sneakers/**").hasRole("USER")
                        //.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Proveedor de autenticación DAO.
     * 
     * @param passwordEncoder el codificador de contraseñas
     * @return el proveedor de autenticación DAO configurado
     */

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * Bean para el administrador de autenticación.
     * 
     * @param authConfig la configuración de autenticación
     * @return el administrador de autenticación configurado
     * @throws Exception si ocurre un error durante la configuración
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}

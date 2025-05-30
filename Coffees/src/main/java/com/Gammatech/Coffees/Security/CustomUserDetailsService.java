/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Gammatech.Coffees.Entities.Users;
import com.Gammatech.Coffees.Service.ServiceUsers;

/**
 *
 * CustomUserDetailsService es una implementación de UserDetailsService
 * que se encarga de cargar los detalles del usuario desde la base de datos.
 * Esta clase es utilizada por Spring Security para autenticar usuarios.
 * 
 * @author Aaron
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ServiceUsers serviceUser;
    /**
     * Constructor for CustomUserDetailsService.
     * 
     * @param serviceUser the service to fetch user details
     */
    public CustomUserDetailsService(ServiceUsers serviceUser) {
        this.serviceUser = serviceUser;
    }
    
    /**
     * Carga un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario del usuario a cargar
     * @return UserDetails que contiene la información del usuario
     * @throws UsernameNotFoundException si no se encuentra el usuario
     */

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Users> user = serviceUser.findByUsername(username);
        if (user.isEmpty()) {
            throw  new UsernameNotFoundException("User not found with username: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.get().getUsername())
                .password(user.get().getPassword())
                .roles(user.get().getRole())
                .build();
    }

}

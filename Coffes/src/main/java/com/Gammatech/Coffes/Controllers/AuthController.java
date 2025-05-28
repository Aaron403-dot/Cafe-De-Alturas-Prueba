/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Gammatech.Coffes.Entities.Users;
import com.Gammatech.Coffes.Service.ServiceUsers;


/**
 * Controlador para la autenticación de usuarios.
 * Proporciona el endpoint para el registro de nuevos usuarios.
 * @author Aaron
 */
@RestController("/auth")
public class AuthController {

    private final ServiceUsers serviceUsers;

    public AuthController(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param user Objeto Users con los datos del usuario a registrar
     * @return Optional con el usuario registrado o vacío si hay error
     */
    @PostMapping("/register")
    public Optional<Users> postMethodName(@RequestBody Users user) {
        // if (ServiceUsers.existsByUsername(user.getUsername())) {
        //         throw new IllegalArgumentException("Username already exists");
        // }
        try {
            
            return Optional.of(serviceUsers.save(user));
        } catch (IllegalArgumentException e) {
            // Handle the exception, e.g., return an error response
            System.err.println("Error saving user: " + e.getMessage());
            return Optional.empty();
        }
    }
    


}

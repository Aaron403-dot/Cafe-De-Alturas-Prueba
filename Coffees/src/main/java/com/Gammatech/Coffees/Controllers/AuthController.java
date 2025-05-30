/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Gammatech.Coffees.Entities.Users;
import com.Gammatech.Coffees.Service.ServiceUsers;
import com.Gammatech.Coffees.Utilities.JWTUtils;


/**
 * Controlador para la autenticación de usuarios.
 * Proporciona el endpoint para el registro de nuevos usuarios.
 * @author Aaron
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final ServiceUsers serviceUsers;

    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder encrypter;


    public AuthController(ServiceUsers serviceUsers, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, PasswordEncoder encrypter, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.encrypter = encrypter;
        this.serviceUsers = serviceUsers;
        this.jwtUtils = jwtUtils;
    }
    
    /**
     * Realiza el inicio de sesión de un usuario.
     * Este método recibe un objeto AuthRequest con el nombre de usuario y la contraseña,
     * autentica al usuario y genera un token JWT si las credenciales son válidas.
     * @param authRequest Objeto AuthRequest con el nombre de usuario y la contraseña
     * @return ResponseEntity con el token JWT si las credenciales son válidas,
     * o un mensaje de error si las credenciales son inválidas.
     * @throws BadCredentialsException si las credenciales son inválidas
     * @throws UsernameNotFoundException si el usuario no se encuentra
     * @throws Exception si ocurre algún otro error durante el proceso de autenticación
     * @see AuthRequest
    */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
        


    /**
     * Registra un nuevo usuario en el sistema.
     * Este método recibe un objeto Users con los datos del usuario,
     * verifica si el nombre de usuario ya está en uso, y si no lo está,
     * guarda el usuario en la base de datos con la contraseña encriptada.
     * @param user Objeto Users con los datos del usuario a registrar
     * @return Optional con el usuario registrado o vacío si hay error
     */
    @PostMapping("/register")
    public ResponseEntity<?> postMethodName(@RequestBody Users user) {
        if(serviceUsers.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(409).body("El nombre de usuario ya está en uso");
        } else {
            String encodedPassword = encrypter.encode(user.getPassword());
            user.setPassword(encodedPassword);
            serviceUsers.save(user);
            return ResponseEntity.ok("User successfully registered");           
        }   
    }
    


}

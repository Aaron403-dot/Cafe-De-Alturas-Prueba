/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Controllers;

/**
 *
 * Clase de respuesta para la autenticación de usuarios.
 * Contiene el token JWT generado al iniciar sesión.
 * @author Aaron
 */
public class AuthResponse {

    private String token;
    /**
     * Constructor por defecto.
     */
    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

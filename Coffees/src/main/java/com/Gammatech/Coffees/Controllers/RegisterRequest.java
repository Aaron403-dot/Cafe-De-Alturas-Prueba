/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Controllers;

/**
 * Clase de petición para el registro de usuarios.
 * Contiene los datos necesarios para crear un nuevo usuario.
 * @author Aaron
 */
public class RegisterRequest {

    private String username;
    private String password;
    private String role;

    /**
     * Creates a new instance of RegisterRequest
     */
    public RegisterRequest() {
    }

    /**
     * Creates a new instance of RegisterRequest with the given parameters.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the user
     */
    public RegisterRequest(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return Nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param username Nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña.
     *
     * @return Contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña.
     *
     * @param password Contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return Rol
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param role Rol
     */
    public void setRole(String role) {
        this.role = role;
    }

}

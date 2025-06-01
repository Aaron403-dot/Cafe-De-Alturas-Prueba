/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entidad que representa un usuario del sistema.
 * @author Aaron
 */
@Entity
public class Users {

    @Id
    private String username;

    private String password;

    private String role = "USER"; // Default role

    public Users() {
    }
    /**
     * Constructor para crear un nuevo usuario.
     *
     * @param username Nombre de usuario
     * @param password Contraseña del usuario
     * @param role Rol del usuario (por defecto es "USER")
     */
    public Users(String username, String password, String role) {
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
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
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

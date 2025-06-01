/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Gammatech.Coffees.Entities.Users;
import com.Gammatech.Coffees.Repo.RepoUsers;


/**
 * Servicio para la gestión de usuarios.
 * Proporciona operaciones CRUD y de negocio para la entidad Users.
 * @author Aaron
 */
@Service
public class ServiceUsers {
    /**
     * Repositorio de usuarios para acceder a la base de datos.
     */
    private final RepoUsers repoUsers;
    
    /**
     * Constructor del servicio de usuarios.
     * @param repoUsers Repositorio de usuarios
     */
    public ServiceUsers(RepoUsers repoUsers) {
        this.repoUsers = repoUsers;
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario
     * @return Optional con el usuario encontrado
     */
    public Optional<Users> findByUsername(String username) {
        return repoUsers.findByUsername(username);
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     * @param user Usuario a guardar
     * @return Usuario guardado
     */
    public Users save(Users user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return repoUsers.save(user);
    }

    /**
     * Verifica si existe un usuario por su nombre de usuario.
     * @param username Nombre de usuario
     * @return true si existe, false en caso contrario
     */
    public boolean existsByUsername(String username) {
        return repoUsers.existsByUsername(username);
    }
}

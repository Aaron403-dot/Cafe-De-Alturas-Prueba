package com.Gammatech.Coffees.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gammatech.Coffees.Entities.Users;
/**
 * Repositorio para la entidad Users.
 * @author Aaron
 */
@Repository
public interface RepoUsers extends JpaRepository<Users, String> {
    /**
     * Verifica si existe un usuario por su nombre de usuario.
     * @param username Nombre de usuario
     * @return true si existe, false en caso contrario
     */
    public boolean existsByUsername(String username);
    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario
     * @return Optional con el usuario encontrado
     */
    public Optional<Users> findByUsername(String username);

}
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

    public boolean existsByUsername(String username);

    public Optional<Users> findByUsername(String username);

}
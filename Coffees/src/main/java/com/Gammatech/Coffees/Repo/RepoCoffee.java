/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gammatech.Coffees.Entities.Coffee;

/**
 *
 * Repositorio para la entidad Coffee.
 * Proporciona operaciones CRUD para la entidad Coffee.
 * @author Aaron
 */
@Repository
public interface RepoCoffee extends JpaRepository<Coffee, Long> {

    
}

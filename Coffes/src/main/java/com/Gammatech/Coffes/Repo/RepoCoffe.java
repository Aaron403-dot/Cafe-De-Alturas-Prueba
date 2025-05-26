/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Gammatech.Coffes.Entities.Coffe;

/**
 *
 * @author Usuario
 */
@Repository
public interface RepoCoffe extends CrudRepository<Coffe, Long> {

    // private final Map<Long, Coffe> coffes = new HashMap<>();
    
    // public List<Coffe> findAll() {
    //     return List.copyOf(coffes.values());
    // }

    // public Optional<Coffe> findById(Long id) {
    //     return Optional.ofNullable(coffes.get(id));
    // }

    // public Coffe save(Coffe coffe) {
    //     coffes.put(coffe.getId(), coffe);
    //     return coffe;
    // }

    // public void delete(Long id) {
    //     coffes.remove(id);
    // }

    // public Coffe update(Coffe coffe) {
    //     coffes.put(coffe.getId(), coffe);
    //     return coffe;
    // }
    
}

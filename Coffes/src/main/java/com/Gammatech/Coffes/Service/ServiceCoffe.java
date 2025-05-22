/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Service;

import java.util.EmptyStackException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Gammatech.Coffes.Entities.Coffe;
import com.Gammatech.Coffes.Repo.RepoCoffe;

/**
 *
 * @author Usuario
 */
@Service
public class ServiceCoffe {


    private final RepoCoffe repoCoffe;

    public ServiceCoffe(RepoCoffe repoCoffe) {
        this.repoCoffe = repoCoffe;
    }

    public List<Coffe> getListaCoffe() {
        return repoCoffe.findAll();
    }


    public Coffe getCoffeById(long id) {
        return repoCoffe.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("No se puede obtener el café. ID inválido"));
    }

    public Coffe addCoffe(Coffe coffe) {
        if (coffe == null || 
            coffe.getName() == null || coffe.getName().isEmpty() ||
            coffe.getPrice() <= 0 ||
            coffe.getDescription() == null || coffe.getDescription().isEmpty()) {
            throw new IllegalArgumentException("El café no puede ser nulo o tener campos vacíos");
        }
        if(repoCoffe.findAll() == null || repoCoffe.findAll().isEmpty()) {
            coffe.setId(1l);
            repoCoffe.save(coffe);
        } else {
            coffe.setId(repoCoffe.findAll().get(repoCoffe.findAll().size()-1).getId()+1l);
            repoCoffe.save(coffe);
        }
        return coffe;
    }
    
    public Coffe putCoffe(Coffe coffe) {
        if (coffe == null || 
            coffe.getName() == null || coffe.getName().isEmpty() ||
            coffe.getPrice() <= 0 ||
            coffe.getDescription() == null || coffe.getDescription().isEmpty()) {
            throw new IllegalArgumentException("El café no puede ser nulo o tener campos vacíos");
        }
        
        return repoCoffe.save(coffe);
    }

    public long deleteCoffe(long id) {
        if (!repoCoffe.findById(id).isPresent()) {
            throw new IllegalArgumentException("No se puede eliminar el café. ID inválido");
        }
        if(repoCoffe.findAll().isEmpty() || repoCoffe.findAll() == null) {
            throw new EmptyStackException();
        }

        repoCoffe.delete(id);
        return id;
    }

    public Coffe patchCoffe(long id, Coffe coffe) {
        Coffe nCoffe = repoCoffe.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("No se puede modificar el café. ID inválido"));
        
        if (coffe.getName() != null && !coffe.getName().isEmpty() && !coffe.getName().isBlank() && !nCoffe.getName().equals(coffe.getName())) {
            nCoffe.setName(coffe.getName());
        }
        if (coffe.getDescription() != null && !coffe.getDescription().isEmpty() && !coffe.getDescription().isBlank() && !nCoffe.getDescription().equals(coffe.getDescription())) {
            nCoffe.setDescription(coffe.getDescription());
        }
        if (coffe.getPrice() > 0) {
            nCoffe.setPrice(coffe.getPrice());
        }
        if (coffe.getStock() > 0) {
            nCoffe.setStock(coffe.getStock());
        }
        if (coffe.getImage() != null) {
            nCoffe.setImage(coffe.getImage());
        }
        return repoCoffe.update(nCoffe);
    }
}

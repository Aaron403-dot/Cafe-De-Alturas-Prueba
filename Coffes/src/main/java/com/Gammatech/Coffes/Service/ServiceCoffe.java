/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Service;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Gammatech.Coffes.Entities.Coffe;
import com.Gammatech.Coffes.Repo.RepoCoffe;

import jakarta.transaction.Transactional;

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
        return (List<Coffe>) repoCoffe.findAll();
    }

    @Transactional
    public Page<Coffe> getListaCoffe(int pagina , int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        return repoCoffe.findAll(pageable);
    }


    public Optional<Coffe> getCoffeById(long id) {
        Optional<Coffe> optionalCoffe = repoCoffe.findById(id);
        if (optionalCoffe.isEmpty()) {
            throw new IllegalArgumentException("No se puede obtener el café. ID inválido");
        }
        return optionalCoffe;
    }

    @Transactional
    public Coffe addCoffe(Coffe coffe) {
        if (coffe == null || 
            coffe.getName() == null || coffe.getName().isEmpty() ||
            coffe.getPrice() <= 0 ||
            coffe.getDescription() == null || coffe.getDescription().isEmpty()) {
            throw new IllegalArgumentException("El café no puede ser nulo o tener campos vacíos");
        }
        if(repoCoffe.findAll() == null || ((List<Coffe>) repoCoffe.findAll()).isEmpty()) {
            repoCoffe.save(coffe);
        } else {
            repoCoffe.save(coffe);
        }
        return coffe;
    }
    
    @Transactional
    public Coffe putCoffe(Coffe coffe) {
        if (coffe == null || 
            coffe.getName() == null || coffe.getName().isEmpty() ||
            coffe.getPrice() <= 0 ||
            coffe.getDescription() == null || coffe.getDescription().isEmpty()) {
            throw new IllegalArgumentException("El café no puede ser nulo o tener campos vacíos");
        }
        if(repoCoffe.findAll() == null || ((List<Coffe>) repoCoffe.findAll()).isEmpty()) {
            throw new EmptyStackException();
        }
        
        return repoCoffe.save(coffe);
    }

    @Transactional
    public Optional<Coffe> deleteCoffe(long id) {
        Optional<Coffe> optionalCoffe = repoCoffe.findById(id);
        if(repoCoffe.findAll() == null || ((List<Coffe>) repoCoffe.findAll()).isEmpty()) {
            throw new EmptyStackException();
        }
        if (optionalCoffe.isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar el café. ID inválido");
        }

        repoCoffe.deleteById(id);
        return optionalCoffe;
    }

    @Transactional
    public Coffe patchCoffe(long id, Coffe coffe) {
        Coffe nCoffe = repoCoffe.findById(id).orElseThrow(() -> 
            new EmptyStackException());
        
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
            return repoCoffe.save(nCoffe);
    }
}

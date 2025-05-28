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
 * Servicio para la gestión de cafés.
 * Proporciona operaciones CRUD y de negocio para la entidad Coffe.
 * @author Aaron
 */
@Service
public class ServiceCoffe {


    private final RepoCoffe repoCoffe;

    public ServiceCoffe(RepoCoffe repoCoffe) {
        this.repoCoffe = repoCoffe;
    }

    /**
     * Obtiene la lista completa de cafés.
     * @return Lista de cafés
     */
    public List<Coffe> getListaCoffe() {
        return (List<Coffe>) repoCoffe.findAll();
    }

    /**
     * Obtiene una página de cafés.
     * @param pagina Número de página
     * @param tamanoPagina Tamaño de la página
     * @return Página de cafés
     */
    @Transactional
    public Page<Coffe> getListaCoffe(int pagina , int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        return repoCoffe.findAll(pageable);
    }

    /**
     * Obtiene un café por su ID.
     * @param id ID del café
     * @return Café encontrado
     */
    public Optional<Coffe> getCoffeById(long id) {
        Optional<Coffe> optionalCoffe = repoCoffe.findById(id);
        if (optionalCoffe.isEmpty()) {
            throw new IllegalArgumentException("No se puede obtener el café. ID inválido");
        }
        return optionalCoffe;
    }

    /**
     * Guarda un nuevo café en la base de datos.
     * @param coffe Café a guardar
     * @return Café guardado
     */
    @Transactional
    public Coffe save(Coffe coffe) {
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
    
    /**
     * Actualiza completamente un café existente.
     * @param coffe Café con datos actualizados
     * @return Café actualizado
     */
    @Transactional
    public Coffe put(Coffe coffe) {
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

    /**
     * Elimina un café por su ID.
     * @param id ID del café
     * @return Café eliminado
     */
    @Transactional
    public Optional<Coffe> delete(long id) {
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

    /**
     * Actualiza parcialmente un café existente.
     * @param id ID del café
     * @param coffe Datos a actualizar
     * @return Café actualizado
     */
    @Transactional
    public Coffe patch(long id, Coffe coffe) {
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

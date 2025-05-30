/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Service;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Gammatech.Coffees.Entities.Coffee;
import com.Gammatech.Coffees.Repo.RepoCoffee;

import jakarta.transaction.Transactional;

/**
 * Servicio para la gestión de cafés.
 * Proporciona operaciones CRUD y de negocio para la entidad Coffe.
 * @author Aaron
 */
@Service
public class ServiceCoffee {


    private final RepoCoffee repoCoffe;

    public ServiceCoffee(RepoCoffee repoCoffe) {
        this.repoCoffe = repoCoffe;
    }

    /**
     * Obtiene la lista completa de cafés.
     * @return Lista de cafés
     */
    public List<Coffee> getListaCoffe() {
        return (List<Coffee>) repoCoffe.findAll();
    }

    /**
     * Obtiene una página de cafés.
     * @param pagina Número de página
     * @param tamanoPagina Tamaño de la página
     * @return Página de cafés
     */
    @Transactional
    public Page<Coffee> getListaCoffe(int pagina , int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        return repoCoffe.findAll(pageable);
    }

    /**
     * Obtiene un café por su ID.
     * @param id ID del café
     * @return Café encontrado
     */
    public Optional<Coffee> getCoffeById(long id) {
        Optional<Coffee> optionalCoffe = repoCoffe.findById(id);
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
    public Coffee save(Coffee coffe) {
        if (coffe == null || 
            coffe.getName() == null || coffe.getName().isEmpty() ||
            coffe.getPrice() <= 0 ||
            coffe.getDescription() == null || coffe.getDescription().isEmpty()) {
            throw new IllegalArgumentException("El café no puede ser nulo o tener campos vacíos");
        }
        if(repoCoffe.findAll() == null || ((List<Coffee>) repoCoffe.findAll()).isEmpty()) {
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
    public Coffee put(Coffee coffe) {
        if (coffe == null || 
            coffe.getName() == null || coffe.getName().isEmpty() ||
            coffe.getPrice() <= 0 ||
            coffe.getDescription() == null || coffe.getDescription().isEmpty()) {
            throw new IllegalArgumentException("El café no puede ser nulo o tener campos vacíos");
        }
        if(repoCoffe.findAll() == null || ((List<Coffee>) repoCoffe.findAll()).isEmpty()) {
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
    public Optional<Coffee> delete(long id) {
        Optional<Coffee> optionalCoffe = repoCoffe.findById(id);
        if(repoCoffe.findAll() == null || ((List<Coffee>) repoCoffe.findAll()).isEmpty()) {
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
    public Coffee patch(long id, Coffee coffe) {
        Coffee nCoffe = repoCoffe.findById(id).orElseThrow(() -> 
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

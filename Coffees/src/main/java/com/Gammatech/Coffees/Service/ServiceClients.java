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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Gammatech.Coffees.Entities.Clients;
import com.Gammatech.Coffees.Repo.RepoClients;

/**
 * Servicio para la gestión de clientes.
 * Proporciona operaciones CRUD y de negocio para la entidad Clients.
 * @author Aaron
 */
@Service
public class ServiceClients {
    /**
     * Repositorio de clientes para acceder a la base de datos.
     */
    private final RepoClients repoClient;
    /**
     * Constructor del servicio de clientes.
     * @param repoClient Repositorio de clientes
     */
    public ServiceClients(RepoClients repoClient) {
        this.repoClient = repoClient;
    }

    /**
     * Obtiene la lista completa de clientes.
     * @return Lista de clientes
     */
    public List<Clients> getListaClients() {
        return (List<Clients>) repoClient.findAll();
    }

    /**
     * Obtiene una página de clientes.
     * @param pagina Número de página
     * @param tamanoPagina Tamaño de la página
     * @return Página de clientes
     */
    @Transactional
    public Page<Clients> getListaClients(int pagina , int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        return repoClient.findAll(pageable);
    }

    /**
     * Obtiene un cliente por su ID.
     * @param id ID del cliente
     * @return Cliente encontrado
     */
    public Clients getClientById(long id) {
        return repoClient.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Cliente no encontrado"));
    }

    /**
     * Guarda un nuevo cliente en la base de datos.
     * @param client Cliente a guardar
     * @return Cliente guardado
     */
    @Transactional
    public Clients save(Clients client) {
        if (client == null || 
            client.getName() == null || client.getName().isEmpty() ||
            client.getEmail() == null || client.getEmail().isEmpty() ||
            client.getPhone() == null || client.getPhone().isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede ser nulo o tener campos vacíos");
        }
        try {
            return repoClient.save(client);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new IllegalArgumentException("Error de concurrencia al guardar el cliente");
        }
    }
    
    /**
     * Actualiza completamente un cliente existente.
     * @param client Cliente con datos actualizados
     * @return Cliente actualizado
     */
    @Transactional
    public Clients put(Clients client) {
        if (client == null || 
            client.getName() == null || client.getName().isEmpty() ||
            client.getEmail() == null || client.getEmail().isEmpty() ||
            client.getPhone() == null || client.getPhone().isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede ser nulo o tener campos vacíos");
        }
        
        if (!repoClient.existsById(client.getId())) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        try {
            return repoClient.save(client);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new IllegalArgumentException("Error de concurrencia al actualizar el cliente");
        }
    }

    /**
     * Elimina un cliente por su ID.
     * @param id ID del cliente
     * @return Cliente eliminado
     */
    @Transactional
    public Optional<Clients> delete(long id) {
        Optional<Clients> optionalClients = repoClient.findById(id);
        if(repoClient.findAll() == null || ((List<Clients>) repoClient.findAll()).isEmpty()) {
            throw new EmptyStackException();
        }
        if (optionalClients.isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar el client. ID inválido");
        }

        repoClient.deleteById(id);
        return optionalClients;
    }

    /**
     * Actualiza parcialmente un cliente existente.
     * @param id ID del cliente
     * @param client Datos a actualizar
     * @return Cliente actualizado
     */
    @Transactional
    public Clients patch(long id, Clients client) {
        Clients existingClient = repoClient.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Cliente no encontrado"));
        
        if (client.getName() != null && !client.getName().isEmpty()) {
            existingClient.setName(client.getName());
        }
        if (client.getSurName() != null && !client.getSurName().isEmpty()) {
            existingClient.setSurName(client.getSurName());
        }
        if (client.getEmail() != null && !client.getEmail().isEmpty()) {
            existingClient.setEmail(client.getEmail());
        }
        if (client.getPhone() != null && !client.getPhone().isEmpty()) {
            existingClient.setPhone(client.getPhone());
        }
        if (client.getDirection() != null && !client.getDirection().isEmpty()) {
            existingClient.setDirection(client.getDirection());
        }
        
        try {
            return repoClient.save(existingClient);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new IllegalArgumentException("Error de concurrencia al actualizar el cliente");
        }
    }
}
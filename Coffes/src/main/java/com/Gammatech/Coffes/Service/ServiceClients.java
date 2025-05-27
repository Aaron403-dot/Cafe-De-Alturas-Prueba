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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Gammatech.Coffes.Entities.Clients;
import com.Gammatech.Coffes.Repo.RepoClient;

/**
 *
 * @author Usuario
 */
@Service
public class ServiceClients {

    private final RepoClient repoClient;

    public ServiceClients(RepoClient repoClient) {
        this.repoClient = repoClient;
    }

    public List<Clients> getListaClients() {
        return (List<Clients>) repoClient.findAll();
    }

    @Transactional
    public Page<Clients> getListaClients(int pagina , int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        return repoClient.findAll(pageable);
    }

    public Clients getClientById(long id) {
        return repoClient.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Cliente no encontrado"));
    }

    @Transactional
    public Clients addClient(Clients client) {
        if (client == null || 
            client.getNombre() == null || client.getNombre().isEmpty() ||
            client.getEmail() == null || client.getEmail().isEmpty() ||
            client.getTelefono() == null || client.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede ser nulo o tener campos vacíos");
        }
        try {
            return repoClient.save(client);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new IllegalArgumentException("Error de concurrencia al guardar el cliente");
        }
    }
    
    @Transactional
    public Clients putClient(Clients client) {
        if (client == null || 
            client.getNombre() == null || client.getNombre().isEmpty() ||
            client.getEmail() == null || client.getEmail().isEmpty() ||
            client.getTelefono() == null || client.getTelefono().isEmpty()) {
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

    @Transactional
    public Optional<Clients> deleteClient(long id) {
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

    @Transactional
    public Clients patchClient(long id, Clients client) {
        Clients existingClient = repoClient.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Cliente no encontrado"));
        
        if (client.getNombre() != null && !client.getNombre().isEmpty()) {
            existingClient.setNombre(client.getNombre());
        }
        if (client.getApellidos() != null && !client.getApellidos().isEmpty()) {
            existingClient.setApellidos(client.getApellidos());
        }
        if (client.getEmail() != null && !client.getEmail().isEmpty()) {
            existingClient.setEmail(client.getEmail());
        }
        if (client.getTelefono() != null && !client.getTelefono().isEmpty()) {
            existingClient.setTelefono(client.getTelefono());
        }
        if (client.getDireccion() != null && !client.getDireccion().isEmpty()) {
            existingClient.setDireccion(client.getDireccion());
        }
        
        try {
            return repoClient.save(existingClient);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new IllegalArgumentException("Error de concurrencia al actualizar el cliente");
        }
    }
}
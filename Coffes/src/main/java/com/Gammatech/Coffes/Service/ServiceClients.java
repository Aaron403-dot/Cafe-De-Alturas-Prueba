/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Service;

import java.util.EmptyStackException;
import java.util.List;

import org.springframework.stereotype.Service;

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
        return repoClient.findAll();
    }

    public Clients getClientById(long id) {
        return repoClient.findById(id).orElseThrow(() -> 
            new EmptyStackException());
    }

    public Clients addClient(Clients client) {
        if (client == null || 
            client.getNombre() == null || client.getNombre().isEmpty() ||
            client.getEmail() == null || client.getEmail().isEmpty() ||
            client.getTelefono() == null || client.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede ser nulo o tener campos vacíos");
        }
        if(repoClient.findAll() == null || repoClient.findAll().isEmpty()) {
            client.setId(1l);
            repoClient.save(client);
        } else {
            client.setId(repoClient.findAll().get(repoClient.findAll().size()-1).getId()+1l);
            repoClient.save(client);
        }
        return client;
    }
    
    public Clients putClient(Clients client) {
        if (client == null || 
            client.getNombre() == null || client.getNombre().isEmpty() ||
            client.getEmail() == null || client.getEmail().isEmpty() ||
            client.getTelefono() == null || client.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede ser nulo o tener campos vacíos");
        }
        
        if(repoClient.findAll() == null || repoClient.findAll().isEmpty()) {
            throw new EmptyStackException();
        }

        return repoClient.save(client);
    }

    public long deleteClient(long id) {
        if (!repoClient.findById(id).isPresent()) {
            throw new IllegalArgumentException("No se puede eliminar el cliente. ID inválido");
        }
        if(repoClient.findAll().isEmpty() || repoClient.findAll() == null) {
            throw new EmptyStackException();
        }

        repoClient.delete(id);
        return id;
    }

    public Clients patchClient(long id, Clients client) {
        Clients nClient = repoClient.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("No se puede modificar el cliente. ID inválido"));
        
        if (client.getNombre() != null && !client.getNombre().isEmpty() && !client.getNombre().isBlank() && !nClient.getNombre().equals(client.getNombre())) {
            nClient.setNombre(client.getNombre());
        }
        if (client.getApellidos() != null && !client.getApellidos().isEmpty() && !client.getApellidos().isBlank() && !nClient.getApellidos().equals(client.getApellidos())) {
            nClient.setApellidos(client.getApellidos());
        }
        if (client.getEmail() != null && !client.getEmail().isEmpty() && !client.getEmail().isBlank() && !nClient.getEmail().equals(client.getEmail())) {
            nClient.setEmail(client.getEmail());
        }
        if (client.getTelefono() != null && !client.getTelefono().isEmpty() && !client.getTelefono().isBlank() && !nClient.getTelefono().equals(client.getTelefono())) {
            nClient.setTelefono(client.getTelefono());
        }
        if (client.getDireccion() != null && !client.getDireccion().isEmpty() && !client.getDireccion().isBlank() && !nClient.getDireccion().equals(client.getDireccion())) {
            nClient.setDireccion(client.getDireccion());
        }
        return repoClient.update(nClient);
    }
} 
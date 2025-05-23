/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Service;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.Gammatech.Coffes.Entities.Coffe;
import com.Gammatech.Coffes.Entities.CoffeeSimplyfied;
import com.Gammatech.Coffes.Entities.Orders;
import com.Gammatech.Coffes.Repo.RepoOrder;

/**
 *
 * @author Usuario
 */
@Service
public class ServiceOrders {

    private final RepoOrder repoOrder;
    private final ServiceCoffe serviceCoffe;

    public ServiceOrders(RepoOrder repoOrder, ServiceCoffe serviceCoffe) {
        this.repoOrder = repoOrder;
        this.serviceCoffe = serviceCoffe;
    }

    private void validarCafesEnOrden(Orders order) {
        if (order.getCafes() == null || order.getCafes().isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un café");
        }

        for (Map.Entry<CoffeeSimplyfied, Integer> entry : order.getCafes().entrySet()) {
            CoffeeSimplyfied cafe = entry.getKey();
            if (cafe == null) {
                throw new IllegalArgumentException("No se puede agregar un café nulo a la orden");
            }
            
            try {
                Coffe coffeCompleto = serviceCoffe.getCoffeById(cafe.getId());
                // Actualizar el precio del café simplificado con el precio real
                cafe.setPrecio(coffeCompleto.getPrice());
                // Guardar la referencia al café completo
                cafe.setCoffeCompleto(coffeCompleto);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El café con ID " + cafe.getId() + " no existe");
            }

            if (entry.getValue() <= 0) {
                throw new IllegalArgumentException("La cantidad del café debe ser mayor a 0");
            }
        }
    }

    public List<Orders> getListaOrders() {
        return repoOrder.findAll();
    }

    public Orders getOrderById(long id) {
        return repoOrder.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("No se puede obtener el pedido. ID inválido"));
    }

    public Orders addOrder(Orders order) {
        if (order == null || order.getClientId() == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener un cliente asignado");
        }

        validarCafesEnOrden(order);
        
        if(repoOrder.findAll() == null || repoOrder.findAll().isEmpty()) {
            order.setId(1l);
            repoOrder.save(order);
        } else {
            order.setId(repoOrder.findAll().get(repoOrder.findAll().size()-1).getId()+1l);
            repoOrder.save(order);
        }
        return order;
    }
    
    public Orders putOrder(Orders order) {
        if (order == null || order.getClientId() == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener un cliente asignado");
        }

        validarCafesEnOrden(order);        
        return repoOrder.save(order);
    }

    public long deleteOrder(long id) {
        if (!repoOrder.findById(id).isPresent()) {
            throw new IllegalArgumentException("No se puede eliminar el pedido. ID inválido");
        }
        if(repoOrder.findAll().isEmpty() || repoOrder.findAll() == null) {
            throw new EmptyStackException();
        }

        repoOrder.delete(id);
        return id;
    }

    public Orders patchOrder(long id, Orders order) {
        Orders nOrder = repoOrder.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("No se puede modificar el pedido. ID inválido"));
        
        if (order.getClientId() != null && !nOrder.getClientId().equals(order.getClientId())) {
            nOrder.setClientId(order.getClientId());
        }
        if (order.getCafes() != null && !order.getCafes().isEmpty() && !nOrder.getCafes().equals(order.getCafes())) {
            nOrder.setCafes(order.getCafes());
        }
        if (order.getPrecioTotal() > 0) {
            nOrder.setPrecioTotal(order.getPrecioTotal());
        }
        if (order.getEstado() != null && !order.getEstado().isEmpty() && !order.getEstado().isBlank() && !nOrder.getEstado().equals(order.getEstado())) {
            nOrder.setEstado(order.getEstado());
        }
        if (order.getFechaOrden() != null && !nOrder.getFechaOrden().equals(order.getFechaOrden())) {
            nOrder.setFechaOrden(order.getFechaOrden());
        }
        return repoOrder.update(nOrder);
    }

    public List<Orders> getOrdersByClientId(long id) {
        if(repoOrder.findByClientId(id) == null || repoOrder.findByClientId(id).isEmpty()) {
            throw new EmptyStackException();
        }
        return repoOrder.findByClientId(id);
    }
} 
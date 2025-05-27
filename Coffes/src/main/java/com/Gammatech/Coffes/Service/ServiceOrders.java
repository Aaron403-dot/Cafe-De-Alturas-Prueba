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
import com.Gammatech.Coffes.Entities.CoffeeSimplyfied;
import com.Gammatech.Coffes.Entities.Orders;
import com.Gammatech.Coffes.Repo.RepoOrder;

import jakarta.transaction.Transactional;

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

        for (CoffeeSimplyfied cafe : order.getCafes()) {
            if (cafe == null) {
                throw new IllegalArgumentException("No se puede agregar un café nulo a la orden");
            }
            
            try {
                Optional<Coffe> coffeCompleto = serviceCoffe.getCoffeById(cafe.getId());
                // Actualizar el precio del café simplificado con el precio real
                cafe.setPrecio(coffeCompleto.get().getPrice());
                // Guardar la referencia al café completo
                cafe.setCoffeCompleto(coffeCompleto.get());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El café con ID " + cafe.getId() + " no existe");
            }

            if (cafe.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad del café debe ser mayor a 0");
            }

            if(order.getClientId() == null || order.getClientId() <= 0) {
                throw new IllegalArgumentException("El ID del cliente debe ser válido y mayor a 0");
            }
        }
    }

    public List<Orders> getListaOrders() {
        return (List<Orders>) repoOrder.findAll();
    }

    @Transactional
    public Page<Orders> getListaOrders(int pagina , int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        return repoOrder.findAll(pageable);
    }


    public Optional<Orders> getOrderById(long id) {
        Optional <Orders> order = repoOrder.findById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Pedido no encontrado con ID: " + id);
        }
        return order;
    }

    @Transactional
    public Orders addOrder(Orders order) {
        if (order == null || order.getClientId() == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener un cliente asignado");
        }

        validarCafesEnOrden(order);
        
        if(repoOrder.findAll() == null || ((List<Orders>) repoOrder.findAll()).isEmpty()) {
            repoOrder.save(order);
        } else {
            repoOrder.save(order);
        }
        return order;
    }
    
    /**
     * TODO: ver por que este metdodo no genera bien las ordenes
    */

    @Transactional
    public Orders putOrder(Orders order) {
        if (order == null || order.getClientId() == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener un cliente asignado");
        }

        validarCafesEnOrden(order);        
        return repoOrder.save(order);
    }

    @Transactional
    public Optional<Orders> deleteOrder(long id) {
        Optional<Orders> orderDel = this.getOrderById(id);
        if(repoOrder.findAll() == null || ((List<Orders>) repoOrder.findAll()).isEmpty()) {
            throw new EmptyStackException();
        }
        if (orderDel.isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar el pedido. ID inválido");
        }
        repoOrder.deleteById(id);
        return orderDel;
    }

    @Transactional
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
        return repoOrder.save(nOrder);
    }

    public List<Orders> getOrdersByClientId(long id) {
        return repoOrder.findByClientId(id);
    }
}
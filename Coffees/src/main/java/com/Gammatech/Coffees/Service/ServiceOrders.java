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
import com.Gammatech.Coffees.Entities.CoffeeSimplyfied;
import com.Gammatech.Coffees.Entities.Orders;
import com.Gammatech.Coffees.Repo.RepoOrders;

/**
 * Servicio para la gestión de pedidos.
 * Proporciona operaciones CRUD y de negocio para la entidad Orders.
 * @author Aaron
 */
@Service
public class ServiceOrders {
    /**
     * Repositorio de pedidos para acceder a la base de datos.
     */
    private final RepoOrders repoOrder;
    /**
     * Servicio de café para validar cafés en las órdenes.
     */
    private final ServiceCoffee serviceCoffe;
    /**
     * Constructor del servicio de pedidos.
     * @param repoOrder Repositorio de pedidos
     * @param serviceCoffe Servicio de café para validar cafés en las órdenes
     */
    public ServiceOrders(RepoOrders repoOrder, ServiceCoffee serviceCoffe) {
        this.repoOrder = repoOrder;
        this.serviceCoffe = serviceCoffe;
    }

    /**
     * Valida que los cafés de una orden existan y sean correctos.
     * @param order Orden a validar
     */
    private void validarCafesEnOrden(Orders order) {
        if (order.getCoffee() == null || order.getCoffee().isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un café");
        }

        for (CoffeeSimplyfied cafe : order.getCoffee()) {
            if (cafe == null) {
                throw new IllegalArgumentException("No se puede agregar un café nulo a la orden");
            }
            
            try {
                Optional<Coffee> coffeCompleto = serviceCoffe.getCoffeById(cafe.getId());
                // Actualizar el precio del café simplificado con el precio real
                cafe.setPrice(coffeCompleto.get().getPrice());
                // Guardar la referencia al café completo
                cafe.setCoffeCompleto(coffeCompleto.get());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El café con ID " + cafe.getId() + " no existe");
            }

            if (cafe.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad del café debe ser mayor a 0");
            }

            if(order.getClientId() == null || order.getClientId() <= 0) {
                throw new IllegalArgumentException("El ID del cliente debe ser válido y mayor a 0");
            }
        }
    }

    /**
     * Obtiene la lista completa de pedidos.
     * @return Lista de pedidos
     */
    public List<Orders> getListaOrders() {
        return (List<Orders>) repoOrder.findAll();
    }

    /**
     * Obtiene una página de pedidos.
     * @param pagina Número de página
     * @param tamanoPagina Tamaño de la página
     * @return Página de pedidos
     */
    public Page<Orders> getListaOrders(int pagina , int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        return repoOrder.findAll(pageable);
    }

    /**
     * Obtiene un pedido por su ID.
     * @param id ID del pedido
     * @return Pedido encontrado
     */
    public Optional<Orders> getOrderById(long id) {
        Optional <Orders> order = repoOrder.findById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Pedido no encontrado con ID: " + id);
        }
        return order;
    }

    /**
     * Guarda un nuevo pedido en la base de datos.
     * @param order Pedido a guardar
     * @return Pedido guardado
     */
    public Orders save(Orders order) {
        if (order == null || order.getClientId() == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener un cliente asignado");
        }
        validarCafesEnOrden(order);
        repoOrder.save(order);
        return order;
    }
    

    /**
     * Actualiza completamente un pedido existente.
     * @param order Pedido con datos actualizados
     * @return Pedido actualizado
     */
    public Orders put(Orders order) {
        if (order == null || order.getClientId() == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo y debe tener un cliente asignado");
        }

        validarCafesEnOrden(order);        
        return repoOrder.save(order);
    }

    /**
     * Elimina un pedido por su ID.
     * @param id ID del pedido
     * @return Pedido eliminado
     */
    public Optional<Orders> delete(long id) {
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

    /**
     * Actualiza parcialmente un pedido existente.
     * @param id ID del pedido
     * @param order Datos a actualizar
     * @return Pedido actualizado
     */
    public Orders patch(long id, Orders order) {
        Orders nOrder = repoOrder.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("No se puede modificar el pedido. ID inválido"));
        
        if (order.getClientId() != null && !nOrder.getClientId().equals(order.getClientId())) {
            nOrder.setClientId(order.getClientId());
        }
        if (order.getCoffee() != null && !order.getCoffee().isEmpty() && !nOrder.getCoffee().equals(order.getCoffee())) {
            nOrder.setCoffee(order.getCoffee());
        }
        if (order.getTotalValue() > 0) {
            nOrder.setTotalValue(order.getTotalValue());
        }
        if (order.getState() != null && !order.getState().isEmpty() && !order.getState().isBlank() && !nOrder.getState().equals(order.getState())) {
            nOrder.setState(order.getState());
        }
        if (order.getOrderDate() != null && !nOrder.getOrderDate().equals(order.getOrderDate())) {
            nOrder.setOrderDate(order.getOrderDate());
        }
        return repoOrder.save(nOrder);
    }

    /**
     * Obtiene la lista de pedidos de un cliente por su ID.
     * @param id ID del cliente
     * @return Lista de pedidos del cliente
     */
    public List<Orders> getOrdersByClientId(long id) {
        return repoOrder.findByClientId(id);
    }
}
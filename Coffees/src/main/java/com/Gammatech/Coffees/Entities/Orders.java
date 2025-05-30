package com.Gammatech.Coffees.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
/**
 * Entidad que representa un pedido realizado en la cafetería.
 * @author Aaron
 */

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Long clientId;

    @OneToMany
    @JoinColumn(name = "cafe_id", nullable = false)
    private List<CoffeeSimplyfied> coffee; //Array esta los café

    @Column(nullable = false)
    private double totalValue;

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now(); // Fecha de la orden

    @Column(nullable = false)
    private String state; // PENDIENTE, EN_PROCESO, COMPLETADA, CANCELADA
    
    // Constructor vacío
    public Orders() {
        this.coffee = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }
    
    // Constructor con parámetros básicos
    public Orders(Long id, Long clientId, String estado) {
        this.id = id;
        this.clientId = clientId;
        this.coffee = new ArrayList<>();
        this.state = estado;
        this.orderDate = LocalDateTime.now();
    }
    
    /**
     * Agrega un café a la orden y recalcula el precio total.
     * @param cafe Café a agregar
     * @param cantidad Cantidad del café
     */
    // Método para agregar un café a la orden
    public void agregarCafe(CoffeeSimplyfied cafe, int cantidad) {
        if (this.coffee == null) {
            this.coffee = new ArrayList<>();
        }
        this.coffee.add(cafe);
        calcularPrecioTotal();
    }
    
    /**
     * Calcula el precio total de la orden.
     * @return Precio total
     */
    // Método para calcular el precio total
    public double calcularPrecioTotal() {
        this.totalValue = this.coffee.stream()
                .mapToDouble(entry -> entry.getPrice() * entry.getQuantity())
                .sum();
        return this.totalValue;
    }
    
    /**
     * Obtiene el identificador de la orden.
     * @return ID
     */
    // Getters y Setters
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la orden.
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del cliente asociado a la orden.
     * @return ID del cliente
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * Establece el identificador del cliente asociado a la orden.
     * @param clientId ID del cliente
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * Obtiene la lista de cafés de la orden.
     * @return Lista de cafés
     */
    public List<CoffeeSimplyfied> getCoffee() {
        return coffee;
    }

    /**
     * Establece la lista de cafés de la orden y recalcula el precio total.
     * @param cafes Lista de cafés
     */
    public void setCoffee(List<CoffeeSimplyfied> cafes) {
        this.coffee = cafes;
        this.calcularPrecioTotal();
    }

    /**
     * Obtiene el valor total de la orden.
     * @return Valor total
     */
    public double getTotalValue() {
        return totalValue;
    }

    /**
     * Establece el valor total de la orden.
     * @param precioTotal Valor total
     */
    public void setTotalValue(double precioTotal) {
        this.totalValue = precioTotal;
    }

    /**
     * Obtiene la fecha de la orden.
     * @return Fecha de la orden
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Establece la fecha de la orden.
     * @param fechaOrden Fecha de la orden
     */
    public void setOrderDate(LocalDateTime fechaOrden) {
        this.orderDate = fechaOrden;
    }

    /**
     * Obtiene el estado de la orden.
     * @return Estado
     */
    public String getState() {
        return state;
    }

    /**
     * Establece el estado de la orden.
     * @param estado Estado
     */
    public void setState(String estado) {
        this.state = estado;
    }
    
    /**
     * Devuelve una representación en String de la orden.
     * @return String con los datos de la orden
     */
    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", numeroCafes=" + (coffee != null ? coffee.size() : 0) +
                ", precioTotal=" + totalValue +
                ", fechaOrden=" + orderDate +
                ", estado='" + state + '\'' +
                '}';
    }
}

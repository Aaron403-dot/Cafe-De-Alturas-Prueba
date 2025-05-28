package com.Gammatech.Coffes.Entities;

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
 *
 * @author Aaron del Cristo Suarez Suarez
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
    private List<CoffeeSimplyfied> cafes; //Array esta los café

    @Column(nullable = false)
    private double precioTotal;

    @Column(nullable = false)
    private LocalDateTime fechaOrden;

    @Column(nullable = false)
    private String estado; // PENDIENTE, EN_PROCESO, COMPLETADA, CANCELADA
    
    // Constructor vacío
    public Orders() {
        this.cafes = new ArrayList<>();
        this.fechaOrden = LocalDateTime.now();
    }
    
    // Constructor con parámetros básicos
    public Orders(Long id, Long clientId, String estado) {
        this.id = id;
        this.clientId = clientId;
        this.cafes = new ArrayList<>();
        this.estado = estado;
        this.fechaOrden = LocalDateTime.now();
    }
    
    // Método para agregar un café a la orden
    public void agregarCafe(CoffeeSimplyfied cafe, int cantidad) {
        if (this.cafes == null) {
            this.cafes = new ArrayList<>();
        }
        this.cafes.add(cafe);
        calcularPrecioTotal();
    }
    
    // Método para calcular el precio total
    public double calcularPrecioTotal() {
        this.precioTotal = this.cafes.stream()
                .mapToDouble(entry -> entry.getPrecio() * entry.getCantidad())
                .sum();
        return this.precioTotal;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<CoffeeSimplyfied> getCafes() {
        return cafes;
    }

    public void setCafes(List<CoffeeSimplyfied> cafes) {
        this.cafes = cafes;
        this.calcularPrecioTotal();
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDateTime getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDateTime fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", numeroCafes=" + (cafes != null ? cafes.size() : 0) +
                ", precioTotal=" + precioTotal +
                ", fechaOrden=" + fechaOrden +
                ", estado='" + estado + '\'' +
                '}';
    }
}

package com.Gammatech.Coffes.Entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Aaron del Cristo Suarez Suarez
 */
public class Orders {
    private Long id;
    private Long clientId;
    private Map<Coffe, Integer> cafes; // Map donde la key es el café y el value es la cantidad
    private double precioTotal;
    private LocalDateTime fechaOrden;
    private String estado; // PENDIENTE, EN_PROCESO, COMPLETADA, CANCELADA
    
    // Constructor vacío
    public Orders() {
        this.cafes = new HashMap<>();
        this.fechaOrden = LocalDateTime.now();
    }
    
    // Constructor con parámetros básicos
    public Orders(Long id, Long clientId, double precioTotal, String estado) {
        this.id = id;
        this.clientId = clientId;
        this.cafes = new HashMap<>();
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.fechaOrden = LocalDateTime.now();
    }
    
    // Método para agregar un café a la orden
    public void agregarCafe(Coffe cafe, int cantidad) {
        if (this.cafes == null) {
            this.cafes = new HashMap<>();
        }
        this.cafes.put(cafe, cantidad);
        this.calcularPrecioTotal();
    }
    
    // Método para calcular el precio total
    private void calcularPrecioTotal() {
        this.precioTotal = this.cafes.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
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

    public Map<Coffe, Integer> getCafes() {
        return cafes;
    }

    public void setCafes(Map<Coffe, Integer> cafes) {
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

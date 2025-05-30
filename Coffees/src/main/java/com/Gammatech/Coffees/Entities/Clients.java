package com.Gammatech.Coffees.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

/**
 * Entidad que representa un cliente de la cafetería.
 * @author Aaron
 */
@Entity
public class Clients {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Transient
	@OneToOne
	private Orders order;
	
	@Column(nullable = false)
	private String name;

	@Column
	private String surName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String phone;
	
	@Column
	private String direction;
	

	/**
	 * Obtiene el pedido asociado al cliente.
	 * @return Pedido
	 */
	public Orders getOrder() {
		return order;
	}

	/**
	 * Asocia un pedido al cliente.
	 * @param order Pedido
	 */
	public void setOrder(Orders order) {
		this.order = order;
	}

	// Constructor vacío
	public Clients() {
	}
	
	// Constructor con todos los parámetros
	public Clients(Long id, String nombre, String apellidos, String email, String telefono, String direccion) {
		this.id = id;
		this.name = nombre;
		this.surName = apellidos;
		this.email = email;
		this.phone = telefono;
		this.direction = direccion;
	}
	
	/**
	 * Obtiene el identificador del cliente.
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador del cliente.
	 * @param id ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del cliente.
	 * @return Nombre
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del cliente.
	 * @param nombre Nombre
	 */
	public void setName(String nombre) {
		this.name = nombre;
	}

	/**
	 * Obtiene los apellidos del cliente.
	 * @return Apellidos
	 */
	public String getSurName() {
		return surName;
	}

	/**
	 * Establece los apellidos del cliente.
	 * @param apellidos Apellidos
	 */
	public void setSurName(String apellidos) {
		this.surName = apellidos;
	}

	/**
	 * Obtiene el email del cliente.
	 * @return Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el email del cliente.
	 * @param email Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene el teléfono del cliente.
	 * @return Teléfono
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Establece el teléfono del cliente.
	 * @param telefono Teléfono
	 */
	public void setPhone(String telefono) {
		this.phone = telefono;
	}

	/**
	 * Obtiene la dirección del cliente.
	 * @return Dirección
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Establece la dirección del cliente.
	 * @param direccion Dirección
	 */
	public void setDirection(String direccion) {
		this.direction = direccion;
	}
	
	/**
	 * Devuelve una representación en String del cliente.
	 * @return String con los datos del cliente
	 */
	@Override
	public String toString() {
		return "Clients{" +
				"id=" + id +
				", nombre='" + name + '\'' +
				", apellidos='" + surName + '\'' +
				", email='" + email + '\'' +
				", telefono='" + phone + '\'' +
				", direccion='" + direction + '\'' +
				'}';
	}
}

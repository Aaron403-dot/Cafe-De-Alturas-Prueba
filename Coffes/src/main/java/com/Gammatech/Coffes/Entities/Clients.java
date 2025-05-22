package com.Gammatech.Coffes.Entities;

/**
*
* @author Aaron del Cristo Suarez Suarez
*/
public class Clients {
	private Long id;
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;
	private String direccion;
	
	// Constructor vacío
	public Clients() {
	}
	
	// Constructor con todos los parámetros
	public Clients(Long id, String nombre, String apellidos, String email, String telefono, String direccion) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public String toString() {
		return "Clients{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", apellidos='" + apellidos + '\'' +
				", email='" + email + '\'' +
				", telefono='" + telefono + '\'' +
				", direccion='" + direccion + '\'' +
				'}';
	}
}

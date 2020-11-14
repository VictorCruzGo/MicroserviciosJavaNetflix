package com.app.commons.usuarios.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable { // Serializable para guardar el entity en una peticion http

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean enabled;

	@Column(length = 60)
	private String password;

	@Column(unique = true, length = 30)
	private String username;

	@Column(unique = true, length = 60)
	private String email;

	private String nombre;

	private String apellido;
	
	private Integer intentos;
	
	//Lazy=Carga los datos a demanda
	//Eager=Carga todos los datos (Pesado)
	//Un usuario que roles tiene?
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuarios_roles", 
	joinColumns=@JoinColumn(name="usuario_id"), 
	inverseJoinColumns=@JoinColumn(name="rol_id"),
	uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol_id"})})
	private List<Rol> roles;

	
	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getIntentos() {
		return intentos;
	}

	public void setIntentos(Integer intentos) {
		this.intentos = intentos;
	}
	

}

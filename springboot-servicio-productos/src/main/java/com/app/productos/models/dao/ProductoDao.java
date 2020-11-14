package com.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.app.commons.models.entity.Producto;



//Al heredar de CrudRepository, ProductoDao ya es un componente de spring por lo tanto ya se puede injectar
public interface ProductoDao extends CrudRepository<Producto, Long> {
	
}

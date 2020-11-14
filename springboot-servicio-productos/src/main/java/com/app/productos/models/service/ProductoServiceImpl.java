package com.app.productos.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.commons.models.entity.Producto;
import com.app.productos.models.dao.ProductoDao;


//Permite registrar la clase como un componente de spring para la Inyection de dependencia
@Service
public class ProductoServiceImpl implements IProductoService{

	@Autowired
	private ProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoDao.save(producto);//save sirve para crear y actualizar
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productoDao.deleteById(id);		
	}

}

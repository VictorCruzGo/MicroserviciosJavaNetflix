package com.app.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.commons.models.entity.Producto;
import com.app.item.clientes.ClienteFeignRest;
import com.app.item.models.Item;

//El problema hay dos servicios que implementan la interface ItemService.
//Una con Feign y la otra con RestTemplate
//Indicar cual es la principal
@Service ("ItemServiceFeignImpl")
//@Primary //Se inyectara por defecto en el controlador. Comentar si se va ha utilizar Qualifiers
public class ItemServiceFeignImpl implements IItemService {

	@Autowired
	private ClienteFeignRest clienteFeign;
	@Override
	public List<Item> findAll() {
		return clienteFeign.listar().stream().map(p->new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(clienteFeign.detalle(id), cantidad); 
	}

	@Override
	public Producto save(Producto producto) {
		return clienteFeign.crear(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {		
		return clienteFeign.editar(producto, id);
	}

	@Override
	public void delete(Long id) {
		clienteFeign.eliminar(id);
	}	
}

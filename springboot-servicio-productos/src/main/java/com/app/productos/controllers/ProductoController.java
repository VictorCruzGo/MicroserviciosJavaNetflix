package com.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.commons.models.entity.Producto;
import com.app.productos.models.service.IProductoService;

//Convierte a Json
@RestController
public class ProductoController {

	//Pare leer el puerto del archivo properties
	@Autowired
	private Environment env;
	
	//Ijectar la variable port del archivo properties
	@Value("${server.port}")
	private Integer port;
	
	//Para inyectar
	@Autowired
	private IProductoService productoService;
	
	//Permite mapear los endpoint a los metodos
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream().map(producto->{
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}
	
	//Permite mapear los endpoint a los metodos
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws Exception {
		Producto producto= productoService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
		
		//EXCEPCION. Lanza una excepcion para probar hystrix
//		boolean ok=false;
//		if(ok==false) {
//			throw new Exception("No se puede cargar el producto");
//		}
		
		//TIMEOUT. El timeout en hystrix y ribbon es de 1 seg.
//		try {
//			Thread.sleep(2000L);	
//		} catch(InterruptedException e) {
//			e.printStackTrace();
//		}			
		return productoService.findById(id);
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);
	}	
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		Producto productodb=productoService.findById(id);
		productodb.setNombre(producto.getNombre());
		productodb.setPrecio(producto.getPrecio());
		
		return productoService.save(productodb);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)//Cuando no retorna nada codigo 204
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
	}
}

package com.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.commons.models.entity.Producto;
import com.app.item.models.Item;
import com.app.item.service.IItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@RefreshScope //Permite actualizar en tiempo de ejecucion el componente de spring mediante un endpoint de spring actuator
@RestController
public class ItemController {

	private static Logger log=LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;
	
	@Value("${configuracion.texto}")
	private String texto;
	
	@Value("${configuracion.nombre}")
	private String nombre;
	
	//No sabe cual servicio inyector, feign o restTemplate. Con @primary se especifica el primer componente
	//Otra forma es usa @Qualifiers. En caso de usar Qualifiers comentar la anotacion @primary
	@Autowired
	@Qualifier("ItemServiceFeignImpl") //Para utilizar el cliente Feign
	//@Qualifier("ItemServiceImpl") //Para utilizar la implementacion RestTemplate
	private IItemService itemService;
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo") //Hystrix para habilitar un metodo alternativo
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}
	
	public Item metodoAlternativo(Long id, Integer cantidad) {
		Item item=new Item();
		Producto producto=new Producto();
		producto.setId(id);
		producto.setNombre("Camara hystrix");
		producto.setPrecio(500.00);		
		
		item.setCantidad(1);
		item.setProducto(producto);
		return item;
	}
	
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfiguracion(@Value("${server.port}") String puerto){
			log.info(texto);
			
			Map<String, String> json=new HashMap<>();
			json.put("texto", texto);
			json.put("puerto", puerto);
			
			if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) 
			{
				//json.put("configuracion.nombre", nombre+"---");//Tambien se puede obtener el valor del properties con la anotacion @value
				json.put("configuracion.nombre", env.getProperty("configuracion.nombre"));				
				json.put("configuracion.email", env.getProperty("configuracion.email"));
			}
			
			return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return itemService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		return itemService.update(producto, id);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		itemService.delete(id);
	}
}

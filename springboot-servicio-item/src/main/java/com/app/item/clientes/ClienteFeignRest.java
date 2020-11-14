package com.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.commons.models.entity.Producto;




//Define que la interfaz es un cliente feign
//Pasa a ser un componente de spring
//@FeignClient(name="servicio-productos", url="localhost:8111")
@FeignClient(name="servicio-productos") //Con ribbon se puede quitar la URL. Se desacopla con solo mencionar el nombre del microservicio.
public interface ClienteFeignRest {
/*La iterfaz y el controller (nombres de metodos, anotaciones, parametros) en Feign deberian ser iguales*/
	//Mapea los endpoint
	@GetMapping("/listar")
	public List<Producto> listar();
	
	//Permite mapear los endpoint a los metodos
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id);
		

	@PostMapping("/crear")
	public Producto crear(@RequestBody Producto producto);
	
	@PutMapping("/editar/{id}")
	public Producto editar(@RequestBody Producto producto,@PathVariable Long id);
	
	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable Long id);
}

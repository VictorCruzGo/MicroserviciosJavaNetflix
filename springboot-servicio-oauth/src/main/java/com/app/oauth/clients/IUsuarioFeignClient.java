package com.app.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.commons.usuarios.models.entity.Usuario;

@FeignClient(name="servicio-usuarios") //Nombre del microservicio con el que se quiere comunicar
public interface IUsuarioFeignClient {
	@GetMapping("/usuarios/search/buscar-usuario") //buscar-usuario, el endpoint del servicio-usuarios renombrado
	public Usuario findByUsername(@RequestParam("nombre") String username);
	
	@PutMapping("/usuarios/{id}")
	public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id);
}

package com.app.oauth.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.commons.usuarios.models.entity.Usuario;

public interface IUsuarioService {
	public Usuario findByUsername(String username);
	
	public Usuario update(Usuario usuario, Long id);
}

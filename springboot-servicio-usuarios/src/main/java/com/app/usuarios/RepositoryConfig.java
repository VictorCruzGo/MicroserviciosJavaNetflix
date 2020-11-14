package com.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.app.commons.usuarios.models.entity.Rol;
import com.app.commons.usuarios.models.entity.Usuario;



//Crear la clase RepositoryConfig para exponer los ID de las clases
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Usuario.class, Rol.class);
	}
}

package com.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.commons.usuarios.models.entity.Usuario;
import com.app.oauth.clients.IUsuarioFeignClient;

import brave.Tracer;
import feign.FeignException;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService { // Spring.security
	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioFeignClient iUsuarioFeignClient;
	
	@Autowired
	private Tracer tracer;

	// El metodo es independiente de JPA, JBDC
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {// Spring.security
		try {
			Usuario usuario = iUsuarioFeignClient.findByUsername(username);//PETICION 1	

			List<GrantedAuthority> authorities = usuario.getRoles() // Spring.security
					.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())) // Spring.security y Objeto Rol
					.peek(authority -> log.info("Rol: " + authority.getAuthority())).collect(Collectors.toList());

			log.info("Usuario autenticado");

			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
					authorities);

		} catch (FeignException e) {
			String error="Error en el login, no existe el usuario";
			log.error(error);
			tracer.currentSpan().tag("error.mensaje", error+" : "+e.getMessage());
			throw new UsernameNotFoundException(error);
		}
	}

	@Override
	public Usuario findByUsername(String username) {
		return iUsuarioFeignClient.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return iUsuarioFeignClient.update(usuario, id);
	}
}

package com.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.commons.usuarios.models.entity.Usuario;
import com.app.oauth.services.IUsuarioService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log=LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUsuarioService iUsuarioService;
	
	@Autowired
	private Tracer tracer;
	
	//Para manejar el exito
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user=(UserDetails)authentication.getPrincipal();
		String mensaje="Success login"+user.getUsername();
		System.out.println(mensaje);
		log.info(mensaje);
		
		Usuario usuario=iUsuarioService.findByUsername(authentication.getName());//PETICION 3	
		
		if(usuario.getIntentos()!=null && usuario.getIntentos()>0) {
			usuario.setIntentos(0);
			iUsuarioService.update(usuario, usuario.getId());
		}
	}

	//Para menejar el error
	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje="Error en el login:"+exception.getMessage();
		System.out.println(mensaje);		
		log.error(mensaje);		
		
		try {
			
		StringBuilder errors=new StringBuilder();
		errors.append(mensaje);
		
		Usuario usuario=iUsuarioService.findByUsername(authentication.getName());
		if(usuario.getIntentos()==null) {
			usuario.setIntentos(0);
		}
		
		log.info("Intentos actual es de "+ usuario.getIntentos());
		usuario.setIntentos(usuario.getIntentos()+1);
		log.info("Intentos despues es de "+ usuario.getIntentos());
		
		errors.append("Intentos despues del login "+ usuario.getIntentos());
		
		if(usuario.getIntentos()>=3) {
			String errorMaxIntentos=String.format("El usuario %s deshabilitado por maximos intentos", usuario.getUsername());
			log.error(errorMaxIntentos);
			errors.append("-"+errorMaxIntentos);
			tracer.currentSpan().tag("error.mensaje", errors.toString());
			usuario.setEnabled(false);
		}
		
		iUsuarioService.update(usuario, usuario.getId());
		}
		catch(FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
	}

}

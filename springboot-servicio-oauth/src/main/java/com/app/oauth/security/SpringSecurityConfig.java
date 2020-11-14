package com.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	//Spring buscara el componente y lo inyectara el servicio UsuarioService porque implementa la interfaz UserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationEventPublisher eventPublisher;
	
	//@Bean permite registrar en contedor de spring objetos. Via metodo. Lo que retorna el metodo es lo que se registra.
	//@Service permite registrar la clase
	//Con @Bean se guardara en el contenedor de spring para encriptar la contrasena.
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
			
	@Override
	@Autowired //Inyectar por metodo el objeto AuthenticationManagerBuilder 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Registrar el userDetailsService
		auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder())
		.and().authenticationEventPublisher(eventPublisher);//Para el manjero erroes durante la autenticacion
	}

	//@Bean permite registrar en el contenedor de spring.
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {	
		return super.authenticationManager();
	}	
	
	
}

package com.app.zuul.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope //Para reiniciar el microservicio con el actuator
@Configuration
@EnableResourceServer //Habilitar la configuracion del servidor recurso
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Value("${config.security.oauth.jwt.key}")
	private String jwtKey;

	//Para configurar el token con la misma estructura que el servidor de autorizacion
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		//Configuracion del tokenstore
		resources.tokenStore(tokenStore());
	}

	//Para proteger los endpoints (uri)
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//Proteger cada ruta de zuul server
//		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll() //Cualquier usuario puede iniciar sesion
//		.antMatchers(HttpMethod.GET, "/api/productos/listar","/api/items/listar","/api/usuarios/usuarios").permitAll()//Cualquier usuario puede listar
//		.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}","/api/items/ver/{id}/cantidad/{cantidad}","/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN","USER")//Cualquier usuario puede ver
//		.antMatchers(HttpMethod.POST, "/api/productos/crear","/api/items/crear","/api/usuarios/usuarios").hasRole("ADMIN")//Solo adminstradores pueden crear
//		.antMatchers(HttpMethod.PUT,"/api/productos/editar/{id}","/api/items/editar/{id}","/api/usuarios/usuarios/{id}").hasRole("ADMIN")//Solo adminstradores pueden editar
//		.antMatchers(HttpMethod.DELETE,"/api/productos/eliminar/{id}","/api/items/eliminar/{id}","/api/usuarios/usuarios/{id}").hasRole("ADMIN");//Solo adminstradores pueden editar
		
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll() //Cualquier usuario puede iniciar sesion
		.antMatchers(HttpMethod.GET, "/api/productos/listar","/api/items/listar","/api/usuarios/usuarios").permitAll()//Cualquier usuario puede listar
		.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}","/api/items/ver/{id}/cantidad/{cantidad}","/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN","USER")//Cualquier usuario puede ver. No es necesario escribir el prefijo ROLE_
		.antMatchers("/api/productos/**","/api/items/**","/api/usuarios/**").hasRole("ADMIN")//Solo adminstradores pueden crear, eliminar y actualizar
		.anyRequest().authenticated()//Cualquier otra ruta requiere autenticacion
		.and().cors().configurationSource(corsConfigurationSource());//CORS
	}

	//Configuracion del CORS a nivel rutas.
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig=new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*")); //Origen de las aplicaciones clientes: http, ruta fisica, puertos.
		corsConfig.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));//Permitir los metodos http. OPTIONS lo utiliza OAUTH2
		corsConfig.setAllowCredentials(true);//Creadenciales
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));//Importante al enviar el token en la cabecera
		
		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();//Pasar la configuracion a los endpoints
		source.registerCorsConfiguration("/**", corsConfig);//Se aplica a todas las rutas
		
		return source;
	}
	
	//Filtros de cors a nivel global de toda la aplicacion
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean=new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);//Prioridad alta
		return bean;
	}
	
	//
	@Bean
	public JwtTokenStore tokenStore() {
		//Recibe el accesotokenconverter para poder crear el token y almacenarlo
		return new JwtTokenStore(accessTokenConverter());
	}
		
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter=new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtKey);//Codigo secreto para firmar el token
		return tokenConverter;
		
	}

}

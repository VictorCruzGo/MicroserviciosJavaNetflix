package com.app.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

 
@RefreshScope //Para reiniciar el microservicio con el actuator
@Configuration
@EnableAuthorizationServer //Habilitar la clase como un servidor de autorizacion
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired //Inyectamos en Bean de la clase SpringSecurityConfig
	private BCryptPasswordEncoder passwordEncoder;
	
	//Hay que registrar en el authenticacionServer
	@Autowired //Inyectamos en Bean de la clase SpringSecurityConfig
	private AuthenticationManager authenticationManager;

	@Autowired
	private InfoAdicionalToken infoAdicionalToken;
	
	//Es el permiso que va a tener el endpoint del servicio de autorizacion.
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()") //Seguridad a los endpoint.  Cualquier cliente puede acceder al endpoint
		.checkTokenAccess("isAuthenticated()"); //Se encarga de validar el token		
	}
	
	//Configuracion de los clientes (android, angular,  app)
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()//registro en memoria
		//.withClient("frontendapp")//identificador de la aplicaicon (android, angular,  app)[USERNAME]
		.withClient(env.getProperty("config.security.oauth.client.id"))//identificador de la aplicaicon (android, angular,  app)[USERNAME]
		//.secret( passwordEncoder.encode("12345"))//contrasena encriptada [PASSWORD]
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))//contrasena encriptada [PASSWORD]
		.scopes("read","write")//Alcance de la aplicacion para leer y escribir
		.authorizedGrantTypes("password", "refresh_token")//Como vamos a obtener el password con credenciales
		.accessTokenValiditySeconds(3600)//tiempo de validad del token
		.refreshTokenValiditySeconds(3600);
//		.and()
//		.withClient("androindapp")
//		.secret( passwordEncoder.encode("1234"))
//		.scopes("read","write")
//		.authorizedGrantTypes("password","refresh_token")
//		.accessTokenValiditySeconds(3600)
//		.refreshTokenValiditySeconds(3600);
	}

	
	//AutenticationManager
	//TokenStorage, se encarga de guardar el token 
	//Access token converte, se encarga guardar los datos en el token. Convierte en el token
	//configure. Endpoint relacionado al oauth2, se encarga de generar el token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//Para agregar informacion adicional al token
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));//PETICION 1-2
		
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);	//Para agregar informacion adicionar al token	
	} 
	
	@Bean
	public JwtTokenStore tokenStore() {
		//Recibe el accesotokenconverter para poder crear el token y almacenarlo
		return new JwtTokenStore(accessTokenConverter());
	}
	
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter=new JwtAccessTokenConverter();
		//tokenConverter.setSigningKey("mi_codigo_secreto");//Codigo secreto para firmar el token
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));//Codigo secreto para firmar el token
		return tokenConverter;
		
	}
	
	
	
}

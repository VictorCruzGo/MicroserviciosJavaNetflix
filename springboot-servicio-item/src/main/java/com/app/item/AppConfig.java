package com.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*Registrar el RestTemplate
 * 
 * */

//Permite crear objetos en spring
@Configuration
public class AppConfig {

	//Es un cliente para trabajar con api REST.
	//Permite acceder a recursos que estan en otros microservicios.
	@Bean("clienteRest")
	@LoadBalanced //Balancear con restTemplate. Usa ribbon para el balanceo de carga
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
}

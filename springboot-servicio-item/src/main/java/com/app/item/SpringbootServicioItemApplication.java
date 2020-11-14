package com.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//Habilita el cliente feing
//Permite injectar el cliente en los controladores
//Habilita ribbon. Se habilita @RibbonClien porque solo hay un cliente Class ProductoClienteRest
@EnableCircuitBreaker //Habiltar hystrix
@EnableEurekaClient //No es necesario puesto que ya existen la dependencia en el POM
//@RibbonClient(name="servicio-productos") //Quitar cuando se utiliza eureka
@EnableFeignClients 
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioItemApplication.class, args);
	}

}

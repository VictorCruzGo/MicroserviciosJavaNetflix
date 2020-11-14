package com.app.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//Habilitar eureka server
@EnableEurekaServer
@SpringBootApplication
public class SpringbootServicioEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioEurekaServerApplication.class, args);
	}

}

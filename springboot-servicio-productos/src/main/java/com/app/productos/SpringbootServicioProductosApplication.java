package com.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient //No es necesario puesto que ya existen la dependencia en el POM
@SpringBootApplication
@EntityScan("com.app.commons.models") //Las clases entities se buscaran en el proyecto commons
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}
}

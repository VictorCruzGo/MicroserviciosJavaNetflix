package com.app.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient //No es necesario, puesto que ya existe la dependencia en el POM
@SpringBootApplication
//@EntityScan( basePackages = {"com.app.usuarios.commons.models.entity"} ) //NO OLVIDAR. tiene que buscar las entities en el paquete models.entity
@EntityScan("com.app.commons.usuarios.models.entity") 
public class SpringbootServicioUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioUsuariosApplication.class, args);
	}
}

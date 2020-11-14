package com.app.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//Agregar la configuracion EnableAutoConfiguration.
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})//Excluir la configuracion del datasource.
public class SpringbootServicioCommonsApplication {
//Quitar porque es un proyecto de libreria
//	public static void main(String[] args) {
//		SpringApplication.run(SpringbootServicioCommonsApplication.class, args);
//	}s
}

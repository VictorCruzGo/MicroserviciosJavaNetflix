package com.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.app.commons.usuarios.models.entity.Usuario;




@RepositoryRestResource(path = "usuarios") //path define el endpoint
public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {
	//En la pagina hay ejemplo de JPA repositories
	//https://docs.spring.io/spring-data/jpa/docs/2.2.6.RELEASE/reference/html/#jpa.query-methods
	@RestResource(path="buscar-usuario")
	public Usuario findByUsername(@Param("nombre") String username);
	
	@Query("select u from Usuario u where u.username=?1")
	//@Query(value="select u from Usuario u where u.username=?1", nativeQuery = true) //Consultas nativas
	public Usuario obtenerPorUsername(String username);	
}

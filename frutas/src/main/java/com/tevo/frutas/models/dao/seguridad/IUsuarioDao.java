package com.tevo.frutas.models.dao.seguridad;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tevo.frutas.models.entity.seguridad.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	
	@Query("select u.username from Usuario u where u.id = ?1")
	public String findUsernameById(Long id);

}

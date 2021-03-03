package com.tevo.frutas.models.services.seguridad;

import com.tevo.frutas.models.entity.seguridad.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
	
	public String findUsernameById(Long id);
}

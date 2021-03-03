package com.tevo.frutas.models.dao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tevo.frutas.models.entity.maestros.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long> {

	@Query("select c from Cliente c order by c.nombreCliente asc")
	public List<Cliente> listDropdown();
}

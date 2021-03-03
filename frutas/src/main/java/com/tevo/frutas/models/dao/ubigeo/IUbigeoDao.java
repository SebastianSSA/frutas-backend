package com.tevo.frutas.models.dao.ubigeo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tevo.frutas.models.entity.ubigeo.Departamento;
import com.tevo.frutas.models.entity.ubigeo.Distrito;
import com.tevo.frutas.models.entity.ubigeo.Provincia;

public interface IUbigeoDao extends CrudRepository<Distrito, Long> {
	
	@Query("select d from Departamento d where UPPER(d.nombre) like CONCAT('%',UPPER(?1),'%') order by d.nombre asc")
	public List<Departamento> autocompleteDepartamento(String term);
	
	@Query("select p from Provincia p where p.departamento.id = ?2 and UPPER(p.nombre) like CONCAT('%',UPPER(?1),'%') order by p.nombre asc")
	public List<Provincia> autocompleteProvinciaByDepartamento(String term, Long idDepartamento);
	
	@Query("select d from Distrito d where d.provincia.id = ?2 and UPPER(d.nombre) like CONCAT('%',UPPER(?1),'%') order by d.nombre asc")
	public List<Distrito> autocompleteDistritoByProvincia(String term, Long idProvincia);
}

package com.tevo.frutas.models.dao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tevo.frutas.models.entity.maestros.Almacen;

public interface IAlmacenDao extends JpaRepository<Almacen, Long> {

	@Query("select a from Almacen a where UPPER(a.descripcion) like CONCAT('%',UPPER(?1),'%') order by a.descripcion asc")
	public List<Almacen> autocompleteList(String term);
	
	@Query("select a from Almacen a order by a.descripcion asc")
	public List<Almacen> listDropdown();
}

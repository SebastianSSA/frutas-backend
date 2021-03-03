package com.tevo.frutas.models.dao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tevo.frutas.models.entity.maestros.Fruta;

public interface IFrutaDao extends JpaRepository<Fruta, Long> {
	
	@Query("select f from Fruta f where UPPER(f.nombre) = UPPER(?1)")
	public Fruta findByNombre(String nombre);

	@Query("select f from Fruta f where UPPER(f.nombre) like CONCAT('%',UPPER(?1),'%') order by f.nombre asc")
	public List<Fruta> autocompleteList(String term);
}

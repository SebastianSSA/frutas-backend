package com.tevo.frutas.models.dao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tevo.frutas.models.entity.maestros.FrutaVariedad;

public interface IFrutaVariedadDao extends JpaRepository<FrutaVariedad, Long> {

	@Query("select f from FrutaVariedad f where f.fruta.id = ?2 and UPPER(f.descripcion) = UPPER(?1)")
	public FrutaVariedad findByDescripcion(String descripcion, Long idFruta);
	
	@Query("select f from FrutaVariedad f where f.fruta.id = ?2 and UPPER(f.descripcion) like CONCAT('%',UPPER(?1),'%') order by f.descripcion asc")
	public List<FrutaVariedad> autocompleteListByFruta(String term, Long idFruta);
}

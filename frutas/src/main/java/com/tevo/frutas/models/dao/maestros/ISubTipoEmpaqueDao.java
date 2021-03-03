package com.tevo.frutas.models.dao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tevo.frutas.models.entity.maestros.SubTipoEmpaque;

public interface ISubTipoEmpaqueDao extends JpaRepository<SubTipoEmpaque, Long>{

	@Query("select s from SubTipoEmpaque s where s.tipoEmpaque.tablaAuxiliarDetalleId.id = ?2 and UPPER(s.nombre) like CONCAT('%',UPPER(?1),'%') order by s.nombre asc")
	public List<SubTipoEmpaque> autocompleteList(String term, Long idTipoEmpaque);
	
	@Query("select s from SubTipoEmpaque s where s.tipoEmpaque.tablaAuxiliarDetalleId.id = ?1 order by s.nombre asc")
	public List<SubTipoEmpaque> listDropdownByEmpaque(Long idTipoEmpaque);
}

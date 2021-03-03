 package com.tevo.frutas.models.dao.maestros;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tevo.frutas.models.entity.maestros.Proveedor;

public interface IProveedorDao extends JpaRepository<Proveedor, Long> {
	
	@Query("select p from Proveedor p where UPPER(p.nickname) = UPPER(?1)")
	public Proveedor findByNickname(String nickname);

	@Query("select p from Proveedor p where UPPER(p.nickname) like CONCAT('%',UPPER(?1),'%')")
	public Page<Proveedor> findAllPageAndSort(String nickname, Pageable pageable);
	
	@Query("select p from Proveedor p where UPPER(p.nickname) like CONCAT('%',UPPER(?1),'%') order by p.nickname asc")
	public List<Proveedor> autocompleteList(String term);
	
	@Query("select p from Proveedor p order by p.nickname asc")
	public List<Proveedor> listDropdown();
}

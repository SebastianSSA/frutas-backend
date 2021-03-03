package com.tevo.frutas.models.dao.ordenventa;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tevo.frutas.models.entity.ordenventa.OrdenVenta;

public interface IOrdenVentaDao extends JpaRepository<OrdenVenta, Long> {

	@Query("select o from OrdenVenta o where UPPER(o.nroOrden) like CONCAT(UPPER(?1),'%')")
	public Page<OrdenVenta> findAllPageAndSort(String nroOrden, Pageable pageable);
	
	public OrdenVenta findFirstByFechaVentaBetweenOrderByFechaVentaDesc(Date fechaIni, Date fechaFin);
}

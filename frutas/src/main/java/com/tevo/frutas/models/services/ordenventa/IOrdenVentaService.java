package com.tevo.frutas.models.services.ordenventa;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.tevo.frutas.models.entity.ordenventa.OrdenVenta;

public interface IOrdenVentaService {

	public OrdenVenta findById(Long id);
	
	public Page<OrdenVenta> findPageAndSort(String nroOrden, String columnSort, int order, int page);
	
	public OrdenVenta save(OrdenVenta ordenVenta);
	
	public String obtenerUltimoCodigo(Date fechaIni, Date fechaFin);
}

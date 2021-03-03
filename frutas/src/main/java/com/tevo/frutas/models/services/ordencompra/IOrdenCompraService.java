package com.tevo.frutas.models.services.ordencompra;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.tevo.frutas.models.entity.ordencompra.OrdenCompra;

public interface IOrdenCompraService {

	public OrdenCompra findById(Long id);
	
	public Page<OrdenCompra> findPageAndSort(String nroOrden, String columnSort, int order, int page);
	
	public Page<OrdenCompra> findAllPageAndSort2(String nroOrden,Date fechaDesde,Date fechaHasta,
			 String comprador,Long proveedorId,Long tipoCompraId,
			 Long tipoOrdenId,Long estadoOrdenId,String columnSort,int order, int page);
	
	public OrdenCompra save(OrdenCompra ordenCompra);
	
	public String obtenerUltimoCodigo(Date fechaIni, Date fechaFin);
	
	public List<String> autocompleteEmpaquetado(Long id, String codigo);
}

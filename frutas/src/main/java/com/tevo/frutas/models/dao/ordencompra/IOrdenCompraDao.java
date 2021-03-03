package com.tevo.frutas.models.dao.ordencompra;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tevo.frutas.models.entity.ordencompra.OrdenCompra;

public interface IOrdenCompraDao extends JpaRepository<OrdenCompra, Long> {

	@Query("select o from OrdenCompra o where UPPER(o.nroOrden) like CONCAT(UPPER(?1),'%')")
	public Page<OrdenCompra> findAllPageAndSort(String nroOrden, Pageable pageable);
	
	@Query("  select o from OrdenCompra o "
			+ "where (UPPER(o.nroOrden) like CONCAT(UPPER(:nroOrden),'%')) "
			+ "and (o.fechaCompra>=:fechaDesde) "
			+ "and (o.fechaCompra<=:fechaHasta) "
			+ "and (UPPER(o.comprador)  like CONCAT(UPPER(:comprador),'%')) "
			+ "and ((0L=:proveedorId) 	or (o.proveedor.id=:proveedorId)) "
			+ "and ((0L=:tipoCompraId)  or (o.tipoCompra.tablaAuxiliarDetalleId.id=:tipoCompraId)) "
			+ "and ((0L=:tipoOrdenId) 	or (o.tipoOrden.tablaAuxiliarDetalleId.id=:tipoOrdenId)) "
			+ "and ((0L=:estadoOrdenId) or (o.estadoOrdenCompra.tablaAuxiliarDetalleId.id=:estadoOrdenId)) ")
	public Page<OrdenCompra> findAllPageAndSort2(String nroOrden,Date fechaDesde,Date fechaHasta,
												 String comprador,Long proveedorId,Long tipoCompraId,
												 Long tipoOrdenId,Long estadoOrdenId,Pageable pageable);
	
	public OrdenCompra findFirstByFechaCompraBetweenOrderByFechaCompraDesc(Date fechaIni, Date fechaFin);
	
	@Query(	"SELECT emp.identificador FROM OrdenCompraDetalle d "
			+ "LEFT JOIN d.empaques emp "
			+ "WHERE d.fruta.id = ?1 "
			+ "AND UPPER(emp.identificador) like CONCAT(UPPER(?2),'%') ")
	public List<String> autocompleteEmpaquetado(Long id,String codigo);
}

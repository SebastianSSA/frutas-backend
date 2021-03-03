package com.tevo.frutas.models.services.ordencompra;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tevo.frutas.models.dao.ordencompra.IOrdenCompraDao;
import com.tevo.frutas.models.entity.ordencompra.OrdenCompra;

@Service
public class OrdenCompraServiceImpl implements IOrdenCompraService {
	
	private static final Integer ITEMS_PER_PAGE = 10;
	
	@Autowired
	private IOrdenCompraDao ordenCompraDao;

	@Override
	@Transactional(readOnly = true)
	public OrdenCompra findById(Long id) {
		return ordenCompraDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OrdenCompra> findPageAndSort(String nroOrden, String columnSort, int order, int page) {
		Pageable sorted = null;
		if (order == 0) {
			sorted = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(columnSort).ascending());
		} else {
			sorted = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(columnSort).descending());
		}
		
		return ordenCompraDao.findAllPageAndSort(nroOrden, sorted);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<OrdenCompra> findAllPageAndSort2(String nroOrden, Date fechaDesde, Date fechaHasta, String comprador,
			Long proveedorId, Long tipoCompraId, Long tipoOrdenId, Long estadoOrdenId,String columnSort, int order, int page) {
		Pageable sorted = null;
		if (order == 0) {
			sorted = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(columnSort).ascending());
		} else {
			sorted = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(columnSort).descending());
		}
//		logger.info(nroOrden+ ','+fechaDesde+ ','+fechaHasta+ ','+comprador+ ','+proveedorId+ ','+tipoCompraId
//				+ ','+tipoOrdenId+ ','+estadoOrdenId+ ','+columnSort+ ','+order+ ','+page);
		return ordenCompraDao.findAllPageAndSort2(nroOrden, fechaDesde, fechaHasta,
				 								  comprador, proveedorId, tipoCompraId,
				 								  tipoOrdenId, estadoOrdenId, sorted);
	}

	@Override
	@Transactional()
	public OrdenCompra save(OrdenCompra ordenCompra) {
		return ordenCompraDao.save(ordenCompra);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String obtenerUltimoCodigo(Date fechaIni, Date fechaFin) {
		if (ordenCompraDao.findFirstByFechaCompraBetweenOrderByFechaCompraDesc(fechaIni, fechaFin) == null) {
			return "0000-99";
		}
		return ordenCompraDao.findFirstByFechaCompraBetweenOrderByFechaCompraDesc(fechaIni, fechaFin).getNroOrden();
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> autocompleteEmpaquetado(Long id, String codigo) {
		return ordenCompraDao.autocompleteEmpaquetado(id,codigo);		
	}

}

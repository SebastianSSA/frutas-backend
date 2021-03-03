package com.tevo.frutas.models.services.ordenventa;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tevo.frutas.models.dao.ordenventa.IOrdenVentaDao;
import com.tevo.frutas.models.entity.ordenventa.OrdenVenta;

@Service
public class OrdenVentaServiceImpl implements IOrdenVentaService {
	
	private static final Integer ITEMS_PER_PAGE = 10;

	@Autowired
	private IOrdenVentaDao ordenVentaDao;
	
	@Override
	@Transactional(readOnly = true)
	public OrdenVenta findById(Long id) {
		return ordenVentaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OrdenVenta> findPageAndSort(String nroOrden, String columnSort, int order, int page) {
		Pageable sorted = null;
		if (order == 0) {
			sorted = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(columnSort).ascending());
		} else {
			sorted = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(columnSort).descending());
		}
		return ordenVentaDao.findAllPageAndSort(nroOrden, sorted);
	}

	@Override
	@Transactional()
	public OrdenVenta save(OrdenVenta ordenVenta) {
		return ordenVentaDao.save(ordenVenta);
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerUltimoCodigo(Date fechaIni, Date fechaFin) {
		if (ordenVentaDao.findFirstByFechaVentaBetweenOrderByFechaVentaDesc(fechaIni, fechaFin) == null) {
			return "0000-99";
		}
		return ordenVentaDao.findFirstByFechaVentaBetweenOrderByFechaVentaDesc(fechaIni, fechaFin).getNroOrden();
	}

}

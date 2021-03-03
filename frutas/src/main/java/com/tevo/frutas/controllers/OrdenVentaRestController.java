package com.tevo.frutas.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tevo.frutas.models.entity.ordenventa.OrdenVenta;
import com.tevo.frutas.models.services.ordenventa.IOrdenVentaService;

@RestController
@RequestMapping("/api")
public class OrdenVentaRestController {
	
	@Autowired
	private IOrdenVentaService ordenVentaService;
	
	@GetMapping("/orden_venta/page/{nroOrden}/{columnSort}/{order}/{page}")
	public Page<OrdenVenta> index(@PathVariable String nroOrden,
			@PathVariable String columnSort,
			@PathVariable Integer order,
			@PathVariable Integer page) {
		
		if (nroOrden.equals("nroOrden")) {
			nroOrden = "";
		}	
		
		return ordenVentaService.findPageAndSort(nroOrden, columnSort, order, page);
	}
	
	@GetMapping("/orden_venta/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		OrdenVenta ordenVenta = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			ordenVenta = ordenVentaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (ordenVenta == null) {
			response.put("mensaje", "La orden de venta no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<OrdenVenta>(ordenVenta, HttpStatus.OK); 
	}
	
	@PostMapping("/orden_venta")
	public ResponseEntity<?> create(@Valid @RequestBody OrdenVenta ordenVenta, BindingResult result) throws ParseException {
		OrdenVenta ordenVentaNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			String nroOrden = obtenerNuevoNro();		
			ordenVenta.setNroOrden(nroOrden);
			ordenVentaNew = ordenVentaService.save(ordenVenta);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Orden de venta registrada exitosamente");
		response.put("ordenVenta", ordenVentaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/orden_venta/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody OrdenVenta ordenVenta, BindingResult result, @PathVariable Long id) {
		OrdenVenta ordenVentaActual = ordenVentaService.findById(id);
		OrdenVenta ordenVentaUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (ordenVentaActual == null) {
			response.put("mensaje", "Error, no se pudo editar: La orden de venta no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			ordenVentaActual.setNroOrden(ordenVenta.getNroOrden());
			ordenVentaActual.setVendedor(ordenVenta.getVendedor());
			ordenVentaActual.setTipoCliente(ordenVenta.getTipoCliente());
			ordenVentaActual.setCliente(ordenVenta.getCliente());
			ordenVentaActual.setNombreCliente(ordenVenta.getNombreCliente());
			ordenVentaActual.setTipoVenta(ordenVenta.getTipoVenta());
			ordenVentaActual.setTipoOrdenVenta(ordenVenta.getTipoOrdenVenta());
			ordenVentaActual.setMoneda(ordenVenta.getMoneda());
			ordenVentaActual.setFormaPagoVenta(ordenVenta.getFormaPagoVenta());
			ordenVentaActual.setIndIgv(ordenVenta.getIndIgv());
			ordenVentaActual.setTotal(ordenVenta.getTotal());
			ordenVentaActual.setFechaVenta(ordenVenta.getFechaVenta());
			ordenVentaActual.setAlmacen(ordenVenta.getAlmacen());
			ordenVentaActual.setDetalle(ordenVenta.getDetalle());
			ordenVentaActual.setEstadoOrdenVenta(ordenVenta.getEstadoOrdenVenta());
			
			ordenVentaUpdate = ordenVentaService.save(ordenVentaActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Proveedor actualizado exitosamente");
		response.put("ordenVenta", ordenVentaUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	public String obtenerNuevoNro() throws ParseException {
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR);
		
		String fechaI = "01/01/".concat(year.toString());
		String fechaF = "31/12/".concat(year.toString());
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIni =  format.parse(fechaI);
		
		Date fechaFin =  format.parse(fechaF);	

		String nroOrden = ordenVentaService.obtenerUltimoCodigo(fechaIni, fechaFin);
		
		String parteCadena = "";
		Integer nroOr = Integer.parseInt(nroOrden.substring(0, 4)) + 1;
		parteCadena = "0000".concat(nroOr.toString());
		
		String nroOrdenNuevo = "";
		year = year - 2000;
		nroOrdenNuevo = parteCadena.substring(parteCadena.length() - 4).concat("-").concat(year.toString());
		
		return nroOrdenNuevo;
	}
}

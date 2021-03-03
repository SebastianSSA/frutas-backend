package com.tevo.frutas.controllers;

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

import com.tevo.frutas.models.entity.maestros.Proveedor;
import com.tevo.frutas.models.services.maestros.IMaestroService;

@RestController
@RequestMapping("/api")
public class ProveedorRestController {

	@Autowired
	private IMaestroService maestroService;
	
	@GetMapping("/proveedor/autocomplete/{nickname}")
	public List<Proveedor> autocompletado(@PathVariable String nickname) {
		if (nickname.equals("inexistente")) {
			nickname = "";
		}
		
		return maestroService.autocompleteProveedor(nickname);
	}
	
	@GetMapping("/proveedor/combo_box")
	public List<Proveedor> comboBox() {
		return maestroService.listProveedorDropdown();
	}
	
	@GetMapping("/proveedor/page/{nickname}/{columnSort}/{order}/{page}")
	public Page<Proveedor> index(@PathVariable String nickname,
			@PathVariable String columnSort,
			@PathVariable Integer order,
			@PathVariable Integer page) {
		
		if (nickname.equals("nickname")) {
			nickname = "";
		}	
		
		return maestroService.findProveedorPageAndSort(nickname, columnSort, order, page);
	}
	
	@GetMapping("/proveedor/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Proveedor proveedor = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			proveedor = maestroService.findProveedorById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (proveedor == null) {
			response.put("mensaje", "El Proveedor no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Proveedor>(proveedor, HttpStatus.OK); 
	}
	
	@GetMapping("/proveedor/nombre/{nickname}")
	public ResponseEntity<?> showNickname(@PathVariable String nickname) {
		Proveedor proveedor = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			proveedor = maestroService.findProveedorByNickname(nickname);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (proveedor == null) {
			response.put("mensaje", "El Proveedor no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Proveedor>(proveedor, HttpStatus.OK); 
	}
	
	@PostMapping("/proveedor")
	public ResponseEntity<?> create(@Valid @RequestBody Proveedor proveedor, BindingResult result) {
		Proveedor proveedorNew = null;
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
			proveedorNew = maestroService.saveProveedor(proveedor);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Proveedor registrado exitosamente");
		response.put("proveedor", proveedorNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/proveedor/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Proveedor proveedor, BindingResult result, @PathVariable Long id) {
		Proveedor proveedorActual = maestroService.findProveedorById(id);
		Proveedor proveedorUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (proveedorActual == null) {
			response.put("mensaje", "Error, no se pudo editar: El proveedor no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			proveedorActual.setTipoProveedor(proveedor.getTipoProveedor());
			proveedorActual.setNickname(proveedor.getNickname());
			proveedorActual.setApellidoPaterno(proveedor.getApellidoPaterno());
			proveedorActual.setApellidoMaterno(proveedor.getApellidoMaterno());
			proveedorActual.setNombres(proveedor.getNombres());
			proveedorActual.setDni(proveedor.getDni());
			proveedorActual.setTelefono1(proveedor.getTelefono1());
			proveedorActual.setTelefono2(proveedor.getTelefono2());
			proveedorActual.setTipoVia(proveedor.getTipoVia());
			proveedorActual.setDireccion(proveedor.getDireccion());
			proveedorActual.setNroDireccion(proveedor.getNroDireccion());
			proveedorActual.setUrbanizacion(proveedor.getUrbanizacion());
			proveedorActual.setDistrito(proveedor.getDistrito());
			proveedorActual.setZona(proveedor.getZona());
			proveedorActual.setRuc(proveedor.getRuc());
			proveedorActual.setRazonSocial(proveedor.getRazonSocial());
			proveedorActual.setTipoOferta(proveedor.getTipoOferta());
			proveedorActual.setSubTipoOferta(proveedor.getSubTipoOferta());
			proveedorActual.setObservacion(proveedor.getObservacion());
			proveedorActual.setFoto(proveedor.getFoto());
			proveedorActual.setFechaBaja(proveedor.getFechaBaja());
			proveedorActual.setEstadoProveedor(proveedor.getEstadoProveedor());
			
			proveedorUpdate = maestroService.saveProveedor(proveedorActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Proveedor actualizado exitosamente");
		response.put("proveedor", proveedorUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}

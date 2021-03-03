package com.tevo.frutas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tevo.frutas.models.entity.maestros.FrutaVariedad;
import com.tevo.frutas.models.services.maestros.IMaestroService;

@RestController
@RequestMapping("/api")
public class FrutaVariedadRestController {

	@Autowired
	private IMaestroService maestroService;
	
	@GetMapping("/variedad_fruta/autocomplete/{term}/{idFruta}")
	public List<FrutaVariedad> autocompletado(@PathVariable String term, @PathVariable Long idFruta) {
		if (term.equals("inexistente")) {
			term = "";
		}
		
		return maestroService.autocompleteFrutaVariedad(term, idFruta);
	}
	
	@GetMapping("/variedad_fruta/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		FrutaVariedad frutaVariedad = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			frutaVariedad = maestroService.findFrutaVariedadById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (frutaVariedad == null) {
			response.put("mensaje", "La variedada de fruta no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<FrutaVariedad>(frutaVariedad, HttpStatus.OK); 
	}
	
	@GetMapping("/variedad_fruta/nombre/{descripcion}/{idFruta}")
	public ResponseEntity<?> showDescripcion(@PathVariable String descripcion, @PathVariable Long idFruta) {
		if (descripcion.equals("inexistente")) {
			descripcion = "";
		}
		
		FrutaVariedad frutaVariedad = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			frutaVariedad = maestroService.findFrutaVariedadByDescripcion(descripcion, idFruta);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (frutaVariedad == null) {
			response.put("mensaje", "La variedad no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<FrutaVariedad>(frutaVariedad, HttpStatus.OK); 
	}
}

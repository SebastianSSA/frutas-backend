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

import com.tevo.frutas.models.entity.maestros.Fruta;
import com.tevo.frutas.models.services.maestros.IMaestroService;

@RestController
@RequestMapping("/api")
public class FrutaRestController {

	@Autowired
	private IMaestroService maestroService;
	
	@GetMapping("/fruta/autocomplete/{term}")
	public List<Fruta> autocompletado(@PathVariable String term) {
		if (term.equals("inexistente")) {
			term = "";
		}
		
		return maestroService.autocompleteFruta(term);
	}
	
	@GetMapping("/fruta/nombre/{nombre}")
	public ResponseEntity<?> showNombre(@PathVariable String nombre) {
		Fruta fruta = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			fruta = maestroService.findFrutaByNombre(nombre);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (fruta == null) {
			response.put("mensaje", "La fruta no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Fruta>(fruta, HttpStatus.OK); 
	}
}

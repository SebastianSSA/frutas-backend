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

import com.tevo.frutas.models.entity.maestros.Almacen;
import com.tevo.frutas.models.services.maestros.IMaestroService;

@RestController
@RequestMapping("/api")
public class AlmacenRestController {

	@Autowired
	private IMaestroService maestroService;
	
	@GetMapping("/almacen/autocomplete/{term}")
	public List<Almacen> autocompletado(@PathVariable String term) {
		if (term.equals("inexistente")) {
			term = "";
		}
		
		return maestroService.autocompleteAlmacen(term);
	}
	
	@GetMapping("/almacen/combo_box")
	public List<Almacen> comboBox() {
		return maestroService.listAlmacenDropdown();
	}
	
	@GetMapping("/almacen/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Almacen almacen = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			almacen = maestroService.findAlmacenById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (almacen == null) {
			response.put("mensaje", "El almacen no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Almacen>(almacen, HttpStatus.OK); 
	}
}

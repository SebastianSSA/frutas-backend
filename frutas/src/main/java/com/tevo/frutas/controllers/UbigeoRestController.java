package com.tevo.frutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tevo.frutas.models.entity.ubigeo.Departamento;
import com.tevo.frutas.models.entity.ubigeo.Distrito;
import com.tevo.frutas.models.entity.ubigeo.Provincia;
import com.tevo.frutas.models.services.ubigeo.IUbigeoService;

@RestController
@RequestMapping("/api")
public class UbigeoRestController {

	@Autowired
	private IUbigeoService ubigeoService;
	
	@GetMapping("/ubigeo/autocomplete/departamento/{term}")
	public List<Departamento> autocompletadoDepartamento(@PathVariable String term) {
		if (term.equals("inexistente")) {
			term = "";
		}
		
		return ubigeoService.autocompleteDepartamento(term);
	}
	
	@GetMapping("/ubigeo/autocomplete/provincia/{term}/{idDepartamento}")
	public List<Provincia> autocompletadoProvincia(@PathVariable String term, @PathVariable Long idDepartamento) {
		if (term.equals("inexistente")) {
			term = "";
		}
		
		return ubigeoService.autocompleteProvinciaByDepartamento(term, idDepartamento);
	}
	
	@GetMapping("/ubigeo/autocomplete/distrito/{term}/{idProvincia}")
	public List<Distrito> autocompletadoDistrito(@PathVariable String term, @PathVariable Long idProvincia) {
		if (term.equals("inexistente")) {
			term = "";
		}
		
		return ubigeoService.autocompleteDistritoByProvincia(term, idProvincia);
	}
}

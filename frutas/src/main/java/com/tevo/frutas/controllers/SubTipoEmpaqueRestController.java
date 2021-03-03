package com.tevo.frutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tevo.frutas.models.entity.maestros.SubTipoEmpaque;
import com.tevo.frutas.models.services.maestros.IMaestroService;

@RestController
@RequestMapping("/api")
public class SubTipoEmpaqueRestController {

	
	@Autowired
	private IMaestroService maestroService;
	
	@GetMapping("/sub_tipo_empaque/autocomplete/{term}/{idTipoEmpaque}")
	public List<SubTipoEmpaque> autocompletado(@PathVariable String term, @PathVariable Long idTipoEmpaque) {
		if (term.equals("inexistente")) {
			term = "";
		}
		
		return maestroService.autocompleteSubTipoEmpaque(term, idTipoEmpaque);
	}
	
	@GetMapping("/sub_tipo_empaque/combo_box/{idTipoEmpaque}")
	public List<SubTipoEmpaque> comboBox(@PathVariable Long idTipoEmpaque) {
		return maestroService.listSubTipoEmpaqueDropdownByEmpaque(idTipoEmpaque);
	}
}

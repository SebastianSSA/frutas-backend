package com.tevo.frutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tevo.frutas.models.entity.maestros.Cliente;
import com.tevo.frutas.models.services.maestros.IMaestroService;

@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IMaestroService maestroService;
	
	@GetMapping("/cliente/combo_box")
	public List<Cliente> comboBox() {
		return maestroService.listClienteDropdown();
	}
}

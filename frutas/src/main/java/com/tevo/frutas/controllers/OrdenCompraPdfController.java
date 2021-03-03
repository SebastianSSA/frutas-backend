package com.tevo.frutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tevo.frutas.models.entity.ordencompra.OrdenCompra;
import com.tevo.frutas.models.services.ordencompra.IOrdenCompraService;

@Controller
@RequestMapping("/api")
public class OrdenCompraPdfController {

	@Autowired
	private IOrdenCompraService ordenCompraService;
	
	@GetMapping("/orden_compra/pdf/{id}")
	public String getPdfById(@PathVariable(value = "id") Long id, Model model) {
		OrdenCompra ordenCompra;
		
		try {
			ordenCompra = ordenCompraService.findById(id);
		} catch (DataAccessException e) {
			ordenCompra = null;
		}
		
		model.addAttribute("ordenCompra", ordenCompra);
		
		return "compra/pdf";	
	}
}

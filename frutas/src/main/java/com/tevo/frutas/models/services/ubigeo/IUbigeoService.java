package com.tevo.frutas.models.services.ubigeo;

import java.util.List;

import com.tevo.frutas.models.entity.ubigeo.Departamento;
import com.tevo.frutas.models.entity.ubigeo.Distrito;
import com.tevo.frutas.models.entity.ubigeo.Provincia;

public interface IUbigeoService {

	public List<Departamento> autocompleteDepartamento(String term);
	
	public List<Provincia> autocompleteProvinciaByDepartamento(String term, Long idDepartamento);
	
	public List<Distrito> autocompleteDistritoByProvincia(String term, Long idProvincia);
}

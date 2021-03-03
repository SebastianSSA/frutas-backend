package com.tevo.frutas.models.services.ubigeo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tevo.frutas.models.dao.ubigeo.IUbigeoDao;
import com.tevo.frutas.models.entity.ubigeo.Departamento;
import com.tevo.frutas.models.entity.ubigeo.Distrito;
import com.tevo.frutas.models.entity.ubigeo.Provincia;

@Service
public class UbigeoServiceImpl implements IUbigeoService {
	
	@Autowired
	private IUbigeoDao ubigeoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> autocompleteDepartamento(String term) {
		return ubigeoDao.autocompleteDepartamento(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Provincia> autocompleteProvinciaByDepartamento(String term, Long idDepartamento) {
		return ubigeoDao.autocompleteProvinciaByDepartamento(term, idDepartamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Distrito> autocompleteDistritoByProvincia(String term, Long idProvincia) {
		return ubigeoDao.autocompleteDistritoByProvincia(term, idProvincia);
	}

}

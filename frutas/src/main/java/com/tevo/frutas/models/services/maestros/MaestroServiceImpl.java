package com.tevo.frutas.models.services.maestros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tevo.frutas.models.dao.maestros.IAlmacenDao;
import com.tevo.frutas.models.dao.maestros.IClienteDao;
import com.tevo.frutas.models.dao.maestros.IFrutaDao;
import com.tevo.frutas.models.dao.maestros.IFrutaVariedadDao;
import com.tevo.frutas.models.dao.maestros.IProveedorDao;
import com.tevo.frutas.models.dao.maestros.ISubTipoEmpaqueDao;
import com.tevo.frutas.models.entity.maestros.Almacen;
import com.tevo.frutas.models.entity.maestros.Cliente;
import com.tevo.frutas.models.entity.maestros.Fruta;
import com.tevo.frutas.models.entity.maestros.FrutaVariedad;
import com.tevo.frutas.models.entity.maestros.Proveedor;
import com.tevo.frutas.models.entity.maestros.SubTipoEmpaque;


@Service
public class MaestroServiceImpl implements IMaestroService {
	
	private static final Integer ITEMS_PER_PAGE = 10;
	
	@Autowired
	private IAlmacenDao almacenDao;
	
	@Autowired
	private IFrutaDao frutaDao;
	
	@Autowired
	private IFrutaVariedadDao frutaVariedadDao;
	
	@Autowired
	private ISubTipoEmpaqueDao subTipoEmpaqueDao;
	
	@Autowired
	private IProveedorDao proveedorDao;
	
	@Autowired
	private IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public Almacen findAlmacenById(Long id) {
		return almacenDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Almacen> autocompleteAlmacen(String term) {
		return almacenDao.autocompleteList(term);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Almacen> listAlmacenDropdown() {
		return almacenDao.listDropdown();
	}

	@Override
	@Transactional(readOnly = true)
	public Fruta findFrutaByNombre(String nombre) {
		return frutaDao.findByNombre(nombre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Fruta findFrutaById(Long id) {
		return frutaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Fruta> autocompleteFruta(String term) {
		return frutaDao.autocompleteList(term);
	}

	@Override
	@Transactional(readOnly = true)
	public FrutaVariedad findFrutaVariedadByDescripcion(String descripcion, Long idFruta) {
		return frutaVariedadDao.findByDescripcion(descripcion, idFruta);
	}
	
	@Override
	@Transactional(readOnly = true)
	public FrutaVariedad findFrutaVariedadById(Long id) {
		return frutaVariedadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FrutaVariedad> autocompleteFrutaVariedad(String term, Long idFruta) {
		return frutaVariedadDao.autocompleteListByFruta(term, idFruta);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SubTipoEmpaque findSubTipoEmpaqueById(Long id) {
		return subTipoEmpaqueDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SubTipoEmpaque> autocompleteSubTipoEmpaque(String term, Long idTipoEmpaque) {
		return subTipoEmpaqueDao.autocompleteList(term, idTipoEmpaque);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<SubTipoEmpaque> listSubTipoEmpaqueDropdownByEmpaque(Long idTipoEmpaque) {
		return subTipoEmpaqueDao.listDropdownByEmpaque(idTipoEmpaque);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proveedor findProveedorById(Long id) {
		return proveedorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Proveedor findProveedorByNickname(String nickname) {
		return proveedorDao.findByNickname(nickname);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Proveedor> findProveedorPageAndSort(String nickname, String columnSort, int order, int page) {
		Pageable sorted = null;
		if (order == 0) {
			sorted = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(columnSort).ascending());
		} else {
			sorted = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(columnSort).descending());
		}
		
		return proveedorDao.findAllPageAndSort(nickname, sorted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> autocompleteProveedor(String nickname) {
		return proveedorDao.autocompleteList(nickname);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> listProveedorDropdown() {
		return proveedorDao.listDropdown();
	}

	@Override
	@Transactional()
	public Proveedor saveProveedor(Proveedor proveedor) {
		return proveedorDao.save(proveedor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> listClienteDropdown() {
		return clienteDao.listDropdown();
	}
	
	
}

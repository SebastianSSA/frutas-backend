package com.tevo.frutas.models.services.maestros;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tevo.frutas.models.entity.maestros.Almacen;
import com.tevo.frutas.models.entity.maestros.Cliente;
import com.tevo.frutas.models.entity.maestros.Fruta;
import com.tevo.frutas.models.entity.maestros.FrutaVariedad;
import com.tevo.frutas.models.entity.maestros.Proveedor;
import com.tevo.frutas.models.entity.maestros.SubTipoEmpaque;

public interface IMaestroService {
	
	//Almacen
	public Almacen findAlmacenById(Long id);
	
	public List<Almacen> autocompleteAlmacen(String term);
	
	public List<Almacen> listAlmacenDropdown();
	//
	
	//Fruta
	public Fruta findFrutaById(Long id);
	
	public Fruta findFrutaByNombre(String nombre);
	
	public List<Fruta> autocompleteFruta(String term);
	//
	
	//FrutaVariedad
	public FrutaVariedad findFrutaVariedadById(Long id);
	
	public FrutaVariedad findFrutaVariedadByDescripcion(String descripcion, Long idFruta);
	
	public List<FrutaVariedad> autocompleteFrutaVariedad(String term, Long idFruta);
	//
	
	//SubTipoEmpaque
	public SubTipoEmpaque findSubTipoEmpaqueById(Long id);
	
	public List<SubTipoEmpaque> autocompleteSubTipoEmpaque(String term, Long idTipoEmpaque);
	
	public List<SubTipoEmpaque> listSubTipoEmpaqueDropdownByEmpaque(Long idTipoEmpaque);
	//
	
	//Proveedor
	public Proveedor findProveedorById(Long id);
	
	public Proveedor findProveedorByNickname(String nickname);
	
	public Page<Proveedor> findProveedorPageAndSort(String nickname, String columnSort, int order, int page);
	
	public List<Proveedor> autocompleteProveedor(String nickname);
	
	public List<Proveedor> listProveedorDropdown();
	
	public Proveedor saveProveedor(Proveedor proveedor);
	//
	
	//Cliente
	public List<Cliente> listClienteDropdown();
	//
}

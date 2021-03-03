package com.tevo.frutas.models.services.auxiliares;

import java.util.List;

import com.tevo.frutas.models.entity.auxiliares.TablaAuxiliarDetalle;

public interface IConfiguracionService {
	//Tabla Auxiliar Detalle
	public TablaAuxiliarDetalle findTablaAuxiliarDetalleById(Long id, String codTablaAuxiliar);

	public TablaAuxiliarDetalle findTablaAuxiliarDetalleByNombre(String nombre, String codTablaAuxiliar);
	
	public List<String> listTablaAuxiliarDetalleByCodigo(String codTablaAuxiliar);
	
	public List<TablaAuxiliarDetalle> autocompleteList(String codTablaAuxiliar, String nombre);
	
	public List<TablaAuxiliarDetalle> listTablaAuxiliarDetalleDropdownByCodigo(String codTablaAuxiliar);
	
	public List<TablaAuxiliarDetalle> listTablaAuxiliarDetalleDropdownByCodigoMerma(String codTablaAuxiliar, Long id);
}

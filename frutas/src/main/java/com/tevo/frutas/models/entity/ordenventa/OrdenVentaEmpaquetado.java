package com.tevo.frutas.models.entity.ordenventa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ove_orden_venta_empaquetado")
public class OrdenVentaEmpaquetado implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigoJaba;
	
	private Double cantidadVendida;
	
	private String observacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoJaba() {
		return codigoJaba;
	}

	public void setCodigoJaba(String codigoJaba) {
		this.codigoJaba = codigoJaba;
	}

	public Double getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(Double cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	private static final long serialVersionUID = 1L;

}

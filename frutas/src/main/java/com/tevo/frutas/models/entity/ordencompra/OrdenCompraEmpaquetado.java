package com.tevo.frutas.models.entity.ordencompra;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tevo.frutas.models.entity.auxiliares.TablaAuxiliarDetalle;
import com.tevo.frutas.models.entity.maestros.Almacen;
import com.tevo.frutas.models.entity.maestros.SubTipoEmpaque;

@Entity
@Table(name = "oco_orden_compra_empaquetado")
public class OrdenCompraEmpaquetado implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private SubTipoEmpaque subTipoEmpaque;
	
	private Double pesoCompra;
	
	private String identificador;
	
	private String observacion;
	
	private Double pesoRecepcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Almacen almacen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle motivoNoIngreso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle categoriaMerma1;
	
	private Double pesoMerma1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle categoriaMerma2;
	
	private Double pesoMerma2;
	
	@Column(name = "id_usuario_crea", updatable = false)
	private Integer idUsuarioCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_crea", updatable = false)
	private Date fechaCrea;
	
	@Column(name = "id_usuario_modifica")
	private Integer idUsuarioModifica;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modifica")
	private Date fechaModifica;
	
	@PrePersist
	public void prePersist() {
		fechaCrea = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubTipoEmpaque getSubTipoEmpaque() {
		return subTipoEmpaque;
	}

	public void setSubTipoEmpaque(SubTipoEmpaque subTipoEmpaque) {
		this.subTipoEmpaque = subTipoEmpaque;
	}

	public Double getPesoCompra() {
		return pesoCompra;
	}

	public void setPesoCompra(Double pesoCompra) {
		this.pesoCompra = pesoCompra;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Double getPesoRecepcion() {
		return pesoRecepcion;
	}

	public void setPesoRecepcion(Double pesoRecepcion) {
		this.pesoRecepcion = pesoRecepcion;
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public TablaAuxiliarDetalle getMotivoNoIngreso() {
		return motivoNoIngreso;
	}

	public void setMotivoNoIngreso(TablaAuxiliarDetalle motivoNoIngreso) {
		this.motivoNoIngreso = motivoNoIngreso;
	}

	public TablaAuxiliarDetalle getCategoriaMerma1() {
		return categoriaMerma1;
	}

	public void setCategoriaMerma1(TablaAuxiliarDetalle categoriaMerma1) {
		this.categoriaMerma1 = categoriaMerma1;
	}

	public Double getPesoMerma1() {
		return pesoMerma1;
	}

	public void setPesoMerma1(Double pesoMerma1) {
		this.pesoMerma1 = pesoMerma1;
	}

	public TablaAuxiliarDetalle getCategoriaMerma2() {
		return categoriaMerma2;
	}

	public void setCategoriaMerma2(TablaAuxiliarDetalle categoriaMerma2) {
		this.categoriaMerma2 = categoriaMerma2;
	}

	public Double getPesoMerma2() {
		return pesoMerma2;
	}

	public void setPesoMerma2(Double pesoMerma2) {
		this.pesoMerma2 = pesoMerma2;
	}

	public Integer getIdUsuarioCrea() {
		return idUsuarioCrea;
	}

	public void setIdUsuarioCrea(Integer idUsuarioCrea) {
		this.idUsuarioCrea = idUsuarioCrea;
	}

	public Date getFechaCrea() {
		return fechaCrea;
	}
//
//	public void setFechaCrea(Date fechaCrea) {
//		this.fechaCrea = fechaCrea;
//	}

	public Integer getIdUsuarioModifica() {
		return idUsuarioModifica;
	}

	public void setIdUsuarioModifica(Integer idUsuarioModifica) {
		this.idUsuarioModifica = idUsuarioModifica;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	private static final long serialVersionUID = 1L;

}

package com.tevo.frutas.models.entity.maestros;

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

@Entity
@Table(name = "mae_sub_tipo_empaque")
public class SubTipoEmpaque implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle tipoEmpaque;
	
	private String nombre;
	
	private Double capacidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle estado;
	
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

	public TablaAuxiliarDetalle getTipoEmpaque() {
		return tipoEmpaque;
	}

	public void setTipoEmpaque(TablaAuxiliarDetalle tipoEmpaque) {
		this.tipoEmpaque = tipoEmpaque;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Double capacidad) {
		this.capacidad = capacidad;
	}

	public TablaAuxiliarDetalle getEstado() {
		return estado;
	}

	public void setEstado(TablaAuxiliarDetalle estado) {
		this.estado = estado;
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

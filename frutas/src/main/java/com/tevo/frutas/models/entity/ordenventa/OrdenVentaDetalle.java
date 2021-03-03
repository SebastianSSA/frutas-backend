package com.tevo.frutas.models.entity.ordenventa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tevo.frutas.models.entity.auxiliares.TablaAuxiliarDetalle;
import com.tevo.frutas.models.entity.maestros.Fruta;
import com.tevo.frutas.models.entity.maestros.FrutaVariedad;

@Entity
@Table(name = "ove_orden_venta_detalle")
public class OrdenVentaDetalle implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Fruta fruta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private FrutaVariedad frutaVariedad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle categoriaFruta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle tamanoFruta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle unidadVenta;
	
	private String observacion;
	
	private Double precioUnitario;
	
	private Double cantidadFruta;
	
	private Double precioTotal;
	
	private Double descuento;
	
	private Double precioTotalDescuento;
	
	private Double Igv;
	
	private Double precioTotalIgv;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "orden_venta_detalle_id")
	private List<OrdenVentaEmpaquetado> empaques;
	
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
	
	public OrdenVentaDetalle() {
		empaques = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Fruta getFruta() {
		return fruta;
	}

	public void setFruta(Fruta fruta) {
		this.fruta = fruta;
	}

	public FrutaVariedad getFrutaVariedad() {
		return frutaVariedad;
	}

	public void setFrutaVariedad(FrutaVariedad frutaVariedad) {
		this.frutaVariedad = frutaVariedad;
	}

	public TablaAuxiliarDetalle getCategoriaFruta() {
		return categoriaFruta;
	}

	public void setCategoriaFruta(TablaAuxiliarDetalle categoriaFruta) {
		this.categoriaFruta = categoriaFruta;
	}

	public TablaAuxiliarDetalle getTamanoFruta() {
		return tamanoFruta;
	}

	public void setTamanoFruta(TablaAuxiliarDetalle tamanoFruta) {
		this.tamanoFruta = tamanoFruta;
	}

	public TablaAuxiliarDetalle getUnidadVenta() {
		return unidadVenta;
	}

	public void setUnidadVenta(TablaAuxiliarDetalle unidadVenta) {
		this.unidadVenta = unidadVenta;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Double getCantidadFruta() {
		return cantidadFruta;
	}

	public void setCantidadFruta(Double cantidadFruta) {
		this.cantidadFruta = cantidadFruta;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getPrecioTotalDescuento() {
		return precioTotalDescuento;
	}

	public void setPrecioTotalDescuento(Double precioTotalDescuento) {
		this.precioTotalDescuento = precioTotalDescuento;
	}

	public Double getIgv() {
		return Igv;
	}

	public void setIgv(Double igv) {
		Igv = igv;
	}

	public Double getPrecioTotalIgv() {
		return precioTotalIgv;
	}

	public void setPrecioTotalIgv(Double precioTotalIgv) {
		this.precioTotalIgv = precioTotalIgv;
	}

	public List<OrdenVentaEmpaquetado> getEmpaques() {
		return empaques;
	}

	public void setEmpaques(List<OrdenVentaEmpaquetado> empaques) {
		this.empaques = empaques;
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

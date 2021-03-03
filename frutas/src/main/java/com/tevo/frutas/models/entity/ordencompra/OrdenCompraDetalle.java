package com.tevo.frutas.models.entity.ordencompra;

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
@Table(name = "oco_orden_compra_detalle")
public class OrdenCompraDetalle implements Serializable {

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
	
	private String procedencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle tamanoFruta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle estadoFruta;
	
	private String observacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle unidadCompra;
	
	private Double precioUnitario;
	
	private Double cantidadFruta;
	
	private Double precioTotal;
	
	private Double descuento;
	
	private Double precioTotalDescuento;
	
	private Double Igv;
	
	private Double precioTotalIgv;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle formaPago;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_pago")
	private Date fechaPago;
	
	private String responsablePago;
	
	private Boolean indAdicional;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle tipoAdicional;
	
	private Double cantidadAdicional;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_arribo")
	private Date fechaArribo;
	
	private Boolean indIndividualizar;
	
	private Boolean indMerma;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "orden_compra_detalle_id")
	private List<OrdenCompraEmpaquetado> empaques;	
	
	private String fotoEmbalaje;
	
	private String fotoMerma;
	
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
	
	public OrdenCompraDetalle() {
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

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public TablaAuxiliarDetalle getTamanoFruta() {
		return tamanoFruta;
	}

	public void setTamanoFruta(TablaAuxiliarDetalle tamanoFruta) {
		this.tamanoFruta = tamanoFruta;
	}

	public TablaAuxiliarDetalle getEstadoFruta() {
		return estadoFruta;
	}

	public void setEstadoFruta(TablaAuxiliarDetalle estadoFruta) {
		this.estadoFruta = estadoFruta;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public TablaAuxiliarDetalle getUnidadCompra() {
		return unidadCompra;
	}

	public void setUnidadCompra(TablaAuxiliarDetalle unidadCompra) {
		this.unidadCompra = unidadCompra;
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

	public TablaAuxiliarDetalle getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(TablaAuxiliarDetalle formaPago) {
		this.formaPago = formaPago;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getResponsablePago() {
		return responsablePago;
	}

	public void setResponsablePago(String responsablePago) {
		this.responsablePago = responsablePago;
	}

	public Boolean getIndAdicional() {
		return indAdicional;
	}

	public void setIndAdicional(Boolean indAdicional) {
		this.indAdicional = indAdicional;
	}

	public TablaAuxiliarDetalle getTipoAdicional() {
		return tipoAdicional;
	}

	public void setTipoAdicional(TablaAuxiliarDetalle tipoAdicional) {
		this.tipoAdicional = tipoAdicional;
	}

	public Double getCantidadAdicional() {
		return cantidadAdicional;
	}

	public void setCantidadAdicional(Double cantidadAdicional) {
		this.cantidadAdicional = cantidadAdicional;
	}

	public Date getFechaArribo() {
		return fechaArribo;
	}

	public void setFechaArribo(Date fechaArribo) {
		this.fechaArribo = fechaArribo;
	}

	public Boolean getIndIndividualizar() {
		return indIndividualizar;
	}

	public void setIndIndividualizar(Boolean indIndividualizar) {
		this.indIndividualizar = indIndividualizar;
	}

	public Boolean getIndMerma() {
		return indMerma;
	}

	public void setIndMerma(Boolean indMerma) {
		this.indMerma = indMerma;
	}

	public List<OrdenCompraEmpaquetado> getEmpaques() {
		return empaques;
	}

	public void setEmpaques(List<OrdenCompraEmpaquetado> empaques) {
		this.empaques = empaques;
	}

	public String getFotoEmbalaje() {
		return fotoEmbalaje;
	}

	public void setFotoEmbalaje(String fotoEmbalaje) {
		this.fotoEmbalaje = fotoEmbalaje;
	}

	public String getFotoMerma() {
		return fotoMerma;
	}

	public void setFotoMerma(String fotoMerma) {
		this.fotoMerma = fotoMerma;
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

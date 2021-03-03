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
import com.tevo.frutas.models.entity.maestros.Proveedor;

@Entity
@Table(name = "oco_orden_compra")
public class OrdenCompra implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nroOrden;
	
	private String comprador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Proveedor proveedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle tipoCompra;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle tipoOrden;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle moneda;
	
	private Boolean indIgv;
	
	private Double total;
	
	private String lugarCompra;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_compra")
	private Date fechaCompra;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "orden_compra_id")
	private List<OrdenCompraDetalle> detalle;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle estadoOrdenCompra;
	
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
	
	public OrdenCompra() {
		detalle = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNroOrden() {
		return nroOrden;
	}

	public void setNroOrden(String nroOrden) {
		this.nroOrden = nroOrden;
	}

	public String getComprador() {
		return comprador;
	}

	public void setComprador(String comprador) {
		this.comprador = comprador;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public TablaAuxiliarDetalle getTipoCompra() {
		return tipoCompra;
	}

	public void setTipoCompra(TablaAuxiliarDetalle tipoCompra) {
		this.tipoCompra = tipoCompra;
	}

	public TablaAuxiliarDetalle getTipoOrden() {
		return tipoOrden;
	}

	public void setTipoOrden(TablaAuxiliarDetalle tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	public TablaAuxiliarDetalle getMoneda() {
		return moneda;
	}

	public void setMoneda(TablaAuxiliarDetalle moneda) {
		this.moneda = moneda;
	}

	public Boolean getIndIgv() {
		return indIgv;
	}

	public void setIndIgv(Boolean indIgv) {
		this.indIgv = indIgv;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getLugarCompra() {
		return lugarCompra;
	}

	public void setLugarCompra(String lugarCompra) {
		this.lugarCompra = lugarCompra;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public List<OrdenCompraDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<OrdenCompraDetalle> detalle) {
		this.detalle = detalle;
	}

	public TablaAuxiliarDetalle getEstadoOrdenCompra() {
		return estadoOrdenCompra;
	}

	public void setEstadoOrdenCompra(TablaAuxiliarDetalle estadoOrdenCompra) {
		this.estadoOrdenCompra = estadoOrdenCompra;
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

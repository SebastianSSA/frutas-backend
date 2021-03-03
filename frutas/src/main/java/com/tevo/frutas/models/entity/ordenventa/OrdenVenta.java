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
import com.tevo.frutas.models.entity.maestros.Almacen;
import com.tevo.frutas.models.entity.maestros.Cliente;

@Entity
@Table(name = "ove_orden_venta")
public class OrdenVenta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nroOrden;

	private String vendedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private TablaAuxiliarDetalle tipoCliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Cliente cliente;
	
	private String nombreCliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private TablaAuxiliarDetalle tipoVenta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private TablaAuxiliarDetalle tipoOrdenVenta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private TablaAuxiliarDetalle moneda;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private TablaAuxiliarDetalle formaPagoVenta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_venta")
	private Date fechaVenta;

	private Boolean indIgv;

	private Double total;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private Almacen almacen;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "orden_venta_id")
	private List<OrdenVentaDetalle> detalle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private TablaAuxiliarDetalle estadoOrdenVenta;

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
	
	public OrdenVenta() {
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

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public TablaAuxiliarDetalle getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TablaAuxiliarDetalle tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public TablaAuxiliarDetalle getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(TablaAuxiliarDetalle tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public TablaAuxiliarDetalle getTipoOrdenVenta() {
		return tipoOrdenVenta;
	}

	public void setTipoOrdenVenta(TablaAuxiliarDetalle tipoOrdenVenta) {
		this.tipoOrdenVenta = tipoOrdenVenta;
	}

	public TablaAuxiliarDetalle getMoneda() {
		return moneda;
	}

	public void setMoneda(TablaAuxiliarDetalle moneda) {
		this.moneda = moneda;
	}

	public TablaAuxiliarDetalle getFormaPagoVenta() {
		return formaPagoVenta;
	}

	public void setFormaPagoVenta(TablaAuxiliarDetalle formaPagoVenta) {
		this.formaPagoVenta = formaPagoVenta;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
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

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public List<OrdenVentaDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<OrdenVentaDetalle> detalle) {
		this.detalle = detalle;
	}

	public TablaAuxiliarDetalle getEstadoOrdenVenta() {
		return estadoOrdenVenta;
	}

	public void setEstadoOrdenVenta(TablaAuxiliarDetalle estadoOrdenVenta) {
		this.estadoOrdenVenta = estadoOrdenVenta;
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

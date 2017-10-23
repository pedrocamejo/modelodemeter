package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;
@Audited @Entity
@Table(name="tbl_dem_aprobacion_exoneracion_contrato", schema="administracion")
public class AprobacionExoneracionContrato implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8253410325397588221L;
	private Integer id;
	private Date fechaRecepcion;
	private Date fechaAprobacion;
	private byte[] archivoSolicitud;
	private byte[] archivoAprobacion;
	
	//datos del contrato
	private String numeroExoneracion;
	private String numeroContrato;
	private String cedRif;
	private String pagador;
	private Double montoBase;
	private String sede;
	private Date   fechaSolicitud;
	private Date   fechaContrato;
	private String motivo;
	private List<DetalleExoneracionContrato> detalleExoneracionContrato;
	private Boolean aprobado;
	
	@SequenceGenerator(name="seq_idaprobacionexoneracion", sequenceName="", allocationSize=1)
	@Column(name="seq_idaprobacionexoneracion")
	@Id @GeneratedValue(generator="seq_idaprobacionexoneracion")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechacreacion")
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaCreacion) {
		this.fechaRecepcion = fechaCreacion;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechaaprobacion")
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}
	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechacontrato")
	public Date getFechaContrato() {
		return fechaContrato;
	}
	public void setFechaContrato(Date fechaContrato) {
		this.fechaContrato = fechaContrato;
	}
	@Column(name = "archivosolitud")
	public byte[] getArchivoSolicitud() {
		return archivoSolicitud;
	}
	public void setArchivoSolicitud(byte[] archivoSolicitud) {
		this.archivoSolicitud = archivoSolicitud;
	}
	@Column(name = "archivoaprobacion")
	public byte[] getArchivoAprobacion() {
		return archivoAprobacion;
	}
	public void setArchivoAprobacion(byte[] archivoAprobacion) {
		this.archivoAprobacion = archivoAprobacion;
	}
	@Column(name = "str_numerocontrato")
	public String getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	@Column(name = "str_cedrifpagador")
	public String getCedRif() {
		return cedRif;
	}
	public void setCedRif(String rif) {
		this.cedRif = rif;
	}
	@Column(name = "str_pagador")
	public String getPagador() {
		return pagador;
	}
	public void setPagador(String pagador) {
		this.pagador = pagador;
	}
	@Column(name = "dbl_montobase")
	public Double getMontoBase() {
		return montoBase;
	}
	public void setMontoBase(Double monto) {
		this.montoBase = monto;
	}
	@Column(name = "str_sede")
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechasolicitud")
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	@Column(name = "str_observacion",columnDefinition = "TEXT")
	
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String observacion) {
		this.motivo = observacion;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="aprobacion", targetEntity=DetalleExoneracionContrato.class, fetch=FetchType.EAGER)
	public List<DetalleExoneracionContrato> getDetalleExoneracionContrato() {
		return detalleExoneracionContrato;
	}
	public void setDetalleExoneracionContrato(
			List<DetalleExoneracionContrato> detalleExoneracionContrato) {
		this.detalleExoneracionContrato = detalleExoneracionContrato;
	}
	@Column(name ="numeroexoneracion")
	public String getNumeroExoneracion() {
		return numeroExoneracion;
	}
	public void setNumeroExoneracion(String numeroExoneracion) {
		this.numeroExoneracion = numeroExoneracion;
	}
	@Column(name="aprobado")
	public Boolean isAprobado() {
		return aprobado;
	}
	public void setAprobado(Boolean aprobado) {
		this.aprobado = aprobado;
	}
			
	
	@Transient
	public String getStrMontoBase() {
		return Formateador.formatearMoneda(Math.abs(montoBase));
	}
	@Transient
	public String getStrEstado() {
		if(this.aprobado==null)return "Por Decidir";
		if(this.aprobado==true){return "Aprobado";}
		else return "rechazado";
		
	}
	@Transient
	public String getStrFechaRecepcion() {
		return  Fecha.obtenerFecha(fechaRecepcion);
	}
	@Transient
	public String getStrFechaAprobacion() {
		if (fechaAprobacion!=null)
		return  Fecha.obtenerFecha(fechaAprobacion);
		else return "";
	}
	@Transient
	public String getStrFechaContrato() {
		return  Fecha.obtenerFecha(fechaContrato);
	}
	@Transient
	public String getStrFechaSolicitud() {
		return  Fecha.obtenerFecha(fechaSolicitud);
	}
}

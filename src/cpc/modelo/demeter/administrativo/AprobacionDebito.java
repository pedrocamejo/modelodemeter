package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cva.pc.demeter.utilidades.Fecha;

@Audited
@Entity
@Table(name = "tbl_dem_aprobaciondebito", schema = "administracion")
public class AprobacionDebito implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1811313127219325806L;
	private Integer id;
	private String usuario;
	private Date fechaAprobacion;
	private UnidadAdministrativa unidadAdministrativa;
	private Double montoRecibo;
	private String cedurif;
	private String pagador;
	private String nroRecibo;
	private String nroFactura;
	private String motivo;

	@Column(name = "seq_idaprobacion")
	@SequenceGenerator(name = "AprobacionDebito_Gen", sequenceName = "administracion.tbl_dem_aprobaciondebito_seq_idaprobacion_seq", allocationSize = 1)
	@Id
	@GeneratedValue(generator = "AprobacionDebito_Gen")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "str_codemp", referencedColumnName = "codemp"),
			@JoinColumn(name = "str_coduniadm", referencedColumnName = "coduniadm") })
	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(
			UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	@Column(name = "str_cedurif")
	public String getCedurif() {
		return cedurif;
	}

	public void setCedurif(String cedurif) {
		this.cedurif = cedurif;
	}

	@Column(name = "str_pagador")
	public String getPagador() {
		return pagador;
	}

	public void setPagador(String pagador) {
		this.pagador = pagador;
	}

	@Column(name = "str_usuario")
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "dtm_fechaaprobacion")
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	
	@Column(name = "dbl_montorecibo")
	public Double getMontoRecibo() {
		return montoRecibo;
	}

	public void setMontoRecibo(Double montoRecibo) {
		this.montoRecibo = montoRecibo;
	}

	@Column(name = "str_nrorecibo")
	public String getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(String nroRecibo) {
		this.nroRecibo = nroRecibo;
	}

	@Column(name = "str_nrofactura")
	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	@Column(name = "str_motivo")
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	@Transient
	public String getStrFechaAprobacion() {
		return  Fecha.obtenerFecha(fechaAprobacion);
	}
}
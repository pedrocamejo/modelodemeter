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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.basico.Sede;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;
@Audited @Entity
@Table(name="tbl_dem_solicitud_exoneracion_contrato", schema="administracion")
public class SolicitudExoneracionContrato implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8263075919992090477L;
	/**
	 * 
	 */

	private Integer id;
	private String nroControl;
	private Contrato contrato;
	private Date fechaSolicitud;
	private Date fechaAprobacion;	
	private byte[] archivoSolicitud;
	private byte[] archivoAprobacion;
	private String motivo;
	private Sede				  sede;
	
	
	@SequenceGenerator(name="seq_solicitudexoneracion", sequenceName="", allocationSize=1)
	@Column(name="seq_solicitudexoneracion")
	@Id @GeneratedValue(generator="seq_solicitudexoneracion")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@OneToOne(cascade=javax.persistence.CascadeType.ALL)
	@JoinColumn(name="int_idcontrato",nullable=false)
	
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechasolictud")
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechaaprobacion")
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}
	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	@Column(name = "archivosolictud")
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
	
	@Transient
	public String getEstado(){
		return contrato.getEstadoExoneracion().getDescripcion();
	}
	
	@Column(name = "str_nrocontrol")
	public String getNroControl() {
		return nroControl;
	}
	public void setNroControl(String nroControl) {
		this.nroControl = nroControl;
	}
	@Column(name = "str_motivo",columnDefinition = "TEXT")
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="str_codemp", referencedColumnName="codemp", insertable=false, updatable=false),
	    @JoinColumn(name="str_sede", referencedColumnName="codubifis", insertable=false, updatable=false),
	})
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	@Transient
	public String getStrContrato(){
		return contrato.getStrNroDocumento();
	}
	@Transient
	public String getStrEstadoExoneracion(){
		return contrato.getEstadoExoneracion().getDescripcion();
	}
	
	@Transient
	public String getStrFechaSolicitud() {
		return  Fecha.obtenerFecha(fechaSolicitud);
	}
	@Transient
	public String getStrFechaAprobacion() {
		return  Fecha.obtenerFecha(fechaAprobacion);
	}
	
	@Transient
	public String getStrMonto() {
		if(contrato!=null)
		return Formateador.formatearMoneda(Math.abs(contrato.getMonto()));
		else return "";
	}
	

}

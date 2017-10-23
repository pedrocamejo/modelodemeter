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


@Audited @Entity
@Table(name="tbl_dem_aprobacionreversorecibo", schema = "administracion")
public class AprobacionReversoRecibo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3500406661402326723L;
	/**
	 * 
	 */
	
	private Integer 				id;
	private String  				usuario;
	private Date          			fechaaprobacion;
	private UnidadAdministrativa 	unidadAdministrativa;
	private Double  				montoRecibo;
	private	String					beneficiariocedurif;
	private	String					beneficiario;
	private	String					recibo;
	private	String					observacion;
	private	String					nombresolicitante;
	private	String					cedulasolicitante;
	@Column(name="seq_idaprobacion")
																				 
	@SequenceGenerator(name="AprobacionReverso_Gen", sequenceName="administracion.tbl_dem_aprobacionreversorecibo_seq_idaprobacion_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="AprobacionReverso_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@Column(name="str_usuario")
	public String getusuario() {
		return usuario;
	}
	public void setusuario(String usuario) {
		this.usuario = usuario;
	}	
	
	
	@Column(name="dtm_fechaanulacion")
	@Temporal(TemporalType.DATE)
	public Date getfechaaprobacion() {
		return fechaaprobacion;
	}
	public void setfechaaprobacion(Date fechaaprobacion) {
		this.fechaaprobacion = fechaaprobacion;
	}
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_coduniadm",referencedColumnName="coduniadm")
	})
	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	
	@Column(name="str_usuario")
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Column(name="dbl_montorecibo")
	public Double getMontoRecibo() {
		return montoRecibo;
	}
	public void setMontoRecibo(Double montoRecibo) {
		this.montoRecibo = montoRecibo;
	}
	@Column(name="str_beneficiariocedrif")
	public String getBeneficiariocedurif() {
		return beneficiariocedurif;
	}
	public void setBeneficiariocedurif(String benefiariocedurif) {
		this.beneficiariocedurif = benefiariocedurif;
	}
	@Column(name="str_beneficiario")
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	@Column(name="str_recibo")
	public String getRecibo() {
		return recibo;
	}
	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}
	@Column(name="str_observacion")
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	@Column(name="str_solicitante")
	public String getNombresolicitante() {
		return nombresolicitante;
	}
	public void setNombresolicitante(String nombresolicitante) {
		this.nombresolicitante = nombresolicitante;
	}
	@Column(name="str_cedrifsolictante")
	public String getCedulasolicitante() {
		return cedulasolicitante;
	}
	public void setCedulasolicitante(String cedulasolicitante) {
		this.cedulasolicitante = cedulasolicitante;
	}
	
	@Transient
	public String getStrFechaAprobacion() {
		return  Fecha.obtenerFecha(fechaaprobacion);
	}
	
	
}
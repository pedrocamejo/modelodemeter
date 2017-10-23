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

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.gestion.MotivoAnulacionSolicitud;
import cpc.modelo.demeter.gestion.Solicitud;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;


@Audited @Entity
@Table(name="tbl_dem_aprobaciondescuento", schema = "administracion")
public class AprobacionDescuento implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -146550518428301190L;
	private Integer 				id;
	private String  				usuario;
	private Date          			fechaaprobacion;
	private UnidadAdministrativa 	unidadAdministrativa;
	private Double  				descuento;
	private	String					str_cedurif;
	private	String					str_beneficiado;
	
	
	@Column(name="seq_idaprobacion")
	@SequenceGenerator(name="AprobacionDescuento_Gen", sequenceName="administracion.tbl_dem_aprobaciondescuento_seq_idaprobacion_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="AprobacionDescuento_Gen")
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
	
	@Column(name="str_cedurif")
	public String getStr_cedurif() {
		return str_cedurif;
	}
	public void setStr_cedurif(String str_cedurif) {
		this.str_cedurif = str_cedurif;
	}
	
	@Column(name="str_beneficiado")
	public String getStr_beneficiado() {
		return str_beneficiado;
	}
	public void setStr_beneficiado(String str_beneficiado) {
		this.str_beneficiado = str_beneficiado;
	}

	@Column(name="dbl_descuento")
	public double getdescuento() {
		return descuento;
	}
	public void setdescuento(Double descuento) {
		this.descuento = descuento;
	}
	
	
}
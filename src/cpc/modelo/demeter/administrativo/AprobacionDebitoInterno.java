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

import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;

@Audited
@Entity
@Table(name = "tbl_dem_aprobaciondebitointerno", schema = "administracion")
public class AprobacionDebitoInterno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3690486844110956568L;
	/**
	 * 
	 */

	private Integer id;
	private Date fechaAprobacion;
	private UnidadAdministrativa unidadAdministrativa;
	private TipoAprobacionDebitoInterno tipo;
	private String NombreResponsable;
	private String cedulaResponsable;
	private String NombreEjecutor;
	private String cedulaEjecutor;
	private String NombreBeneficiario;
	private String IdLegalBeneficiario;
	private String NroControlDocumentoAfectado;
	private String NroDocumentoAfectado;
	private String Motivo;
	private String Observacion;
	private Double monto;

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

	@Temporal(TemporalType.DATE)
	@Column(name = "dtm_fecha")
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	@OneToOne
	@JoinColumn(name = "int_idtipo")
	public TipoAprobacionDebitoInterno getTipo() {
		return tipo;
	}

	public void setTipo(TipoAprobacionDebitoInterno tipo) {
		this.tipo = tipo;
	}

	@Column(name = "str_nombreresponsable")
	public String getNombreResponsable() {
		return NombreResponsable;
	}

	public void setNombreResponsable(String nombreResponsable) {
		NombreResponsable = nombreResponsable;
	}

	@Column(name = "str_cedularesponsable")
	public String getCedulaResponsable() {
		return cedulaResponsable;
	}

	public void setCedulaResponsable(String cedulaResponsable) {
		this.cedulaResponsable = cedulaResponsable;
	}

	@Column(name = "str_nombreejecutor")
	public String getNombreEjecutor() {
		return NombreEjecutor;
	}

	public void setNombreEjecutor(String nombreEjecutor) {
		NombreEjecutor = nombreEjecutor;
	}

	@Column(name = "str_cedulaejecutor")
	public String getCedulaEjecutor() {
		return cedulaEjecutor;
	}

	public void setCedulaEjecutor(String cedulaEjecutor) {
		this.cedulaEjecutor = cedulaEjecutor;
	}

	@Column(name = "str_nombrebeneficiario")
	public String getNombreBeneficiario() {
		return NombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		NombreBeneficiario = nombreBeneficiario;
	}

	@Column(name = "str_idlegalbeneficiario")
	public String getIdLegalBeneficiario() {
		return IdLegalBeneficiario;
	}

	public void setIdLegalBeneficiario(String idLegalBeneficiario) {
		IdLegalBeneficiario = idLegalBeneficiario;
	}

	@Column(name = "str_nrocontroldocumentoafectado")
	public String getNroControlDocumentoAfectado() {
		return NroControlDocumentoAfectado;
	}

	public void setNroControlDocumentoAfectado(
			String nroControlDocumentoAfectado) {
		NroControlDocumentoAfectado = nroControlDocumentoAfectado;
	}

	@Column(name = "str_nrodocumentoafectado")
	public String getNroDocumentoAfectado() {
		return NroDocumentoAfectado;
	}

	public void setNroDocumentoAfectado(String nroDocumentoAfectado) {
		NroDocumentoAfectado = nroDocumentoAfectado;
	}

	@Column(name = "str_motivo", columnDefinition = "TEXT")
	public String getMotivo() {
		return Motivo;
	}

	public void setMotivo(String motivo) {
		Motivo = motivo;
	}

	@Column(name = "str_observacion", columnDefinition = "TEXT")
	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}
	
	@Column(name="dbl_monto")
	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	@Transient
	public String getStrFechaAprobacion() {
		return Fecha.obtenerFecha(fechaAprobacion);
	}
	@Transient
	public String getStrMonto() {
		return Formateador.formatearMoneda(Math.abs(monto));
	}
}
package cpc.modelo.demeter.transporte;
  
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import cpc.modelo.ministerio.dimension.UbicacionSector;

@Audited
@AuditTable(value="tbl_dem_solicitudTransporte_aud",schema="auditoria")
@Entity
@Table(name="tbl_dem_solicitudTransporte",schema="transporte")
public class SolicitudTransporte implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer 		id;
	private Gerencia 		gerencia;

	private Date 			fechasalida;
	private Date		 	fechallegada;
	
	private String 			lugarSalida;
	private UbicacionSector sector;
	
	private String 			motivo;
	private Integer 		nroPasajero;
	private Integer 		estado = 1; 
	
	private String justificacion; // si esta rechazada tiene una Justificacion :D 
	
	
	public SolicitudTransporte() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	@SequenceGenerator(name="seq_solicitudtransporte",sequenceName="transporte.seq_solicitudtransporte")
	@Id
	@Column(name="idsolicitud")
	@GeneratedValue(generator="seq_solicitudtransporte")
	public Integer getIdSolicitud() {
		return id;
	}
	public void setIdSolicitud(Integer id) {
		this.id = id;
	}
	
	@Column(name="fechasalida")
	public Date getFechasalida() {
		return fechasalida;
	}
	public void setFechasalida(Date fechasalida) {
		this.fechasalida = fechasalida;
	}
	
	@Column(name="fechallegada")
	public Date getFechallegada() {
		return fechallegada;
	}
	public void setFechallegada(Date fechallegada) {
		this.fechallegada = fechallegada;
	}
	
	@Column(name="lugarSalida")
	public String getLugarSalida() {
		return lugarSalida;
	}
	public void setLugarSalida(String lugarSalida) {
		this.lugarSalida = lugarSalida;
	}
	
	
	 
	@Column(name="motivo")
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	@Column(name="nroPasajero") 
	public Integer getNroPasajero() {
		return nroPasajero;
	}
	public void setNroPasajero(Integer nroPasajero) {
		this.nroPasajero = nroPasajero;
	}

	@Column(name="estado",nullable=false)
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	@ManyToOne
	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	@Column(name="justificacion")
	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	
	@Transient
	public String getEstadoStr()
	{
		return EstadoSolicitudTransporte.estado(estado);
	}
	
	@ManyToOne
	public UbicacionSector getSector() {
		return sector;
	}

	public void setSector(UbicacionSector sector) {
		this.sector = sector;
	}
	
	@Transient
	public String getGerenciaStr()
	{
		return gerencia.getDescripcion();
	}
	
}

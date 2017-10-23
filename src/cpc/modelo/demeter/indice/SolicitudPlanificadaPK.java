package cpc.modelo.demeter.indice;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;


public class SolicitudPlanificadaPK implements Serializable{

	private static final long serialVersionUID = -1561810375298968647L;

	private long idSolicitud;
	private Date fechaInicioPlan;

	public SolicitudPlanificadaPK() {
	}

	public SolicitudPlanificadaPK(long idSolicitud, Date fechaInicioPlan) {
		super();
		this.idSolicitud = idSolicitud;
		this.fechaInicioPlan = fechaInicioPlan;
	}

	@Column(name="seq_idsolicitud")
	public long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	@Column(name="dtm_fecha_inicio")
	public Date getFechaInicioPlan() {
		return this.fechaInicioPlan;
	}	
	
	public void setFechaInicioPlan(Date fechaInicioPlan) {
		this.fechaInicioPlan = fechaInicioPlan;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SolicitudPlanificadaPK)) {
			return false;
		}
		SolicitudPlanificadaPK other = (SolicitudPlanificadaPK) o;
		return true
			&& (idSolicitud ==  other.getIdSolicitud() &&  idSolicitud == other.getIdSolicitud());
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime *  getIdSolicitud());
		result = (int) (result + prime * getFechaInicioPlan().hashCode());
		return result;
	}	
	
}

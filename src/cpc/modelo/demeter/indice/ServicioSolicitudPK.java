package cpc.modelo.demeter.indice;

import java.io.Serializable;

import javax.persistence.Column;


public class ServicioSolicitudPK implements Serializable{

	private static final long serialVersionUID = -1561810375298968647L;

	private long idSolicitud;
	private int idServicio;

	public ServicioSolicitudPK() {
	}

	public ServicioSolicitudPK(long idSolicitud, int idServicio) {
		super();
		this.idSolicitud = idSolicitud;
		this.idServicio = idServicio;
	}

	@Column(name="int_idsolicitud")
	public long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	@Column(name="int_idservicio")
	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ServicioSolicitudPK)) {
			return false;
		}
		ServicioSolicitudPK other = (ServicioSolicitudPK) o;
		return true
			&& (idServicio ==  other.getIdServicio() &&  idSolicitud == other.getIdSolicitud());
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime *  getIdServicio();
		result = (int) (result + prime * getIdSolicitud());
		return result;
	}	
	
}

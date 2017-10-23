package cpc.modelo.demeter.indice;

import java.io.Serializable;

import javax.persistence.Column;


public class DetalleServicioSolicitudPK implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1561810375298968647L;

	private long idSolicitud;
	private int idServicio;
	private int idRubro;
	private int idUnidad;

	public DetalleServicioSolicitudPK() {
	}



	public DetalleServicioSolicitudPK(long idSolicitud, int idServicio,
			int idRubro, int idUnidad) {
		super();
		this.idSolicitud = idSolicitud;
		this.idServicio = idServicio;
		this.idRubro = idRubro;
		this.idUnidad = idUnidad;
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


	@Column(name="int_rubro")
	public int getIdRubro() {
		return idRubro;
	}



	public void setIdRubro(int idRubro) {
		this.idRubro = idRubro;
	}

	@Column(name="int_idumedida")
	public int getIdUnidad() {
		return idUnidad;
	}



	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DetalleServicioSolicitudPK)) {
			return false;
		}
		DetalleServicioSolicitudPK other = (DetalleServicioSolicitudPK) o;
		return true
			&& (idServicio ==  other.getIdServicio() &&  idSolicitud == other.getIdSolicitud()
			&& idUnidad == other.getIdUnidad() && idRubro == other.getIdRubro());
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime *  getIdServicio();
		result = (int) (prime * result + getIdSolicitud());
		result = prime * result + getIdRubro();
		result = prime * result + getIdUnidad();
		return result;
	}	
	
	
}

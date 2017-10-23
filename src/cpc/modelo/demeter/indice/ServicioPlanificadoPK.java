package cpc.modelo.demeter.indice;

import java.io.Serializable;

import javax.persistence.Column;


public class ServicioPlanificadoPK implements Serializable{

	private static final long serialVersionUID = -1561810375298968647L;

	private long idPlan;
	private long idServicio;

	public ServicioPlanificadoPK() {
	}

	public ServicioPlanificadoPK(long idPlan, long idServicio) {
		super();
		this.idPlan = idPlan;
		this.idServicio = idServicio;
	}

	@Column(name="int_idplansema")
	public long getIdPlanSemanal() {
		return idPlan;
	}

	public void setIdPlanSemanal(long idSolicitud) {
		this.idPlan = idSolicitud;
	}

	@Column(name="seq_idservicio")
	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ServicioPlanificadoPK)) {
			return false;
		}
		ServicioPlanificadoPK other = (ServicioPlanificadoPK) o;
		return true
			&& (idServicio ==  other.getIdServicio() &&  idPlan == other.getIdPlanSemanal());
	}
	
	public int hashCode() {
		final int prime = 31;
		long result = 1;
		result = prime *  getIdServicio();
		result = (int) (result + prime * getIdPlanSemanal());
		return (int) result;
	}	
	
}

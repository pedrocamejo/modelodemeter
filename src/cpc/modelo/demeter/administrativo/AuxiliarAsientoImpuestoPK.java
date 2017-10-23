package cpc.modelo.demeter.administrativo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AuxiliarAsientoImpuestoPK implements Serializable{   
   
	private static final long serialVersionUID = 5604259801684784412L;
	private Date 				fecha;
	private Boolean				tipo;
	
	public AuxiliarAsientoImpuestoPK() {
		super();
	}
	
	public AuxiliarAsientoImpuestoPK(Date fecha, Boolean tipo) {
		this.fecha = fecha;
		this.tipo = tipo;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof AuxiliarAsientoImpuestoPK)) {
			return false;
		}
		AuxiliarAsientoImpuestoPK other = (AuxiliarAsientoImpuestoPK) o;
		return true
			&& (getFecha() == null ? other.getFecha() == null : getFecha().equals(other.getFecha()))
			&& (getTipo() == null ? other.getTipo() == null : getTipo().equals(other.getTipo()));
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getFecha() == null ? 0 : getFecha().hashCode());
		result = prime * result + (getTipo() == null ? 0 : getTipo().hashCode());
		return result;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="bol_haber")
	public Boolean getTipo() {
		return tipo;
	}
	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}
	
}

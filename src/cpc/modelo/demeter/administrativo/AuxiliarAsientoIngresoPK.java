package cpc.modelo.demeter.administrativo;


import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AuxiliarAsientoIngresoPK implements Serializable{   
   
	private static final long serialVersionUID = 5604259801684784412L;
	private Date 				fecha;
	private Boolean				tipo;
	private String				cuentaContable;
	
	public AuxiliarAsientoIngresoPK() {
		super();
	}
	
	public AuxiliarAsientoIngresoPK(Date fecha, String cuenta, Boolean tipo) {
		this.fecha = fecha;
		this.cuentaContable = cuenta;
		this.tipo = tipo;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof AuxiliarAsientoIngresoPK)) {
			return false;
		}
		AuxiliarAsientoIngresoPK other = (AuxiliarAsientoIngresoPK) o;
		return true
			&& (getCuentaContable() == null ? other.getCuentaContable() == null : getCuentaContable().equals(other.getCuentaContable()))
			&& (getFecha() == null ? other.getFecha() == null : getFecha().equals(other.getFecha()))
			&& (getTipo() == null ? other.getTipo() == null : getTipo().equals(other.getTipo()));
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getFecha() == null ? 0 : getFecha().hashCode());
		result = prime * result + (getCuentaContable() == null ? 0 : getCuentaContable().hashCode());
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
	
	@Column(name="str_cuentaingreso")
	public String getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	
	
	
}

package cpc.modelo.sigesp.basico;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class UnidadCentralizadaPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9212663878486278049L;
	private String		empresa;
	private String		UnidadCentralizada;

	
	
	public UnidadCentralizadaPK() {
		super();
	}
	
	@Basic
	@Column(name="codemp",insertable = false, updatable = false)
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	@Basic
	@Column(name="coduac",insertable = false, updatable = false)
	public String getUnidadCentralizada() {
		return UnidadCentralizada;
	}
	public void setUnidadCentralizada(String UnidadCentralizada) {
		this.UnidadCentralizada = UnidadCentralizada;
	}
	
	public  UnidadCentralizadaPK(String empresa, String UnidadCentralizada) {
		super();
		this.empresa = empresa;
		this.UnidadCentralizada = UnidadCentralizada;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof UnidadCentralizadaPK)) {
			return false;
		}
		UnidadCentralizadaPK other = (UnidadCentralizadaPK) o;
		return true
		&& (getEmpresa().equals(other.getEmpresa()) && getUnidadCentralizada().equals(other.getUnidadCentralizada()));
	}
	

	public int hashCode() {
		final int prime = 51;
		int result = 1;
		result = prime * result + (getEmpresa() == null ? 0 : getEmpresa().hashCode());
		result = prime * result + (getUnidadCentralizada() == null ? 0 : getUnidadCentralizada().hashCode());
		return result;
	}

	
	
	
	
	
}

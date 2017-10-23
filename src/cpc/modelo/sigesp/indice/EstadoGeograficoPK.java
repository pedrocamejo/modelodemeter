package cpc.modelo.sigesp.indice;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;



public class EstadoGeograficoPK implements Serializable{   
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 5223311485733663668L;

	private String pais;
	private String codigoEstado;

	public EstadoGeograficoPK() {
	}
	
	public EstadoGeograficoPK(String codpai, String codest) {
		this.pais = codpai;
		this.codigoEstado = codest;
	}

	
	@Column(name="codpai")
	public String getPais() {
		return this.pais;
	}

	
	public void setPais(String codpai) {
		this.pais = codpai;
	}
	

	@Column(name="codest")
	public String getCodigoEstado() {
		return this.codigoEstado;
	}

	public void setCodigoEstado(String codest) {
		this.codigoEstado = codest;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EstadoGeograficoPK)) {
			return false;
		}
		EstadoGeograficoPK other = (EstadoGeograficoPK) o;
		return true
			&& (getPais() == null ? other.getPais() == null : getPais().equals(other.getPais()))
			&& (getCodigoEstado() == null ? other.getCodigoEstado() == null : getCodigoEstado().equals(other.getCodigoEstado()));
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getPais() == null ? 0 : getPais().hashCode());
		result = prime * result + (getCodigoEstado() == null ? 0 : getCodigoEstado().hashCode());
		return result;
	}
   
   
}

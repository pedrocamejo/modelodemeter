package cpc.modelo.sigesp.indice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SedePK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5095037461479873839L;
	
	private String codigoEmpresa;
	private String id;

	public SedePK() {
	}
	
	public SedePK(String id) {
		this.id = id;
		this.codigoEmpresa = "0001";
	}
	
	public SedePK(String codigoEmpresa, String id) {
		this.id = id;
		this.codigoEmpresa = codigoEmpresa;
	}

	@Column(name="codemp")
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	@Column(name="codubifis")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SedePK)) {
			return false;
		}
		SedePK other = (SedePK) o;
		return true
			&& (getCodigoEmpresa() == null ? other.getCodigoEmpresa() == null : getCodigoEmpresa().equals(other.getCodigoEmpresa()))
			&& (getId() == null ? other.getId() == null : getId().equals(other.getId()));
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getCodigoEmpresa() == null ? 0 : getCodigoEmpresa().hashCode());
		result = prime * result + (getId() == null ? 0 : getId().hashCode());
		return result;
	}



}

package cpc.modelo.sigesp.indice;

import java.io.Serializable;

import javax.persistence.Column;

public class BancoPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5095037461479873839L;
	private String codigoEmpresa;
	private String id;

	public BancoPK() {
	}
	
	public BancoPK(String id) {
		this.id = id;
		this.codigoEmpresa = "0001";
	}
	
	public BancoPK(String codigoEmpresa, String id) {
		this.id = id;
		this.codigoEmpresa = codigoEmpresa;
	}

	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof BancoPK)) {
			return false;
		}
		BancoPK other = (BancoPK) o;
		return true
			&& (getCodigoEmpresa() == null ? other.getCodigoEmpresa() == null : getCodigoEmpresa().equals(other.getCodigoEmpresa()))
			&& (getId() == null ? other.getId() == null : getId().equals(other.getId()));
	}
	
	public int hashCode() {
		final int prime = 7;
		int result = 1;
		result = prime * result + (getCodigoEmpresa() == null ? 0 : getCodigoEmpresa().hashCode());
		result = prime * result + (getId() == null ? 0 : getId().hashCode());
		return result;
	}

	@Column(name="codemp")
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	@Column(name="codban")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

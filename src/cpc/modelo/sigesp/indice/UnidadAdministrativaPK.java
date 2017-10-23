package cpc.modelo.sigesp.indice;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UnidadAdministrativaPK implements Serializable{

	private static final long serialVersionUID = 6906712234776865782L;
	private String		empresa;
	private String		sede;
	
	public UnidadAdministrativaPK() {
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
	@Column(name="coduniadm",insertable = false, updatable = false)
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	
	public UnidadAdministrativaPK(String empresa, String sede) {
		super();
		this.empresa = empresa;
		this.sede = sede;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof UnidadAdministrativaPK)) {
			return false;
		}
		UnidadAdministrativaPK other = (UnidadAdministrativaPK) o;
		return true
		&& (getEmpresa().equals(other.getEmpresa()) && getSede().equals(other.getSede()));
	}
	

	public int hashCode() {
		final int prime = 51;
		int result = 1;
		result = prime * result + (getEmpresa() == null ? 0 : getEmpresa().hashCode());
		result = prime * result + (getSede() == null ? 0 : getSede().hashCode());
		return result;
	}

}

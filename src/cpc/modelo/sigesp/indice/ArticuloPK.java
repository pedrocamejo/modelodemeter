package cpc.modelo.sigesp.indice;


import java.io.Serializable;
import java.lang.String;
import javax.persistence.Column;

public class ArticuloPK implements Serializable{   
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -5694278915701189267L;
	private String 	empresa;
	private String 	id;
	
	public ArticuloPK() {
		super();
	}
	
	public ArticuloPK(String empresa, String id) {
		this.id = id;
		this.empresa = empresa;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ArticuloPK)) {
			return false;
		}
		ArticuloPK other = (ArticuloPK) o;
		return true
			&& (getEmpresa() == null ? other.getEmpresa() == null : getEmpresa().equals(other.getEmpresa()))
			&& (getId() == null ? other.getId() == null : getId().equals(other.getId()));
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getEmpresa() == null ? 0 : getEmpresa().hashCode());
		result = prime * result + (getId() == null ? 0 : getId().hashCode());
		return result;
	}

	@Column(name="str_codemp")
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}


	@Column(name="str_codart")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
   
	
	
}

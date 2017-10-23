package cpc.modelo.sigesp.indice;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ActivoPK implements Serializable{

	private static final long serialVersionUID = 9028628801755086979L;
    private String		empresa;
    private String		codigoActivo;
    private String		idEjemplarActivo;
        
    public ActivoPK() {
		super();
	}
    
	public ActivoPK(String empresa, String activo, String ideact) {
		super();
		this.empresa = empresa;
		this.codigoActivo = activo;
		this.idEjemplarActivo = ideact;
	}
	
	@Basic
	@Column(name="codemp", insertable = false, updatable = false)
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	@Basic
	@Column(name="codact",insertable = false, updatable = false)
	public String getCodigoActivo() {
		return codigoActivo;
	}
	public void setCodigoActivo(String activo) {
		this.codigoActivo = activo;
	}
	
	@Basic
	@Column(name="ideact",insertable = false, updatable = false)
	public String getIdEjemplarActivo() {
		return idEjemplarActivo;
	}
	public void setIdEjemplarActivo(String ideact) {
		this.idEjemplarActivo = ideact;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ActivoPK)) {
			return false;
		}
		ActivoPK other = (ActivoPK) o;
		boolean lola = true;
		lola = (getEmpresa().equals(other.getEmpresa()) && getCodigoActivo().equals(other.getCodigoActivo()) && getIdEjemplarActivo().equals(other.getIdEjemplarActivo()));
		return lola;
	}
	

	public int hashCode() {
		final int prime = 59;
		int result = 1;
		result = prime * result + (getEmpresa() == null ? 0 : getEmpresa().hashCode());
		result = prime * result + (getCodigoActivo() == null ? 0 : getCodigoActivo().hashCode());
		result = prime * result + (getIdEjemplarActivo() == null ? 0 : getIdEjemplarActivo().hashCode());
		return result;
	}
	
}

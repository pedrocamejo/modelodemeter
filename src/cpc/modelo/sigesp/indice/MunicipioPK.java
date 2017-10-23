package cpc.modelo.sigesp.indice;


import java.io.Serializable;
import java.lang.String;
import javax.persistence.Column;

public class MunicipioPK implements Serializable{   
   
	
	private static final long serialVersionUID = 634993297442093592L;
	private String 	pais;
	private String 	codigoEstado;
	private	String	codigoMunicipio; 
	
	public MunicipioPK() {
		super();
	}
	
	public MunicipioPK(String codpai, String codest, String	codigoMunicipio) {
		this.pais = codpai;
		this.codigoEstado = codest;
		this.codigoMunicipio = codigoMunicipio;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof MunicipioPK)) {
			return false;
		}
		MunicipioPK other = (MunicipioPK) o;
		return true
			&& (getCodigoEstado() == null ? other.getPais() == null : getPais().equals(other.getPais()))
			&& (getCodigoEstado() == null ? other.getCodigoEstado() == null : getCodigoEstado().equals(other.getCodigoEstado()))
			&& (getCodigoMunicipio()  == null ? other.getCodigoMunicipio()  == null : getCodigoMunicipio() .equals(other.getCodigoMunicipio() ));
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getPais() == null ? 0 : getPais().hashCode());
		result = prime * result + (getCodigoEstado() == null ? 0 : getCodigoEstado().hashCode());
		result = prime * result + (getCodigoMunicipio() == null ? 0 : getCodigoMunicipio().hashCode());
		return result;
	}



	@Column(name="codmun")
	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}




	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
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

}

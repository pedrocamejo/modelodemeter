package cpc.modelo.sigesp.indice;



import java.io.Serializable;
import javax.persistence.Column;

public class ParroquiaPK implements Serializable{   
   
	private static final long serialVersionUID = -121319133309413871L;

	private	String		codigoMunicipio; 
	private String 		pais;
	private String 		codigoEstado;
	private String		codigoParroquia;
	
	
	public ParroquiaPK() {
		
	}

	public ParroquiaPK(String codpai, String codest, String	codigoMunicipio, String	codigoParroquia) {
		this.pais = codpai;
		this.codigoEstado = codest;
		this.codigoMunicipio = codigoMunicipio;
		this.codigoParroquia = codigoParroquia;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ParroquiaPK)) {
			return false;
		}
		ParroquiaPK other = (ParroquiaPK) o;
		return true
			&& (getPais() == null ? other.getPais() == null : getPais().equals(other.getPais()))
			&& (getCodigoEstado() == null ? other.getCodigoEstado() == null : getCodigoEstado().equals(other.getCodigoEstado()))
			&& (getCodigoMunicipio()  == null ? other.getCodigoMunicipio()  == null : getCodigoMunicipio() .equals(other.getCodigoMunicipio() ))
			&& (getCodigoParroquia()  == null ? other.getCodigoParroquia()  == null : getCodigoParroquia() .equals(other.getCodigoParroquia() ));
	}
	

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getPais() == null ? 0 : getPais().hashCode());
		result = prime * result + (getCodigoEstado() == null ? 0 : getCodigoEstado().hashCode());
		result = prime * result + (getCodigoMunicipio() == null ? 0 : getCodigoMunicipio().hashCode());
		result = prime * result + (getCodigoParroquia() == null ? 0 : getCodigoParroquia().hashCode());
		return result;
	}


	@Column(name="codpar")
	public String getCodigoParroquia() {
		return codigoParroquia;
	}

	public void setCodigoParroquia(String codigoParroquia) {
		this.codigoParroquia = codigoParroquia;
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
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	@Column(name="codest")
	public String getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}




   
   
}

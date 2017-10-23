package cpc.modelo.ministerio.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_institucionfinanciera", schema = "ministerio")
public class InstitucionCrediticia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2747508365668133354L;
	private Integer				id;
	private String				denotacion;
	private boolean				banca;			//no institucion
	private boolean				privada;		//no publica
	private TipoFinanciamientoCrediticio	tipoFinanciamiento;
	
	@Id
	@Column(name="seq_idinstitucionf")
	@SequenceGenerator(name="seqInstitucionCrediticia", sequenceName="ministerio.tbl_dem_institucionfinanciera_seq_idinstitucionf_seq", allocationSize=1)
	@GeneratedValue(generator="seqInstitucionCrediticia")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDenotacion() {
		return denotacion;
	}
	public void setDenotacion(String denotacion) {
		this.denotacion = denotacion;
	}
	
	@Column(name="bol_banca")
	public boolean isBanca() {
		return banca;
	}
	public void setBanca(boolean banca) {
		this.banca = banca;
	}
	
	@Column(name="bol_privado")
	public boolean isPrivada() {
		return privada;
	}
	public void setPrivada(boolean privada) {
		this.privada = privada;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipofinanciamiento")
	public TipoFinanciamientoCrediticio getTipoFinanciamiento() {
		return tipoFinanciamiento;
	}
	public void setTipoFinanciamiento(TipoFinanciamientoCrediticio tipoFinanciamiento) {
		this.tipoFinanciamiento = tipoFinanciamiento;
	}
	
	@Transient
	public String getStrTipoFinanciamiento() {
		if (tipoFinanciamiento == null)
			return null;
		else
			return tipoFinanciamiento.getDenotacion();
	}
	
	public String toString(){
		return getDenotacion();
	}
	public boolean equals(Object objeto){
		try{
			InstitucionCrediticia cuenta = (InstitucionCrediticia) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

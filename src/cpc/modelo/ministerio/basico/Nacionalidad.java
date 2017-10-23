package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.ministerio.dimension.UbicacionPais;

@Audited @Entity
@Table(name="tbl_dem_nacionalidad", schema="ministerio")

public class Nacionalidad implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -404405844137156623L;
	private Integer			id;
	private String			denotacion;
	private UbicacionPais	pais;
	
	@Id
	@Column(name="seq_idnacionalidad")
	@SequenceGenerator(name="SeqNacionalidad", sequenceName="ministerio.tbl_dem_nacionalidad_seq_idnacionalidad_seq")
	@GeneratedValue(generator="SeqNacionalidad")
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

	@OneToOne
	@JoinColumn(name="int_idpais")
	public UbicacionPais getPais() {
		return pais;
	}

	public void setPais(UbicacionPais pais) {
		this.pais = pais;
	}

	@Transient
	public boolean isExtrangero() {
		return !pais.isLocal();
	}

	@Transient
	public String getStrPais() {
		return pais.getNombre();
	}
	
	
	public String toString(){
		return denotacion;
	}
	
	public boolean equals(Object objeto){
		try{
			Nacionalidad cuenta = (Nacionalidad) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_tipoverisuelo", schema="basico")
public class TipoVerificacionSuelo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3370761649255622450L;
	/**
	 * 
	 */
	
	private Integer		id;
	private String		descripcion;
	
	@Id
	@Column(name="seq_idtipoverisuelo")
	@SequenceGenerator(name="SeqTipoVerificacionSuelo", sequenceName="basico.tbl_dem_tipoverisuelo_seq_idtipoverisuelo_seq")
	@GeneratedValue(generator="SeqTipoVerificacionSuelo")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoVerificacionSuelo cuenta = (TipoVerificacionSuelo) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

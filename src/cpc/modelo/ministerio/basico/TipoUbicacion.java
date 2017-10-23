package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_tipoubicacion", schema="ministerio")
public class TipoUbicacion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8357336182133699534L;
	private Integer 	id;
	private String		descripcion;
	private Boolean		propietario;
	private Boolean		usoInterno;
	
	@Id
	@Column(name="seq_idtipoubicacion")
	@SequenceGenerator(name="SeqTipoUbicacion", sequenceName="ministerio.tbl_dem_tipoubicacion_seq_idtipoubicacion_seq")
	@GeneratedValue(generator="SeqTipoUbicacion")
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
	
	@Column(name="bol_propietario")
	public Boolean getPropietario() {
		return propietario;
	}
	public void setPropietario(Boolean propietario) {
		this.propietario = propietario;
	}
	
	@Column(name="bol_usointerno")
	public Boolean getUsoInterno() {
		return usoInterno;
	}
	public void setUsoInterno(Boolean usoInterno) {
		this.usoInterno = usoInterno;
	}
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoUbicacion cuenta = (TipoUbicacion) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

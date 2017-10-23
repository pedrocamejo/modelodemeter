package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tiposilo", schema="basico")
public class TipoUnidadArrime implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849780870311392867L;
	private Integer		id;
	private String		descripcion;
	private Boolean		activo;
	
	@Id
	@Column(name="seq_idtiposilo")
	@SequenceGenerator(name="seqTipoSilo", sequenceName="basico.tbl_dem_tiposilo_seq_idtiposilo_seq", allocationSize=1)
	@GeneratedValue(generator="seqTipoSilo")
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
	
	@Column(name="bol_activo")
	public Boolean isActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public String toString(){
		return descripcion;
	}

	public boolean equals(Object objeto){
		try{
			TipoUnidadArrime cuenta = (TipoUnidadArrime) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

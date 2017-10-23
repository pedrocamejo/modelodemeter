package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipotenencia", schema="ministerio")
public class TipoTenenciaTierra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3432258679742322148L;
	private Integer			id;
	private String			nombre;
	
	@Id
	@Column(name="seq_idtipotenencia")
	@SequenceGenerator(name="SeqTenenciaTierra", sequenceName="ministerio.tbl_dem_tipotenencia_seq_idtipotenencia_seq")
	@GeneratedValue(generator="SeqTenenciaTierra")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString(){
		return nombre;
	}
	public boolean equals(Object objeto){
		try{
			TipoTenenciaTierra cuenta = (TipoTenenciaTierra) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

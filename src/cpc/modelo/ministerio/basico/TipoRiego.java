package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tiporiego", schema="ministerio")
public class TipoRiego implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8409719857909287039L;
	private Integer		id;
	private String 		nombre;
	private boolean		tieneRiego;
	
	@Id
	@Column(name="seq_idtiporiego")
	@SequenceGenerator(name="SeqTipoRiego", sequenceName="ministerio.tbl_dem_tiporiego_seq_idtiporiego_seq")
	@GeneratedValue(generator="SeqTipoRiego")
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
	
	@Column(name="bol_tienriego")
	public boolean isTieneRiego() {
		return tieneRiego;
	}
	public void setTieneRiego(boolean tieneRiego) {
		this.tieneRiego = tieneRiego;
	}

	public String toString(){
		return nombre.toUpperCase();
	}
	
	public boolean equals(Object objeto){
		try{
			TipoRiego cuenta = (TipoRiego) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tiposuperficie", schema="ministerio")
public class TipoSuperficie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5659628162761410324L;
	private Integer			id;
	private String			nombre;
	private boolean			usable;
	
	@Id
	@Column(name="seq_idtiposuperficie")
	@SequenceGenerator(name="SeqTipoSuperficie", sequenceName="ministerio.tbl_dem_tiposuperficie_seq_idtiposuperficie_seq")
	@GeneratedValue(generator="SeqTipoSuperficie")
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

	@Column(name="bol_usable")
	public boolean isUsable() {
		return usable;
	}
	public void setUsable(boolean usable) {
		this.usable = usable;
	}
	
	public String toString(){
		return nombre;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoSuperficie cuenta = (TipoSuperficie) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

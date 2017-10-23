package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_sectoragricola", schema="basico")
public class SectorAgricola implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4209522994782117740L;
	private Integer			id;
	private String			nombre;
	
	@Id
	@Column(name="seq_idsectoragricola")
	@SequenceGenerator(name="SeqSectorAgricola", sequenceName="ministerio.tbl_dem_sectoragricola_seq_idsectoragricola_seq")
	@GeneratedValue(generator="SeqSectorAgricola")
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
			SectorAgricola cuenta = (SectorAgricola) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}

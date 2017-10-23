package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_tipousotierra", schema ="ministerio")
public class TipoUsoTierra implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7264376493107424853L;
	private Integer		id;
	private String		nombre;
	
	@Id
	@Column(name="seq_idtipousotierra")
	@SequenceGenerator(name="SeqTipoUsoTierra", sequenceName="ministerio.tbl_dem_tipousotierra_seq_idtipousotierra_seq")
	@GeneratedValue(generator="SeqTipoUsoTierra")
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
	public void setNombre(String descripcion) {
		this.nombre = descripcion;
	}
	
	public String toString(){
		return nombre;
	}
	public boolean equals(Object objeto){
		try{
			TipoUsoTierra cuenta = (TipoUsoTierra) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

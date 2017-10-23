package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipovialidad", schema="ministerio")
public class TipoVialidad implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2891894227402701754L;
	private Integer		id;
	private String		denotacion;
	
	@Id
	@Column(name="seq_idtipovialidad")
	@SequenceGenerator(name="SeqTipoVialidad", sequenceName="ministerio.tbl_dem_tipovialidad_seq_idtipovialidad_seq")
	@GeneratedValue(generator="SeqTipoVialidad")
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
	
	public String toString(){
		return denotacion;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoVialidad cuenta = (TipoVialidad) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

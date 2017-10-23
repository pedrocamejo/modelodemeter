package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_genero", schema="ministerio")
public class Genero implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8578404577506137442L;
	private Integer		id;
	private String		nombre;
	
	@Id
	@Column(name="seq_idgenero")
	@SequenceGenerator(name="SeqGenero", sequenceName="ministerio.tbl_dem_genero_seq_idgenero_seq")
	@GeneratedValue(generator="SeqGenero")
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
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Genero)) {
			return false;
		}
		Genero other = (Genero) o;
		return true && (getId().equals(other.getId()));
	}
}

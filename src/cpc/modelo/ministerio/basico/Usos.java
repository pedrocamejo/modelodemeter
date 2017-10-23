package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_usos", schema="ministerio")
public class Usos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8578404577506137442L;
	private Integer		id;
	private String		nombre;
	
	@Id
	@Column(name="seq_idusos")
	@SequenceGenerator(name="SeqUsos", sequenceName="ministerio.tbl_dem_usos_seq_idusos_seq")
	@GeneratedValue(generator="SeqUsos")
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
		if (!(o instanceof Usos)) {
			return false;
		}
		Usos other = (Usos) o;
		return true && (getId().equals(other.getId()));
	}
}

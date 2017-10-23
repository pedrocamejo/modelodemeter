package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="sno_cargos", schema = "sigesp")
public class Cargo implements Serializable{

	private static final long serialVersionUID = -6911516668287710031L;
	private Integer 	id;
	private String 		descripcion;
	
	public Cargo(){
	}
	
	@Column(name="seq_ser_cargo")
	@SequenceGenerator(name="Cargos_Gen", sequenceName="sigesp.sno_cargos_seq_ser_cargo_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="Cargos_Gen")
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
}
package cpc.modelo.demeter.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_especialidades", schema = "public")
public class EspecilidadCargo implements Serializable{

	private static final long serialVersionUID = -6911516668287710031L;
	private Long 	id;
	private String 	descripcion;
	
	public EspecilidadCargo(){
	}
	
	@Column(name="seq_ser_especialidad")
	@SequenceGenerator(name="EspecilidadCargo_Gen", sequenceName="public.tbl_dem_especialidades_seq_ser_especialidad_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="EspecilidadCargo_Gen")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EspecilidadCargo)) {
			return false;
		}
		EspecilidadCargo other = (EspecilidadCargo) o;
		return true && other.getId().equals(this.getId());
	}
}
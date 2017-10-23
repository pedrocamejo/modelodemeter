package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_estado", schema = "mantenimiento")
public class EstadoFuncional implements Serializable{
	
	private static final long serialVersionUID = 6621919254446480816L;
	private Integer 	id;
	private String 	descripcion;
	
	public EstadoFuncional(){	
	}
	
	public EstadoFuncional(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	@Column(name="seq_ser_estado")
	@SequenceGenerator(name="EstadoFuncional_Gen", sequenceName="mantenimiento.tbl_dem_estado_seq_ser_estado_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="EstadoFuncional_Gen")
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
	
	public boolean equals(Object objeto){
		try{
			EstadoFuncional cuenta = (EstadoFuncional) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {	
		return descripcion.toUpperCase().trim();
	}
}
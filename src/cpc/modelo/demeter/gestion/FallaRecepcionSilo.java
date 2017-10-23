package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_fallas_recepcion_silo", schema = "gestion")
public class FallaRecepcionSilo implements Serializable{

	private static final long serialVersionUID = -6911516668287710031L;
	private Integer 	id;
	private String 		descripcion;
	
	public FallaRecepcionSilo(){
	}
	
	
	
	public FallaRecepcionSilo(String descripcion) {
		super();
		this.descripcion = descripcion;
	}

	@Column(name="seq_idfallarecepcionsilo")
	@SequenceGenerator(name="fallarecepcionsilo_Gen", sequenceName="gestion.tbl_dem_fallas_recepcion_silo_seq_idfallarecepcionsilo_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="fallarecepcionsilo_Gen")
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
			FallaRecepcionSilo cuenta = (FallaRecepcionSilo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	public String toString(){
		return getDescripcion();
	}
}
package cpc.modelo.demeter.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipo_trabajador", schema = "public")
public class TipoTrabajador implements Serializable{

	private static final long serialVersionUID = 4228223117483236021L;
	private Integer 	id;
	private String 		descripcion;
	
	
	public TipoTrabajador(){
		
	}
	
	public TipoTrabajador(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	@Column(name="seq_ser_tipotraba")
	@SequenceGenerator(name="TipoTrabajador_Gen", sequenceName="public.tbl_dem_tipo_trabajador_seq_ser_tipotraba_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="TipoTrabajador_Gen")
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
			TipoTrabajador cuenta = (TipoTrabajador) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
	
	public String toString(){
		return descripcion;
	}

}
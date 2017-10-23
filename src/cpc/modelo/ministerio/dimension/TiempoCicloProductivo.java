package cpc.modelo.ministerio.dimension;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Audited @Entity
public class TiempoCicloProductivo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5803452746424175888L;
	private Integer 	id;
	private String		descripcion;
	
	
	@SequenceGenerator(name="cicloProductivo_Gen", sequenceName="basico.tbl_cicloproductivo_idcicloproductivo_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="cicloProductivo_Gen")
	@Column(name="idcicloproductivo", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean equals(Object objeto){
		try{
			TiempoCicloProductivo cuenta = (TiempoCicloProductivo) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

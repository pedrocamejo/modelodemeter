package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_cicloproductivo", schema = "basico")
public class CicloProductivo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5145361698019401620L;
	private Integer		id;
	private String		descripcion;
	
	@Id
	@Column(name="seq_idcicloproductivo")
	@SequenceGenerator(name="SeqCicloProductivo", sequenceName="ministerio.tbl_dem_cicloproductivo_seq_idcicloproductivo_seq")
	@GeneratedValue(generator="SeqCicloProductivo")
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
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			CicloProductivo cuenta = (CicloProductivo) objeto;
			if (cuenta.getId()==getId())
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}

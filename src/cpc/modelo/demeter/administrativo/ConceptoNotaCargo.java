package cpc.modelo.demeter.administrativo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Audited @Entity
@Table(name="tbl_dem_conceptonotacargo", schema="administracion")
public class ConceptoNotaCargo {
	private Integer	 id;
	private String	 descripcion;
	
	@Id 
	@Column(name="seq_idconcepto")
	@SequenceGenerator(name = "seq_idconcepto", sequenceName = "tbl_dem_conceptonotacargo_seq_idconcepto_seq", allocationSize = 1)
	@GeneratedValue(generator = "seq_idconcepto")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion" )
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return descripcion ;
	}
	
	
	
}

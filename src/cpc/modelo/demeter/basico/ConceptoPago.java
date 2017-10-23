package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_conceptopago", schema="basico")
public class ConceptoPago implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5456339671760751545L;
	private Integer id;
	private String 	descripcion;
	
	@Id
	@SequenceGenerator(sequenceName="basico.tbl_dem_conceptopago_seq_idconcepto_seq", name="seqConceptoPago", allocationSize=1)
	@GeneratedValue(generator="seqConceptoPago")
	@Column(name="seq_idconcepto")
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
			ConceptoPago cuenta = (ConceptoPago) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}

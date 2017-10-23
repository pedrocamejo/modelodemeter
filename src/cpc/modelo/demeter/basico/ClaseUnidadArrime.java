package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_clasesilo", schema="basico")
public class ClaseUnidadArrime implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849780870311392867L;
	private Integer				id;
	private TipoUnidadArrime	tipo;
	private String				descripcion;
	
	@Id
	@Column(name="seq_idclasesilo")
	@SequenceGenerator(name="seqClaseSilo", sequenceName="basico.tbl_dem_clasesilo_seq_idclasesilo_seq", allocationSize=1)
	@GeneratedValue(generator="seqClaseSilo")
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
	
	@ManyToOne
	@JoinColumn(name="int_idtiposilo")
	public TipoUnidadArrime getTipo() {
		return tipo;
	}
	public void setTipo(TipoUnidadArrime tipo) {
		this.tipo = tipo;
	}	
	
	public String	toString(){
		return descripcion;
	}

	public boolean equals(Object objeto){
		try{
			ClaseUnidadArrime cuenta = (ClaseUnidadArrime) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}

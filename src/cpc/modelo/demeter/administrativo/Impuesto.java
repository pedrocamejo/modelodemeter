package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipo_impuesto", schema="administracion")
public class Impuesto implements Serializable{
	
	private static final long serialVersionUID = -8638323658589147982L;
	private Integer	id;
	private String	descripcion;
	private double	porcentaje;
	
	public Impuesto() {
		super();
	}
	
	
	public Impuesto(int id, String descripcion, double porcentaje) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
	}
	
	@SequenceGenerator(name="SeqImpuesto", sequenceName="administracion.tbl_dem_tipo_impuesto_seq_idtipoimpuesto_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqImpuesto")
	@Column(name="seq_idtipoimpuesto")
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="dbl_porcentaje")
	public double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}
	@Override
	public String toString() {		
		return this.descripcion +" ["+ this.porcentaje+" % ]";
	}

	public boolean equals(Object objeto){
		try{
			Impuesto cuenta = (Impuesto) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
}

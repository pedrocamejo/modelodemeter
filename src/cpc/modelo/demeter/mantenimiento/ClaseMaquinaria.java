package cpc.modelo.demeter.mantenimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name="tbl_dem_ClaseMaquinaria" ,schema="mantenimiento")
public class ClaseMaquinaria {

	 private Integer id; //tractor, moto niveladora tractor
	 private String	 descripcion;
	 
	@Column(name="seq_idClaseMaquinaria")
	@SequenceGenerator(name="ClaseMaquinaria_Gen", sequenceName="mantenimiento.tbl_dem_ClaseMaquinaria_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="ClaseMaquinaria_Gen") 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClaseMaquinaria other = (ClaseMaquinaria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return descripcion.toUpperCase();
	}  
	
	
	
	
}

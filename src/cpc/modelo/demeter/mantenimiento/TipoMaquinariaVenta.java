package cpc.modelo.demeter.mantenimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name="tbl_dem_TipoMaquinariaVenta" ,schema="mantenimiento")
public class TipoMaquinariaVenta {
	 
	 private Integer id; // agricola, vial , cosechadora 
	 private String	 descripcion;

	 @Column(name="seq_idMaquinariaVenta")
	@SequenceGenerator(name="MaquinariaVenta_Gen", sequenceName="mantenimiento.tbl_dem_MaquinariaVenta_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="MaquinariaVenta_Gen")
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
		TipoMaquinariaVenta other = (TipoMaquinariaVenta) obj;
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

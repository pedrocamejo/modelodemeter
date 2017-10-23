package cpc.modelo.demeter.basico;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Table;

import cpc.modelo.demeter.basico.Trabajador;

@Audited @Entity
@Table(name="tbl_dem_trabajadores")
@DiscriminatorValue(value="3")
public class Conductor extends Trabajador{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539655288087006545L;

	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Conductor)) {
			return false;
		}
		Conductor other = (Conductor) o;
		return true && other.getId().equals(this.getId());
	}
}

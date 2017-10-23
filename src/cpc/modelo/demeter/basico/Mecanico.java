package cpc.modelo.demeter.basico;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Table;

import cpc.modelo.demeter.basico.Trabajador;

@Audited @Entity
@Table(name="tbl_dem_trabajadores")
@DiscriminatorValue(value="4")
public class Mecanico extends Trabajador{

	/**
	 * 
	 */
	private static final long serialVersionUID = -311345113276335169L;

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Mecanico)) {
			return false;
		}
		Mecanico other = (Mecanico) o;
		return true && other.getId().equals(this.getId());
	}
}

package cpc.modelo.demeter.basico;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Table;

import cpc.modelo.demeter.basico.Trabajador;

@Audited @Entity
@Table(name="tbl_dem_trabajadores")
@DiscriminatorValue(value="3")
public class Operador extends Trabajador{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2983927763464473353L;

	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Operador)) {
			return false;
		}
		Operador other = (Operador) o;
		return true && other.getId().equals(this.getId());
	}
	
}

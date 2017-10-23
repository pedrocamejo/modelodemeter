package cpc.modelo.demeter.basico;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Table;

import cpc.modelo.demeter.basico.Trabajador;

@Audited @Entity
@Table(name="tbl_dem_trabajadores")
@DiscriminatorValue(value="3")
public class TecnicoCampo extends Trabajador{

	/**
	 * 
	 */
	private static final long serialVersionUID = 887579481701470491L;

	public boolean equals(Object objeto){
		try{
			TecnicoCampo cuenta = (TecnicoCampo) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}

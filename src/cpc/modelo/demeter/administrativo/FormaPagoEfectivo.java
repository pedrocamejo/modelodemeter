package cpc.modelo.demeter.administrativo;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;



@Audited
@Entity
@Table(name="tbl_dem_Forma_Pago_Efectivo", schema="administracion")

public class FormaPagoEfectivo  extends FormaPago{

	
	
}

package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
@Audited
@Entity
@PrimaryKeyJoinColumn(name="int_iddetallearticulomovimiento")
@Table(name="tbl_dem_detalle_salida_interna_articulo", schema="mantenimiento")
public class DetalleSalidaInternaArticulo  extends DetalleArticuloMovimiento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3276477450695647475L;

}

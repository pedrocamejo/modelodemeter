package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.basico.Almacen;
@Audited
@Entity
@PrimaryKeyJoinColumn(name="int_iddetallearticulomovimiento")
@Table(name="tbl_dem_detalle_transferencia_articulo", schema="mantenimiento")
public class DetalleTransferenciaArticulo  extends DetalleArticuloMovimiento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8287852694376927611L;
	private Almacen almacenDestino;
	
	@OneToOne
	@JoinColumn(name="int_idalmacendestino")
	public Almacen getAlmacenDestino() {
		return almacenDestino;
	}
	public void setAlmacenDestino(Almacen almacenDestino) {
		this.almacenDestino = almacenDestino;
	}

}

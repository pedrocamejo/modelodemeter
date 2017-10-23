package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import cpc.modelo.sigesp.basico.Almacen;
@Audited
@Entity
@PrimaryKeyJoinColumn(name="int_iddetallearticulomovimiento")
@Table(name="tbl_dem_detalle_entrada_articulo", schema="mantenimiento")
public class DetalleEntradaArticulo extends DetalleArticuloMovimiento implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 6161770450729038315L;
private Almacen almacenDestino;

@OneToOne
@JoinColumn(name="int_idalmacendestino")
public Almacen getAlmacenDestino() {
	return almacenDestino;
}

public void setAlmacenDestino(Almacen almacenDestino) {
	this.almacenDestino = almacenDestino;
}
@Transient
public String getStrAlmacenDestino(){
	return this.almacenDestino.getNombre();
}
}

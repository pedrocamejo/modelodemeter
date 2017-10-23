package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table(name="tbl_dem_detalle_devolucion_articulo", schema="mantenimiento")
public class DetalleDevolucionArticulo  extends DetalleArticuloMovimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4240645094019064455L;
	
	
	private Almacen almacenDestino;
	private String nota;
	@OneToOne
	@JoinColumn(name="int_idalmacendestino")
	public Almacen getAlmacenDestino() {
		return almacenDestino;
	}

	public void setAlmacenDestino(Almacen almacenDestino) {
		this.almacenDestino = almacenDestino;
	}
	@Column (name="str_nota")
	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	

}

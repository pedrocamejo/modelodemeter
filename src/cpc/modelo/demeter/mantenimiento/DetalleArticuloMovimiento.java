package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.sigesp.basico.Almacen;
@Audited
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="tbl_dem_detalle_articulo_movimiento",schema="mantenimiento")

public class DetalleArticuloMovimiento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1520264278643660000L;
	private Integer 						id;
	private MovimientoArticulo	 		movimiento;
	private ArticuloVenta				articuloVenta;
	private Double 						cantidad;
	private Almacen 					almacenOrigen;
	private String						observacion;
	
	
	@Column(name="seq_iddetallearticulomovimiento")
	@SequenceGenerator (name= "Seq_iddetallearticulomovimiento", sequenceName="mantenimiento.tbl_dem_detalle_articulo_movi_seq_iddetallearticulomovimien_seq", allocationSize=1)
	@Id @GeneratedValue(generator="Seq_iddetallearticulomovimiento")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Basic
	@ManyToOne
	@JoinColumn(name="int_idmovimiento")
	
	public MovimientoArticulo getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(MovimientoArticulo movimiento) {
		this.movimiento = movimiento;
	}
	
	@OneToOne
    @JoinColumn(name="int_idarticulo")
	public ArticuloVenta getArticuloVenta() {
		return articuloVenta;
	}
	public void setArticuloVenta(ArticuloVenta articuloVenta) {
		this.articuloVenta = articuloVenta;
	}
	@Column (name="dlb_cantidad")
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	
	@OneToOne
	@JoinColumn(name="int_idalmacenorigen")
	public Almacen getAlmacenOrigen() {
		return almacenOrigen;
	}
	public void setAlmacenOrigen(Almacen almacen) {
		this.almacenOrigen = almacen;
	}
	
	@Column (name="str_observacion")
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
			}
	@Transient
	public String getStrAlmacenOrigen(){
		return this.almacenOrigen.getNombre();
	}
}

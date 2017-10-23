package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.ministerio.basico.TipoProductor;

@Audited @Entity
@Table(name="tbl_dem_precioproducto", schema="basico")
public class PrecioProducto implements Serializable, Comparable<PrecioProductoPK>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5154573758100024024L;
	private Integer			id;
	private Producto		producto;
	private TipoProductor	tipoCliente;
	private double			precio;
	
	/*@Transient
	private PrecioProductoPK idCompuesto;*/
	
	@Column(name="seq_idprecio")
	@SequenceGenerator(name="seqPrecio", sequenceName="basico.tbl_dem_precioproducto_seq_idprecio_seq", allocationSize =1)
	@Id @GeneratedValue(generator="seqPrecio")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idproducto")
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipocliente")
	public TipoProductor getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(TipoProductor tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	
	@Column(name="dbl_precio")
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String toString(){
		return String.valueOf(precio);
	}
	
	public int compareTo(PrecioProductoPK o) {
		PrecioProductoPK este = new PrecioProductoPK(producto, tipoCliente);
		return o.hashCode()-este.hashCode();
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PrecioProducto)) {
			return false;
		}
		PrecioProducto otro = (PrecioProducto) o;
		return otro.getIdCompuesto().equals(this.getIdCompuesto());
	}

	@Transient
	public String getIdCompuesto(){
		/*if (idCompuesto == null) 
			idCompuesto = new PrecioProductoPK(producto, tipoCliente);
		return idCompuesto;*/ 
		return String.format("%d-%d", producto.getId(),tipoCliente.getId());
	}
	
}

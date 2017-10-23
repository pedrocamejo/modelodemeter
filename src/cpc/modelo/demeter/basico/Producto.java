package cpc.modelo.demeter.basico;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.interfaz.IProducto;


@Audited @Entity
@Table(name="tbl_dem_producto", schema="basico")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Producto implements Serializable, IProducto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 605934918231700626L;
	private Integer									id;
	private String									descripcion;
	private TipoProducto							tipoProducto;
	private Set<PrecioProducto>						precios;
	private Impuesto								impuesto;
	
	@Column(name="seq_idproducto")
	@SequenceGenerator(name="seqProducto", sequenceName="basico.tbl_dem_producto_seq_idproducto_seq", allocationSize =1)
	@Id @GeneratedValue(generator="seqProducto")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}
	
	@OneToMany(mappedBy="producto", targetEntity=PrecioProducto.class)
	public Set<PrecioProducto> getPrecios() {
		return precios;
	}
	public void setPrecios(Set<PrecioProducto> precios) {
		this.precios = precios;
	}
	
	@ManyToOne
	@JoinColumn(name="seq_idtipoproducto")
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipoimpuesto")
	public Impuesto getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}
	
	@Transient
	public String getStrTipoProducto(){
		if ( tipoProducto == null)
			return null;
		else
			return tipoProducto.getDescripcion();
	}
	
	public String toString(){
		return descripcion;
	}
	
	@Transient
	public List<UsoPreciosProducto> getListadoPrecios() {
		return UsoPreciosProducto.getListaPrecios(getPrecios(), impuesto);
	}
	
	
	public boolean equals(Object objeto){
		try{
			Producto cuenta = (Producto) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}

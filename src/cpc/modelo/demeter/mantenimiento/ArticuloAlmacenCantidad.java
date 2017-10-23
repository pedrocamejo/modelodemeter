package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.sigesp.basico.Almacen;
@Audited
@Entity
@Table(name="tbl_dem_articulo_almacen_cantidad", schema="mantenimiento")
public class ArticuloAlmacenCantidad  implements Serializable{
	    
	

		/**
	 * 
	 */
	private static final long serialVersionUID = -769692718320892332L;
		//private Integer						id;
		private ArticuloAlmacenCantidadPK      id;
		private ArticuloVenta 		articuloVenta;
		
		private Almacen     				almacen;
		private Double						cantidad;
	
		//private Date						fechaRegistro, fechaActualizacion;
		//private String 						usuario, observacion, motivoDesincorporacion;
		//private MotivoTransferenciaActivo 	motivo;
	
		
		
		
		
		
		
		public ArticuloAlmacenCantidad() {
			super();
		}
		
		public ArticuloAlmacenCantidad(ArticuloVenta object,Almacen almacen, Double cantidad ) {
			super();
		       this.articuloVenta = object;
		       this.almacen = almacen;
		       this.cantidad = cantidad;
		      
		}
/*
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
*/
		
		@EmbeddedId
		public ArticuloAlmacenCantidadPK getId() {
			return id;
		}

		public void setId(ArticuloAlmacenCantidadPK id) {
			this.id = id;
		}
		
		/*
		@OneToMany(mappedBy="object")
		@JoinColumn(name="int_idobjeto")
		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}
	*/
		
		
		
		@ManyToOne
		@JoinColumn(name="int_idalmacen",insertable = false ,updatable = false)
		public Almacen getAlmacen() {
			return almacen;
		}

		public void setAlmacen(Almacen almacen) {
			this.almacen = almacen;
		}

		
		
		
		@Column(name="dlb_cantidad")
		public Double getCantidad() {
			return cantidad;
		}

		public void setCantidad(Double cantidad) {
			this.cantidad = cantidad;
		}

		@ManyToOne(targetEntity=ArticuloVenta.class )
		@JoinColumn(name="int_idarticulo",insertable = false ,updatable = false)
		public ArticuloVenta getArticuloVenta() {
			return articuloVenta;
		}

		public void setArticuloVenta(ArticuloVenta articuloVenta) {
			this.articuloVenta = articuloVenta;
		}
		
	@Transient 
	public String getStrAlmacen(){
		return this.almacen.getNombre();
		
	}
	
	@Transient 
	public String getStrArticuloVentaDen(){
		return this.articuloVenta.getDenominacionFabricante();
		
	}
	
	@Transient 
	public String getStrArticuloVentaCodFabricante(){
		return this.articuloVenta.getCodigoFabricante();
		
	}
		
		
}

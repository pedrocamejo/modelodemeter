package cpc.modelo.demeter.basico;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.basico.TipoProductor;
import cpc.modelo.sigesp.basico.TipoArticuloSIGESP;
import cpc.modelo.sigesp.basico.UnidadMedidaSIGESP;

@Audited @Entity
@Table(name="tbl_dem_costobancario", schema="basico")
@PrimaryKeyJoinColumn(name="int_idproducto")
public class CostoBancario extends Producto implements IProducto, Comparable<CostoBancario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6568238239030281360L;
	private boolean activo;
	private Double precio;
	@Transient
	private String 			codigoFabricante;
	@Transient
	private String 	      strTipo;
	@Transient
	public String getStrClase() {
		if (getTipoProducto() == null)
			return null;
		else
			return getTipoProducto().getDescripcion();
	}
	
	
	
	
	@Transient
	public Double getPrecioNeto() {
		if (precio !=null)
			return precio * (1 +getImpuesto().getPorcentaje()/100);
		return null;
	}@Transient
	public Double getIvaProducto(){
		if (precio !=null)
			return precio * (getImpuesto().getPorcentaje()/100);
		return null;
	}	

	@Transient
	public Double getPrecioBase(){
		return getPrecio();
	}
	
	@Transient
	public String getStrdPrecioBase(){
		return ""+getPrecio();
	}
	

	public String toString(){
		return getDescripcion();
	}
	
	@Column(name="dbl_precio")
	public Double getPrecio() {		
		return precio;
	}
	
	
	@Column(name="bol_activo")
	public boolean  getActivo(){		
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}


/*

	@Override
	public String getStrTipo() {
		// TODO Auto-generated method stub
		return null;
	}
*/



	public void setPrecio(Double precio) {
		this.precio = precio;
	}




	@Transient
	public String getCodigoFabricante() {
		return codigoFabricante;
	}

	public void setCodigoFabricante(String codigoFabricante) {
		this.codigoFabricante = codigoFabricante;
	}

	public Double getPrecioBase(TipoProductor tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public int compareTo(CostoBancario o) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Transient
	public void setStrTipo(String strTipo) {
		this.strTipo = strTipo;
	}



	@Transient
 	public String getStrTipo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
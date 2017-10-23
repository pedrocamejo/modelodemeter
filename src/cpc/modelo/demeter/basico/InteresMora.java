package cpc.modelo.demeter.basico;

import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.ministerio.basico.TipoProductor;


@Audited @Entity
@Table(name="tbl_dem_interesmora", schema="basico")
@PrimaryKeyJoinColumn(name="int_idproducto")
public class InteresMora extends Producto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2880720325495075070L;
	
	@Transient
	private double precio;

	@Transient
	private String			codigoFabricante;
	
	
	@Transient	
	public Double getIvaProducto() {
		return precio*getImpuesto().getPorcentaje()/100;
	}

	@Transient	
	public void setPrecio(double precio){
		this.precio = precio;
	}
	
	@Transient
	public Double getPrecioBase(TipoProductor tipo) {
		return precio;
	}
	
	@Transient
	public Double getPrecioNeto() {
		return precio*(1+getImpuesto().getPorcentaje()/100);
	}

	@Transient
	public String getStrClase() {
		return "INTERES MORA";
	}

	@Transient
	public String getStrTipo() {
		return "INTERES";
	}

	@Transient
	public String getCodigoFabricante() {
		
		return codigoFabricante;
	}

	public void setCodigoFabricante(String codigoFabricante) {
		this.codigoFabricante = codigoFabricante;
	}

	@Transient
	public boolean getActivo() {
		// TODO Auto-generated method stub
		return false;
	}

}

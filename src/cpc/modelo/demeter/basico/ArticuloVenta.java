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
@Table(name="tbl_dem_articulo", schema="basico")
@PrimaryKeyJoinColumn(name="int_idproducto")
public class ArticuloVenta extends Producto implements IProducto{

	private static final long serialVersionUID = -2991005515878546418L;
	
	private TipoArticuloSIGESP  tipoArticulo;
	private UnidadMedidaSIGESP  unidadMedida;
	private String        		codigoSIGESP;
	private String        		codigoCCNU;
	private String        		codigoFabricante;
	private String        		denominacionFabricante;
	private String 		  		cuentaPresupuestariaIngreso;
	private Double		  		precio;
	private boolean		  		activo;

	
	
	@OneToOne
	@JoinColumn(name="str_codtipart")
	public TipoArticuloSIGESP getTipoArticuloSIGESP() {
		return tipoArticulo;
	}


	public void setTipoArticuloSIGESP(TipoArticuloSIGESP tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}

	@OneToOne
	@JoinColumn(name="str_codunimed")
	public UnidadMedidaSIGESP getUnidadMedidaSIGESP() {
		return unidadMedida;
	}


	public void setUnidadMedidaSIGESP(UnidadMedidaSIGESP unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	@Column(name="str_codigoarticulo")
	public String getCodigoSIGESP() {
		return codigoSIGESP;
	}


	public void setCodigoSIGESP(String codigoSIGESP) {
		this.codigoSIGESP = codigoSIGESP.toUpperCase();
	}


	@Column(name="str_codccnu")
	public String getCodigoCCNU() {
		return codigoCCNU;
	}


	public void setCodigoCCNU(String codigoCCNU) {
		this.codigoCCNU = codigoCCNU.toUpperCase();
	}


	@Column(name="str_codigofabricante")
	public String getCodigoFabricante() {
		return codigoFabricante;
	}


	public void setCodigoFabricante(String codigoFabricante) {
		this.codigoFabricante = codigoFabricante.toUpperCase();
	}

	@Column(name="str_denfabricante")
	public String getDenominacionFabricante() {
		return denominacionFabricante;
	}


	public void setDenominacionFabricante(String denominacionFabricante) {
		this.denominacionFabricante = denominacionFabricante.toUpperCase();
	}

	@Column(name="str_spicuenta")
	public String getCuentaPresupuestariaIngreso() {
		return cuentaPresupuestariaIngreso;
	}


	public void setCuentaPresupuestariaIngreso(String cuentaPresupuestariaIngreso) {
		this.cuentaPresupuestariaIngreso = cuentaPresupuestariaIngreso;
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


	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	@Transient
	public String toString(){
		return getDescripcion();
	}
	
	@Transient
	public String getStrTipo() {
		if (tipoArticulo == null)
			return "";
		else
			return tipoArticulo.getDescripcion();
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
	
	@Transient
	public String getStrTipoArticulo() {
		return tipoArticulo.getDescripcion().toUpperCase();
	}
	
	@Transient
	public String getStrUnidadMedida() {
		return unidadMedida.getDescripcion().toUpperCase();
	}

	public boolean equals(Object objeto){
		try{
			ArticuloVenta cuenta = (ArticuloVenta) objeto;
			if (cuenta.getId().equals(this.getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

	
	@Transient
	public String getStrClase() {
		
		return null;
	}

	
	public Double getPrecioBase(TipoProductor tipo) {		
		return null;
	} 
	
	@Transient
	public String getStrInactivo() {
		if (activo)	{return "ACTIVO";} 
		else return "INACTIVO";  
	}
	
}
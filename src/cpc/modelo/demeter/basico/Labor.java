package cpc.modelo.demeter.basico;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.basico.TipoProductor;

@Audited @Entity
@Table(name="tbl_dem_labor", schema = "basico")
@PrimaryKeyJoinColumn(name="int_idproducto")
public class Labor extends Producto implements IProducto, Comparable<Labor>{
	
	private static final long serialVersionUID = 9178011312136527252L;
	private UnidadMedida	medidaGestion;
	private UnidadMedida	medidaCobro;
	private Servicio		servicio;
	private boolean         activo;
	
	@Transient
	private Double			precio;
	
	@Transient
	private String 			codigoFabricante;
	
	@Transient
	private List<UsoPreciosProducto> 	listaPrecios;
	
	
	public Labor() {
		super();
	}

	@ManyToOne
	@JoinColumn(name="int_idumedidalabor")
	public UnidadMedida getMedidaGestion() {
		return medidaGestion;
	}

	public void setMedidaGestion(UnidadMedida medidaGestion) {
		this.medidaGestion = medidaGestion;
	}

	@ManyToOne
	@JoinColumn(name="int_idumedidaprecio")
	public UnidadMedida getMedidaCobro() {
		return medidaCobro;
	}

	public void setMedidaCobro(UnidadMedida medidaCobro) {
		this.medidaCobro = medidaCobro;
	}

	@ManyToOne
	@JoinColumn(name="int_idservicio")
	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	
	

	@Column(name="bol_activo")
	public boolean  getActivo(){		
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Transient
	public String getStrClase() {
		if (servicio == null)
			return null;
		else
			return servicio.getDescripcion();
	}

	@Transient
	public String getStrTipo() {
		if (servicio == null)
			return null;
		else 
			return servicio.getStrTipoServicio();
	}
	
	@Transient
	public Double getPrecioNeto() {
		if (precio !=null)
			return precio * (1+getImpuesto().getPorcentaje()/100);
		return null;
	}
	
	@Transient
	public String getStrPrecioNeto() {
		return String.format("%1$.4f", getPrecioNeto());
	}
	
	@Transient
	public String getStrUnidadMedidaCobro() {
		if (medidaCobro == null)
			return "";
		return medidaCobro.getDescripcion();
	}
	
	@Transient
	public String getStrUnidadMedidagestion() {
		if (medidaGestion == null)
			return "";
		return medidaGestion.getDescripcion();
	}

	@Transient
	public Double getPrecioBase(TipoProductor tipo) {
		for (PrecioProducto item : getPrecios()) {
			if (item.getTipoCliente().equals(tipo)){
				precio= item.getPrecio();
				return precio;
			}
		}
		return null;
	}
	
	@Transient
	public Double getIvaProducto(){
		if (precio !=null)
			return precio * (getImpuesto().getPorcentaje()/100);
		return null;
	}

	public String toString(){
		return getDescripcion();
	}

	@Transient
	public void transferirPrecios() {
		listaPrecios = super.getListadoPrecios();
	}
	
	@Transient
	public List<UsoPreciosProducto> getListaPrecios() {
		return listaPrecios;
	}
	
	@Transient
	public String getCodigoFabricante() {
		return codigoFabricante;
	}

	public void setCodigoFabricante(String codigoFabricante) {
		this.codigoFabricante = codigoFabricante;
	}

	public void setListaPrecios(List<UsoPreciosProducto> listaPrecios) {
		this.listaPrecios = listaPrecios;
	}

	@Transient
	public Double getPrecio1(){
		List<UsoPreciosProducto> preciosT = UsoPreciosProducto.getListaPrecios(getPrecios(), getImpuesto());
		try{
			if (preciosT !=null)
				return preciosT.get(0).getPrecio()*(1+getImpuesto().getPorcentaje()/100);
		}catch (RuntimeException e) {
		}
		return null;
	}
	@Transient
	public Double getPrecio2(){
		List<UsoPreciosProducto> preciosT = UsoPreciosProducto.getListaPrecios(getPrecios(), getImpuesto());
		try{
			if (preciosT !=null)
				return preciosT.get(1).getPrecio()*(1+getImpuesto().getPorcentaje()/100);
		}catch (RuntimeException e) {
		}
		return null;
	}
	@Transient
	public Double getPrecio3(){
		List<UsoPreciosProducto> preciosT = UsoPreciosProducto.getListaPrecios(getPrecios(), getImpuesto());
		try{
			if (preciosT !=null)
				return preciosT.get(2).getPrecio()*(1+getImpuesto().getPorcentaje()/100);
		}catch (RuntimeException e) {
		}
		return null;
	}
	
	@Transient
	public Double getPrecio4(){
		List<UsoPreciosProducto> preciosT = UsoPreciosProducto.getListaPrecios(getPrecios(), getImpuesto());
		try{
			if (preciosT !=null)
				return preciosT.get(3).getPrecio()*(1+getImpuesto().getPorcentaje()/100);
		}catch (RuntimeException e) {
		}
		return null;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Labor)) {
			return false;
		}
		Labor other = (Labor) o;
		return true && other.getId().equals(this.getId());
	}

	public int compareTo(Labor o) {
		if (o == null) return -1;
		return this.getId() - o.getId();
	}

	@Transient
	public String getStrInactivo() {
		if (activo)	{return "ACTIVO";} 
		else return "INACTIVO";  
	}
	
}

package cpc.modelo.demeter.interfaz;

import java.util.Set;

import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.basico.PrecioProducto;
import cpc.modelo.ministerio.basico.TipoProductor;


public interface IProducto{
	public String getDescripcion();
	public String getStrClase();
	public String getStrTipo();
	public String getStrTipoProducto();
	public String getCodigoFabricante();
	
	public Integer getId();
	public Set<PrecioProducto> getPrecios();
	public Double getPrecioBase(TipoProductor tipo);
	public Double getPrecioNeto();
	public Double getIvaProducto();
	public Impuesto getImpuesto();
	public boolean getActivo();
}

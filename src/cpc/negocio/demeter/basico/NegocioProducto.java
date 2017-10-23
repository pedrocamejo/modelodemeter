package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.basico.Producto;
import cpc.modelo.demeter.basico.TipoProducto;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.basico.PerProducto;
import cpc.persistencia.demeter.implementacion.basico.PerTipoProducto;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioProducto extends NegocioGenerico<Producto, PerProducto, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioProducto 				negocio;
	
	private NegocioProducto(){
		setPersistencia(new PerProducto());
	}
	
	public  static synchronized NegocioProducto getInstance() {
		if (negocio == null)
			negocio = new NegocioProducto();
		return negocio;
	}

	public List<IProducto> getProductos() throws ExcFiltroExcepcion{
		List<IProducto> servicios = new PerLabor().getAllLabor();
		servicios.addAll(new PerArticuloVenta().getAllArticulo());
		return servicios; 
	}
	
	
	public List<TipoProducto> getTiposProductos() throws ExcFiltroExcepcion{
		return new PerTipoProducto().getAll(); 
	}

}

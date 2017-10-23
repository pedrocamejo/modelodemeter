package cpc.negocio.demeter.basico;

import cpc.modelo.demeter.basico.TipoProducto;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerTipoProducto;


public class NegocioTipoProducto extends NegocioGenerico<TipoProducto, PerTipoProducto, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoProducto 				negocio;
	
	private NegocioTipoProducto(){
		setPersistencia(new PerTipoProducto());
	}
	
	public  static synchronized NegocioTipoProducto getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoProducto();
		return negocio;
	}

	

}

package cpc.negocio.demeter.basico;




import java.util.List;

import cpc.modelo.demeter.basico.TipoVerificacionSuelo;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerTipoVerificacionSuelo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioVerificacionSuelo extends NegocioGenerico<TipoVerificacionSuelo, PerTipoVerificacionSuelo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioVerificacionSuelo 				negocio;
	
	private NegocioVerificacionSuelo(){
		setPersistencia(new PerTipoVerificacionSuelo());
	}
	
	public  static synchronized NegocioVerificacionSuelo getInstance() {
		if (negocio == null)
			negocio = new NegocioVerificacionSuelo();
		return negocio;
	}
	
	public List<TipoVerificacionSuelo> getVerificacionesSuelos() throws ExcFiltroExcepcion{
		return  new PerTipoVerificacionSuelo().getAll(); 
	}

	
}

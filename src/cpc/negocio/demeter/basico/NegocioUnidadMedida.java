package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.basico.TipoUnidadMedida;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.basico.PerTipoUnidadMedida;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioUnidadMedida extends NegocioGenerico<UnidadMedida, PerUnidadMedida, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioUnidadMedida 				negocio;
	
	private NegocioUnidadMedida(){
		setPersistencia(new PerUnidadMedida());
	}
	
	public  static synchronized NegocioUnidadMedida getInstance() {
		if (negocio == null)
			negocio = new NegocioUnidadMedida();
		return negocio;
	}

	
	public List<TipoUnidadMedida> getTipos() throws ExcFiltroExcepcion{
		return new PerTipoUnidadMedida().getAll(); 
	}

}

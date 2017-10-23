package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.basico.ClaseUnidadArrime;
import cpc.modelo.demeter.basico.TipoUnidadArrime;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerClaseUnidadArrime;
import cpc.persistencia.demeter.implementacion.basico.PerTipoUnidadArrime;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioClaseUnidadArrime extends NegocioGenerico<ClaseUnidadArrime, PerClaseUnidadArrime, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -873463696223498841L;
	private static NegocioClaseUnidadArrime 				negocio;
	
	private NegocioClaseUnidadArrime(){
		setPersistencia(new PerClaseUnidadArrime());
	}
	
	public  static synchronized NegocioClaseUnidadArrime getInstance() {
		if (negocio == null)
			negocio = new NegocioClaseUnidadArrime();
		return negocio;
	}

	public List<TipoUnidadArrime> obtenerTiposUnidadArrime() throws ExcFiltroExcepcion{
		return new PerTipoUnidadArrime().getAll();
	} 
	
}

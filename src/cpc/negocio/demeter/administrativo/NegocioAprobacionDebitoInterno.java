package cpc.negocio.demeter.administrativo;

import java.util.List;

import cpc.modelo.demeter.administrativo.AprobacionDebitoInterno;
import cpc.modelo.demeter.administrativo.TipoAprobacionDebitoInterno;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.administrativo.PerAprobacionDebitoInterno;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioAprobacionDebitoInterno extends
		NegocioGenerico<AprobacionDebitoInterno, PerAprobacionDebitoInterno, Integer> {

	
	/**
	 * 
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2523820061945904081L;
	/**
	 * 
	 */
	
	private static NegocioAprobacionDebitoInterno negocio;

	private NegocioAprobacionDebitoInterno() {
		setPersistencia(new PerAprobacionDebitoInterno());
	}

	public static synchronized NegocioAprobacionDebitoInterno getInstance() {
		if (negocio == null)
			negocio = new NegocioAprobacionDebitoInterno();
		return negocio;
	}

	

	

	
	public List<AprobacionDebitoInterno> getAll() throws ExcFiltroExcepcion {
		return new PerAprobacionDebitoInterno().getAll();
	}
	
	public List<TipoAprobacionDebitoInterno> getAllTipos() throws ExcFiltroExcepcion {
		return new PerAprobacionDebitoInterno().getTiposAprobacionDebitoInternos();
	}

	public void guardar(AprobacionDebitoInterno aprobacion){
	
		new PerAprobacionDebitoInterno().guardar(aprobacion, aprobacion.getId());
		
	}
	
	
		
}
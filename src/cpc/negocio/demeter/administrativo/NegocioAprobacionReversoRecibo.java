package cpc.negocio.demeter.administrativo;

import java.util.Date;
import java.util.List;

import cpc.modelo.demeter.administrativo.AprobacionDebito;
import cpc.modelo.demeter.administrativo.AprobacionReversoRecibo;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.administrativo.PerAprobacionDebito;
import cpc.persistencia.demeter.implementacion.administrativo.PerAprobacionReversoRecibo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioAprobacionReversoRecibo extends
		NegocioGenerico<AprobacionReversoRecibo, PerAprobacionReversoRecibo, Integer> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3895966011665363400L;
	/**
	 * 
	 */
	
	private static NegocioAprobacionReversoRecibo negocio;

	private NegocioAprobacionReversoRecibo() {
		setPersistencia(new PerAprobacionReversoRecibo());
	}

	public static synchronized NegocioAprobacionReversoRecibo getInstance() {
		if (negocio == null)
			negocio = new NegocioAprobacionReversoRecibo();
		return negocio;
	}

	public AprobacionReversoRecibo nuevo() throws ExcFiltroExcepcion {
		setModelo(new AprobacionReversoRecibo());
		getModelo().setfechaaprobacion(new Date());
return getModelo();
	}

	/*
	 * public List<ClaseArticulo> getClases() throws ExcFiltroExcepcion{ return
	 * new PerClaseArticulo().getAll(); }
	 */

	public void setAprobacionReversoRecibo(AprobacionReversoRecibo aprobacionrReversoRecibo) {
		super.setModelo(aprobacionrReversoRecibo);
	}

	/*
	 * public List<TipoArticuloSIGESP> getTipoArticulos() throws
	 * ExcFiltroExcepcion{ return new PerTipoArticulo().getAll(); }
	 * 
	 * public List<UnidadMedidaSIGESP> getUnidades() throws ExcFiltroExcepcion{
	 * return new PerUnidadMedida().getTodos(); }
	 */
	public List<AprobacionDebito> getAll() throws ExcFiltroExcepcion {
		return new PerAprobacionDebito().getAll();
	}

	
	public List<AprobacionDebito> getAll(Date inicio,Date fin) throws ExcFiltroExcepcion {
		return new PerAprobacionDebito().getAllAprobacionDebito(inicio,fin);
	}
	
}
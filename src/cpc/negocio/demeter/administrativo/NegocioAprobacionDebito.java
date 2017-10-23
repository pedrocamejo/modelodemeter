package cpc.negocio.demeter.administrativo;

import java.util.Date;
import java.util.List;

import cpc.modelo.demeter.administrativo.AprobacionDebito;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.administrativo.PerAprobacionDebito;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioAprobacionDebito extends
		NegocioGenerico<AprobacionDebito, PerAprobacionDebito, Integer> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6483035818679196484L;
	private static NegocioAprobacionDebito negocio;

	private NegocioAprobacionDebito() {
		setPersistencia(new PerAprobacionDebito());
	}

	public static synchronized NegocioAprobacionDebito getInstance() {
		if (negocio == null)
			negocio = new NegocioAprobacionDebito();
		return negocio;
	}

	public AprobacionDebito nuevo() throws ExcFiltroExcepcion {
		setModelo(new AprobacionDebito());
		getModelo().setFechaAprobacion(new Date());
return getModelo();
	}

	/*
	 * public List<ClaseArticulo> getClases() throws ExcFiltroExcepcion{ return
	 * new PerClaseArticulo().getAll(); }
	 */

	public void setAprobacionDebito(AprobacionDebito aprobacionDebito) {
		super.setModelo(aprobacionDebito);
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
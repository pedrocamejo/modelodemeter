package cpc.negocio.demeter.administrativo;

import java.util.Date;
import java.util.List;

import cpc.modelo.demeter.administrativo.AprobacionDescuento;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.administrativo.PerAprobacionDescuento;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioAprobacionDescuento extends
		NegocioGenerico<AprobacionDescuento, PerAprobacionDescuento, Integer> {

	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioAprobacionDescuento negocio;

	private NegocioAprobacionDescuento() {
		setPersistencia(new PerAprobacionDescuento());
	}

	public static synchronized NegocioAprobacionDescuento getInstance() {
		if (negocio == null)
			negocio = new NegocioAprobacionDescuento();
		return negocio;
	}

	public AprobacionDescuento nuevo() throws ExcFiltroExcepcion {
		setModelo(new AprobacionDescuento());
		getModelo().setfechaaprobacion(new Date());
return getModelo();
	}

	/*
	 * public List<ClaseArticulo> getClases() throws ExcFiltroExcepcion{ return
	 * new PerClaseArticulo().getAll(); }
	 */

	public void setAprobacionDescuento(AprobacionDescuento aprobacionDescuento) {
		super.setModelo(aprobacionDescuento);
	}

	/*
	 * public List<TipoArticuloSIGESP> getTipoArticulos() throws
	 * ExcFiltroExcepcion{ return new PerTipoArticulo().getAll(); }
	 * 
	 * public List<UnidadMedidaSIGESP> getUnidades() throws ExcFiltroExcepcion{
	 * return new PerUnidadMedida().getTodos(); }
	 */
	public List<AprobacionDescuento> getAll() throws ExcFiltroExcepcion {
		return new PerAprobacionDescuento().getAll();
	}

}
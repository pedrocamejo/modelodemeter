package cpc.negocio.ministerio.basico;
import java.util.List;

import cpc.modelo.ministerio.dimension.TipoUnidad;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.Unidad;

import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerDireccion;
import cpc.persistencia.ministerio.basico.PerTipoUnidad;
import cpc.persistencia.ministerio.basico.PerUnidad;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioUnidad extends NegocioGenerico<Unidad, PerUnidad, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioUnidad 				negocio;
	
	private NegocioUnidad(){
		setPersistencia(new PerUnidad());
	}
	
	public  static synchronized NegocioUnidad getInstance() {
		if (negocio == null)
			negocio = new NegocioUnidad();
		return negocio;
	}
	

	public List<TipoUnidad> getTiposUnidades() throws ExcFiltroExcepcion{
		return new PerTipoUnidad().getAll();
	}
	
	public List<UbicacionDireccion> getUbicacionesFisicas() throws ExcFiltroExcepcion{
		return new PerDireccion().getAll();
	}
	
}

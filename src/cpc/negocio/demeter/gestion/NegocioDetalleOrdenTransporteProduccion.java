package cpc.negocio.demeter.gestion;


import java.util.List;

import cpc.modelo.demeter.basico.UnidadArrime;
import cpc.modelo.demeter.gestion.DetalleOrdenTransporteProduccion;
import cpc.modelo.demeter.gestion.FallaRecepcionSilo;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerUnidadArrime;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleOrdenTransporteProduccion;
import cpc.persistencia.demeter.implementacion.gestion.PerFallaRecepcionSilo;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioDetalleOrdenTransporteProduccion extends NegocioGenerico<DetalleOrdenTransporteProduccion, PerDetalleOrdenTransporteProduccion, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioDetalleOrdenTransporteProduccion 		negocio;
	
	private NegocioDetalleOrdenTransporteProduccion(){
		setPersistencia(new PerDetalleOrdenTransporteProduccion());
	}
	
	public  static synchronized NegocioDetalleOrdenTransporteProduccion getInstance() {
		if (negocio == null)
			negocio = new NegocioDetalleOrdenTransporteProduccion();
		return negocio;
	}

	
	public List<FallaRecepcionSilo> getFallas() throws ExcFiltroExcepcion{
		return new PerFallaRecepcionSilo().getAll();
	}
	
	public List<UnidadArrime> getSilos() throws ExcFiltroExcepcion{
		return new PerUnidadArrime().getAll();
	}
	
	
	public void guardar(DetalleOrdenTransporteProduccion datoProcesado, DetalleOrdenTransporteProduccion nuevo) throws ExcFiltroExcepcion {
		getPersistencia().guardar(datoProcesado, nuevo);
	}
	
}

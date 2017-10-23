package cpc.negocio.demeter.gestion;

import java.util.Date;
import java.util.List;

import cpc.modelo.demeter.gestion.SabanaMecanizadoAgricola;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.gestion.PerSabanaMecanizadoAgricola;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSabanaMecanizado extends NegocioGenerico<SabanaMecanizadoAgricola, PerSabanaMecanizadoAgricola, Integer>{

	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioSabanaMecanizado 				negocio;
	
	private NegocioSabanaMecanizado(){
		setPersistencia(new PerSabanaMecanizadoAgricola());
	}
	
	public  static synchronized NegocioSabanaMecanizado getInstance() {
		if (negocio == null)
			negocio = new NegocioSabanaMecanizado();
		return negocio;
	}

	public List<SabanaMecanizadoAgricola> getListadoServicoMecanizado(Date inicio, Date fin, UbicacionSector sector ) throws ExcFiltroExcepcion{
		return getPersistencia().getSabanaServicioMecanizacion(inicio, fin, sector);
	}
}

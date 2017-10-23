package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerDireccion;
import cpc.persistencia.ministerio.basico.PerParroquia;
import cpc.persistencia.ministerio.basico.PerSector;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSector extends
		NegocioGenerico<UbicacionSector, PerSector, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioSector negocio;

	private NegocioSector() {
		setPersistencia(new PerSector());
	}

	public static synchronized NegocioSector getInstance() {
		if (negocio == null)
			negocio = new NegocioSector();
		return negocio;
	}

	public List<UbicacionParroquia> getParroquias() throws ExcFiltroExcepcion {
		return new PerParroquia().getAll();
	}

	public List<UbicacionDireccion> getMunicipios() throws ExcFiltroExcepcion {
		return new PerDireccion().getTodos(getModelo());
	}

	public List<UbicacionSector> getTodosPorNombreYParroquia(String nombre,
			UbicacionParroquia parroquia) throws ExcFiltroExcepcion {
		return getPersistencia().getTodosPorNombreYParroquia(nombre, parroquia);
	}
}

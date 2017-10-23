package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerMunicipio;
import cpc.persistencia.ministerio.basico.PerParroquia;
import cpc.persistencia.ministerio.basico.PerSector;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioParroquia extends NegocioGenerico<UbicacionParroquia, PerParroquia, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioParroquia 				negocio;
	
	private NegocioParroquia(){
		setPersistencia(new PerParroquia());
	}
	
	public  static synchronized NegocioParroquia getInstance() {
		if (negocio == null)
			negocio = new NegocioParroquia();
		return negocio;
	}

	
	public List<UbicacionMunicipio> getMunicipios() throws ExcFiltroExcepcion{
		return new PerMunicipio().getAll(); 
	}

	public List<UbicacionSector> getSectores() throws ExcFiltroExcepcion{
		return new PerSector().getTodos(getModelo()); 
	}
}

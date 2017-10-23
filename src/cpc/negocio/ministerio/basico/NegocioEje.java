package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.ministerio.dimension.UbicacionEje;
import cpc.modelo.ministerio.dimension.UbicacionEstado;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionPais;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerEje;
import cpc.persistencia.ministerio.basico.PerEstado;
import cpc.persistencia.ministerio.basico.PerMunicipio;
import cpc.persistencia.ministerio.basico.PerPais;
import cpc.persistencia.ministerio.basico.PerParroquia;
import cpc.persistencia.ministerio.basico.PerSector;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioEje extends NegocioGenerico<UbicacionEje, PerEje, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioEje 				negocio;
	
	private PerEstado 		perEstado;
	private PerMunicipio 	perMunicipio;
	private PerParroquia  	perParroquia;
	private PerSector		perSector;
	
	
	private NegocioEje(){
		setPersistencia(new PerEje());
		
		perEstado = new PerEstado();
		perMunicipio = new PerMunicipio();
		perParroquia = new PerParroquia();
		perSector = new PerSector();
	}
	
	public  static synchronized NegocioEje getInstance() {
		if (negocio == null)
			negocio = new NegocioEje();
		return negocio;
	}

	
	public List<UbicacionPais> getPaises() throws ExcFiltroExcepcion{
		return new PerPais().getAll(); 
	}


	
	public List<UbicacionEstado> getEstados() throws ExcFiltroExcepcion{
		if(getModelo() == null)
		{
			return perEstado.getAll();
		}
		return new PerEstado().getTodos(getModelo()); 
	}
	
	public List<UbicacionMunicipio> getMunicipios(UbicacionEstado estado) throws ExcFiltroExcepcion
	{
		return perMunicipio.getTodos(estado);
	}

	public List<UbicacionParroquia> getParroquias(UbicacionMunicipio municipio) throws ExcFiltroExcepcion
	{
		return perParroquia.getTodos(municipio);
	}

	public List<UbicacionSector> getSectores(UbicacionParroquia parroquia) throws ExcFiltroExcepcion
	{
		return perSector.getTodos(parroquia);
	}
	
}

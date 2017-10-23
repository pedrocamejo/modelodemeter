package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.ministerio.dimension.UbicacionEje;
import cpc.modelo.ministerio.dimension.UbicacionEstado;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionPais;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerEje;
import cpc.persistencia.ministerio.basico.PerEstado;
import cpc.persistencia.ministerio.basico.PerMunicipio;
import cpc.persistencia.ministerio.basico.PerPais;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioEstado extends NegocioGenerico<UbicacionEstado, PerEstado, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioEstado 				negocio;
	
	private NegocioEstado(){
		setPersistencia(new PerEstado());
	}
	
	public  static synchronized NegocioEstado getInstance() {
		if (negocio == null)
			negocio = new NegocioEstado();
		return negocio;
	}
	
	public List<UbicacionPais> getPaises() throws ExcFiltroExcepcion{
		return new PerPais().getAll(); 
	}
	
	public List<UbicacionEje> getEjes() throws ExcFiltroExcepcion{
		return new PerEje().getAll(); 
	}

	public List<UbicacionEje> getEjes(UbicacionPais pais) throws ExcFiltroExcepcion{
		return new PerEje().getTodos(pais); 
	}
	
	public List<UbicacionMunicipio> getMunicipios() throws ExcFiltroExcepcion{
		return new PerMunicipio().getTodos(getModelo()); 
	}
	
}

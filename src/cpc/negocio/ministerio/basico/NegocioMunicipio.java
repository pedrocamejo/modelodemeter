package cpc.negocio.ministerio.basico;


import java.util.List;

import cpc.modelo.ministerio.dimension.UbicacionEstado;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerEstado;
import cpc.persistencia.ministerio.basico.PerMunicipio;
import cpc.persistencia.ministerio.basico.PerParroquia;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioMunicipio extends NegocioGenerico<UbicacionMunicipio, PerMunicipio, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioMunicipio 				negocio;
	
	private NegocioMunicipio(){
		setPersistencia(new PerMunicipio());
	}
	
	public  static synchronized NegocioMunicipio getInstance() {
		if (negocio == null)
			negocio = new NegocioMunicipio();
		return negocio;
	}

	
	public List<UbicacionEstado> getEstados() throws ExcFiltroExcepcion{
		return new PerEstado().getAll(); 
	}

	public List<UbicacionParroquia> getParroquias() throws ExcFiltroExcepcion{
		return new PerParroquia().getTodos(getModelo()); 
	}
}

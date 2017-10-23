package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.ministerio.basico.Nacionalidad;
import cpc.modelo.ministerio.dimension.UbicacionPais;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerNacionalidad;
import cpc.persistencia.ministerio.basico.PerPais;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioNacionalidad extends NegocioGenerico<Nacionalidad, PerNacionalidad, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioNacionalidad 				negocio;
	
	private NegocioNacionalidad(){
		setPersistencia(new PerNacionalidad());
	}
	
	public  static synchronized NegocioNacionalidad getInstance() {
		if (negocio == null)
			negocio = new NegocioNacionalidad();
		return negocio;
	}

	
	public List<UbicacionPais> getPaises() throws ExcFiltroExcepcion{
		return new PerPais().getAll(); 
	}

}

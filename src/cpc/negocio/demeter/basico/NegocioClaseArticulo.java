package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.basico.ClaseArticulo;
import cpc.modelo.demeter.basico.TipoArticulo;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerClaseArticulo;
import cpc.persistencia.demeter.implementacion.basico.PerTipoArticulo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioClaseArticulo extends NegocioGenerico<ClaseArticulo, PerClaseArticulo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioClaseArticulo 				negocio;
	
	private NegocioClaseArticulo(){
		setPersistencia(new PerClaseArticulo());
	}
	
	public  static synchronized NegocioClaseArticulo getInstance() {
		if (negocio == null)
			negocio = new NegocioClaseArticulo();
		return negocio;
	}

	
	public List<TipoArticulo> getTipos() throws ExcFiltroExcepcion{
		return new PerTipoArticulo().getAll(); 
	}
}

package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.ministerio.gestion.Organizacion;
import cpc.modelo.ministerio.gestion.TipoOrganizacion;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerOrganizacion;
import cpc.persistencia.ministerio.basico.PerTipoOrganizacion;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioOrganizacion extends NegocioGenerico<Organizacion, PerOrganizacion, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioOrganizacion 				negocio;
	
	private NegocioOrganizacion(){
		setPersistencia(new PerOrganizacion());
	}
	
	public  static synchronized NegocioOrganizacion getInstance() {
		if (negocio == null)
			negocio = new NegocioOrganizacion();
		return negocio;
	}

	
	public List<TipoOrganizacion> getTiposOrganizaciones() throws ExcFiltroExcepcion{
		return new PerTipoOrganizacion().getAll(); 
	}


}

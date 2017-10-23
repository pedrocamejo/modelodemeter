package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.gestion.TipoOrganizacion;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerTipoOrganizacion;

public class NegocioTipoOrganizacion extends NegocioGenerico<TipoOrganizacion, PerTipoOrganizacion, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoOrganizacion 				negocio;
	
	private NegocioTipoOrganizacion(){
		setPersistencia(new PerTipoOrganizacion());
	}
	
	public  static synchronized NegocioTipoOrganizacion getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoOrganizacion();
		return negocio;
	}

}

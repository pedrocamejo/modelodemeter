package cpc.persistencia.ministerio.basico;

import cpc.modelo.ministerio.gestion.TipoOrganizacion;
import cpc.persistencia.DaoGenerico;





public class PerTipoOrganizacion extends DaoGenerico<TipoOrganizacion, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerTipoOrganizacion() {
		super(TipoOrganizacion.class);

	}

	
}

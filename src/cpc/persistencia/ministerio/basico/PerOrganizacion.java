package cpc.persistencia.ministerio.basico;

import cpc.modelo.ministerio.gestion.Organizacion;
import cpc.persistencia.DaoGenerico;


public class PerOrganizacion extends DaoGenerico<Organizacion, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerOrganizacion() {
		super(Organizacion.class);
	}
	
	
}

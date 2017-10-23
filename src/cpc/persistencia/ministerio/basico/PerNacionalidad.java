package cpc.persistencia.ministerio.basico;

import cpc.modelo.ministerio.basico.Nacionalidad;
import cpc.persistencia.DaoGenerico;


public class PerNacionalidad extends DaoGenerico<Nacionalidad, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerNacionalidad() {
		super(Nacionalidad.class);

	}

}

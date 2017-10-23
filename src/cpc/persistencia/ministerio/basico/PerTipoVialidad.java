package cpc.persistencia.ministerio.basico;

import cpc.modelo.ministerio.basico.TipoVialidad;
import cpc.persistencia.DaoGenerico;




public class PerTipoVialidad extends DaoGenerico<TipoVialidad, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerTipoVialidad() {
		super(TipoVialidad.class);

	}

	
}

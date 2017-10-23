package cpc.persistencia.ministerio.basico;

import cpc.modelo.ministerio.dimension.TipoUnidad;
import cpc.persistencia.DaoGenerico;





public class PerTipoUnidad extends DaoGenerico<TipoUnidad, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerTipoUnidad() {
		super(TipoUnidad.class);

	}

	
}

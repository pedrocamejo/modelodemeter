package cpc.persistencia.ministerio.basico;

import cpc.modelo.ministerio.basico.TipoProductor;
import cpc.persistencia.DaoGenerico;




public class PerTipoProductor extends DaoGenerico<TipoProductor, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerTipoProductor() {
		super(TipoProductor.class);

	}

	
}

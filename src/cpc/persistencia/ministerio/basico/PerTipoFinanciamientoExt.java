package cpc.persistencia.ministerio.basico;

import cpc.modelo.ministerio.gestion.TipoFinanciamientoCrediticio;
import cpc.persistencia.DaoGenerico;

public class PerTipoFinanciamientoExt extends DaoGenerico<TipoFinanciamientoCrediticio, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerTipoFinanciamientoExt() {
		super(TipoFinanciamientoCrediticio.class);

	}

	
}

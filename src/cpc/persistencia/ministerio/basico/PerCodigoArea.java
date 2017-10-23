package cpc.persistencia.ministerio.basico;

import cpc.modelo.demeter.basico.CodigoArea;
import cpc.persistencia.DaoGenerico;





public class PerCodigoArea extends DaoGenerico<CodigoArea, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerCodigoArea() {
		super(CodigoArea.class);

	}

}

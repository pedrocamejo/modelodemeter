package cpc.persistencia.ministerio.basico;

import cpc.modelo.demeter.basico.SectorAgricola;
import cpc.persistencia.DaoGenerico;



public class PerSectorAgricola extends DaoGenerico<SectorAgricola, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerSectorAgricola() {
		super(SectorAgricola.class);
	}

}

package cpc.persistencia.ministerio.basico;


import cpc.modelo.demeter.basico.TipoCoordenadaGeografica;
import cpc.persistencia.DaoGenerico;

public class PerTipoCoordenada extends DaoGenerico<TipoCoordenadaGeografica, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerTipoCoordenada() {
		super(TipoCoordenadaGeografica.class);
	}

	
}

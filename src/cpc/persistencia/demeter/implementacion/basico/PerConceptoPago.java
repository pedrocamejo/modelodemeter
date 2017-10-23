package cpc.persistencia.demeter.implementacion.basico;

import cpc.modelo.demeter.basico.ConceptoPago;
import cpc.persistencia.DaoGenerico;

public class PerConceptoPago extends DaoGenerico<ConceptoPago, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerConceptoPago() {
		super(ConceptoPago.class);
	}
	
}

package cpc.persistencia.demeter.implementacion.basico;

import cpc.modelo.demeter.basico.CicloProductivo;
import cpc.persistencia.DaoGenerico;


public class PerCicloProductivo extends DaoGenerico<CicloProductivo, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerCicloProductivo() {
		super(CicloProductivo.class);
	}
	
}

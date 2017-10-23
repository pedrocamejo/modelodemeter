package cpc.persistencia.demeter.implementacion.mantenimiento;

import cpc.modelo.demeter.mantenimiento.MomentoFalla;
import cpc.persistencia.DaoGenerico;

public class PerMomentoFalla extends DaoGenerico<MomentoFalla, Integer>{

	

	private static final long serialVersionUID = 4262849881174532532L;

	public PerMomentoFalla() {
		super(MomentoFalla.class);
	}
}
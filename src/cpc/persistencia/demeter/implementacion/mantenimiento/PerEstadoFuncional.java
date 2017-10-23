package cpc.persistencia.demeter.implementacion.mantenimiento;


import cpc.modelo.demeter.mantenimiento.EstadoFuncional;
import cpc.persistencia.DaoGenerico;

public class PerEstadoFuncional extends DaoGenerico<EstadoFuncional, Integer>{

	private static final long serialVersionUID = 3410227421083988304L;

	public PerEstadoFuncional() {
		super(EstadoFuncional.class);
	}
}
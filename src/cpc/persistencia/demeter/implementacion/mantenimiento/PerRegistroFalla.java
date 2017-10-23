package cpc.persistencia.demeter.implementacion.mantenimiento;


import cpc.modelo.demeter.mantenimiento.RegistroFalla;
import cpc.persistencia.DaoGenerico;

public class PerRegistroFalla extends DaoGenerico<RegistroFalla, Long>{

	
	private static final long serialVersionUID = 2433533404193449110L;
	public PerRegistroFalla() {
		super(RegistroFalla.class);
	}
	
}

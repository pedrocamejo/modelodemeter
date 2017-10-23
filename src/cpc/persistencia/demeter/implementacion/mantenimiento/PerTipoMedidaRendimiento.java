package cpc.persistencia.demeter.implementacion.mantenimiento;

import cpc.modelo.demeter.mantenimiento.TipoMedidaRendimiento;
import cpc.persistencia.DaoGenerico;

public class PerTipoMedidaRendimiento extends DaoGenerico<TipoMedidaRendimiento, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410227421083988304L;

	public PerTipoMedidaRendimiento() {
		super(TipoMedidaRendimiento.class);
	}
}
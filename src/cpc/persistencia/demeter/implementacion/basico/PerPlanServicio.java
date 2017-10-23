package cpc.persistencia.demeter.implementacion.basico;

import cpc.modelo.demeter.basico.PlanServicio;
import cpc.persistencia.DaoGenerico;

public class PerPlanServicio extends DaoGenerico<PlanServicio, Integer>{

	private static final long serialVersionUID = -7328452047757768298L;

	public PerPlanServicio() {
		super(PlanServicio.class);
	}
	
}

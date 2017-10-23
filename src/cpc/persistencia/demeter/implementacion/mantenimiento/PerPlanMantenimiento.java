package cpc.persistencia.demeter.implementacion.mantenimiento;
 
import cpc.modelo.demeter.mantenimiento.PlanMantenimiento;
import cpc.persistencia.DaoGenerico;

public class PerPlanMantenimiento extends DaoGenerico<PlanMantenimiento, Integer>{

	private static final long serialVersionUID = 4262849881174532532L;

	public PerPlanMantenimiento() {
		super(PlanMantenimiento.class);
	}
}
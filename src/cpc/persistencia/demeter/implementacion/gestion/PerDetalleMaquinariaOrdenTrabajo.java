package cpc.persistencia.demeter.implementacion.gestion;

import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.DetalleOrdenTrabajo;
import cpc.persistencia.DaoGenerico;



public class PerDetalleMaquinariaOrdenTrabajo extends DaoGenerico<DetalleMaquinariaOrdenTrabajo, Long>{

	
	private static final long serialVersionUID = 9163448561567355812L;

	public PerDetalleMaquinariaOrdenTrabajo() {
		super(DetalleOrdenTrabajo.class);		
	}
	
	
}

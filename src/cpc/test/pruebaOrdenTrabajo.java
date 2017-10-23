package cpc.test;


import java.util.List;

import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.negocio.demeter.gestion.NegocioOrdenTrabajoMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoTrabajo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaOrdenTrabajo {

	public static void main(String[] args) throws ExcFiltroExcepcion {
		
		/*PerTipoTrabajo perTipoTrabajo = new PerTipoTrabajo();
		System.out.println(perTipoTrabajo.getTipoMecanizado().getDescripcion()); 
		NegocioOrdenTrabajoMecanizado negocio = NegocioOrdenTrabajoMecanizado.getInstance();
		negocio.getTodos();*/
		PerOrdenTrabajoMecanizado perOrdenTrabajoMecanizado = new PerOrdenTrabajoMecanizado();
		List<OrdenTrabajoMecanizado> a = perOrdenTrabajoMecanizado.getActivasRecientes();
		System.out.println(a.size());
		
	}
}
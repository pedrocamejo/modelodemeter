package prueba;

import java.util.ArrayList;
import java.util.List;

import cpc.modelo.demeter.gestion.OrdenTrabajoTransporte;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoTransporte;


public class pruebasReportes {
public List<OrdenTrabajoTransporte> getOrdenes() {
	List<OrdenTrabajoTransporte> prueba= new ArrayList<OrdenTrabajoTransporte>();
	try {
		prueba.addAll(new PerOrdenTrabajoTransporte().getAll());
		return prueba;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return prueba;
	}
	
}
public List<OrdenTrabajoTransporte> getOrdenes2() {
	List<OrdenTrabajoTransporte> prueba= new ArrayList<OrdenTrabajoTransporte>();
	return prueba;
	
}
}

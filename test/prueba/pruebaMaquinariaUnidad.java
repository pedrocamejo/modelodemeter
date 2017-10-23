package prueba;

import java.util.List;
 

import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.OrdenTrabajoTransporte;
import cpc.negocio.demeter.gestion.NegocioOrdenTrabajoTransporte;
import cpc.persistencia.demeter.implementacion.gestion.PerImplementoUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoTransporte;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaMaquinariaUnidad {
public static void main (String[] args) throws ExcFiltroExcepcion{
	//NegocioOrdenTrabajoTransporte negocio = NegocioOrdenTrabajoTransporte.getInstance();
	
	List<OrdenTrabajoTransporte> ordenTrabajoTransportes = new PerOrdenTrabajoTransporte().getAll();
	for (OrdenTrabajoTransporte ordenTrabajoTransporte : ordenTrabajoTransportes) {

		 OrdenTrabajoTransporte ordenTrabajoTransporte2 = new PerOrdenTrabajoTransporte().inizializar(ordenTrabajoTransporte);
		System.out.println(ordenTrabajoTransporte2.getEquipos());
	}
	List<OrdenTrabajoTransporte> ordens= new PerOrdenTrabajoTransporte().getActivas();
	List<MaquinariaUnidad> aa = new PerMaquinariaUnidad().getMaquinariaTransporte();
	System.out.println(aa.size());
	List<ImplementoUnidad> bb = new PerImplementoUnidad().getImplementoTransporte();
	System.out.println(bb.size());
}
}

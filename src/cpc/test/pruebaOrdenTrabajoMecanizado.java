package cpc.test;

import java.util.List;

import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.negocio.demeter.gestion.NegocioOrdenTrabajoMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoTrabajo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaOrdenTrabajoMecanizado {
	
	public static void main(String[] args) throws ExcFiltroExcepcion, ExcDatosNoValido {
		NegocioOrdenTrabajoMecanizado negocio = NegocioOrdenTrabajoMecanizado.getInstance();
		//System.out.println(new PerTipoTrabajo().getTipoMecanizado().getDescripcion());
		/*List<Trabajador> empleados = negocio.getOperadores();
		for (Trabajador activo : empleados) {
			System.out.println(activo.getApellido()+" "+activo.getNombre());
		}
		List<MaquinariaUnidad> todos = negocio.getMaquinarias(new PerUnidadFuncional().buscarId(1));
		for (MaquinariaUnidad activo : todos) {
			System.out.println(activo.getActivo().getDescripcionMarca().trim()+" "+activo.getActivo().getSerial());
		}*/
	/*	List<DetalleContrato> cttos = negocio.getContratos();
		for (DetalleContrato item: cttos) {
			System.out.println(item.getNombreCliente()+" "+item.getStrNroDocumento()+" "+item.getStrProducto());
		}*/
		/*List<OperadorOrdenMecanizado> listado = negocio.getListadoOperador(new Date(1,1,2000), new Date(1,1,2020), new PerSector().buscarId(19));
		for (OperadorOrdenMecanizado item: listado) {
			System.out.println(item.getOperador().getNombre());
		}*/
		List<OrdenTrabajoMecanizado> a = negocio.getTodosProject();
		for (OrdenTrabajoMecanizado ordenTrabajoMecanizado : a) {
	//		System.out.println(ordenTrabajoMecanizado.getId());
		}
	}

}

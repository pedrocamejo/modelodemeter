package cpc.test;

import java.util.List;

import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.negocio.demeter.gestion.NegocioSolicitudMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerControlUnidadFuncional;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaSolicitudMecanizado {
	
	public static void main(String[] args) throws ExcFiltroExcepcion, ExcDatosNoValido {
		NegocioSolicitudMecanizado negocio = NegocioSolicitudMecanizado.getInstance();
		@SuppressWarnings("unused")
		List<SolicitudMecanizado> todos = negocio.getTodos();
		//SolicitudMecanizado nuevo = negocio.getNuevaSolicitud();
		ControlUnidadFuncional control = new PerControlUnidadFuncional().getControl(new PerUnidadFuncional().getAll().get(1));
		System.out.println(control);
	}

}

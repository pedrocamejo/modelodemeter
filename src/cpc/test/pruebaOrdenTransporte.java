package cpc.test;

import java.util.List;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;

import cpc.modelo.demeter.gestion.OrdenTransporteProduccion;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.negocio.demeter.gestion.NegocioOrdenTransporteProduccion;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaOrdenTransporte {
	
	public static void main(String[] args) throws ExcFiltroExcepcion, ExcDatosNoValido {
		NegocioOrdenTransporteProduccion negocio = NegocioOrdenTransporteProduccion.getInstance();
		OrdenTransporteProduccion	orden= negocio.getNuevaOrdenTrabajo();
		System.out.println(orden.getTipo().getDescripcion());
		List<Labor> empleados = negocio.getLabores();
		for (Labor activo : empleados) {
			System.out.println(activo.getDescripcion());
		}
		List<MaquinariaUnidad> todos = negocio.getMaquinarias(new PerUnidadFuncional().buscarId(1));
		for (MaquinariaUnidad activo : todos) {
			System.out.println(activo.getActivo().getDescripcionMarca().trim()+" "+activo.getActivo().getSerial());
		}
	}

}

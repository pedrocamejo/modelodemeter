package cpc.test;

import java.util.List;

import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.basico.PerServicio;
import cpc.persistencia.demeter.implementacion.basico.PerTipoUnidadMedida;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PruebaUnidadMedida {
	public static void main(String[] args) throws ExcFiltroExcepcion {
		PerUnidadMedida persistencia = new PerUnidadMedida();
		List<Servicio> servicios = new PerServicio().getAll();
		for (Servicio unidadMedida : servicios) {
			System.out.printf("%s %s %d \n", unidadMedida.getDescripcion(), unidadMedida.getTipoUnidadMedida().getDescripcion(), unidadMedida.getTipoUnidadMedida().getId());
		}
		System.out.println("----------------------------------------------------------");
		List<UnidadMedida> unidades = persistencia.getAll(new PerTipoUnidadMedida().buscarId(6));
		for (UnidadMedida unidadMedida : unidades) {
			System.out.printf("%s %s \n", unidadMedida.getDescripcion(), unidadMedida.getAbreviatura());
		}
		System.out.println("----------------------------------------------------------");
		List<UnidadMedida> unidadesHijas = persistencia.getAll(persistencia.buscarId(12));
		for (UnidadMedida unidadMedida : unidadesHijas) {
			System.out.printf("%s %s \n", unidadMedida.getDescripcion(), unidadMedida.getAbreviatura());
		}
		
	}
}

package cpc.test;

import cpc.negocio.demeter.basico.NegocioArticuloVenta;
import cpc.negocio.demeter.basico.NegocioLabor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaServicio {

	public static void main(String[] args) throws ExcFiltroExcepcion {
		
		NegocioArticuloVenta negocio = NegocioArticuloVenta.getInstance();
		negocio.getTodos();		
		negocio.getClases();
		NegocioLabor	negocio2 = NegocioLabor.getInstance();
		negocio2.getTodos();
		negocio2.getTipos();
		negocio2.getServicios();
		negocio2.getUnidadesMedidas();
		
	
	}
}
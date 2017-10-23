package cpc.test;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.Sistema;
import cpc.negocio.demeter.mantenimiento.NegocioSistemaBienProduccion;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaSistema {

	/**
	 * @param args
	 * @throws ExcFiltroExcepcion 
	 */

	public static void main(String[] args) throws ExcFiltroExcepcion {

		try {
			
			NegocioSistemaBienProduccion negocio = NegocioSistemaBienProduccion.getInstance();	
			try {
					List<Sistema> ordenes = negocio.getTodos();
					for (Sistema orden : ordenes) {
						System.out.println("Id: "+orden.getId()
										   +"... Descrp: "+orden.getDescripcion()
										   );
					}
			} catch (Exception e) { e.printStackTrace(); 	}
		}catch(Exception e){}
		
	}
}
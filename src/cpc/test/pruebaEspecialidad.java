package cpc.test;

import java.util.List;
import cpc.modelo.demeter.basico.EspecilidadCargo;
import cpc.negocio.demeter.NegocioEspecialidadCargo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaEspecialidad {

	public static void main(String[] args) throws ExcFiltroExcepcion {

		try {
			
			NegocioEspecialidadCargo negocio = NegocioEspecialidadCargo.getInstance();	
			try {
					List<EspecilidadCargo> ordenes = negocio.getTodos();
					for (EspecilidadCargo orden : ordenes) {
						System.out.println("Id: "+orden.getId()
										   +"... Descrp: "+orden.getDescripcion()
										   );
					}
			} catch (Exception e) { e.printStackTrace(); 	}
		}catch(Exception e){}
		
	}
}
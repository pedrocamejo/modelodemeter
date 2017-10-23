package cpc.test;

import java.util.List;
import cpc.modelo.demeter.gestion.FallaRecepcionSilo;
import cpc.negocio.demeter.gestion.NegocioFallaRecepcionSilo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaFallaRecepcionSilo {

	public static void main(String[] args) throws ExcFiltroExcepcion {

		try {
			
			NegocioFallaRecepcionSilo negocio = NegocioFallaRecepcionSilo.getInstance();	
			try {
					List<FallaRecepcionSilo> ordenes = negocio.getTodos();
					for (FallaRecepcionSilo orden : ordenes) {
						System.out.println("Id: "+orden.getId()
										   +"... Descrp: "+orden.getDescripcion()
										   );
					}
			} catch (Exception e) { e.printStackTrace(); 	}
		}catch(Exception e){}
		
	}
}

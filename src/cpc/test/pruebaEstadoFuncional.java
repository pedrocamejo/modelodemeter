package cpc.test;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.EstadoFuncional;
import cpc.negocio.demeter.mantenimiento.NegocioEstadoFuncional;


import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaEstadoFuncional  {

	public static void main(String[] args) throws ExcFiltroExcepcion {

					
		NegocioEstadoFuncional negocio = NegocioEstadoFuncional.getInstance();	
			
			/*try { 
				
				//-------------Crea un Registro------------------//
				EstadoFuncional estado = new EstadoFuncional();
				EstadoFuncional.setDescripcion("inactivo");
				negocio.setEstadoFuncional(estado);
				negocio.guardar();	*/	
							
			
			try {
					//---------Lista todos los Registros-----//	
					List<EstadoFuncional> ordenes = negocio.getTodos();
					for (EstadoFuncional orden : ordenes) {
						System.out.println("Id: "+orden.getId()
										   +"... Descrp: "+orden.getDescripcion()
										   );
					}
			} catch (Exception e) { e.printStackTrace(); 	}
			
		/*}catch(Exception e){}*/
		
	}
}

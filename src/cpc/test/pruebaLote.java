package cpc.test;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.Lote;
import cpc.negocio.demeter.mantenimiento.NegocioLote;



import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaLote  {

	public static void main(String[] args) throws ExcFiltroExcepcion {

					
			NegocioLote negocio = NegocioLote.getInstance();	
			
			try { 
				
				//-------------Crea un Registro------------------//
				Lote lote = new Lote();
				lote.setDescripcion("2009-2");
				negocio.setLote(lote);
				negocio.guardar();		
							
			
			try {
					//---------Lista todos los Registros-----//	
					List<Lote> ordenes = negocio.getTodos();
					for (Lote orden : ordenes) {
						System.out.println("Id: "+orden.getId()
										   +"... Descrp: "+orden.getDescripcion()
										   );
					}
			} catch (Exception e) { e.printStackTrace(); 	}
			
		}catch(Exception e){}
		
	}
}

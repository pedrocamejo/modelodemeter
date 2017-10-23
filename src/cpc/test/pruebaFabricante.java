package cpc.test;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.Fabricante;
import cpc.negocio.demeter.mantenimiento.NegocioFabricante;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaFabricante  {

	public static void main(String[] args) throws ExcFiltroExcepcion {

					
			NegocioFabricante negocio = NegocioFabricante.getInstance();	
			
			try { 
				
				//-------------Crea un Registro------------------//
				Fabricante fabricante = new Fabricante();
				fabricante.setDescripcion("UN Fabricante");
				negocio.setFabricante(fabricante);
				negocio.guardar();		
							
			
			try {
					//---------Lista todos los Registros-----//	
					List<Fabricante> ordenes = negocio.getTodos();
					for (Fabricante orden : ordenes) {
						System.out.println("Id: "+orden.getId()
										   +"... Descrp: "+orden.getDescripcion()
										   );
					}
			} catch (Exception e) { e.printStackTrace(); 	}
			
		}catch(Exception e){}
		
	}
}

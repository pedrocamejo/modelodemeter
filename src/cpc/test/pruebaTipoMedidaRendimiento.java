package cpc.test;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.TipoMedidaRendimiento;
import cpc.negocio.demeter.mantenimiento.NegocioTipoMedidaRendimiento;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaTipoMedidaRendimiento {

	public static void main(String[] args) throws ExcFiltroExcepcion {

					
			NegocioTipoMedidaRendimiento negocio = NegocioTipoMedidaRendimiento.getInstance();	
			
			try { 
				
				//-------------Crea un Registro------------------//
				TipoMedidaRendimiento tipo = new TipoMedidaRendimiento();
				tipo.setDescripcion("horas3");
				tipo.setTipoHora(true);
				negocio.setTipoMedidaRendimiento(tipo);
				negocio.guardar();
				
				TipoMedidaRendimiento tipo2 = new TipoMedidaRendimiento();
				tipo.setDescripcion("horas4");
				tipo.setTipoHora(false);
				negocio.setTipoMedidaRendimiento(tipo2);
								
				negocio.guardar();		
							
			
			try {
					//---------Lista todos los Registros-----//	
					List<TipoMedidaRendimiento> ordenes = negocio.getTodos();
					for (TipoMedidaRendimiento orden : ordenes) {
						System.out.println("Id: "+orden.getId()
										   +"... Descrp: "+orden.getDescripcion()
										   );
					}
			} catch (Exception e) { e.printStackTrace(); 	}
			
		}catch(Exception e){}
		
	}
}


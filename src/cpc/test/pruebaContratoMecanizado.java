package cpc.test;

import java.util.List;

import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.negocio.demeter.administrativo.NegocioContratoMecanizado;
import cpc.persistencia.demeter.implementacion.PerCliente;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class pruebaContratoMecanizado {

	public static void main(String[] args) throws ExcFiltroExcepcion {

		try {			
			NegocioContratoMecanizado negocio = NegocioContratoMecanizado.getInstance();
			/*List<SolicitudMecanizado> solicitudes = negocio.getSolicitudes();
			List<ContratoMecanizado> contratos = negocio.getContratos();*/
			Cliente cliente = new PerCliente().buscarId(114);
			List<UnidadProductiva> unidades = negocio.getDirecciones(cliente);
			for (UnidadProductiva item : unidades) {
				System.out.println(item.getDireccion());
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
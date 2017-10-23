package cpc.test;

import java.util.List;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerContrato;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class pruebaContrato {

	public static void main(String[] args) throws ExcFiltroExcepcion {

		try {			
			PerContrato negocio = new PerContrato();
			List<Contrato> cttos = negocio.getContratosParaAbono();
			for (Contrato item : cttos) {
				System.out.println(item.getStrNroDocumento());
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
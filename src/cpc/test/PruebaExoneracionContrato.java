package cpc.test;

import java.util.List;


import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.EstadoExoneracionContrato;
import cpc.negocio.demeter.administrativo.NegocioFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoExoneracionContrato;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PruebaExoneracionContrato {

public void test(){
EstadoExoneracionContrato 	NOEXONERADO = new PerEstadoExoneracionContrato().getNoExonerado();
EstadoExoneracionContrato	EXONERACIONRECHAZADA =new PerEstadoExoneracionContrato().getExoneracionRechazada();
EstadoExoneracionContrato 	EXONERADO =new PerEstadoExoneracionContrato().getExonerado();
EstadoExoneracionContrato   POREXONERAR=new PerEstadoExoneracionContrato().getPorExonerar();
try {
	List<Contrato> lista = NegocioFactura.getInstance().getContratosPost();
	System.out.println(lista.size());
} catch (ExcFiltroExcepcion e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
}
}

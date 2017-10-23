package cpc.modelo.demeter.administrativo;


import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

 
import org.junit.Test;

import static org.junit.Assert.*;
import cpc.negocio.demeter.administrativo.NegocioCierreDiario;
import cpc.persistencia.demeter.implementacion.administrativo.PerRecibo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class CierreDiarioTest {

	@Test
	public void cierrediaro_xml() throws ExcFiltroExcepcion, ParserConfigurationException, TransformerException
	{
		NegocioCierreDiario negocio = NegocioCierreDiario.getInstance();
		List lista = negocio.getTodos();
		negocio.cierreDiarioToXml(lista.subList(lista.size()-500, lista.size()));
	}

	@Test
	public void recibos_por_dia() throws ExcFiltroExcepcion, ParserConfigurationException, TransformerException
	{
		
		
		NegocioCierreDiario negocio = NegocioCierreDiario.getInstance();
		CierreDiario c = negocio.getCierre(1997);
		PerRecibo perRecibo = new PerRecibo();
		//"2016-05-12"
		System.out.print(c.getFecha().getDate());
		assertEquals(2016,c.getFecha().getYear()+1900);
		assertEquals(5,c.getFecha().getMonth()+1);
		assertEquals(12,c.getFecha().getDate());
		
		assertEquals(3,perRecibo.getAll(c.getFecha().getDate(),
					c.getFecha().getMonth()+1,
					c.getFecha().getYear()+1900).size());
	}
	
}

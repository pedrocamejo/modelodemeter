package prueba.reporte;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;


import org.junit.Test;



import cpc.negocio.demeter.administrativo.NegocioDeposito;

public class TestComandoDepositos {

	@Test
	public void test() {
		try {
			NegocioDeposito negocio = NegocioDeposito.getInstance();

			
			Date inicio = new GregorianCalendar(2015,1,1).getTime();
			Date fin =  new GregorianCalendar(2015,12,31).getTime();

			
			HashMap parametros = new HashMap();
			//System.out.println(String.format("%1$td/%1$tm/%1$tY", inicio));
			//System.out.println(String.format("%1$td/%1$tm/%1$tY", fin));
			//parametros.put("inicio", String.format("%1$td/%1$tm/%1$tY", inicio));
			//parametros.put("fin", String.format("%1$td/%1$tm/%1$tY", fin));
			parametros.put("usuario","asdasd");
			//parametros.put("logo", Index.class.getResource("/").getPath()+ "../../imagenes/cintillo_inst.png");
			List lista = negocio.getTodos(inicio, fin);
			//JRDataSource ds = new JRBeanCollectionDataSource(lista);
			System.out.println(lista.size());
		
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}

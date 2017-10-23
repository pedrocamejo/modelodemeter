package prueba.reporte;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
 
import java.sql.Date;
import java.util.ArrayList; 
 
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

import cpc.modelo.demeter.administrativo.Recibo;
import cpc.negocio.demeter.administrativo.NegocioFormaPago;

public class TestComandoCierreCaja {

	@Test
	public void test() throws IOException {
 	/*
		NegocioFormaPago negocio = NegocioFormaPago.getInstance();
		Date inicio = new Date(2015,01,01);
		Date fin  = new Date(2015,12,31); 

		HashMap parametros = new HashMap();
 
		parametros.put("usuario","AutoGenerado");
		parametros.put("logo","/home/rchirinos/Programacion/Demeter/Worskpacesincro/demeterSedes/WebContent/imagenes/cintillo_inst.png");
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(negocio.getAllPagos(inicio, fin));
		
		String url = "/home/rchirinos/worskpace/demeterSedes/WebContent/reportes/CajaDiaria.jasper";
		JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(url));
		List lista = negocio.getAllPagos(inicio, fin);
 		byte[] resultado = JasperRunManager.runReportToPdf(reporte,parametros, new JRBeanCollectionDataSource(lista));

		FileOutputStream fileOuputStream = new FileOutputStream("report.pdf");
		fileOuputStream.write(resultado);
		fileOuputStream.close();

		*/
 		
		
		
	}

}

package prueba;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.sigesp.basico.CuentaBancaria;
import cpc.negocio.demeter.administrativo.NegocioRecibo;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class PruebaReciboPdf {

	/**
	 * @param args
	 * el banco en la forma de pago transferencia 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
  
			JasperReport reporte;
			try {
				
				NegocioRecibo negocioRecibo = NegocioRecibo.getInstance();
	 
				Recibo recibo = negocioRecibo.getPersistenciaRecibo().buscarId(new Long(2555));
				List<Recibo> lista = new ArrayList();
				lista.add(recibo);
				System.out.println("<<<<< ----------------------------------------- >>>>");
				System.out.println(recibo.getFormaspago());
				System.out.println(recibo.getConcepto());
				System.out.println(recibo.getCliente());
				System.out.println(recibo.getStrIdentidadLegalPagador());
				System.out.println(recibo.getStrPagador());
				System.out.println("<<<<< ----------------------------------------- >>>>");
				
				
				HashMap mapa = new HashMap();
				
				mapa.put("logo","/home/rchirinos/worskpacenew/demeterSedes/WebContent/imagenes/cintillo_inst.png");
				reporte = (JasperReport) JRLoader.loadObject("/home/rchirinos/worskpacenew/demeterSedes/WebContent/reportes/recibo.jasper");
				
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,mapa,new JRBeanCollectionDataSource(lista));
				
				JRExporter exporter = new JRPdfExporter();
				
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File("/home/rchirinos/hola.pdf"));
				
				exporter.exportReport();
				
			
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
	}

}

package prueba;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.Reintegro;
import cpc.negocio.demeter.administrativo.NegocioRecibo;
import cpc.negocio.demeter.administrativo.NegocioReintegro;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PruebaReintegroPDF {
	public static void main(String[] args) throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
  
			JasperReport reporte;
			try {
				
				NegocioReintegro negocio = new NegocioReintegro();
				
				List lista = new ArrayList();
				Reintegro reintegro = negocio.getall().get(0);
				for(Recibo r : reintegro.getRecibos())
				{
					for(FormaPago fp : r.getFormaspago())
					{
						System.out.println(fp.getFecha());
					}
				}
				
				lista.add(reintegro);
				
				HashMap mapa = new HashMap();
				mapa.put("sede","escp central");
				mapa.put("logo","/home/rchirinos/worskpacenew/demeterSedes/WebContent/imagenes/cintillo_inst.png");
				mapa.put("prueba","valor por Defecto --- :_D  ");
				reporte = (JasperReport) JRLoader.loadObject("/home/rchirinos/worskpacenew/demeterSedes/WebContent/reportes/reintegroNotaCredito.jasper");
				
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

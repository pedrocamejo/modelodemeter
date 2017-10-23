package cpc.negocio.demeter;

import java.util.Date;

import util.Seguridad;

import cpc.modelo.demeter.ReporteCodigo;
import cpc.persistencia.demeter.PerReporteCodigo;

public class NegocioReporteCodigo {
	
	private  PerReporteCodigo perReporteCodigo;
 
	
	private static NegocioReporteCodigo negocio;
 	
	public NegocioReporteCodigo( ) 
	{
		super();
		perReporteCodigo = new PerReporteCodigo();
		
	}
	
	public  static synchronized NegocioReporteCodigo getInstance() {
		if (negocio == null)
			negocio = new NegocioReporteCodigo( );
		return negocio;
	}
	
	
	public ReporteCodigo  getReporteCodigo(String codigo) throws Exception
	{
		return perReporteCodigo.getReporteCodigo(codigo);
	}
	
	//Codigo para generar el Md5 y la fecha de la operacion  y el usuario que reliza la operacion 
	public void guadar(String codigotoMD5,Date fecha,String usuario) throws Exception
	{
		String codigo = Seguridad.getMD5(codigotoMD5+fecha.toString());
		ReporteCodigo reporte = new ReporteCodigo();
		reporte.setUsername(usuario);
		reporte.setFecha(fecha);
		reporte.setCodigo(codigo);
		perReporteCodigo.guardar(reporte,null);
	}
		 
}

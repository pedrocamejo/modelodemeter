package cpc.negocio.demeter.administrativo.sigesp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.zkoss.zkplus.spring.SpringUtil;

import util.ZipFile;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.sigesp.ExtracionDatosSigesp;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerDeposito;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerFormaPago;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaCredito;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaDebito;
import cpc.persistencia.demeter.implementacion.administrativo.sigesp.PerExtracionDatosSigesp;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioExtracionDatosSigesp implements Serializable {
	
	private PerExtracionDatosSigesp perExtracionDatosSigesp; 
	private PerFormaPago 			perFormaPago;
	private PerDeposito				perDeposito;
	private PerFactura				perFactura;
	private PerNotaDebito			perNotaDebito;
	private PerNotaCredito 			perNotaCredito;
	
	
	
	private static NegocioExtracionDatosSigesp negocio;
	
	public NegocioExtracionDatosSigesp() {
		super();
		perExtracionDatosSigesp = new PerExtracionDatosSigesp();
		perFormaPago = new PerFormaPago();
		perDeposito = new PerDeposito();
		perFactura = new PerFactura();
		perNotaDebito = new PerNotaDebito();
		perNotaCredito = new PerNotaCredito();
		
		
	}
	
	public  static synchronized NegocioExtracionDatosSigesp getInstance() {
		if (negocio == null)
			negocio = new NegocioExtracionDatosSigesp();
		return negocio;
	}

	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	
	
	public byte[] generarZip(ExtracionDatosSigesp modelo) throws Exception
	{
		File file = new File(modelo.getFilename());
		
		if(file.isFile())
		{
			List<File> files = new ArrayList<File>();
			files.add(file);
			return ZipFile.GenerarZip(files).toByteArray();
		}
		return "".getBytes();
	}
	
	public void guardar(ExtracionDatosSigesp modelo) throws Exception
	{
		ControlSede sede = getControlSede();
		//validar fechas 
		Integer ano = (new Date().getYear()+1900);
		Integer mes = (new Date().getMonth());
		
		if(modelo.getAno() > ano)
		{
			throw new Exception("Año Invalido ");
		}
		else
		{
			if(modelo.getAno() == ano && modelo.getMes() > mes)
			{
				throw new Exception("Mes Invalido No Sido Terminado");
			}
		}

		//Nombre Archivo 
		String nombreFile = (sede.getStrSede() +"-"+ modelo.getAno() +"-"+ modelo.getMes()+".xml");
		modelo.setNombre(nombreFile.trim().replace(" ","-"));
		modelo.setNombreSede(sede.getStrSede());

		Element xmlDemeter = new Element("demeter");
		Document documento = new Document(xmlDemeter);
		
		xmlDemeter.setAttribute("id",sede.getId().toString());
		xmlDemeter.setAttribute("sede",sede.getSede().getId().getId());
		xmlDemeter.setAttribute("codempresa",sede.getSede().getId().getCodigoEmpresa());
		xmlDemeter.setAttribute("mes",modelo.getMes().toString());
		xmlDemeter.setAttribute("ano",modelo.getAno().toString());
		
		//Datos de las Formas de Pago 
		Element formaPagos = perFormaPago.getFormaPagosXml(modelo.getMes(),modelo.getAno());
		
		Element deposistos = perDeposito.getDepositosXml(modelo.getMes(),modelo.getAno());
		Element facturas = perFactura.getFacturasXml(modelo.getMes(),modelo.getAno());
		Element notasDebito = perNotaDebito.getDebitoXml(modelo.getMes(),modelo.getAno());
		Element notasCredito = perNotaCredito.getCreditoXml(modelo.getMes(),modelo.getAno());

		//Datos de las Facturas 
		xmlDemeter.addContent(formaPagos);
		xmlDemeter.addContent(deposistos);
		xmlDemeter.addContent(facturas);
		xmlDemeter.addContent(notasDebito);
		xmlDemeter.addContent(notasCredito);
		
		
		//Datos de las nota de Debito 
		//Datos de las nota de Credito  
		//Guardar Archivo 
		perExtracionDatosSigesp.guardar(modelo,documento);
		//nombre 
	}

	public List<ExtracionDatosSigesp> getExtracionesDatosSigesp() throws ExcFiltroExcepcion
	{
		return perExtracionDatosSigesp.getAll();
	}
	
	
	public ExtracionDatosSigesp getExtracionDatosSigesp (Integer ano , Integer mes) throws ExcFiltroExcepcion
	{
		return perExtracionDatosSigesp.buscar(ano,mes);
	}
	
	
	public void eliminar (ExtracionDatosSigesp modelo) throws Exception
	{
		perExtracionDatosSigesp.borrar(modelo);
	}
}

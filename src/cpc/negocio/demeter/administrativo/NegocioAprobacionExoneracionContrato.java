package cpc.negocio.demeter.administrativo;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import cpc.modelo.demeter.administrativo.AprobacionExoneracionContrato;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.DetalleExoneracionContrato;
import cpc.modelo.demeter.administrativo.SolicitudExoneracionContrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerAprobacionExoneracionContrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerCierreDiario;
import cpc.persistencia.demeter.implementacion.administrativo.PerRecibo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioAprobacionExoneracionContrato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6194790844135055804L;
	
	private static NegocioAprobacionExoneracionContrato negocio;
	private static String patronBusqueda = "=>9f0<AwB7J8CpDuÑd5E6FQGlxnHMbcI3ñK4LeUNzO1ms2PtRvSVkWXqirTYaghZjoy.,\"";
	private static String patronEncripta = "\"<>,.wxBU7nIGj9Flm8f0ñAH1bcK3hdi4WJ5ZLCpDeMvTQuVkXqraYE6gosyNzÑOP2RSt=";

	
	public  static synchronized  NegocioAprobacionExoneracionContrato getInstance(){
		if (negocio == null)
			negocio = new NegocioAprobacionExoneracionContrato();
		return negocio;
	}
	
	private NegocioAprobacionExoneracionContrato(){
		}
	public List<AprobacionExoneracionContrato> getTodos() throws ExcFiltroExcepcion{
		return new PerAprobacionExoneracionContrato().getAll();
	}
	public void guardar(AprobacionExoneracionContrato aprobacion) throws Exception{
		new PerAprobacionExoneracionContrato().guardar(aprobacion, aprobacion.getId());
	}
	public AprobacionExoneracionContrato leerxlmSolicitud(String xmlemcriptado,AprobacionExoneracionContrato aprobacion) throws ParseException{
		String xmlSolictud = desencriptaCadena(xmlemcriptado);
		
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
		    Document doc = saxBuilder.build(new StringReader(xmlSolictud));
		    Element solicitud = doc.getRootElement();
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		    aprobacion.setFechaSolicitud(formatter.parse(solicitud.getAttribute("fechaSolicitud").getValue()));
		    aprobacion.setFechaContrato(formatter.parse(solicitud.getAttribute("fechaContrato").getValue()));
		    aprobacion.setNumeroExoneracion(solicitud.getAttribute("numeroExoneracion").getValue());
		    aprobacion.setSede(solicitud.getAttribute("sede").getValue());
		    aprobacion.setNumeroContrato(solicitud.getAttribute("numeroContrato").getValue());
		    aprobacion.setPagador(solicitud.getAttribute("pagador").getValue());
		    aprobacion.setCedRif(solicitud.getAttribute("cedRif").getValue());
		    aprobacion.setMontoBase(Double.parseDouble(solicitud.getAttribute("montoBase").getValue()));
		    aprobacion.setMotivo(solicitud.getAttribute("motivo").getValue());
		    
		    List<DetalleExoneracionContrato> detalles = new ArrayList<DetalleExoneracionContrato>();
		    List list = solicitud.getChildren("detalleContrato");
		    for (int i = 0; i < list.size(); i++) {
		    	   Element node = (Element) list.get(i);
		    	   DetalleExoneracionContrato detalle = new DetalleExoneracionContrato();
		    	   
		    	   detalle.setProducto(node.getAttribute("producto").getValue());
		    	   detalle.setCantidad(Double.parseDouble(node.getAttribute("cantidad").getValue()));
		    			String strcantidadReal = node.getAttribute("cantidadReal").getValue();
					if(!strcantidadReal.equals("null")){
		    	   detalle.setCantidadReal(Double.parseDouble(strcantidadReal));
		    	   }else detalle.setCantidadReal(new Double(0));
					
		    	   detalle.setPrecioUnitario(Double.parseDouble(node.getAttribute("precioUnitario").getValue()));
		    	   detalle.setSubtotal(Double.parseDouble(node.getAttribute("subTotal").getValue()));
		    	   detalle.setTipoProducto(node.getAttribute("tipoProducto").getValue());
		    	   detalle.setAprobacion(aprobacion);
		    	detalles.add(detalle);
		    }
		    aprobacion.setDetalleExoneracionContrato(detalles);
		} catch (JDOMException e) {
		    // handle JDOMException
			e.printStackTrace();
		} catch (IOException e) {
		    // handle IOException
			e.printStackTrace();
		}
			
		return aprobacion;
		}
	
public String GenerarxlmAprobacion(AprobacionExoneracionContrato aprobacion){
		DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
	
		Element xmlAprobacion = new Element("aprobacion");
		xmlAprobacion.setAttribute("fechaAprobacion",df.format(aprobacion.getFechaAprobacion()));	
		xmlAprobacion.setAttribute("numeroExoneracion",String.valueOf(aprobacion.getNumeroExoneracion()));	
		xmlAprobacion.setAttribute("estadoAprobacion",String.valueOf(aprobacion.isAprobado()));
	   			String	cadena=new XMLOutputter().outputString(xmlAprobacion);
		return encriptarCadena(cadena);
	}
public static String encriptarCaracter(String caracter, int variable,
		int indice) {
	int ind;
	if (patronBusqueda.indexOf(caracter) != -1) {
		ind = (patronBusqueda.indexOf(caracter) + variable + indice)
				% patronBusqueda.length();
		return patronEncripta.substring(ind, ind + 1);
	}
	return caracter;
}

public static String encriptarCadena(String cadena) {
	String resultado = "";
	for (int pos = 0; pos < cadena.length(); pos++) {
		if (pos == 0) {
			resultado = encriptarCaracter(cadena.substring(pos, pos + 1),
					cadena.length(), pos);
		} else {
			resultado += encriptarCaracter(cadena.substring(pos, pos + 1),
					cadena.length(), pos);
		}
	}
	return resultado;
}

public static String desencriptaCadena(String cadena) {
	String original = "";
	for (int pos = 0; pos < cadena.length(); pos++) {
		if (pos == 0) {
			original = desencriptaCaracter(cadena.substring(pos, pos + 1),
					cadena.length(), pos);
		} else {
			original += desencriptaCaracter(cadena.substring(pos, pos + 1),
					cadena.length(), pos);
		}
	}
	return original;
}

public static String desencriptaCaracter(String caracter, int variable,
		int indice) {
	int ind = 0;
	if (patronEncripta.indexOf(caracter) != -1) {
		if ((patronEncripta.indexOf(caracter) - variable - indice) > 0) {
			ind = (patronEncripta.indexOf(caracter) - variable - indice)
					% patronEncripta.length();
		} else {
			ind = (patronBusqueda.length())
					+ ((patronEncripta.indexOf(caracter) - variable - indice) % patronEncripta
							.length());
		}
		ind = ind % patronEncripta.length();
		return patronBusqueda.substring(ind, ind + 1);
	} else {
		return caracter;
	}
}
public List<AprobacionExoneracionContrato> gettodos() throws ExcFiltroExcepcion{
	return new PerAprobacionExoneracionContrato().getAll();
}

public List<DetalleExoneracionContrato> getDetalles(Date inicio, Date fin) throws ExcFiltroExcepcion{
	return new PerAprobacionExoneracionContrato().getDetallesAprobacion(inicio,fin);
	
	
	
}

}

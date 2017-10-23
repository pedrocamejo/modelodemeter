package cpc.negocio.demeter.administrativo;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.SolicitudExoneracionContrato;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerContrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoExoneracionContrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerSolicitudExoneracionContrato;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSolicitudExoneracion implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3405391064933941778L;
	private static NegocioSolicitudExoneracion negocio;
	private static String patronBusqueda = "=>9f0<AwB7J8CpDuÑd5E6FQGlxnHMbcI3ñK4LeUNzO1ms2PtRvSVkWXqirTYaghZjoy.,\"";
	private static String patronEncripta = "\"<>,.wxBU7nIGj9Flm8f0ñAH1bcK3hdi4WJ5ZLCpDeMvTQuVkXqraYE6gosyNzÑOP2RSt=";

	public  static synchronized NegocioSolicitudExoneracion getInstance() {
		if (negocio == null)
			negocio = new NegocioSolicitudExoneracion();
		return negocio;
	}

	private NegocioSolicitudExoneracion(){
		
	}
	public List<SolicitudExoneracionContrato> getTodos() throws ExcFiltroExcepcion{
		return new PerSolicitudExoneracionContrato().getAll();
	};
	
	public void guardar(SolicitudExoneracionContrato solictud) throws Exception{
		
		new PerSolicitudExoneracionContrato().guardar(solictud, solictud.getId(),getControlSede());
	}
	public void Procesar(SolicitudExoneracionContrato solictud) throws Exception{
		new PerSolicitudExoneracionContrato().guardar(solictud, solictud.getId());
	}
	public String GenerarxlmSolicitud(SolicitudExoneracionContrato solictud){
		
		DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
		
		
		Element xmlSolictud = new Element("solicitud");
		xmlSolictud.setAttribute("fechaSolicitud",df.format(solictud.getFechaSolicitud()));
	//	xmlSolictud.setAttribute("fechaAprobacion",String.valueOf(solictud.getFechaAprobacion().toString()));	
		xmlSolictud.setAttribute("fechaContrato",df.format(solictud.getContrato().getFecha()));
		xmlSolictud.setAttribute("numeroContrato",solictud.getContrato().getStrNroDocumento());
		xmlSolictud.setAttribute("numeroExoneracion",solictud.getNroControl());
		xmlSolictud.setAttribute("cedRif",solictud.getContrato().getPagador().getIdentidadLegal());
		xmlSolictud.setAttribute("pagador",solictud.getContrato().getPagador().getNombres());
		xmlSolictud.setAttribute("montoBase",solictud.getContrato().getMonto().toString());
	//	xmlSolictud.setAttribute("sede",solictud.getContrato().get);
	//	xmlSolictud.setAttribute("fechaSolicitud",solictud.getContrato().);
		xmlSolictud.setAttribute("motivo",String.valueOf(solictud.getMotivo()));
		xmlSolictud.setAttribute("sede",solictud.getSede().getNombre());
	   
		
		
		for (DetalleContrato detalle : solictud.getContrato().getDetallesContrato()) {
			Element xmlDetalle = new Element("detalleContrato");
			xmlSolictud.addContent(xmlDetalle);
			xmlDetalle.setAttribute("producto",detalle.getProducto().getDescripcion());
			xmlDetalle.setAttribute("cantidad",String.valueOf(detalle.getCantidad()));
		/*	Double strcantidadReal = detalle.getCantidadReal();
			if(!strcantidadReal.equals("null"))*/
			xmlDetalle.setAttribute("cantidadReal",String.valueOf(detalle.getCantidadReal()));
			xmlDetalle.setAttribute("precioUnitario",String.valueOf(detalle.getPrecioUnidad()));
			xmlDetalle.setAttribute("subTotal",String.valueOf(detalle.getSubtotal()));
			xmlDetalle.setAttribute("tipoProducto",String.valueOf(detalle.getProducto().getStrTipo()));
			
			
		}
				String	cadena=new XMLOutputter().outputString(xmlSolictud);
		return encriptarCadena(cadena);
				
			
		
		
	}
	
	public SolicitudExoneracionContrato leerxlmAprobacion(String xmlemcriptado,SolicitudExoneracionContrato solicitudExoneracionContrato) 
				throws Exception, ParseException {
		
		String xmlAprobacion = desencriptaCadena(xmlemcriptado);
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
		    Document doc = saxBuilder.build(new StringReader(xmlAprobacion));
		    Element solicitud = doc.getRootElement();
		    String fechaAproacion = solicitud.getAttribute("fechaAprobacion").getValue();
		    boolean aprobacion =Boolean.parseBoolean(solicitud.getAttributeValue("estadoAprobacion"));
		    String numeroExoneracion = solicitud.getAttribute("numeroExoneracion").getValue();
		    if(!numeroExoneracion.equals(solicitudExoneracionContrato.getNroControl())){
				   throw new Exception("Esta Aprobacion no es para esta Solicitud");
		    }
		   
		   if (aprobacion){
			   solicitudExoneracionContrato.getContrato().setEstadoExoneracion(new PerEstadoExoneracionContrato().getExonerado());
		   }
		   else{
			   solicitudExoneracionContrato.getContrato().setEstadoExoneracion(new PerEstadoExoneracionContrato().getExoneracionRechazada());
		   }

		   SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		   solicitudExoneracionContrato.setFechaAprobacion(formatter.parse(fechaAproacion));
		}
		catch (JDOMException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
public Object leerxlmSolicitud(String xmlemcriptado){
	String xmlSolictud = desencriptaCadena(xmlemcriptado);
	
	SAXBuilder saxBuilder = new SAXBuilder();
	try {
	    Document doc = saxBuilder.build(new StringReader(xmlSolictud));
	    Element solicitud = doc.getRootElement();
	    
	    String fechaSolicitud = solicitud.getAttribute("fechaSolicitud").getValue();
	   
	    String FechaContrato=   solicitud.getAttribute("fechaContrato").getValue();
	    String numeroContrato=   solicitud.getAttribute("numeroContrato").getValue();
	    String cedRif=  solicitud.getAttribute("cedRif").getValue();
	    String pagador= solicitud.getAttribute("pagador").getValue();
	    String  monto= solicitud.getAttribute("monto").getValue();
	    String  motivo= solicitud.getAttribute("motivo").getValue();
 	    List<Object> detalles = new ArrayList<Object>();
	    List list = solicitud.getChildren("detalleContrato");
	    for (int i = 0; i < list.size(); i++) {
	    	   Element node = (Element) list.get(i);
	    	   Object[] detalle = new Object[5];
	    	   
	    	   detalle[0]=node.getChildText("producto");
	    	   detalle[1]=node.getChildText("cantidad");
	    	   detalle[2]=node.getChildText("cantidadReal");
	    	   detalle[3]=node.getChildText("precioUnitario");
	    	   detalle[4]=node.getChildText("Subtotal");
	    	   
	    	
	    }
	    System.out.println("");
	} catch (JDOMException e) {
	    // handle JDOMException
		e.printStackTrace();
	} catch (IOException e) {
	    // handle IOException
		e.printStackTrace();
	}
		
	return null;
	}
public List<Contrato> getContratosPorExonerar(){
	return new PerContrato().getAllPorExonerar();
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

private ControlSede getControlSede(){
	 Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
	return new PerControlSede().buscarId(IdControl);
}
}

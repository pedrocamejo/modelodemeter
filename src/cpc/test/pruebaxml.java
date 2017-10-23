package cpc.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import cpc.modelo.demeter.administrativo.SolicitudExoneracionContrato;
import cpc.negocio.demeter.administrativo.NegocioFactura;
import cpc.negocio.demeter.administrativo.NegocioLibroVenta;
import cpc.negocio.demeter.administrativo.NegocioSolicitudExoneracion;
import cpc.persistencia.demeter.implementacion.administrativo.PerSolicitudExoneracionContrato;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaxml {
	public static void main(String[] args) throws ExcFiltroExcepcion,
			ExcEntradaInconsistente, IOException {
	//	Element libro = NegocioLibroVenta.getInstance().getLibroXml(new Integer(2), new Integer(2015));
	//	System.out.println(libro);

	//	String xml = new XMLOutputter().outputString(libro);
	//	String xml2 = new String(encriptarCadena(xml));
	// xml3 = new String(desencriptaCadena(xml2));
	
		
		
		
		
		List<SolicitudExoneracionContrato> solicitudes = new PerSolicitudExoneracionContrato().getAll();
		SolicitudExoneracionContrato solictud = solicitudes.get(0);
		String ruta = creararchivozipxml(solictud);
		byte[] bytess = archivoxmlzipTobytes(ruta);
		String xml4 = NegocioSolicitudExoneracion.getInstance().GenerarxlmSolicitud(solictud);
		
		NegocioSolicitudExoneracion.getInstance().leerxlmSolicitud(xml4);

	}

	private static String patronBusqueda = "=>9f0<AwB7J8CpDuÑd5E6FQGlxnHMbcI3ñK4LeUNzO1ms2PtRvSVkWXqirTYaghZjoy.,\"";
	private static String patronEncripta = "\"<>,.wxBU7nIGj9Flm8f0ñAH1bcK3hdi4WJ5ZLCpDeMvTQuVkXqraYE6gosyNzÑOP2RSt=";

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
	
public static String creararchivozipxml(SolicitudExoneracionContrato solictud) throws IOException{
String xml=	NegocioSolicitudExoneracion.getInstance().GenerarxlmSolicitud(solictud);

System.out.println(xml);
String ruta = "/home/erivas/";
File archivoXml = new File(ruta+solictud.getNroControl());
//FileUtils.writeStringToFile(archivoXml, xml);

byte[] buffer = new byte[1024];

try{

	FileOutputStream fos = new FileOutputStream(ruta+solictud.getNroControl()+".zip");
	ZipOutputStream zos = new ZipOutputStream(fos);
	ZipEntry ze= new ZipEntry("xml.xml");
	zos.putNextEntry(ze);
	FileInputStream in = new FileInputStream(archivoXml);

	int len;
	while ((len = in.read(buffer)) > 0) {
		zos.write(buffer, 0, len);
	}

	in.close();
	zos.closeEntry();

	//remember close it
	zos.close();

	System.out.println("Done");
	return ruta+solictud.getNroControl()+".zip";

}catch(IOException ex){
   ex.printStackTrace();
}
return ruta;

}
public static byte[] archivoxmlzipTobytes(String ruta) throws IOException{
	File archivozip =new File(ruta);
	   FileInputStream fileInputStream = new FileInputStream(archivozip);
	    byte[] bFile = new byte[(int) archivozip.length()];
		fileInputStream.read(bFile);
	    fileInputStream.close();
	    return bFile;
}


}

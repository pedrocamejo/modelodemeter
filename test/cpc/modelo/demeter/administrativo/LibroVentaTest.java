package cpc.modelo.demeter.administrativo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import cpc.negocio.demeter.administrativo.NegocioLibroVenta;

public class LibroVentaTest {

	public static void main(String...arg) throws Exception{
		ByteArrayOutputStream out = NegocioLibroVenta.getInstance().XmlSigespLibroByte(94);
		FileOutputStream file = new FileOutputStream(File.createTempFile("prueba",".zip"));
		out.writeTo(file);
		file.close();
		out.close();
	}
	
}

package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.sun.org.apache.regexp.internal.recompile;
 

public class ZipFile {


	//descomprimir se pasa la ruta donde se descomprime y el archivo con su paht completo 
	public static void DescomprimirArchivo(String destino,String archivo) throws Exception
	{
		byte[] buffer = new byte[1024];
		GenerarCarpeta(destino);
		File farchivo = new File(archivo);
	
		if (farchivo.exists() && farchivo.isFile())
		{
			
				FileInputStream fileInputStream = new FileInputStream(farchivo);
				ZipInputStream  zipfile = new ZipInputStream(fileInputStream);
				ZipEntry zipentry = zipfile.getNextEntry();
			
				while(zipentry != null)
				{
					String nombre = armarURL(destino,zipentry.getName(),null);
					File newFile = new File(nombre);
					newFile.createNewFile();
					FileOutputStream fileout = new FileOutputStream(newFile);
					int len;
					while( (len = zipfile.read(buffer)) >0)
					{
						fileout.write(buffer,0,len);
					}
					fileout.close();
					zipentry = zipfile.getNextEntry();
				}
				zipfile.closeEntry();
				zipfile.close();
		}
		else
		{
			throw new Exception("EL Archivo No Existe ");
		}
	}
	
	public static ByteArrayOutputStream   GenerarZip(List<File> archivos) throws Exception
	{
		//pregunto si existe !!! si no lo creo 
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream fous = new ByteArrayOutputStream( );
		ZipOutputStream zos = new ZipOutputStream(fous);
 
		for(File f : archivos)
		{
			// preparo el nombre con el cual se va a guardar en el File 
			ZipEntry ze = new ZipEntry(f.getName());
			zos.putNextEntry(ze);
			//cargo el archivo 
			FileInputStream in = new FileInputStream(f);
			int len;
			while( (len = in.read(buffer)) > 0)
			{
				zos.write(buffer,0,len);
			}
			in.close();
		}
		zos.closeEntry();
		zos.close();
		return fous;
	}
	
	// Origen de donde voy a sacar los archivos 
	// Destino a donde va a ir los archivos 
	// nombre del Archivo 
	// seran todo lo que este en la carpeta origen 
	public static  void   GenerarZipSincronizacion(String destino,String origen,String nombre) throws Exception
	{
		//pregunto si existe !!! si no lo creo 
		
		File fdestino = GenerarCarpeta(destino);
		File forigen =  new File(origen);
		
		byte[] buffer = new byte[1024];
		
		
		if(forigen.isDirectory())
		{
			//Busco los Archivo que el Tenga 
			//Genero el .zip File 
			List<File> archivos =  listaArchivos(origen);
			
			nombre = armarURL(destino,nombre,".ZIP");
			FileOutputStream fous = new FileOutputStream(nombre);
			ZipOutputStream zos = new ZipOutputStream(fous);
			
			for(File f : archivos)
			{
				// preparo el nombre con el cual se va a guardar en el File 
				ZipEntry ze = new ZipEntry(f.getName());
				zos.putNextEntry(ze);
					//cargo el archivo 
					FileInputStream in = new FileInputStream(f);
					int len;
					while( (len = in.read(buffer)) > 0)
					{
						zos.write(buffer,0,len);
					}
					in.close();
			}
			
			zos.closeEntry();
			zos.close();
		}
		else
		{
			throw new Exception("EL Origen no es un Diretorio ");
		}
		
	}
	
	
	private static String armarURL(String destino, String nombre,String extens) {
		String ruta = new String();
		extens = (extens == null ? " ":".ZIP");
		
		//analizoo el nombre
		if(!nombre.toUpperCase().contains(extens))
		{
			nombre  = nombre.trim().concat(extens);
		}
		//lo armo con la url 
		if(destino.charAt(destino.length()-1) !='/')
		{
			ruta = destino.concat("/".concat(nombre)).trim();
		}		else
		{
			ruta = destino.concat(nombre).trim();
		}
		
		return ruta;
	}


	//si no esta la genero !! 
	public static File  GenerarCarpeta(String url)
	{
		File file = new File(url);
		if(!file.isDirectory())
		{	
			file.mkdirs();
		}
		return (file.isDirectory() ? file: null);
	}
	
	public static List<File> listaArchivos(String carpeta) throws Exception
	{
		
		List<File> lista = new ArrayList<File>();
		File file = new File(carpeta);
		if(file.isDirectory())
		{
			for (File archivo : file.listFiles())
			{
				if(archivo.isFile())
				 {
					 lista.add(archivo);
				 }
			 }
		}
		else
		{
			throw new Exception("La Carpeta no es Un directorio Valido " + carpeta);
		}
		return lista;
		
	}
	
	
}

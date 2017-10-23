package cpc.persistencia.demeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.sincronizacion.DetalleExport;
import cpc.modelo.demeter.sincronizacion.Exportar;



public class ExportarObjeto {
	public static <T> void main(String[] args) throws SQLException, Exception {

		String ruta = new String("/home/erivas/sincronizacion/");
 	Calendar calendar = Calendar.getInstance();
 	int	mes = calendar.get(Calendar.MONTH) ;
 	int	año = calendar.get(Calendar.YEAR) ;
 	System.out.println(ruta+mes+"_"+año);
 	 ruta=(ruta+año+"/"+mes+"/");
 	 File carpeta= new File(ruta);
 	//	ruta = ruta.concat(sede.getIdSede());
 		File dir = new File(ruta.trim());
 		
 		if (!dir.exists()) { System.out.println(" escribimos algo si no existe"); 
 		if (!dir.isDirectory())
 		{
 		boolean d = dir.mkdirs();
 		System.out.println(d);
 		}
 		}
 		
    	/*if (dir.isDirectory()) { System.out.println("escribimos algo si es un directorio"); 
    	if (!dir.isDirectory())
 		{
 		boolean d = dir.mkdirs();
 		System.out.println(d);
 		}}
		if (dir.isFile()) { System.out.println(" escribimos algo si es un fichero"); }
*/

 		
 		
	 	System.out.println(ruta+"_"+mes+"_"+año); 
		
		Configuration cfg = new Configuration().configure();
	SessionFactory sf = cfg.buildSessionFactory();
	org.hibernate.classic.Session session2 = sf.openSession(  );
	Transaction tx = session2.beginTransaction();
	//List<Sincronizacion> porsincronizar =session2.createCriteria(Sincronizacion.class).list();
     List<DetalleExport> detalleExports =session2.createCriteria(DetalleExport.class).add(Restrictions.eq("completado",false)).list();
	Exportar exportar = new Exportar();
	exportar.setCedula("tuma");
	exportar.setMd5("md5");
	exportar.setDetalles(detalleExports);
	for (DetalleExport detalleExport : detalleExports) {
		detalleExport.setExportar(exportar);
	};
	
	try {
		File salida = new File("/home/erivas/OBJETO");
		ObjectOutputStream o;
		o = new ObjectOutputStream(new FileOutputStream(salida));
		o.writeObject(exportar);
		o.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	session2.save(exportar);
	
	   tx.commit();
	 
	}
 
}

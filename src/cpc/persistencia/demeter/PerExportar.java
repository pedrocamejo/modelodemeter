package cpc.persistencia.demeter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.sincronizacion.DetalleExport;
import cpc.modelo.demeter.sincronizacion.Exportar;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerExportar extends DaoGenerico<Exportar,Integer>
{

	public PerExportar( ) {
		super(Exportar.class);
		// TODO Auto-generated constructor stub
	}
	






	
	public String getid_toString(Exportar obj) {
		// TODO Auto-generated method stub
		return obj.getId().toString();
	}

 
	public File CrearArchivo(Exportar expor,String ruta) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		File salida = new File(ruta);
		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(salida));
		o.writeObject(expor);
		o.close();
		return salida;
	}
 
	public Exportar cargarArchivoExport(String string) throws FileNotFoundException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		File entrada = new File("Exportar.obj");
		ObjectInputStream ios = new ObjectInputStream(new FileInputStream(entrada));
		Object obj = ios.readObject();
		return (Exportar) obj;
	}
 
	
	public Integer getid(Exportar objeto) {
		// TODO Auto-generated method stub
		return null;
	} 
	
	public void sincronizarObjecto(Exportar obj_sede, Exportar obj_export,
			Exportar exportar, cpc.modelo.demeter.sincronizacion.Importar importar) {
		// TODO Auto-generated method stub
		
	} 
	
	public Exportar CrearExportar(String cedula,String md5) throws ExcFiltroExcepcion {
		
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			   List<DetalleExport> detalleExports =em.createCriteria(DetalleExport.class).add(Restrictions.eq("completado",false)).addOrder(Order.asc("id") ).list();
				Exportar exportar = new Exportar();
				exportar.setCedula(cedula);
				exportar.setMd5(md5);
				exportar.setDetalles(detalleExports);
				for (DetalleExport detalleExport : detalleExports) {
					detalleExport.setExportar(exportar);
				};
				em.save(exportar);
				return exportar;
	}
		catch (Exception e) 
		{
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}

}
	}

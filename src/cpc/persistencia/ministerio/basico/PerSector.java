package cpc.persistencia.ministerio.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;




public class PerSector extends DaoGenerico<UbicacionSector, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerSector() {
		super(UbicacionSector.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<UbicacionSector> getTodos(UbicacionParroquia parroquia) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UbicacionSector> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UbicacionSector>) em.createCriteria(UbicacionSector.class)
				.add(Restrictions.eq("parroquia",parroquia))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  

	public List<UbicacionSector> getTodosPorNombreYParroquia(String nombre, UbicacionParroquia parroquia) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UbicacionSector> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UbicacionSector>) em.createCriteria(UbicacionSector.class)
				.add(Restrictions.eq("nombre",nombre.trim()).ignoreCase())
				.add(Restrictions.eq("parroquia",parroquia))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  
	
}

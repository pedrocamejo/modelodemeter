package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.sigesp.basico.ClaseAlmacen;
import cpc.modelo.sigesp.basico.UnidadCentralizada;
import cpc.modelo.sigesp.basico.UnidadCentralizadaPK;

import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerClaseAlmacen extends DaoGenerico<ClaseAlmacen,Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3681879609266543690L;

	public PerClaseAlmacen() {
		super(ClaseAlmacen.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ClaseAlmacen> getTodos() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<ClaseAlmacen> clasesAlmacen = null;
		
		tx = em.beginTransaction();
		try{
			clasesAlmacen = em.createCriteria(ClaseAlmacen.class)
				.addOrder(Order.asc("id"))
				.list();
			tx.commit();
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return clasesAlmacen;
	}

	public ClaseAlmacen getMecanizado() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		ClaseAlmacen clasesAlmacen = null;
		
		tx = em.beginTransaction();
		try{
			clasesAlmacen = (ClaseAlmacen) em.createCriteria(ClaseAlmacen.class)
				.add(Restrictions.eq("id",1)) 
				.uniqueResult();
			tx.commit();
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return clasesAlmacen;
	}
	public ClaseAlmacen getServicioTecnico() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		ClaseAlmacen clasesAlmacen = null;
		
		tx = em.beginTransaction();
		try{
			clasesAlmacen = (ClaseAlmacen) em.createCriteria(ClaseAlmacen.class)
				.add(Restrictions.eq("id",2)) 
				.uniqueResult();
			tx.commit();
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return clasesAlmacen;
	}
	
	
	
}
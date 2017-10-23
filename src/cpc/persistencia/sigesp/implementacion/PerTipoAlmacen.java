package cpc.persistencia.sigesp.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.sigesp.basico.ClaseAlmacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerTipoAlmacen extends DaoGenerico<TipoAlmacen, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845245036426782947L;

	public PerTipoAlmacen() {
		super(TipoAlmacen.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoAlmacen> getAll(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<TipoAlmacen> tiposAlmacen = null;
		tx = em.beginTransaction();
		try{
			tiposAlmacen = em.createQuery("SELECT ta FROM TipoAlmacen ta ORDER BY ta.id ASC  ")
				.list();
			tx.commit();
			
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return tiposAlmacen;
	}
	
	public TipoAlmacen getTipoOperativo(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoAlmacen criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoAlmacen) em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("operativo", true))
			.add(Restrictions.eq("taller", false))
			.add(Restrictions.eq("mobiliario", false))
			.add(Restrictions.eq("transitorio", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoAlmacen getTipoDeposito(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoAlmacen criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoAlmacen) em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("operativo", false))
			.add(Restrictions.eq("taller", false))
			.add(Restrictions.eq("mobiliario", false))
			.add(Restrictions.eq("transitorio", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoAlmacen getTipoTaller(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoAlmacen criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoAlmacen) em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("operativo", false))
			.add(Restrictions.eq("taller", true))
			.add(Restrictions.eq("mobiliario", false))
			.add(Restrictions.eq("transitorio", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	public TipoAlmacen getTipoMobiliario(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoAlmacen criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoAlmacen) em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("operativo", false))
			.add(Restrictions.eq("taller", false))
			.add(Restrictions.eq("mobiliario", true))
			.add(Restrictions.eq("transitorio", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoAlmacen getTipoTransitorio(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoAlmacen criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoAlmacen) em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("operativo", false))
			.add(Restrictions.eq("taller", false))
			.add(Restrictions.eq("mobiliario", false))
			.add(Restrictions.eq("transitorio", true))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TipoAlmacen> getTipoMecanizado(){
		Transaction tx = null;
		   ClaseAlmacen clasemecanizado = new PerClaseAlmacen().getMecanizado();
		Session em = SessionDao.getInstance().getCurrentSession();
	 
		List<TipoAlmacen> criterio = null;
		tx = em.beginTransaction();
		try{
			criterio =  em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("claseAlmacen", clasemecanizado))
						.list();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public TipoAlmacen getTipoSalidaInterna(){
		Transaction tx = null;
	Session em = SessionDao.getInstance().getCurrentSession();
	 
		TipoAlmacen criterio = null;
		tx = em.beginTransaction();
		try{
			criterio =  (TipoAlmacen) em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("id", 10))
						.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public TipoAlmacen getTipoSalidaExterna(){
		Transaction tx = null;
	Session em = SessionDao.getInstance().getCurrentSession();
	 
		TipoAlmacen criterio = null;
		tx = em.beginTransaction();
		try{
			criterio =  (TipoAlmacen) em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("id", 9))
						.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TipoAlmacen> getTipoServicoTecnico(){
		ClaseAlmacen claseAlmacen = new PerClaseAlmacen().getServicioTecnico();
		
		Transaction tx = null;
	Session em = SessionDao.getInstance().getCurrentSession();

		List<TipoAlmacen> criterio = null;
		tx = em.beginTransaction();
		try{
			criterio =   em.createCriteria(TipoAlmacen.class)
			.add(Restrictions.eq("claseAlmacen", claseAlmacen))
						.list();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
}

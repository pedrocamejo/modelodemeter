package cpc.persistencia.demeter.implementacion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.TipoServicio;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;



public class PerTipoServicio extends DaoGenerico<TipoServicio, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1831417605195237111L;

	public PerTipoServicio() {
		super(TipoServicio.class);
	}
	
	public TipoServicio getTipoMecanizado(){
		Transaction tx = null;
		TipoServicio salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = (TipoServicio) em.createCriteria(TipoServicio.class)
				.add(Restrictions.eq("mecanizado",Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	public TipoServicio getTipoTransporteCosecha(){
		Transaction tx = null;
		TipoServicio salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = (TipoServicio) em.createCriteria(TipoServicio.class)
				.add(Restrictions.eq("transporteCosecha",Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	public TipoServicio getTipoTransporte(){
		Transaction tx = null;
		TipoServicio salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = (TipoServicio) em.createCriteria(TipoServicio.class)
				.add(Restrictions.eq("transporte",Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	public TipoServicio getTipoLineaAmarilla(){
		Transaction tx = null;
		TipoServicio salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = (TipoServicio) em.createCriteria(TipoServicio.class)
				.add(Restrictions.eq("lineaAmarilla",Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	public TipoServicio getTiponoMecanizado(){
		Transaction tx = null;
		TipoServicio salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = (TipoServicio) em.createCriteria(TipoServicio.class)
				.add(Restrictions.eq("mecanizado",Boolean.FALSE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	public TipoServicio getTipoServicioTecnico(){
		Transaction tx = null;
		TipoServicio salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = (TipoServicio) em.createCriteria(TipoServicio.class)
				.add(Restrictions.eq("id",42))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
}

package cpc.persistencia.demeter.implementacion.gestion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerEstadoOrdenTrabajo extends DaoGenerico<EstadoOrdenTrabajo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4643962432907532441L;

	public PerEstadoOrdenTrabajo() {
		super(EstadoOrdenTrabajo.class);
	}
	

	
	public EstadoOrdenTrabajo getInicio(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoOrdenTrabajo criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoOrdenTrabajo) em.createCriteria(EstadoOrdenTrabajo.class)
				.add(Restrictions.eq("activa", Boolean.TRUE))
				.add(Restrictions.eq("detenida", Boolean.FALSE))
				.add(Restrictions.eq("finalizada", Boolean.FALSE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoOrdenTrabajo getEnEspera(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoOrdenTrabajo criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoOrdenTrabajo) em.createCriteria(EstadoOrdenTrabajo.class)
				.add(Restrictions.eq("id", 2))
			
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoOrdenTrabajo getTerminado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoOrdenTrabajo criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoOrdenTrabajo) em.createCriteria(EstadoOrdenTrabajo.class)
				.add(Restrictions.eq("activa", Boolean.TRUE))
				.add(Restrictions.eq("finalizada", Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoOrdenTrabajo getCancelado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoOrdenTrabajo criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoOrdenTrabajo) em.createCriteria(EstadoOrdenTrabajo.class)
				.add(Restrictions.eq("activa", Boolean.FALSE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoOrdenTrabajo getCargaDatos(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoOrdenTrabajo criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoOrdenTrabajo) em.createCriteria(EstadoOrdenTrabajo.class)
				.add(Restrictions.like("descripcion", "%atos%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoOrdenTrabajo getCargaCompleta(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoOrdenTrabajo criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoOrdenTrabajo) em.createCriteria(EstadoOrdenTrabajo.class)
					.add(Restrictions.like("descripcion", "%ompleta%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	
	
	
	
}
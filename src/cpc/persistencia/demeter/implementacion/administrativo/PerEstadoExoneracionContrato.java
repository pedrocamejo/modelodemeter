package cpc.persistencia.demeter.implementacion.administrativo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.EstadoExoneracionContrato;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerEstadoExoneracionContrato  extends DaoGenerico<EstadoExoneracionContrato,Integer>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6617457808104012801L;
	
	public PerEstadoExoneracionContrato() {
		super(EstadoExoneracionContrato.class);
		// TODO Auto-generated constructor stub
	}

	public EstadoExoneracionContrato getNoExonerado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoExoneracionContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoExoneracionContrato) em.createCriteria(EstadoExoneracionContrato.class)
				.add(Restrictions.like("descripcion", "%NO EXONERADO%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	public EstadoExoneracionContrato getExoneracionRechazada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoExoneracionContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoExoneracionContrato) em.createCriteria(EstadoExoneracionContrato.class)
				.add(Restrictions.like("descripcion", "%EXONERACION RECHAZADA%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;}
	public EstadoExoneracionContrato getExonerado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoExoneracionContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoExoneracionContrato) em.createCriteria(EstadoExoneracionContrato.class)
				.add(Restrictions.like("descripcion", "EXONERADO"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	public EstadoExoneracionContrato getPorExonerar(){Transaction tx = null;
	Session em =SessionDao.getInstance().getCurrentSession();
	EstadoExoneracionContrato criterio= null;
 	tx = em.beginTransaction();
	try{
		
		criterio = (EstadoExoneracionContrato) em.createCriteria(EstadoExoneracionContrato.class)
			.add(Restrictions.like("descripcion", "%POR EXONERAR%"))
			.uniqueResult();
		tx.commit();
	}catch(Exception e){
		e.printStackTrace();
		tx.rollback();
	}
	return criterio;}

}

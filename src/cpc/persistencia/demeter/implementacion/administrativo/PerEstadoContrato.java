package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerEstadoContrato extends DaoGenerico<EstadoContrato, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5562869963999412772L;

	public PerEstadoContrato() {
		super(EstadoContrato.class);

	}
	
	public EstadoContrato getActivo(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%Activo%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoContrato getRechazado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%Rechazado%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<EstadoContrato> getPorYEnEjecucion(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<EstadoContrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio =  em.createCriteria(EstadoContrato.class)
				.add(Restrictions.le("id", 3))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	public EstadoContrato getCulminado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%ulminado%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoContrato getPorFirmar(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%irmar%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoContrato getEnEjecucion(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%jecuci%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoContrato getConcluidoAdministracion(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%Administraci%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoContrato getEmitido(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoContrato criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%Emitido%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
}
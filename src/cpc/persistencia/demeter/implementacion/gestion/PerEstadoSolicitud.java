package cpc.persistencia.demeter.implementacion.gestion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.TipoSolicitud;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerEstadoSolicitud extends DaoGenerico<EstadoOrdenTrabajo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4643962432907532441L;

	public PerEstadoSolicitud() {
		super(EstadoSolicitud.class);
	}
	

	
	public EstadoSolicitud getrecibida(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "recibida"))
				.add(Restrictions.eq("tipoSolicitud.id",1))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	public EstadoSolicitud getaprobada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "aprobada"))
				.add(Restrictions.eq("tipoSolicitud.id",1))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoSolicitud getrechazada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "rechazada"))
				.add(Restrictions.eq("tipoSolicitud.id",1))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoSolicitud getfirmada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "firmada"))
				.add(Restrictions.eq("tipoSolicitud.id",1))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		System.out.println(criterio.getEstado());
		return criterio;
	}
	
	public EstadoSolicitud getenproceso(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "en proceso"))
				.add(Restrictions.eq("tipoSolicitud.id",1))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoSolicitud getculminada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "culminada"))
				.add(Restrictions.eq("tipoSolicitud.id",1))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoSolicitud getanulada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "anulada"))
				.add(Restrictions.eq("tipoSolicitud.id",1))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoSolicitud getexpirada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "expirada"))
				.add(Restrictions.eq("tipoSolicitud.id",1))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoSolicitud getSegunEstado(String estado, TipoSolicitud tipo){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", estado))
				.add(Restrictions.eq("tipoSolicitud", tipo))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	

	public EstadoSolicitud getServicoTecnicoRecibida(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "recibida"))
				.add(Restrictions.eq("tipoSolicitud.id",5))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	public EstadoSolicitud getServicioTecnicoAprobada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "aprobada"))
				.add(Restrictions.eq("tipoSolicitud.id",5))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoSolicitud getServicioTecnicoRechazada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "rechazada"))
				.add(Restrictions.eq("tipoSolicitud.id",5))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	public EstadoSolicitud getServicioTecnicoDespachada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "despachada"))
				.add(Restrictions.eq("tipoSolicitud.id",5))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoSolicitud getServicioTecnicoTotalizada(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "totalizada"))
				.add(Restrictions.eq("tipoSolicitud.id",5))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
}
	
package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.administrativo.EstadoExoneracionContrato;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerContrato extends DaoGenerico<Contrato, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4524428514868526037L;

	public PerContrato() {
		super(Contrato.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Contrato> getContratosParaAbono(){
		Transaction tx = null;
		EstadoContrato estado = new PerEstadoContrato().getCulminado();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<Contrato>) em.createCriteria(Contrato.class)
				.add(Restrictions.gt("saldo", 0.0))
				.add(Restrictions.eq("facturado", Boolean.FALSE))
				.add(Restrictions.ne("estado", estado))
				.list();em.evict(estado);
			em.evict(estado);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Contrato> getAllPorFirmar(){
		Transaction tx = null;
		EstadoContrato estado = new PerEstadoContrato().getPorFirmar();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<Contrato>) em.createCriteria(Contrato.class)
				.add(Restrictions.eq("estado", estado))
				.list();
			em.evict(estado);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Contrato> getAllPorFirmarSinFactura(){
		Transaction tx = null;
		EstadoContrato estado = new PerEstadoContrato().getPorFirmar();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<Contrato>) em.createCriteria(Contrato.class)
				.add(Restrictions.eq("estado", estado))
				.add(Restrictions.eq("facturado", Boolean.FALSE))
				.list();
			em.evict(estado);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<Contrato> getAllTerminadosSinFactura(){
		Transaction tx = null;
		List<EstadoContrato> culminados = new ArrayList<EstadoContrato> ();
;
		EstadoContrato culminado = new PerEstadoContrato().getCulminado();
		
		EstadoContrato culmidadoAdministracion = new PerEstadoContrato().getConcluidoAdministracion();
		
		culminados.add(culminado);
		culminados.add(culmidadoAdministracion);
		//EstadoContrato estado = new PerEstadoContrato().getCulminado();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<Contrato>) em.createCriteria(Contrato.class,"contrato")
				.createAlias("estadoExoneracion", "estadoexoneracion")
				.add(Restrictions.in("estado", culminados))
				.add(Restrictions.eq("facturado", Boolean.FALSE))
				.add(Restrictions.eq("estadoexoneracion.gravado", Boolean.TRUE))
				.list();
			
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Contrato> getAllActivos(){
		Transaction tx = null;
		EstadoContrato estado = new PerEstadoContrato().getActivo();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
     	
		try{
			
			criterio = (List<Contrato>) em.createCriteria(Contrato.class)
				.add(Restrictions.eq("estado", estado))
				.list();
			em.evict(estado);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Contrato> getAllEnEjecucion(){
		Transaction tx = null;
		EstadoContrato estado = new PerEstadoContrato().getEnEjecucion();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<Contrato>) em.createCriteria(Contrato.class)
				.add(Restrictions.eq("estado", estado))
				.list();
			em.evict(estado);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Contrato> getAllTerminados(){
		Transaction tx = null;
		EstadoContrato estado = new PerEstadoContrato().getCulminado();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<Contrato>) em.createCriteria(Contrato.class)
				.add(Restrictions.eq("estado", estado))
				.list();
			em.evict(estado);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Contrato> getPorYEnEjecucion(){
		Transaction tx = null;
		List<EstadoContrato> estados = new PerEstadoContrato().getPorYEnEjecucion();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio =  em.createCriteria(Contrato.class)
				.add(Restrictions.in("estado", estados))
				.list();
	em.evict(estados);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> getIdsPorYEnEjecucion(){
		Transaction tx = null;
		List<EstadoContrato> estados = new PerEstadoContrato().getPorYEnEjecucion();
		Session em =SessionDao.getInstance().getCurrentSession();
		//List<Integer> ids= new ArrayList<Integer>();
		List<Integer> criterio= null;
     	tx = em.beginTransaction();
		try{
			

			
			  ProjectionList  projections = Projections.projectionList() ;
				 			 
				 projections.add(Projections.property("id"));
				 
			criterio =  em.createCriteria(Contrato.class)
				.add(Restrictions.in("estado", estados))
				.setProjection(projections)
				.list();
	/*for (Object object : criterio) {
		Object[] objects=(Object[]) object;
		Integer id = (Integer) objects[0]; 
		
		ids.add(id);
	}*/
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	
	public List<Integer> getIdsPorEnEjecucionyCerradoAdmistracion(){
		Transaction tx = null;
		List<EstadoContrato> estados = new PerEstadoContrato().getPorYEnEjecucion();
		estados.add(new PerEstadoContrato().getConcluidoAdministracion());
		Session em =SessionDao.getInstance().getCurrentSession();
		//List<Integer> ids= new ArrayList<Integer>();
		List<Integer> criterio= null;
     	tx = em.beginTransaction();
		try{
			

			
			  ProjectionList  projections = Projections.projectionList() ;
				 			 
				 projections.add(Projections.property("id"));
				 
			criterio =  em.createCriteria(Contrato.class)
				.add(Restrictions.in("estado", estados))
				.setProjection(projections)
				.list();
	/*for (Object object : criterio) {
		Object[] objects=(Object[]) object;
		Integer id = (Integer) objects[0]; 
		
		ids.add(id);
	}*/
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Contrato> getCerradoAdministracion(){
		Transaction tx = null;
		EstadoContrato estados = new PerEstadoContrato().getConcluidoAdministracion();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio =  em.createCriteria(Contrato.class)
				.add(Restrictions.eq("estado", estados))
				.list();
	em.evict(estados);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	@SuppressWarnings("unchecked")
	public List<Contrato> getAllPorExonerar(){
		Transaction tx = null;
		EstadoContrato estado = new PerEstadoContrato().getActivo();
		EstadoExoneracionContrato exonerado = new PerEstadoExoneracionContrato().getExonerado();
		EstadoExoneracionContrato porexonerar = new PerEstadoExoneracionContrato().getPorExonerar();
		EstadoExoneracionContrato rechazada = new PerEstadoExoneracionContrato().getExoneracionRechazada();
		List<EstadoExoneracionContrato> estados=new ArrayList<EstadoExoneracionContrato>();
		estados.add(exonerado);
		estados.add(porexonerar);
		estados.add(rechazada);
	//	EstadoExoneracionContrato enonerado = new PerEstadoExoneracionContrato().getExonerado();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Contrato> criterio= null;
     	tx = em.beginTransaction();
     	
		try{
			
			criterio = (List<Contrato>) em.createCriteria(Contrato.class)
				.add(Restrictions.eq("facturado", false))
				.add(Restrictions.not(Restrictions.in("estadoExoneracion", estados)))
				.list();
			em.evict(estado);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
}

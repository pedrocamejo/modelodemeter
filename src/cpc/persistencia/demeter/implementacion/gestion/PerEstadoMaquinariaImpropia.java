package cpc.persistencia.demeter.implementacion.gestion;


 

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.EstadoMaquinariaImpropia;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao; 

public class PerEstadoMaquinariaImpropia extends DaoGenerico<EstadoMaquinariaImpropia, Integer>{


	public PerEstadoMaquinariaImpropia() {
		super(EstadoMaquinariaImpropia.class);		
	}
	
 
	public EstadoMaquinariaImpropia getIngresado() {
		EstadoMaquinariaImpropia resultado ;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		resultado = (EstadoMaquinariaImpropia) em.createCriteria(EstadoMaquinariaImpropia.class).
					add(Restrictions.eq("id",EstadoMaquinariaImpropia.INGRESADO)).uniqueResult();
		tx.commit();
		return resultado;
	}


	public EstadoMaquinariaImpropia getVerificado() {
		EstadoMaquinariaImpropia resultado ;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		resultado = (EstadoMaquinariaImpropia) em.createCriteria(EstadoMaquinariaImpropia.class).
					add(Restrictions.eq("id",EstadoMaquinariaImpropia.VERIFICADO)).uniqueResult();
		tx.commit();
		return resultado;
	}
	


	public EstadoMaquinariaImpropia getMigrado() {
		EstadoMaquinariaImpropia resultado ;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		resultado = (EstadoMaquinariaImpropia) em.createCriteria(EstadoMaquinariaImpropia.class).
					add(Restrictions.eq("id",EstadoMaquinariaImpropia.MIGRADO)).uniqueResult();
		tx.commit();
		return resultado;
	}

	

	public EstadoMaquinariaImpropia getDesactivado() {
		EstadoMaquinariaImpropia resultado ;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		resultado = (EstadoMaquinariaImpropia) em.createCriteria(EstadoMaquinariaImpropia.class).
					add(Restrictions.eq("id",EstadoMaquinariaImpropia.DESACTIVADO)).uniqueResult();
		tx.commit();
		return resultado;
	}

	
	public EstadoMaquinariaImpropia getDesincorporado() {
		EstadoMaquinariaImpropia resultado ;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		resultado = (EstadoMaquinariaImpropia) em.createCriteria(EstadoMaquinariaImpropia.class).
					add(Restrictions.eq("id",EstadoMaquinariaImpropia.DESINCORPORADO)).uniqueResult();
		tx.commit();
		return resultado;
	}

}

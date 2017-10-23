package cpc.persistencia.demeter.implementacion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.FuncionTrabajador;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerFuncionTrabajador extends DaoGenerico<FuncionTrabajador, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5562869963999412772L;

	public PerFuncionTrabajador() {
		super(FuncionTrabajador.class);

	}
	
	public FuncionTrabajador getTipoTecnico(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		FuncionTrabajador criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (FuncionTrabajador) em.createCriteria(FuncionTrabajador.class)
				.add(Restrictions.like("descripcion", "%TECNICO%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public FuncionTrabajador getTipoOperador(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		FuncionTrabajador criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (FuncionTrabajador) em.createCriteria(FuncionTrabajador.class)
				.add(Restrictions.like("descripcion", "%OPERA%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public FuncionTrabajador getConductor(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		FuncionTrabajador criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (FuncionTrabajador) em.createCriteria(FuncionTrabajador.class)
				.add(Restrictions.like("descripcion", "%CHOFER%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public FuncionTrabajador getTipoComunitaria(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		FuncionTrabajador criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (FuncionTrabajador) em.createCriteria(FuncionTrabajador.class)
				.add(Restrictions.like("descripcion", "%COMUNITARIA%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public FuncionTrabajador getDatos(FuncionTrabajador funcionTrabajador){
		FuncionTrabajador funcion = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		try{
			funcion = (FuncionTrabajador) em.load(FuncionTrabajador.class, funcionTrabajador.getId());
			for (@SuppressWarnings("unused") Trabajador item: funcion.getTrabajadores()){			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return funcion;
	}
}
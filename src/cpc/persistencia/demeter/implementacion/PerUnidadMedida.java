package cpc.persistencia.demeter.implementacion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.basico.TipoUnidadMedida;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;



public class PerUnidadMedida extends DaoGenerico<UnidadMedida, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3857075368140941391L;

	public PerUnidadMedida() {
		super(UnidadMedida.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadMedida> getAll(TipoUnidadMedida tipo){
		Transaction tx = null;
		
		List<UnidadMedida> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(UnidadMedida.class)
				.add(Restrictions.eq("tipo",tipo))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public List<UnidadMedida> getAll(UnidadMedida unidad){
		if (unidad == null) return null;
		List<UnidadMedida> hijos = new ArrayList<UnidadMedida>();
		if (unidad.isCompuesto()){ 
			Session em =SessionDao.getInstance().getCurrentSession();
			Transaction tx = null;
			
			UnidadMedida docu = null;
	     	tx = em.beginTransaction();
	     	try{
	     		docu = (UnidadMedida) em.createCriteria(UnidadMedida.class)
	     		.add(Restrictions.eq("id",unidad.getId()))
				.uniqueResult();
				for (UnidadMedida item: docu.getUnidades()){
					hijos.add(item);
				}
				tx.commit();
	     	}catch (Exception e) {
				e.printStackTrace();
				tx.rollback();
			}	
		}else{
			hijos.add(unidad);
		}
		return hijos;
	}
	

	@SuppressWarnings("unchecked")
	public List<UnidadMedida> getAllSimples(){
		Transaction tx = null;
		List<UnidadMedida> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(UnidadMedida.class)
				.add(Restrictions.eq("compuesto",Boolean.FALSE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<UnidadMedida> getUnidades(){
		Transaction tx = null;
		List<UnidadMedida> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(UnidadMedida.class)
				.add(Restrictions.eq("id",18))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings({"unchecked" })
	public List<UnidadMedida> getAllHijas(TipoUnidadMedida tipo){
		Transaction tx = null;
		List<UnidadMedida> criterio= null;
		List<UnidadMedida> salida= new ArrayList<UnidadMedida>();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(UnidadMedida.class)
				.add(Restrictions.eq("tipo",tipo))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		for (UnidadMedida item : criterio) {
			salida.addAll(getAll(item));
		} 
		return salida;
	}	
			
}

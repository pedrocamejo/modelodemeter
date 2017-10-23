package cpc.persistencia.demeter.implementacion.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.basico.TipoUnidadMedida;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerTipoUnidadMedida extends DaoGenerico<TipoUnidadMedida, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerTipoUnidadMedida() {
		super(TipoUnidadMedida.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoUnidadMedida> getAllSimples(){
		Transaction tx = null;
		List<TipoUnidadMedida> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(TipoUnidadMedida.class)
				.add(Restrictions.eq("compuesto",Boolean.FALSE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}

	@SuppressWarnings("unchecked")
	public List<TipoUnidadMedida> getAllTipo(String tipo){
		Transaction tx = null;
		List<TipoUnidadMedida> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(TipoUnidadMedida.class)
				.add(Restrictions.eq("descripcion",tipo))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
}

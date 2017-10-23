package cpc.persistencia.demeter.implementacion.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.basico.ClaseArticulo;
import cpc.modelo.demeter.basico.TipoArticulo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerClaseArticulo extends DaoGenerico<ClaseArticulo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerClaseArticulo() {
		super(ClaseArticulo.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ClaseArticulo> getAll(TipoArticulo tipo){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<ClaseArticulo> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<ClaseArticulo>) em.createCriteria(ClaseArticulo.class)
				.add(Restrictions.eq("tipoArticulo",tipo))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
}

package cpc.persistencia.sigesp.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cpc.modelo.sigesp.basico.ClaseCCNU;
import cpc.modelo.sigesp.basico.ProductoCCNU;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerClaseCCNU extends DaoGenerico<ClaseCCNU, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2271704254784385569L;

	public PerClaseCCNU() {
		super(ClaseCCNU.class);
	}
		
	@SuppressWarnings("unused")
	public List<ProductoCCNU> getProductos(ClaseCCNU familia){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		ClaseCCNU docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (ClaseCCNU) em.load(ClaseCCNU.class, familia.getId());
			for (ProductoCCNU item: docu.getProductos()){em.evict(item);
			}
			tx.commit();
			return  docu.getProductos();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
     	return null;
	}	
	
}

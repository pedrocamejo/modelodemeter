package cpc.persistencia.sigesp.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cpc.modelo.sigesp.basico.ClaseCCNU;
import cpc.modelo.sigesp.basico.FamiliaCCNU;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerFamiliaCCNU extends DaoGenerico<FamiliaCCNU, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6862044156353511052L;

	public PerFamiliaCCNU() {
		super(FamiliaCCNU.class);
	}
		
	@SuppressWarnings("unused")
	public List<ClaseCCNU> getClases(FamiliaCCNU familia){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		FamiliaCCNU docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (FamiliaCCNU) em.load(FamiliaCCNU.class, familia.getId());
			for (ClaseCCNU item: docu.getClases()){em.evict(item);
			}
			tx.commit();
			return  docu.getClases();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
     	return null;
	}	
	
	}

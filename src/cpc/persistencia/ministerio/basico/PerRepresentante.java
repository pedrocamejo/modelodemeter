package cpc.persistencia.ministerio.basico;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.ministerio.gestion.ProductorJuridico;
import cpc.modelo.ministerio.gestion.Representante;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerRepresentante extends DaoGenerico<Representante, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerRepresentante() {
		super(Representante.class);
	}
	
	@SuppressWarnings("unused") 
	public Representante getDato(Representante entrada){
		if (entrada == null)
			return null;
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		Representante docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (Representante) em.get(Representante.class, entrada.getId());
			for (ProductorJuridico item: docu.getProductores()){em.evict(item);
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
}

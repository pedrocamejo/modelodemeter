package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import cpc.modelo.sigesp.basico.UnidadMedidaSIGESP;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerUnidadMedida extends DaoGenerico<UnidadMedidaSIGESP, String>{

	private static final long serialVersionUID = -2045317152155101858L;

	public PerUnidadMedida() {
		super(UnidadMedidaSIGESP.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadMedidaSIGESP> getTodos() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<UnidadMedidaSIGESP> unidadesAdministrativas = null;
		
		tx = em.beginTransaction();
		try{
			unidadesAdministrativas = em.createCriteria(UnidadMedidaSIGESP.class).addOrder(Order.asc("codigo"))
				.list();
			tx.commit();
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return unidadesAdministrativas;
	}
	
	

}

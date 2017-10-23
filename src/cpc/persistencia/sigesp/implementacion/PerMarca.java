package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.sigesp.basico.Marca;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerMarca extends DaoGenerico<Marca, String>{

	private static final long serialVersionUID = 6783679159246142270L;

	public PerMarca() {
		super(Marca.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Marca> getTodos(){
		Session em;
		Transaction tx = null;
		List<Marca> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<Marca>) em.createCriteria(Marca.class)
				.add(Restrictions.eq("estado", true))
				.addOrder(Order.asc("codigoMarca"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Marca> getTodosOrdenadoPorNombre(){
		Session em;
		Transaction tx = null;
		List<Marca> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<Marca>) em.createCriteria(Marca.class)
				.add(Restrictions.eq("estado", true))
				.addOrder(Order.asc("descripcion"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}

}

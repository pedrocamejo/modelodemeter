package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.sigesp.basico.Categoria;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerCategoria extends DaoGenerico<Categoria, String>{

	private static final long serialVersionUID = 8786233955113339010L;

	public PerCategoria() {
		super(Categoria.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> getTodas(){
		Session em;
		Transaction tx = null;
		List<Categoria> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<Categoria>) em.createCriteria(Categoria.class)
				.add(Restrictions.eq("estatus", true))
				.addOrder(Order.asc("codigoCategoria"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> getImplementos(){
		Session em;
		Transaction tx = null;
		List<Categoria> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<Categoria>) em.createCriteria(Categoria.class)
				.add(Restrictions.eq("estatus", true))
				.add(Restrictions.eq("implemento", true))
				.add(Restrictions.eq("maquinaria", false))
				.addOrder(Order.asc("codigoCategoria"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> getMaquinarias(){
		Session em;
		Transaction tx = null;
		List<Categoria> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<Categoria>) em.createCriteria(Categoria.class)
				.add(Restrictions.eq("estatus", true))
				.add(Restrictions.eq("implemento", false))
				.add(Restrictions.eq("maquinaria", true))
				.addOrder(Order.asc("codigoCategoria"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}

}

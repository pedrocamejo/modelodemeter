package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.administrativo.AprobacionReversoRecibo;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;




public class PerAprobacionReversoRecibo extends DaoGenerico<AprobacionReversoRecibo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerAprobacionReversoRecibo() {
		// TODO Auto-generated constructor stub
		super(AprobacionReversoRecibo.class);
	}
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllAprobacionReversoRecibo(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(AprobacionReversoRecibo.class)
					//.add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	
	
	public void guardar(AprobacionReversoRecibo objeto, Integer indice){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null){
		   		em.save(objeto);
		   	}else{
		   		em.update(objeto);
		   	}
		   	
			
		    //em.flush();
		   	tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			
		}
	}
	
}

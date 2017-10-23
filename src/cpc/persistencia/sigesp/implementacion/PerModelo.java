package cpc.persistencia.sigesp.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerModelo extends DaoGenerico<Modelo, String>{

	private static final long serialVersionUID = 37407505977944255L;

	public PerModelo() {
		super(Modelo.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Modelo> getPorMarca(Marca marca ){
		Session em;
		Transaction tx = null;
		List<Modelo> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession( );
		tx = em.beginTransaction();
		
		try {
			lista = (List<Modelo>) em.createCriteria(Modelo.class)
				.add(Restrictions.eq("marca", marca))
				.addOrder(Order.asc("codigoModelo"))
				.list();
			tx.commit();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Modelo> getModeloTipoGarantia( ) {
		// TODO Auto-generated method stub
		Session em;
		Transaction tx = null;
		List<Modelo> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession( );
		tx = em.beginTransaction();
		
		try {
			lista = em.createQuery("select m from Modelo m,TipoGarantia tp where tp.modelo = m ").list();
			tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	 
	}

	@SuppressWarnings("unchecked")
	public List<Modelo> getModeloSinTipoGarantia( ) {
		// TODO Auto-generated method stub
		Session em;
		Transaction tx = null;
		List<Modelo> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession( );
		tx = em.beginTransaction();
		
		try {
			lista = em.createQuery("select m from Modelo m where m.idModelo not in (select dg.modelo from TipoGarantia dg)").list();
			tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	  
	}

	
	@SuppressWarnings("unchecked")
	public List<Modelo> getModeloConActivos( ) {
		// TODO Auto-generated method stub
		Session em;
		Transaction tx = null;
		List<Modelo> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession( );
		tx = em.beginTransaction();
		
		try {
			lista = em.createQuery("select m from Modelo m where m.idModelo in (select dg.modelo.idModelo from Activo dg)").list();
			tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	  
	}
	
	
	
}

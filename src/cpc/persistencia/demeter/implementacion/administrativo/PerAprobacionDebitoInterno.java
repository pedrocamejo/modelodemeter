package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.administrativo.AprobacionDebitoInterno;
import cpc.modelo.demeter.administrativo.TipoAprobacionDebitoInterno;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;




@SuppressWarnings("unchecked")
public class PerAprobacionDebitoInterno extends DaoGenerico<AprobacionDebitoInterno, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7717611544168322489L;




	/**
	 * 
	 */


	public PerAprobacionDebitoInterno() {
		// TODO Auto-generated constructor stub
		super(AprobacionDebitoInterno.class);
	}
	@SuppressWarnings("unchecked")
	public List<AprobacionDebitoInterno> getAllAprobacionDebitoInterno(){
		Transaction tx = null;
		List<AprobacionDebitoInterno> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(AprobacionDebitoInterno.class)
					//.add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	
	
	public void guardar(AprobacionDebitoInterno objeto, Integer indice){
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
	
	public List<TipoAprobacionDebitoInterno> getTiposAprobacionDebitoInternos(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		List<TipoAprobacionDebitoInterno>salida =new ArrayList<TipoAprobacionDebitoInterno>();
		try{
			salida = em.createCriteria(TipoAprobacionDebitoInterno.class)
					//.add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
		
	}
}

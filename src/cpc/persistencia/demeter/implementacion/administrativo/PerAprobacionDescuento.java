package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.AprobacionDescuento;
import cpc.modelo.demeter.basico.CostoBancario;
import cpc.modelo.demeter.basico.PrecioProducto;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;




public class PerAprobacionDescuento extends DaoGenerico<AprobacionDescuento, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerAprobacionDescuento() {
		super(AprobacionDescuento.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllAprobacionDescuento(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(AprobacionDescuento.class)
					//.add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	
	
	public void guardar(AprobacionDescuento objeto, Integer indice){
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

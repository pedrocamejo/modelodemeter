package cpc.persistencia.demeter.implementacion.basico;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.CostoBancario;
import cpc.modelo.demeter.basico.PrecioProducto;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerCostoBancario extends DaoGenerico<CostoBancario, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerCostoBancario() {
		super(CostoBancario.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllCostoBancario(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(CostoBancario.class)
					//.add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllCostoBancarioActivo(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(CostoBancario.class).add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllCostoBancarioActivoNoCheque(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(CostoBancario.class).add(Restrictions.eq("activo", true))
					.add(Restrictions.not(Restrictions.like("descripcion", "DEVOLUCION DE CHEQUE")))
					.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getReversoRecibo(){
		Transaction tx = null;
		List<IProducto>  salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida =  em.createCriteria(CostoBancario.class).add(Restrictions.eq("descripcion", "REVERSO DE RECIBO"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	@SuppressWarnings("unused")
	public CostoBancario getDato(IProducto labor){
		if (labor == null) return null;
		List<UnidadMedida> hijos = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
	
		CostoBancario docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (CostoBancario) em.load(CostoBancario.class, labor.getId());
			for (PrecioProducto index : docu.getPrecios()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	
	public void guardar(CostoBancario objeto, Integer indice){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null){
		   		em.save(objeto);
		   	}else{
		   		em.update(objeto);
		   	}
		   	for (PrecioProducto item : objeto.getPrecios()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
			}
		    //em.flush();
		   	tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			
		}
	}

	public IProducto getAllCostoBancarioActivo(String descripcion) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		IProducto  salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = (IProducto) em.createCriteria(CostoBancario.class).add(Restrictions.like("descripcion",descripcion)).uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
}

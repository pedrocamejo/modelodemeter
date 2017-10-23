package cpc.persistencia.demeter.implementacion.basico;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.PrecioProducto;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.mantenimiento.Consumible;
import cpc.modelo.demeter.mantenimiento.Herramienta;
import cpc.modelo.demeter.mantenimiento.Repuesto;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerConsumible;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerHerramienta;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerRepuesto;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class PerArticuloVenta extends DaoGenerico<ArticuloVenta, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerArticuloVenta() {
		super(ArticuloVenta.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllArticulo(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(ArticuloVenta.class)
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
	public List<ArticuloVenta> getAllArticuloVenta(){
		Transaction tx = null;
		List<ArticuloVenta> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(ArticuloVenta.class)
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
	public List<ArticuloVenta> getAllArticuloConsumible() throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<ArticuloVenta> salida = null; 
		
		List<Consumible> a = new PerConsumible().getAll();
		List<Integer> ids = new ArrayList<Integer>();
		
		for (Consumible consumible : a) {
			ids.add(consumible.getArticuloVenta().getId());
		}
			
		Session em =SessionDao.getInstance().getCurrentSession();
		
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(ArticuloVenta.class).add(Restrictions.in("id", ids))
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
	public List<ArticuloVenta> getAllArticuloHerramienta() throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<ArticuloVenta> salida = null; 
		
		List<Herramienta> a = new PerHerramienta().getAll();
		List<Integer> ids = new ArrayList<Integer>();
		
		for (Herramienta herramienta : a) {
			ids.add(herramienta.getArticuloVenta().getId());
		}
			
		Session em =SessionDao.getInstance().getCurrentSession();
		
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(ArticuloVenta.class).add(Restrictions.in("id", ids))
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
	public List<ArticuloVenta> getAllArticuloRepuesto() throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<ArticuloVenta> salida = null; 
		
		List<Repuesto> a = new PerRepuesto().getAll();
		List<Integer> ids = new ArrayList<Integer>();
		
		for (Repuesto repuesto : a) {
			ids.add(repuesto.getArticuloVenta().getId());
		}
			
		Session em =SessionDao.getInstance().getCurrentSession();
		
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(ArticuloVenta.class).add(Restrictions.in("id", ids))
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
	public List<ArticuloVenta> getArticulosEnExistencia() throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<ArticuloVenta> salida = null; 
		
		List<ArticuloAlmacenCantidad > a = new PerArticuloAlmacenCantidad().getEnExistencia();
		List<Integer> ids = new ArrayList<Integer>();
		
		for (ArticuloAlmacenCantidad articuloAlmacenCantidad : a) {
			ids.add(articuloAlmacenCantidad.getArticuloVenta().getId());
		}
		
		Session em =SessionDao.getInstance().getCurrentSession();
		
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(ArticuloVenta.class).add(Restrictions.in("id", ids))
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
	public List<IProducto> getAllArticuloActivo(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(ArticuloVenta.class).add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	@SuppressWarnings("unused")
	public ArticuloVenta getDato(IProducto labor){
		if (labor == null) return null;
		List<UnidadMedida> hijos = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
	ArticuloVenta aa = (ArticuloVenta) labor;
		ArticuloVenta docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (ArticuloVenta) em.load(ArticuloVenta.class, aa.getId());
			Hibernate.initialize(docu);
						for (PrecioProducto index : docu.getPrecios()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	
	public void guardar(ArticuloVenta objeto, Integer indice){
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
	
}

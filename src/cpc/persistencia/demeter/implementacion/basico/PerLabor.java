package cpc.persistencia.demeter.implementacion.basico;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.PrecioProducto;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.UsoPreciosProducto;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcAccesoInvalido;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerLabor extends DaoGenerico<Labor, Integer>{
	private static final long serialVersionUID = 5649285453859595020L;

	public PerLabor() {
		super(Labor.class);
	}
	
	
	
	public Labor getDato(Labor labor){
		if (labor == null) return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Labor docu = null;
		Transaction tx = em.beginTransaction();
     	try{
			docu = (Labor) em.get(Labor.class, labor.getId());
			for (PrecioProducto index : docu.getPrecios()){
				index.getPrecio();
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	public List<Labor> enriqueserDatos(List<Labor> labores) throws ExcAccesoInvalido{
		if (labores == null) return null;
		List<Labor> salida = new ArrayList<Labor>();
		Labor labor2;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
     	try{
     		for (Labor labor : labores) {
     			labor2 = (Labor) em.load(Labor.class, labor.getId());
    			for (PrecioProducto index : labor2.getPrecios()){
    				index.getPrecio();
    			}
    			salida.add(labor2);
			}
     		tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcAccesoInvalido("No se pudo enriqueser Datos");
		}	
		return salida;
	}
	
	
	public void guardar(Labor objeto, Integer indice) throws ExcFiltroExcepcion{
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
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error almacenando Labor");
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<IProducto> getAllLabor(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Labor.class)
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
	public List<IProducto> getAllLaborACTIVAS(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Labor.class).add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	@SuppressWarnings("unchecked")
	public List<Labor> getLaboresActivas(){
		Transaction tx = null;
		List<Labor> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Labor.class).add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	@SuppressWarnings("unchecked")
	public List<Labor> getLaboresInactivas(){
		Transaction tx = null;
		List<Labor> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Labor.class).add(Restrictions.eq("activo", false))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllLaborACTIVAS(TipoServicio tipoServicio){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Labor.class).add(Restrictions.eq("activo", true))
					.createAlias("servicio", "servicio")
					.add(Restrictions.eq("servicio.tipoServicio",tipoServicio)).
				list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllLabor(Servicio servicio){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Labor.class)
				.add(Restrictions.eq("servicio",servicio))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllLaborActivas(Servicio servicio){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Labor.class)
				.add(Restrictions.eq("servicio",servicio))
				.add(Restrictions.eq("activo", true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	@SuppressWarnings("unchecked")
	public List<Labor> getAll(Servicio servicio){
		Transaction tx = null;
		List<Labor> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Labor.class)
				.add(Restrictions.eq("servicio",servicio))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	@SuppressWarnings("unchecked")
	public List<Labor> getAll(TipoServicio Tipo){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Labor> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = em.createQuery("SELECT l FROM Labor l INNER JOIN l.servicio s  INNER JOIN  s.tipoServicio t where t.id = :tipo")
				.setInteger("tipo", Tipo.getId())
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	

	@SuppressWarnings("unchecked")
	public List<IProducto> getnoTipo(TipoServicio Tipo){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<IProducto> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = em.createQuery("SELECT l FROM Labor l INNER JOIN l.servicio s  INNER JOIN  s.tipoServicio t where not t.id = :tipo and l.activo= true ")
				.setInteger("tipo", Tipo.getId())
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllProductos(TipoServicio Tipo){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<IProducto> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = em.createQuery("SELECT l FROM Labor l INNER JOIN l.servicio s  INNER JOIN  s.tipoServicio t where t.id = :tipo")
				.setInteger("tipo", Tipo.getId())
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public void borrar(Labor t) throws ExcFiltroExcepcion {  
    	Session em =SessionDao.getInstance().getCurrentSession();
    	Transaction tx = null;
    	try{
	     	tx = em.beginTransaction();
	     	for (PrecioProducto item : t.getPrecios()) {
	   			em.delete(item);
			}
	        //em.remove(em.merge(t));
	    	em.delete(em.merge(t));
	    	em.flush();
	    	tx.commit();
    	}catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion("Error eliminando labor");
		}
	}
	
	public List<UsoPreciosProducto> getPreciosDetallados(List<Labor> labores) throws ExcAccesoInvalido{
		List<Labor> lista = enriqueserDatos(labores);
		List<UsoPreciosProducto> precios = new ArrayList<UsoPreciosProducto>();
		List<UsoPreciosProducto> tmp;
		for (Labor labor : lista) {
			tmp = UsoPreciosProducto.getListaPrecios(labor.getPrecios(), labor);
			precios.addAll(tmp);
		} 
		return precios;
	}
	
}

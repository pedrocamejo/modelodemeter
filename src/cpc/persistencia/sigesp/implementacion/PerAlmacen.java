package cpc.persistencia.sigesp.implementacion;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class PerAlmacen extends DaoGenerico<Almacen, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8424506142794658220L;

	public PerAlmacen() {
		super(Almacen.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Almacen> getAlmacenesPorUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa,List<TipoAlmacen> tipoAlmacen) throws ExcFiltroExcepcion{
		Session em;
		Transaction tx = null;
		List<Almacen> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<Almacen>) em.createCriteria(Almacen.class)
				.add(Restrictions.eq("unidadAdministrativa", unidadAdministrativa))
				.add(Restrictions.in("tipoAlmacen", tipoAlmacen))
				.addOrder(Order.asc("id"))
				.list();
			if (lista.size() <1)
				throw new ExcFiltroExcepcion("No tiene Almacenes para esta unidad Funcional");
		}catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}

	
	@SuppressWarnings("unchecked")
	public List<Integer> getIdAlmacenesOperativos(List<TipoAlmacen> tipoAlmacen){
		Session em;
		Transaction tx = null;
		List<Integer> lista = new ArrayList<Integer>();
		List<Almacen> almacenes = null;
		TipoAlmacen operativo = new PerTipoAlmacen().getTipoOperativo();
	
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			almacenes = (List<Almacen>) em.createCriteria(Almacen.class)
				.add(Restrictions.eq("tipoAlmacen", operativo))
				.add(Restrictions.in("tipoAlmacen", tipoAlmacen))
				.list();
			for (Almacen item : almacenes){
				lista.add(item.getId());
				em.evict(item);
			}
			
		} catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
		return lista;
	}
	
	/*
	@SuppressWarnings("unchecked")
	public List<Almacen> getAlmacenesOperativos(){
		Session em;
		Transaction tx = null;
		List<Almacen> almacenes = null;
		TipoAlmacen operativo = new PerTipoAlmacen().getTipoOperativo();
		
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			almacenes = (List<Almacen>) em.createCriteria(Almacen.class)
				.add(Restrictions.eq("tipoAlmacen", operativo))
				.list();
			
		} catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
		return almacenes;
	}
	*/
	
	@SuppressWarnings("unchecked")
	public List<Almacen> getAlmacenesOperativos(List<TipoAlmacen> tipoAlmacen){
		Session em;
		Transaction tx = null;
		List<Almacen> almacenes = null;
		TipoAlmacen operativo = new PerTipoAlmacen().getTipoOperativo();
	
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			almacenes = (List<Almacen>) em.createCriteria(Almacen.class)
				.add(Restrictions.in("tipoAlmacen", tipoAlmacen))
					.add(Restrictions.eq("tipoAlmacen", operativo))
				.list();
			
		} catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
		return almacenes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Almacen> getAlmacenesSalidaInterna(){
		Session em;
		Transaction tx = null;
		List<Almacen> almacenes = null;
		
		TipoAlmacen tipoAlmacen = new PerTipoAlmacen().getTipoSalidaInterna();
	
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			almacenes = (List<Almacen>) em.createCriteria(Almacen.class)
						.add(Restrictions.eq("tipoAlmacen", tipoAlmacen))
				.list();
			
		} catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
		return almacenes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Almacen> getAlmacenesSalidaExterna(){
		Session em;
		Transaction tx = null;
		List<Almacen> almacenes = null;
		
		TipoAlmacen tipoAlmacen = new PerTipoAlmacen().getTipoSalidaExterna();
	
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			almacenes = (List<Almacen>) em.createCriteria(Almacen.class)
						.add(Restrictions.eq("tipoAlmacen", tipoAlmacen))
				.list();
			
		} catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
		return almacenes;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Almacen> getAlmacenesServicioTecnico(){
		Session em;
		Transaction tx = null;
		List<Almacen> almacenes = null;
		
		List<TipoAlmacen> tipoAlmacen = new PerTipoAlmacen().getTipoServicoTecnico();
	
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			almacenes = (List<Almacen>) em.createCriteria(Almacen.class)
						.add(Restrictions.in("tipoAlmacen", tipoAlmacen))
				.list();
			
		} catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
		return almacenes;
	}
	
	
	
	
}

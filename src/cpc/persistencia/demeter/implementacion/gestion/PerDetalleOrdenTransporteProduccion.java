package cpc.persistencia.demeter.implementacion.gestion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.DetalleOrdenTransporteProduccion;
import cpc.modelo.demeter.gestion.OrdenTransporteProduccion;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class PerDetalleOrdenTransporteProduccion extends DaoGenerico<DetalleOrdenTransporteProduccion, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 188670267206954382L;



	public PerDetalleOrdenTransporteProduccion() {
		super(DetalleOrdenTransporteProduccion.class);		
	}
	
	public void guardar(DetalleOrdenTransporteProduccion procesado, DetalleOrdenTransporteProduccion nuevo) throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
	   		em.update(procesado);
	   		em.save(nuevo);
		    em.flush();
		   	tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion("Error Almacenando");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DetalleOrdenTransporteProduccion> getTodas(OrdenTransporteProduccion orden) throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<DetalleOrdenTransporteProduccion> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DetalleOrdenTransporteProduccion.class)
				.add(Restrictions.eq("orden", orden))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public DetalleOrdenTransporteProduccion getActiva(OrdenTransporteProduccion orden) throws ExcFiltroExcepcion{
		Transaction tx = null;
		DetalleOrdenTransporteProduccion criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = (DetalleOrdenTransporteProduccion) em.createCriteria(DetalleOrdenTransporteProduccion.class)
				.add(Restrictions.eq("orden", orden))
				.add(Restrictions.eq("efectivo", Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;

	}
	
}

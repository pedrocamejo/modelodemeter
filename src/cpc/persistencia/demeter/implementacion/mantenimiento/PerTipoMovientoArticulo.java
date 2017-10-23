package cpc.persistencia.demeter.implementacion.mantenimiento;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.mantenimiento.Maquinaria;
import cpc.modelo.demeter.mantenimiento.TipoMovimientoArticulo;
import cpc.modelo.sigesp.basico.Activo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerTipoMovientoArticulo extends DaoGenerico<TipoMovimientoArticulo, Integer>{

	
	private static final long serialVersionUID = -3250167924835147970L;

	public PerTipoMovientoArticulo() {
		super(TipoMovimientoArticulo.class);
	}
	
	
	public TipoMovimientoArticulo getEntradaArticulo(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		TipoMovimientoArticulo resultado = null;
		try{
			resultado = (TipoMovimientoArticulo) em.createCriteria(TipoMovimientoArticulo.class).add(Restrictions.eq("id",1)).uniqueResult();				
				
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return resultado;
	}
	
	

	public TipoMovimientoArticulo getSalidaInterna(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		TipoMovimientoArticulo resultado = null;
		try{
			resultado = (TipoMovimientoArticulo) em.createCriteria(TipoMovimientoArticulo.class).add(Restrictions.eq("id",2)).uniqueResult();				
				
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return resultado;
	}
	
	

	public TipoMovimientoArticulo getSalidaExterna(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		TipoMovimientoArticulo resultado = null;
		try{
			resultado = (TipoMovimientoArticulo) em.createCriteria(TipoMovimientoArticulo.class).add(Restrictions.eq("id",3)).uniqueResult();				
				
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return resultado;
	}
	
	
	

	public TipoMovimientoArticulo getTransferencia(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		TipoMovimientoArticulo resultado = null;
		try{
			resultado = (TipoMovimientoArticulo) em.createCriteria(TipoMovimientoArticulo.class).add(Restrictions.eq("id",4)).uniqueResult();				
				
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return resultado;
	}
	
	
	

	public TipoMovimientoArticulo getDevolucion(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		TipoMovimientoArticulo resultado = null;
		try{
			resultado = (TipoMovimientoArticulo) em.createCriteria(TipoMovimientoArticulo.class).add(Restrictions.eq("id",5)).uniqueResult();				
				
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return resultado;
	}
	
	
	

}

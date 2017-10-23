package cpc.persistencia.demeter.implementacion.gestion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.sigesp.basico.Activo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerImplementoUnidad extends DaoGenerico<ImplementoUnidad, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7094152471159345612L;

	public PerImplementoUnidad() {
		super(ImplementoUnidad.class);		
	}
	
	@SuppressWarnings("unchecked")
	public List<ImplementoUnidad> getImplementos(UnidadFuncional unidad){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<ImplementoUnidad>  criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<ImplementoUnidad>) em.createCriteria(ImplementoUnidad.class)
				.add(Restrictions.eq("unidad", unidad))
				.add(Restrictions.eq("operativo", Boolean.TRUE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public ImplementoUnidad getPorImplementoYUnidadFuncional(UnidadFuncional unidad, Activo activo){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		ImplementoUnidad  criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (ImplementoUnidad) em.createCriteria(ImplementoUnidad.class)
				.add(Restrictions.eq("unidad", unidad))
				.add(Restrictions.eq("activo", activo))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public  List<ImplementoUnidad> getImplementoTransporte(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<ImplementoUnidad>  criterio= null;
		List<String> tipo = new ArrayList<String>();
		tipo.add("00000003");
		tipo.add("00000004");
		 
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<ImplementoUnidad>)  em.createCriteria(ImplementoUnidad.class,"imp")			
				.add(Restrictions.in("cat1.codigoCategoria", tipo))
				.createAlias("imp.activo", "act")
				.createAlias("act.categoria", "cat1")
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	

}

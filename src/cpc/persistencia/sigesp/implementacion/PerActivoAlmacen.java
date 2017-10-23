package cpc.persistencia.sigesp.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.ActivoAlmacen;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerActivoAlmacen extends DaoGenerico<ActivoAlmacen, Long>{

	private static final long serialVersionUID = 4725628820389171853L;

	public PerActivoAlmacen() {
		super(ActivoAlmacen.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActivoAlmacen> getTodos (){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<ActivoAlmacen> criterio = null;
		
		tx = em.beginTransaction();
		try {
			criterio = em.createCriteria(ActivoAlmacen.class)
				.add(Restrictions.eq("trasladado", false))
				.add(Restrictions.eq("desincorporado", false))
				.list();
			tx.commit();
			
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<ActivoAlmacen> getTodosSinAlmacenOperativo (List<TipoAlmacen> tipoAlmacens){
		Transaction tx = null;
	//	List<Almacen> almacenes = new PerAlmacen().getAlmacenesOperativos();
		List<Almacen> almacenes = new PerAlmacen().getAlmacenesOperativos(tipoAlmacens) ;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<ActivoAlmacen> criterio = null;
		
		tx = em.beginTransaction();
		try {
			criterio = em.createCriteria(ActivoAlmacen.class)
				.add(Restrictions.eq("trasladado", false))
				.add(Restrictions.eq("desincorporado", false))
				.add(Restrictions.not(Restrictions.in("almacen", almacenes)))
				.list();			
			tx.commit();		
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getTodosMaquinarias(UnidadFuncional unidad) {
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Activo> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = em.createQuery("SELECT DISTINCT a FROM ActivoAlmacen aa INNER JOIN aa.activo a  INNER JOIN  a.categoria c " +
					"INNER JOIN aa.almacen l INNER JOIN l.tipoAlmacen t Inner Join l.unidadFuncional u "+
					"where c.implemento = :esImplemento and c.estatus = :estaActivo and t.operativo = :esoperativo and  u.id = :id")
				.setBoolean("esImplemento", Boolean.FALSE)
				.setBoolean("estaActivo", Boolean.TRUE)
				.setBoolean("esoperativo", Boolean.TRUE)
				.setInteger("id", unidad.getId())
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getTodosImplementos(UnidadFuncional unidad){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Activo> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = em.createQuery("SELECT DISTINCT a FROM ActivoAlmacen aa INNER JOIN aa.activo a  INNER JOIN  a.categoria c " +
					"INNER JOIN aa.almacen l INNER JOIN l.tipoAlmacen t Inner Join l.unidadFuncional u "+
					"where c.implemento = :esImplemento and c.estatus = :estaActivo and t.operativo = :esoperativo and  u.id = :id")
				.setBoolean("esImplemento", Boolean.TRUE)
				.setBoolean("estaActivo", Boolean.TRUE)
				.setBoolean("esoperativo", Boolean.TRUE)
				.setInteger("id", unidad.getId())
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<ActivoAlmacen> getTrasladados (){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<ActivoAlmacen> criterio = null;
		
		tx = em.beginTransaction();
		try {
			criterio = em.createCriteria(ActivoAlmacen.class)
				.add(Restrictions.eq("trasladado", true))
				.add(Restrictions.eq("desincorporado", false))
				.list();
			tx.commit();
			
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
		
	public ActivoAlmacen getPorActivo(Activo activo){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		ActivoAlmacen criterio = null;
		
		tx = em.beginTransaction();
		try {
			criterio = (ActivoAlmacen) em.createCriteria(ActivoAlmacen.class)
				.add(Restrictions.eq("activo", activo))
				.uniqueResult();
			tx.commit();
			
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

}

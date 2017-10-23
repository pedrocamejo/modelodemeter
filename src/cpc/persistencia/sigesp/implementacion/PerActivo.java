package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import sun.awt.geom.AreaOp.AddOp;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Categoria;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.modelo.sigesp.indice.ActivoPK;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerActivo extends DaoGenerico<Activo, ActivoPK>{

	private static final long serialVersionUID = -713007351813583582L;

	public PerActivo() {
		super(Activo.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getTodos() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createCriteria(Activo.class)
				.addOrder(Order.asc("id"))
				.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getTodosImplementos() {
		Transaction tx = null;
		List<Categoria > implementos = new PerCategoria().getImplementos();
		
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createCriteria(Activo.class)
				.add(Restrictions.in("categoria", implementos))
				.addOrder(Order.asc("id"))
				.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getTodasMaquinaria() {
		Transaction tx = null;
		List<Categoria > implementos = new PerCategoria().getImplementos();
		
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createCriteria(Activo.class)
				.add(Restrictions.not(Restrictions.in("categoria", implementos)))
				.addOrder(Order.asc("id"))
				.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}

	@SuppressWarnings("unchecked")
	public List<Activo> getPorModelo(Modelo modelo) {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createCriteria(Activo.class)
				.add(Restrictions.eq("modelo", modelo))
				.addOrder(Order.asc("id"))
				.add(Restrictions.or(Restrictions.eq("desincorporado", false),Restrictions.isNull("desincorporado")) ) 
				.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getPorModeloConAlmacen(Modelo modelo) {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createCriteria(Activo.class)
				.add(Restrictions.eq("modelo", modelo))
				.add(Restrictions.isNotNull("almacen"))
				.add(Restrictions.eq("desincorporado", false))
				.addOrder(Order.asc("id"))
				.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getPorModeloSinAlmacenOperativo(Modelo modelo) {
		Transaction tx = null;
		TipoAlmacen operativo = new PerTipoAlmacen().getTipoOperativo();
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createQuery("SELECT DISTINCT a FROM ActivoAlmacen aa INNER JOIN aa.activo a  "+
					"INNER JOIN aa.almacen l "+
					"where l.tipoAlmacen <> :esOperativo " +
					"AND a.modelo = :modelo " +
					"AND a.desincorporado = :esdesincorporado " +
					"AND a.trasladado = :estrasladado "+
					"ORDER BY a.id")
				.setEntity("esOperativo", operativo)
				.setEntity("modelo", modelo)
				.setBoolean("estrasladado", Boolean.FALSE)
				.setBoolean("esdesincorporado", Boolean.FALSE)
				.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getPorModeloSinAlmacen(Modelo modelo) {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createCriteria(Activo.class)
				.add(Restrictions.eq("modelo", modelo))
				.add(Restrictions.eq("marca", modelo.getMarca()))
				.add(Restrictions.isNull("almacen"))
				.addOrder(Order.asc("id"))
				.list();
			
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}
	
		
	@SuppressWarnings("unchecked")
	public List<Activo> getTodosConAlmacen() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createCriteria(Activo.class)
			.add(Restrictions.isNotNull("almacen"))
			.add(Restrictions.eq("desincorporado", false))
			.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getTodosSinAlmacenOperativo(List<TipoAlmacen> tipoAlmacens) {
		List<Integer> almacenes = new PerAlmacen().getIdAlmacenesOperativos(tipoAlmacens);
	
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<Activo> activos = null;
		tx = em.beginTransaction();
		
		try{
			activos = em.createCriteria(Activo.class)
			.add(Restrictions.isNotNull("almacen"))
			.add(Restrictions.eq("desincorporado", false))
			.add(Restrictions.eq("trasladado", false))
			.add(Restrictions.not(Restrictions.in("almacen", almacenes)))
			.add(Restrictions.in("tipoAlmacen", tipoAlmacens  ))
			.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return activos;
	}
	
}

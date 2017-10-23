package cpc.persistencia;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.Type;

import cpc.modelo.demeter.interfaz.IDaoGenerico;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class DaoGenerico<T, PK> implements IDaoGenerico<T, PK>, Serializable {

	private static final long serialVersionUID = -7299318686293971700L;

	@SuppressWarnings("rawtypes")
	private Class persistenciaClass;

	@SuppressWarnings("rawtypes")
	public DaoGenerico(Class persistenciaClass) {
		this.persistenciaClass = persistenciaClass;
	}

	public void guardar(T objeto, PK indice) throws Exception {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			if (indice == null)
				em.save(objeto);
			else
				em.update(objeto);
			em.flush();
			tx.commit();
		} catch (ConstraintViolationException e) {
			throw new Exception(
					"Error al intentar Almacenar. Existen valores en el Registro actual que no puede repetirse.",
					e.getCause());

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new Exception("Error al intentar Alamcenar. "
					+ e.getMessage(), e.getCause());
		}
	}

	public void guardaroupdate(T objeto, PK indice) throws Exception {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			if (indice == null)
				em.save(objeto);
			else
				em.saveOrUpdate(objeto);
			em.flush();
			tx.commit();
		} catch (ConstraintViolationException e) {
			throw new Exception(
					"Error al intentar Almacenar. Existen valores en el Registro actual que no puede repetirse."
							+ " " + e.getMessage(), e.getCause());

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new Exception("Error al intentar Alamcenar. "
					+ e.getMessage(), e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() throws ExcFiltroExcepcion {
		List<T> entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			entities = em.createCriteria(persistenciaClass).list();
			tx.commit();
		} catch (Exception e) {
			if(tx != null)	
				tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<T> getcritaeriaAll() throws ExcFiltroExcepcion {
		List<T> entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		System.out.println("la clase es " + persistenciaClass.toString());
		Transaction tx = null;
		try {
			tx = em.beginTransaction();

			entities = em.createCriteria(persistenciaClass)
					//.setCacheable(true)
					.list();

			System.out.println(entities.size());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<T> getcritaeriaAll(List<Criterion> a) throws ExcFiltroExcepcion {
		List<T> entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		System.out.println("la clase es " + persistenciaClass.toString());
		Transaction tx = null;

		try {
			tx = em.beginTransaction();
			Criteria z = em.createCriteria(persistenciaClass);
			if (a != null) {
				for (Criterion criterion : a) {
					z.add(criterion);
				}
			}
			entities = z.list();

			System.out.println(entities.size());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}

	@SuppressWarnings("unchecked")
	public T getcritaeriaUno(List<Criterion> a) throws ExcFiltroExcepcion {
		T entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		System.out.println("la clase es " + persistenciaClass.toString());
		Transaction tx = null;

		try {
			tx = em.beginTransaction();
			Criteria z = em.createCriteria(persistenciaClass);
			if (a != null) {
				for (Criterion criterion : a) {
					z.add(criterion);
				}
			}
			entities = (T) z.uniqueResult();

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}

	public void borrar(T t) throws Exception {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			em.delete(em.merge(t));
			em.flush();
			tx.commit();
		} catch (ConstraintViolationException e) {
			throw new Exception(
					"Error al intentar Eliminar. El Registro esta siendo usado por otras Entidades.",
					e.getCause());
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new Exception(
					"Error al intentar Eliminar. " + e.getMessage(),
					e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	public T buscarId(Serializable id) {
		T objeto = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			objeto = (T) em.get(persistenciaClass, id);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return objeto;
	}

	@SuppressWarnings("unchecked")
	public T buscarUnoPorCampo(String field, String value) {
		T objeto = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			objeto = (T) em.createQuery(
					"FROM " + persistenciaClass.getName() + " WHERE " + field
							+ " = " + value).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return objeto;
	}

	@SuppressWarnings("rawtypes")
	public List getQueryNombrado(String nombre) {
		Session em = SessionDao.getInstance().getCurrentSession();

		return em.getNamedQuery(nombre).list();
	}

	public Query getConsultaNombrada(String consulta) {
		Session em = SessionDao.getInstance().getCurrentSession();
		return em.getNamedQuery(consulta);
	}

	@SuppressWarnings("rawtypes")
	public List getQuery(String query) {
		Session em = SessionDao.getInstance().getCurrentSession();
		return em.createQuery(query).list();
	}

	public Session getEm() {
		Session em = SessionDao.getInstance().getCurrentSession();
		return em;
	}

	@SuppressWarnings("rawtypes")
	public Class getPersistenciaClass() {
		return persistenciaClass;
	}

	@SuppressWarnings("rawtypes")
	public void setPersistenciaClass(Class persistenciaClass) {
		this.persistenciaClass = persistenciaClass;
	}

	@SuppressWarnings("unchecked")
	public ResultSet correrSqlinsertupdate(String sql) throws Exception {
		T objeto = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		ResultSet s = null;
		try {
			tx = em.beginTransaction();
			em.createSQLQuery(sql).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw e;
		}
		return s;
	}

/*
	@SuppressWarnings("rawtypes")
	public List correrSql3(String sql, HashMap mapa) throws Exception {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		List s;
		try {
			tx = em.beginTransaction();
			SQLQuery query = em.createSQLQuery(sql);

			Set set = mapa.entrySet();
			Iterator itera = set.iterator();
			while (itera.hasNext()) {
				Map.Entry me = (Map.Entry) itera.next();
				String nombre = (String) me.getKey();
				Object valor = me.getValue();
				if (valor.getClass().equals(Integer.class)) {
					query.setInteger(nombre, (Integer) valor);
				}
				if (valor.getClass().equals(Double.class)) {
					query.setDouble(nombre, (Double) valor);
				}
				if (valor.getClass().equals(String.class)) {
					query.setString(nombre, (String) valor);
					if (valor.equals(Date.class)) {
						query.setDate(nombre, (Date) valor);
					}
				}
			}
			System.out.println(query.getQueryString());
			String[] parmaetros = query.getNamedParameters();
			for (String string : parmaetros) {
				System.out.println(string);
			}
			s = query.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw e;
		}
		return s;
	}
	*/
	@SuppressWarnings("rawtypes")
	public int correrSqlUpdateinsert(String sql, HashMap mapa) throws Exception {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		int s;
		try {
			tx = em.beginTransaction();
			SQLQuery query = em.createSQLQuery(sql);
			Set set = mapa.entrySet();
			Iterator itera = set.iterator();
			while (itera.hasNext()) {
				Map.Entry me = (Map.Entry) itera.next();
				String nombre = (String) me.getKey();
				Object valor = me.getValue();
				if (valor.getClass().equals(Integer.class)) {
					query.setInteger(nombre, (Integer) valor);
				}
				if (valor.getClass().equals(Double.class)) {

					query.setDouble(nombre, (Double) valor);
				}
				if (valor.getClass().equals(String.class)) {
					query.setString(nombre, (String) valor);
				}
				if (valor.equals(Date.class)) {

					query.setDate(nombre, (Date) valor);
				};
			}
			s = query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw e;
		}
		return s;
	}
}

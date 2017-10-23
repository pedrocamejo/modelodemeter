package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerTipoFormaPago extends DaoGenerico<TipoFormaPago, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4050240296848910728L;

	public PerTipoFormaPago() {
		super(TipoFormaPago.class);
		// TODO Auto-generated constructor stub
	}

	public TipoFormaPago getTipoDeposito(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoFormaPago criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (TipoFormaPago) em.createCriteria(TipoFormaPago.class)
				.add(Restrictions.eq("deposito",Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoFormaPago getTipoCheque(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoFormaPago criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (TipoFormaPago) em.createCriteria(TipoFormaPago.class)
				.add(Restrictions.eq("usaCuenta",Boolean.TRUE))
				.add(Restrictions.eq("usaBanco",Boolean.TRUE))
				.add(Restrictions.eq("deposito",Boolean.FALSE))
				.add(Restrictions.eq("nota",Boolean.FALSE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoFormaPago getTipoEfectivo(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoFormaPago criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (TipoFormaPago) em.createCriteria(TipoFormaPago.class)
				.add(Restrictions.eq("usaCuenta",Boolean.FALSE))
				.add(Restrictions.eq("usaBanco",Boolean.FALSE))
				.add(Restrictions.eq("usaDocumento",Boolean.FALSE))
				.add(Restrictions.eq("nota",Boolean.FALSE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoFormaPago> getTipoNotas(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<TipoFormaPago> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio =  em.createCriteria(TipoFormaPago.class)
				.add(Restrictions.eq("nota",Boolean.TRUE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	

	/*
	 * Listado de Recibos con Forma de Pago y la forma de Pago no esta cancelada
	 * y es de tipo efectivo 
	**/
	public List getRecibosFormaPagoEfectivo() {
		// TODO Auto-generated method stub
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List objectos = em.createQuery("SELECT fp FROM FormaPagoEfectivo fp WHERE fp.depositado =false ").list();
		tx.commit();
		return objectos;
	}
	
	/*
	 * Listado de Recibos con Forma de Pago y la forma de Pago no esta cancelada
	 * y es de tipo efectivo 
	**/
	public List getRecibosFormaPagoCheque() {
		// TODO Auto-generated method stub
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List objectos = em.createQuery("SELECT fp FROM FormaPagoCheque fp WHERE fp.depositado = :valor ")
					.setParameter("valor",false)
					.list();
		tx.commit();
		return objectos;
	
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoFormaPago> getTipoElectronico(){
		Transaction tx = null;
		List<String> descripcionElectronicas=new  ArrayList<String>();
		descripcionElectronicas.add("TRANSFERENCIA");
		descripcionElectronicas.add("PUNTO ELECTRONICO");
		Session em =SessionDao.getInstance().getCurrentSession();
		List<TipoFormaPago> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio =  em.createCriteria(TipoFormaPago.class)
				.add(Restrictions.in("descripcion",descripcionElectronicas))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	@SuppressWarnings("unchecked")
	public List<TipoFormaPago> getTipoNoElectronico(){
		Transaction tx = null;
		List<String> descripcionElectronicas=new  ArrayList<String>();
		descripcionElectronicas.add("TRANSFERENCIA");
		descripcionElectronicas.add("PUNTO ELECTRONICO");
		Session em =SessionDao.getInstance().getCurrentSession();
		List<TipoFormaPago> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio =  em.createCriteria(TipoFormaPago.class)
				.add(Restrictions.not(Restrictions.in("descripcion",descripcionElectronicas)))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
}

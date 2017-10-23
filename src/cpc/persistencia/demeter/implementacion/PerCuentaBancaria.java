package cpc.persistencia.demeter.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.basico.CuentaBancaria;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerCuentaBancaria extends DaoGenerico<CuentaBancaria, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5924936167823690256L;


	public PerCuentaBancaria() {
		super(CuentaBancaria.class);

	}

	
	public CuentaBancaria getCuentaBancaria(Banco banco, String nroCuenta){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		CuentaBancaria criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (CuentaBancaria) em.createCriteria(CuentaBancaria.class)
				.add(Restrictions.eq("banco",banco))
				.add(Restrictions.eq("nroCuenta",nroCuenta))
				.uniqueResult();
			tx.commit();
			if (criterio == null){
				criterio = new CuentaBancaria();
				criterio.setBanco(banco);
				criterio.setNroCuenta(nroCuenta);
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public List<CuentaBancaria> getCuentasBancaria(Banco banco){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CuentaBancaria> lista = null;
		tx = em.beginTransaction();
		try{
			lista =  em.createCriteria(CuentaBancaria.class).add(Restrictions.eq("banco",banco)).list();
			tx.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return lista;
	}
	
}

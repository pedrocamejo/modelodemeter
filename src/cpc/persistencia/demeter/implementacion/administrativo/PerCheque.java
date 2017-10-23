package cpc.persistencia.demeter.implementacion.administrativo;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.ast.tree.RestrictableStatement;
import org.hibernate.transform.Transformers;

import cpc.modelo.demeter.administrativo.Cheque;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerCheque extends DaoGenerico<Cheque,Integer> {

	
	
	public PerCheque() {
		super(Cheque.class);
		// TODO Auto-generated constructor stub
	}

	public Boolean getnroChequeUsado(String nroCheque, String nrocuenta) throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
		List entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			entities = em.createCriteria(Cheque.class).add(Restrictions.eq("nroCheque",nroCheque)).add(Restrictions.eq("nroCuenta",nrocuenta)).list();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return (entities.size() != 0? true:false);
	}

	public List<Cheque> getAll(DocumentoFiscal factura) throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
		
		List entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			entities = em.createQuery("select distinct cheque from Recibo r inner join r.formaspago fp inner join fp.cheques cheque where r.documento=:factura ").setParameter("factura",factura).list();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}
		
	

	public List<Cheque> getChequesNoUsados() throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
		/*
		 * Los Cheques se usan 2 formas asociados a FormadepagoCheque con el Recibo 
		 * y asociado con un deposito con una forma de pago Deposito
		 * la forma de pago tiene un estado si este esta true es por que la forma de pago tiene un recibo activo 
		 *  por lo cual si quiero saber cuales son los cheques que no se estan usando 
		 *  son los cheques q no pertenecen al conjunto de cheque que estan en formaPagocheque con la forma de pago activa
		 *  y los depositos que tiene forma de pago que este pago esta asociado a una forma de pago activa 
		 *  
		 * */

		List entities = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			entities = em.createSQLQuery(
					" select cheque.* from administracion.tbl_dem_cheque cheque where cheque.seq_idcheque not in( "+
							"	select  int_idcheque   from administracion.tbl_dem_forma_pago_cheque fp left join administracion.tbl_dem_formapago formapago "+
							"	on formapago.seq_idformapago =   fp.seq_idformapago	where formapago.estado = true  ) and cheque.seq_idcheque not in ( "+
							"	 select ch.seq_idcheque from administracion.tbl_dem_forma_pago_deposito fpd  inner join administracion.tbl_dem_cheque ch "+ 
							"	on ch.int_idformapago = fpd.seq_idformapago  inner join administracion.tbl_dem_formapago formapago "+
							"	on formapago.seq_idformapago =  fpd.seq_idformapago  where formapago.estado = true ) "
					).addEntity(Cheque.class).list();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}
	
	
	
	
}

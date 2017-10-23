package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.envers.tools.graph.TopologicalSort;

import cpc.modelo.demeter.mantenimiento.EnteExterno;
import cpc.modelo.demeter.mantenimiento.Garantia;
import cpc.modelo.demeter.mantenimiento.TipoGarantia;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerGarantia extends DaoGenerico<Garantia,Integer> {

	public PerGarantia() {
		super(Garantia.class);
		// TODO Auto-generated constructor stub
	}

	public List<Garantia> getxEnte(EnteExterno ente ) {
		// TODO Auto-generated method stub
	 
		
		List<Garantia> entities = null; 
		
        Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
       
        try {  
         	tx = em.beginTransaction();
         	entities = em.createCriteria(Garantia.class).add(Restrictions.eq("ente",ente)).list();
            tx.commit();
        } catch (Exception e) {  
        	tx.rollback();
        	e.printStackTrace();
        }  
        return entities; 
	}

	public List<Marca> getmarcasxGarantia( ) {
		// TODO Auto-generated method stub

		List<Marca> entities = null; 
		Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
        try {  
         	tx = em.beginTransaction();
         	entities = em.createQuery("select distinct(m) from Marca m , Modelo mo , TipoGarantia g" +
         							  " where mo.marca = m " +
         							  " and   g.modelo = mo").list();
            tx.commit();
        } catch (Exception e) {  
        	tx.rollback();
        	e.printStackTrace();
        }  
        return entities; 
	}

	public List<TipoGarantia> getModeloxGarantia(Marca m ) {
		// TODO Auto-generated method stub
		List<TipoGarantia> entities = new ArrayList<TipoGarantia>(); 
		Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
        try {  
         	tx = em.beginTransaction();
         	
         	Query q = em.createQuery("select g from TipoGarantia g " +
         							 " where g.modelo.marca = :m");
         	q.setParameter("m",m);
         	entities = q.list();
         	
         	tx.commit();
        } catch (Exception e) {  
        	tx.rollback();
        	e.printStackTrace();
        }  
        return entities;  
	}
	
}

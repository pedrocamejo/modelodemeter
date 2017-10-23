package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.mantenimiento.Lote;
import cpc.modelo.demeter.mantenimiento.MantenimientoMaquinaria;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerMantenimientoMaquinaria  extends DaoGenerico<MantenimientoMaquinaria, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410227421083988304L;

	public PerMantenimientoMaquinaria() {
		super(MantenimientoMaquinaria.class);
	}
	
	public List<Modelo> getmodeloConTipoGarantia()
	{
		List<Modelo> entities = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction tx = null;
        try {  
         	tx = em.beginTransaction();
         	
         	Query q = em.createQuery("select tg.modelo from TipoGarantia tg where tg not in (select mm.tipogarantia from MantenimientoMaquinaria mm) ");
         	entities = q.list();
         	tx.commit();
        } catch (Exception e) {  
        	tx.rollback();
        	e.printStackTrace();
        }  
        return entities;  		
	}
 
}
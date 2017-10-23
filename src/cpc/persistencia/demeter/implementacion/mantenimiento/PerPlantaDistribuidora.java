package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.DetalleActaAutorizacion;
import cpc.modelo.demeter.mantenimiento.PlantaDistribuidora;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerPlantaDistribuidora extends DaoGenerico<PlantaDistribuidora, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerPlantaDistribuidora() {
		super(PlantaDistribuidora.class);
		// TODO Auto-generated constructor stub
	}

	public List<PlantaDistribuidora> getPlantas(Modelo modelo) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<PlantaDistribuidora> lista = null;
		tx = em.beginTransaction();
		
		try{
			lista = em.createQuery("Select pl from PlantaDistribuidora pl join pl.productos producto where producto.modelo = :pro ").setParameter("pro",modelo).list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return lista;
	}
	
}

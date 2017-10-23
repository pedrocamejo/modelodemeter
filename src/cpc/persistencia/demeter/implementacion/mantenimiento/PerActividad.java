package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.mantenimiento.Actividad;
import cpc.modelo.demeter.mantenimiento.DetalleGarantina;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerActividad extends DaoGenerico<Actividad,Integer>{

	public PerActividad() {
		super(Actividad.class);
		// TODO Auto-generated constructor stub
	} 
	
	
	public List<Actividad> getActividad_DetalleGarantia( DetalleGarantina detalleGarantia, Modelo modelo)
	{
		List<Actividad> entities;
	    Session em =SessionDao.getInstance().getCurrentSession();
        Transaction tx = null;
        	tx = em.beginTransaction();
  /*      	
        	Query q	= em.createQuery("select a from Actividad a, TipoGarantia tg join a.detallesGarantia a_dg " +
  					" where a.planMantenimiento.mantenimientoMaquinaria.tipogarantia.modelo  = tg "+
  					"  and tg.modelo = :modelo "+
  					" and a_dg =:detalle ");
        	q.setParameter("modelo",modelo);
        	q.setParameter("detalle",detalleGarantia);
*/        	
        	
        	Query q	= em .createQuery("select a from Actividad a " +
					" join a.detallesGarantia a_dg " +
				" where   " +
				"  a_dg in(select dt from TipoGarantia tg join tg.detallesGarantia dt where tg.modelo =:modelo) "+
				" and a_dg =:dato ");//piezas
        	q.setParameter("modelo",modelo);
        	q.setParameter("dato",detalleGarantia);
        	
	/**   	Query q =  em.createSQLQuery("select a.seq_idactividad {actividad.id}," +
					  " a.descripcion  {actividad.descripcion} ," +
					  " a.int_planmantenimeinto {actividad.planMantenimiento} " +
					  " from mantenimiento.tbl_dem_actividad a ," +
			   " mantenimiento.tbl_dem_detalleGarantia dt ," +
			   " mantenimiento.tbl_dem_actividad_detalleGarantia dta" +
			   "  where dta.id_actividad = a.seq_idActividad" +
			   "  and dta.id_detallegarantia = dt.seq_idDetalleGarantia " +
			   " and dt.seq_idDetalleGarantia = :detalleGara" ).addEntity("actividad",Actividad.class);
      	 q.setParameter("detalleGara",detalleGarantia);
      	 **/
      	 entities = q.list();
      	 tx.commit();
	     return entities;
	}

}

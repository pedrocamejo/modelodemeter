package cpc.persistencia.demeter.implementacion.gestion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cpc.modelo.demeter.basico.TipoTrabajo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerTipoTrabajo extends DaoGenerico<TipoTrabajo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9163448561567355812L;

	public PerTipoTrabajo() {
		super(TipoTrabajo.class);		
	}
	
	public TipoTrabajo getTipoMecanizado(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		TipoTrabajo facturas = null;
		try{
			facturas = (TipoTrabajo) em.createQuery("SELECT  tt FROM TipoTrabajo tt INNER JOIN tt.tipoSolicitud td  where td.nombre like '%MECANIZA%'")
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}
		return facturas;
	}

	public TipoTrabajo getTipoTransporte(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		TipoTrabajo facturas = null;
		try{
			facturas = (TipoTrabajo) em.createQuery("SELECT  tt FROM TipoTrabajo tt INNER JOIN tt.tipoSolicitud td  where td.nombre like 'TRANSPORTE'").uniqueResult();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	public TipoTrabajo getTipoTransporteInterno(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		TipoTrabajo facturas = null;
		try{
			facturas = (TipoTrabajo) em.createQuery("SELECT  tt FROM TipoTrabajo tt INNER JOIN tt.tipoSolicitud td  where td.nombre like 'TRANSPORTE INTERNO'").uniqueResult();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	
}

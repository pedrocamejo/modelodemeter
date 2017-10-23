package cpc.persistencia.demeter.implementacion.gestion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.gestion.ActaAutorizacion;
import cpc.modelo.demeter.gestion.DetalleActaAutorizacion;
import cpc.modelo.demeter.gestion.DetalleActaSalida;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerActaAutorizacion extends DaoGenerico<ActaAutorizacion, Integer>{

	private static final long serialVersionUID = 7747636335829559227L;

	public PerActaAutorizacion() {
		super(ActaAutorizacion.class);
	}
	
	@SuppressWarnings("unused") 
	public ActaAutorizacion getDatos(ActaAutorizacion acta) {
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		ActaAutorizacion docu = null;
     	tx = em.beginTransaction();
     	try{
     		
			docu = (ActaAutorizacion) em.load(ActaAutorizacion.class, acta.getIdActa());
			try{
				for (DetalleActaAutorizacion item: docu.getActaActivos()){
				}
     		}catch (ClassCastException e){
     		}
     		try{
				for (DetalleActaSalida item: docu.getDetalleActaSalidas()){
				}
     		}catch (ClassCastException e){
     		}
			tx.commit();
     	 
     		
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public List<ActaAutorizacion> getActasAutorizacion(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<ActaAutorizacion> lista = null;
		tx = em.beginTransaction();
		try{
			lista = em.createQuery("SELECT DISTINCT dt.actaAutorizacion FROM DetalleActaAutorizacion dt WHERE dt.usadoEnMovimiento = :usado" )
				.setBoolean("usado", Boolean.FALSE)
				.list();
			tx.commit();
			
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<DetalleActaAutorizacion> getDetallesActasPorUnidad(UnidadAdministrativa unidad,
			ActaAutorizacion acta){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<DetalleActaAutorizacion> lista = null;
		tx = em.beginTransaction();
		
		try{
			lista = em.createCriteria(DetalleActaAutorizacion.class)
				.add(Restrictions.eq("actaAutorizacion", acta))
				.add(Restrictions.eq("unidadAdministrativa", unidad))
				.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<DetalleActaSalida> getDetallesActasSalidasPorUnidad(UnidadAdministrativa unidad,
			ActaAutorizacion acta){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<DetalleActaSalida> lista = null;
		tx = em.beginTransaction();
		
		try{
			lista = em.createCriteria(DetalleActaSalida.class)
				.add(Restrictions.eq("actaAutorizacion", acta))
				.add(Restrictions.eq("unidadAdministrativa", unidad))
				.list();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
		}
		return lista;
	}

}

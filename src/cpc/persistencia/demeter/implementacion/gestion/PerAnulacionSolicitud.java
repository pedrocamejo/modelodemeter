package cpc.persistencia.demeter.implementacion.gestion;


import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.TipoSolicitud;
import cpc.modelo.demeter.gestion.AnulacionSolicitud;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.MotivoAnulacionSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.modelo.sigesp.basico.Activo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerAnulacionSolicitud  extends DaoGenerico<AnulacionSolicitud, Integer>{

	private static final long serialVersionUID = -9102899506689198268L;
	public PerAnulacionSolicitud() {
		super(AnulacionSolicitud.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<MotivoAnulacionSolicitud> getMotivos(TipoSolicitud tipo){
		Session em;
		Transaction tx = null;
		List<MotivoAnulacionSolicitud> lista = null;
	
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try {
			lista = (List<MotivoAnulacionSolicitud>) em.createCriteria(MotivoAnulacionSolicitud.class)
				.add(Restrictions.eq("tipoSolicitud", tipo))
				.add(Restrictions.eq("activo",true))
				.addOrder(Order.asc("motivo"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}
	
	public AnulacionSolicitud getAnulacionMecanizado(SolicitudMecanizado entrada) throws ExcFiltroExcepcion{
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		AnulacionSolicitud docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (AnulacionSolicitud) em.createCriteria(AnulacionSolicitud.class)
					.add(Restrictions.eq("solicitud", entrada)).uniqueResult();
					
			
		tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(e.getMessage());
		}	
		return docu;
	}
	
	public AnulacionSolicitud getAnulacionServicioTecnico(SolicitudServicioTecnico entrada) throws ExcFiltroExcepcion{
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		AnulacionSolicitud docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (AnulacionSolicitud) em.createCriteria(AnulacionSolicitud.class)
					.add(Restrictions.eq("solicitud", entrada)).uniqueResult();
					
			
		tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(e.getMessage());
		}	
		return docu;
	}
	
	public void guardar(AnulacionSolicitud objeto,Session em,Transaction tx )  throws ExcFiltroExcepcion{
		System.out.println("Guardando");
		
		try{
	    
		   
		   		System.out.println("Creando");
		   		objeto.setfechaanulacion(new Date());
		   		em.save(objeto); 	
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Solicitud, "+ e.getMessage());
		}
	}
	
	
}

package cpc.persistencia.demeter.implementacion.mantenimiento;


import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.AnulacionSolicitud;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.mantenimiento.Maquinaria;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.modelo.sigesp.basico.Activo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.gestion.PerAnulacionSolicitud;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;

public class PerSolicitudServicioTecnico  extends DaoGenerico<SolicitudServicioTecnico, Long>{

	
;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3192965214586711482L;


	public PerSolicitudServicioTecnico() {
		super(SolicitudServicioTecnico.class);
	}
	
	
	
	public void nuevo( SolicitudServicioTecnico docu, ControlUnidadFuncional control, Session em, Transaction tx )  throws Exception {
		control.incrementarSolicitudServicioTEcnico();
		docu.setFecha(new Date()); 
		docu.setAprobada(true);
		docu.setNroControl(control.getSerieSolicitudServicioTecnico()+ Formateador.rellenarNumero(control.getControlSolicitudServicioTEcnico(),"00000"));
		em.save(docu);
		em.update(control);
	}
	
	public void guardar(SolicitudServicioTecnico objeto, Long indice, ControlUnidadFuncional control)  throws ExcFiltroExcepcion{

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null){
		   		nuevo( objeto, control, em, tx);
		   	}else{
		   		em.update(objeto);
		   	}
		   	for (DetalleSolicitud item : objeto.getDetalles()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
		   		for (UnidadSolicitada itemInt : item.getSolicitado()){
		   			if (itemInt.getId() == null)
			   			em.save(itemInt);
			   		else
			   			em.update(itemInt);
		   		}
			}
		   	tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Solicitud, "+ e.getMessage());
		}
	}
	
	
	public int getHijosActivos(Integer id) {
		/*Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		int cantidad = 0;
		Long criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (Long) em.createQuery("SELECT count(n) FROM ContratoMecanizado n INNER JOIN n.solicitud f where f.id = :id")
				.setLong("id", id)
				.uniqueResult();
			cantidad +=criterio;
			System.out.println("primer query"+ cantidad);
			criterio = (Long) em.createQuery("SELECT count(n) FROM OrdenTrabajoMecanizado n INNER JOIN n.solicitud f where f.id = :id")
			.setLong("id", id)
			.uniqueResult();
			cantidad +=criterio;
			System.out.println("segundo "+cantidad);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		} 
		return cantidad;*/
		return 0;
	}
	
	
	
	public void anular(SolicitudServicioTecnico docu,AnulacionSolicitud anulacion) throws HibernateException, ExcFiltroExcepcion {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession() ;
		PerAnulacionSolicitud peranulacion = new PerAnulacionSolicitud();
		
		try{ tx= em.beginTransaction();
			  //docu.setanulada(true);
		 	 docu.setAprobada(false);
			 em.update(docu);
			 peranulacion.guardar(anulacion,em,tx);
		//	 em.flush();
			 tx.commit();
			
		 }catch (Exception e) {
			 e.printStackTrace();
			tx.rollback();
			}		
}
	
	
	@SuppressWarnings("unchecked")
	public List<SolicitudServicioTecnico> getSegunEstado(EstadoSolicitud estado){
		Transaction tx = null;
		List<SolicitudServicioTecnico> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{//correjir luego
			criterio = em.createCriteria(SolicitudServicioTecnico.class)
				.add(Restrictions.eq("estadosolicitud",estado))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public SolicitudServicioTecnico getDatos(SolicitudServicioTecnico entrada) throws ExcFiltroExcepcion{
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		SolicitudServicioTecnico docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (SolicitudServicioTecnico) em.load(SolicitudServicioTecnico.class, entrada.getId());
		//	Hibernate.initialize(docu);
		//	Hibernate.initialize(docu.getDetalles());
			
			for (DetalleSolicitud item: docu.getDetalles()){
		//		Hibernate.initialize(item);
				for (UnidadSolicitada item2: item.getSolicitado()){
				}
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(e.getMessage());
		}	
		return docu;
	}
	
}

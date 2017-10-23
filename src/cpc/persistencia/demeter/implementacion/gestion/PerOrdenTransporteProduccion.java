package cpc.persistencia.demeter.implementacion.gestion;


import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.DetalleOrdenTrabajo;
import cpc.modelo.demeter.gestion.DetalleOrdenTransporteProduccion;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.modelo.demeter.gestion.OrdenTransporteProduccion;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;


public class PerOrdenTransporteProduccion extends DaoGenerico<OrdenTransporteProduccion, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 188670267206954382L;



	public PerOrdenTransporteProduccion() {
		super(OrdenTransporteProduccion.class);		
	}
	
	public OrdenTransporteProduccion getDatos(OrdenTransporteProduccion entrada) throws ExcFiltroExcepcion{
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		OrdenTransporteProduccion docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (OrdenTransporteProduccion) em.load(OrdenTransporteProduccion.class, entrada.getId());
			for (@SuppressWarnings("unused") DetalleOrdenTrabajo item: docu.getDetalles()){
			}
			for (@SuppressWarnings("unused") DetalleMaquinariaOrdenTrabajo item: docu.getEquipos()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(e.getMessage());
		}	
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrdenTransporteProduccion> getAll(EstadoOrdenTrabajo estado) throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<OrdenTransporteProduccion> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(OrdenTransporteProduccion.class)
				.add(Restrictions.eq("estado",estado))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<OrdenTransporteProduccion> getActivas() throws ExcFiltroExcepcion{
		Transaction tx = null;
		EstadoOrdenTrabajo estado1 = new PerEstadoOrdenTrabajo().getEnEspera();
		EstadoOrdenTrabajo estado2 = new PerEstadoOrdenTrabajo().getInicio();
		List<OrdenTransporteProduccion> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			Criterion condicion1 = Restrictions.eq("estado",estado1);
			Criterion condicion2 = Restrictions.eq("estado",estado2);
			criterio = em.createCriteria(OrdenTransporteProduccion.class)
				.add(Restrictions.or(condicion1,condicion2))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	
	public void guardar(OrdenTransporteProduccion objeto, Long indice, ControlUnidadFuncional control)  throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null){
		   		nuevo( objeto, control, em, tx);
		   	}else{
		   		em.update(objeto);
		   	}
		   	for (DetalleMaquinariaOrdenTrabajo item : objeto.getEquipos()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
			}
		   	for (DetalleOrdenTrabajo item : objeto.getDetalles()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
			}
		   	DetalleOrdenTransporteProduccion detalle = new DetalleOrdenTransporteProduccion();
		   	detalle.setOrden(objeto);
		   	detalle.setDestino(objeto.getUnidadArrimeSolicitada());
		   	detalle.setOrigen(objeto.getOrigen());
		   	detalle.setFechaSalida(objeto.getFechaSalida());
		   	detalle.setEfectivo(true);
   			em.save(detalle);
		   	tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Orden de Trabajo, "+ e.getMessage());
		}
	}
	
	public void cerrar(OrdenTransporteProduccion objeto, Long indice)  throws ExcFiltroExcepcion{
		Transaction tx = null;
		try{
			objeto.setEstado(new PerEstadoOrdenTrabajo().getTerminado());
			if (objeto.getSolicitud() != null)
				objeto.getSolicitud().setPrestada(true);
			Session em =SessionDao.getInstance().getCurrentSession();
	     	tx = em.beginTransaction();
	   		em.update(objeto);
   			if (objeto.getOrdenTrabajoMecanizado() != null){
   				objeto.getOrdenTrabajoMecanizado().setTransportado(true);
   				objeto.getOrdenTrabajoMecanizado().setCantidadRealProducida(objeto.getProduccionReal());
   				em.update(objeto.getOrdenTrabajoMecanizado());
   			}
   			for (DetalleOrdenTrabajo item : objeto.getDetalles()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
			}
		   	tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Cierre de Orden Transporte, "+ e.getMessage());
		}
	}
	
	public void anular(OrdenTransporteProduccion objeto, Long indice)  throws ExcFiltroExcepcion{
		
		Transaction tx = null;
		try{
			objeto.setEstado(new PerEstadoOrdenTrabajo().getCancelado());
			if (objeto.getSolicitud() != null)
				objeto.getSolicitud().setAprobada(false);
			Session em =SessionDao.getInstance().getCurrentSession();
	     	tx = em.beginTransaction();
	   		em.update(objeto);
	   		tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error Anulando Orden Trabajo, "+ e.getMessage());
		}
	}
	
	
	public void nuevo( OrdenTransporteProduccion docu, ControlUnidadFuncional control, Session em, Transaction tx )  throws Exception {
		control.incrementarSeguimiento();
		docu.setFecha(new Date()); 
		docu.setNroControl(control.getSerie()+ Formateador.rellenarNumero(control.getControlSeguimiento(),"000000"));
		if (docu.getSolicitud() != null)
			docu.getSolicitud().setPlanificada(true);
		em.save(docu);
		em.update(control);
		if (docu.getSolicitud() != null)
			em.update(docu.getSolicitud());
	}
	
}

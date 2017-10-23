package cpc.persistencia.demeter.implementacion.administrativo;


import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Cotizacion;
import cpc.modelo.demeter.administrativo.CuotasAPagarCliente;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.TipoCotizacion;
import cpc.modelo.demeter.basico.TipoCiclo;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.gestion.LaborOrdenServicio;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.TipoMovimientoArticulo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCuotaAPagarCliente;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoOrdenTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerCotizacion extends DaoGenerico<Cotizacion, Integer>{

	


	public PerCotizacion() {
		super(Cotizacion.class);
	}
	
	public void guardar(Cotizacion objeto,  ControlSede sede)  throws Exception, SQLException {
	   	 if (objeto.getId() == null)
	   	 {
	   		 if (objeto.getExpediente()==true)
	   	 	 {
	   			 nuevoExpediente( objeto, sede);
	   		 }
	   		 else
	   		 { 
	   			 nuevo( objeto, sede);
	   		 }
	   	 }	   		 
	   		
	   	 else
	   	   super.guardar(objeto, objeto.getId());
	}
	
	
	
	public void nuevo(Cotizacion docu, ControlSede control)  throws ExcFiltroExcepcion, SQLException {
		docu.setEstadoExoneracion(new PerEstadoExoneracionContrato().getNoExonerado());
		Transaction tx = null;
		Session em ;
		try{		
			docu.setSerie(control.getProximaSerieCotizacion());
			docu.setNroControl(control.getControlCotizacion());	
			docu.getSalidaExternaArticulo().setCodigoMovimiento(control.getPrefijoSalidaArticulo());
			control.incrementarControlSalidaArticulo();
			docu.getSalidaExternaArticulo().setNumerocontrol(control.getControlSalidaArticulo());
			docu.getSalidaExternaArticulo().setSede(control.getSede());
			docu.getSalidaExternaArticulo().setEstado(true);
			//docu.setEstado(new EstadoContrato(EstadoContrato.ESTADO_CULMINADO,""));	
			docu.setEstado(new PerEstadoContrato().getCulminado());	
			
			//System.out.println("lo que esta seteado en docu"+ docu.getSolicitud().getEstadosolicitud().getEstado());
			em =SessionDao.getInstance().getCurrentSession();
			
		    tx = em.beginTransaction();
		    if (docu.getExpediente()==false){
		    docu.setFecha(control.getFechaCierreFactura());
		    docu.setFechaDesde(control.getFechaCierreFactura());
		   
			docu.setDiasVigencia(3);
		    }
			em.save(docu);
			docu.getSalidaExternaArticulo().setFecha(control.getFechaCierreFactura());
			em.saveOrUpdate(docu.getSalidaExternaArticulo());
			for (DetalleSalidaExternaArticulo detalleSalidaExternaArticulo : docu.getSalidaExternaArticulo().getDetalleSalidaExternaArticulos()) {
			new PerArticuloAlmacenCantidad().actualizarCantidades(detalleSalidaExternaArticulo.getAlmacenOrigen(), detalleSalidaExternaArticulo.getArticuloVenta(),detalleSalidaExternaArticulo.getCantidad(), false, em);	
			}
		
			em.update(control);
		
		    em.flush();
		    tx.commit(); 
		   
		}catch (Exception e) {
			 
			e.printStackTrace();
			tx.rollback();
			if (control==null) throw new SQLException("Indeterminado el Control Numerico del Contrato. ¿Su registro de usuario pertenece a la Sede actual? ");
			throw new ExcFiltroExcepcion();
		}
	}
	
	public void nuevoExpediente(Cotizacion docu, ControlSede control)  throws ExcFiltroExcepcion, SQLException {
		docu.setEstadoExoneracion(new PerEstadoExoneracionContrato().getNoExonerado());
		Transaction tx = null;
		Session em ;
		try{		
			docu.setSerie(control.getProximaSerieCotizacion());
			docu.setNroControl(control.getControlCotizacion());	
			
			
			
			
			
			//docu.setEstado(new EstadoContrato(EstadoContrato.ESTADO_CULMINADO,""));
			docu.setEstado(new PerEstadoContrato().getCulminado());	
			
			
			//System.out.println("lo que esta seteado en docu"+ docu.getSolicitud().getEstadosolicitud().getEstado());
			em =SessionDao.getInstance().getCurrentSession();
			
		    tx = em.beginTransaction();
		
			
			em.save(docu);
			
			
			
		
			em.update(control);
		
		    em.flush();
		    tx.commit(); 
		   
		}catch (Exception e) {
			 
			e.printStackTrace();
			tx.rollback();
			if (control==null) throw new SQLException("Indeterminado el Control Numerico del Contrato. ¿Su registro de usuario pertenece a la Sede actual? ");
			throw new ExcFiltroExcepcion();
		}
	}
	
	@SuppressWarnings("unused")
	public Cotizacion getDetalle(Cotizacion entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	Cotizacion c = (Cotizacion) em.load(Cotizacion.class, entrada.getId());
     	try{			
     		
			for (DetalleContrato item: c.getDetallesContrato()){ em.evict(item);				
				
			}
           for (CuotasAPagarCliente item: c.getCuotasAPagarCliente()){ em.evict(item);			
				
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return c;
	}
	
	@SuppressWarnings("unused")
	public ContratoMecanizado getEnriqueser(Contrato entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	ContratoMecanizado c = (ContratoMecanizado) em.get(ContratoMecanizado.class, entrada.getId());
     	try{			
			for (DetalleContrato item: c.getDetallesContrato()){	 em.evict(item);			
				
			}
           for (CuotasAPagarCliente item: c.getCuotasAPagarCliente()){	 em.evict(item);		
				
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return c;
	}
	
	
	/*@SuppressWarnings("unchecked")
	public List<DetalleContrato> getContratosServicio(){
		Transaction tx = null;
		List<Contrato> cttos = new PerContrato().getPorYEnEjecucion();
		List<DetalleContrato> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DetalleContrato.class)
				.add(Restrictions.in("contrato", cttos))
				.add(Restrictions.eq("prestado", Boolean.FALSE))
				.addOrder(Order.desc("id"))
				.list();
			em.evict(cttos);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}*/
	
	public Contrato validarDetalle(Contrato entrada, LaborOrdenServicio labor){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	Contrato c = (Contrato) em.load(Contrato.class, entrada.getId());
     	try{			
			for (DetalleContrato item: c.getDetallesContrato()){
				if (labor.getLabor().equals(item.getProducto())){
					item.setCantidadReal(labor.getCantidad());
					item.setPrestado(true);
					 em.evict(item);
				}
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return c;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotizacion> getAllExistencia() {
		Transaction tx = null;
		List<Cotizacion> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Cotizacion.class)
				.add(Restrictions.eq("expediente", Boolean.FALSE))
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
	public List<Cotizacion> getAllExpediente() {
		Transaction tx = null;
		List<Cotizacion> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Cotizacion.class)
				.add(Restrictions.eq("expediente", Boolean.TRUE))
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
	public List<Cotizacion> getAllExpediente(TipoCotizacion tipo) {
		Transaction tx = null;
		List<Cotizacion> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Cotizacion.class)
				.add(Restrictions.eq("expediente", Boolean.TRUE))
				.add(Restrictions.eq("tipoCotizacion",tipo))
				.addOrder(Order.desc("id"))
				.list();
			    tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
		
	}

	
	public void anular(Cotizacion cotizacion) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em ;
		try {
		em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		cotizacion.setEstado(new EstadoContrato(EstadoContrato.ESTADO_ANULADO,""));
		em.update(cotizacion);
		tx.commit();
		} catch (Exception e) {e.printStackTrace();
		tx.rollback();
		throw e;
			// TODO: handle exception
		}
	}
	
	
	
public int getHijosActivos(Contrato ctto) throws Exception{
		
		Transaction tx = null;
		Session em ;
		int hijos = 0;
		
		try {
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
			em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
	/*	List<OrdenTrabajoMecanizado> criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.eq("contrato",ctto))
				.add(Restrictions.not(Restrictions.eq("estado",estado)))
				.addOrder(Order.desc("id"))
				.list();
		
	*/
		
		@SuppressWarnings("unchecked")
		List<DocumentoFiscal> criterio2 = em.createCriteria(DocumentoFiscal.class)
		    		.add(Restrictions.eq("contrato",ctto))
		    	.add(Restrictions.not(Restrictions.eq("estado",estado)))
			    	.list();
		hijos =  criterio2.size();
		
		tx.commit();
		} catch (Exception e) {e.printStackTrace();
		tx.rollback();
		throw e;
			// TODO: handle exception
		}
		
	
		return hijos;
	}
	




	

	
}

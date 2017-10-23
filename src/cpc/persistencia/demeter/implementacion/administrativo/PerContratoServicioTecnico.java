package cpc.persistencia.demeter.implementacion.administrativo;


import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoServicioTecnico;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.CuotasAPagarCliente;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.DetalleContratoDevolucionArticulo;
import cpc.modelo.demeter.administrativo.DetalleContratoSalidaArticulo;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.gestion.LaborOrdenServicio;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCuotaAPagarCliente;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoOrdenTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerContratoServicioTecnico extends DaoGenerico<ContratoServicioTecnico, Integer>{

	
	private static final long serialVersionUID = 4524428514868526037L;

	public PerContratoServicioTecnico() {
		super(ContratoServicioTecnico.class);
	}
	
	public void guardar(ContratoServicioTecnico objeto,  ControlSede sede)  throws Exception, SQLException {
	   	 if (objeto.getId() == null)	   		 
	   		nuevo( objeto, sede);
	   	 else
	   	   super.guardar(objeto, objeto.getId());
	}
	
	
	
	public void nuevo(ContratoServicioTecnico docu, ControlSede control)  throws ExcFiltroExcepcion, SQLException {
		docu.setEstadoExoneracion(new PerEstadoExoneracionContrato().getNoExonerado());
		Transaction tx = null;
		Session em ;
		try{		
			docu.setSerie(control.getProximaSerieContratoServicioTecnico());
			docu.setNroControl(control.getControlContratoServicioTecnico());	
			docu.setEstado(new PerEstadoContrato().getCulminado());	
			docu.getSolicitud().setEstadosolicitud(new PerEstadoSolicitud().getServicioTecnicoTotalizada());
			System.out.println("lo que esta seteado en docu"+ docu.getSolicitud().getEstadosolicitud().getEstado());
			em =SessionDao.getInstance().getCurrentSession();
			
		    tx = em.beginTransaction();
			em.save(docu);
			em.update(control);
			em.update(docu.getSolicitud());
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
	public ContratoServicioTecnico getDetalle(ContratoServicioTecnico entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	ContratoServicioTecnico c = (ContratoServicioTecnico) em.load(ContratoServicioTecnico.class, entrada.getId());
     	try{		
     	     for (DetalleContratoSalidaArticulo item : c.getDetalleContratoSalidaArticulos())
             {}
             
             for (DetalleContratoDevolucionArticulo item : c.getDetalleContratoDevolucionArticulos())
             {}
     		
			for (DetalleContrato item: c.getDetallesContrato()){ //em.evict(item);				
				
			}
       for (CuotasAPagarCliente item: c.getCuotasAPagarCliente()){ //em.evict(item);			
				
			}
      
           
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return c;
	}
	
	@SuppressWarnings("unused")
	public ContratoServicioTecnico getEnriqueser(Contrato entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	ContratoServicioTecnico c = (ContratoServicioTecnico) em.get(ContratoServicioTecnico.class, entrada.getId());
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
	
/*	
	@SuppressWarnings("unchecked")
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
	
	public Contrato validarDetalle(Contrato entrada, ArticuloVenta articuloVenta, Double cantidad){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	Contrato c = (Contrato) em.load(Contrato.class, entrada.getId());
     	try{			
			for (DetalleContrato item: c.getDetallesContrato()){
				if (articuloVenta.getDescripcion().equals(item.getProducto().getDescripcion())){
					item.setCantidadReal((cantidad));
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
	
	
	public void anularContrato(ContratoServicioTecnico contrato) throws Exception, SQLException{		
		Transaction tx = null;
		Session em ;
		int hijosactivos= getHijosActivos(contrato);
	
		try{  EstadoSolicitud estadosolictud = new PerEstadoSolicitud().getaprobada();
		 EstadoContrato estadocontrato = new EstadoContrato(EstadoContrato.ESTADO_ANULADO,"");
		
			em =SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			/*Tiene una Factura No Anulada*/
			if (hijosactivos>=1){
				throw new SQLException("Imposible Anular el Documento porque tiene una Factura o una orden activa");
			}
			/*Tiene un Servicio para ser Prestado en Alguna Orden de Trabajo*/
			  //throw new SQLException("Imposible Anular el Documento porque tiene una Orden de Trabajo Activa");
			/* **** **/
			contrato.getSolicitud().setEstadosolicitud(estadosolictud);
			contrato.setEstado(estadocontrato);
			em.update(contrato);
			em.update(contrato.getSolicitud()); 
			new PerCuotaAPagarCliente().eliminarCuotasApagar(contrato,em,tx);
			 tx.commit(); 
		
		}catch(SQLException e){
			e.printStackTrace();
			tx.rollback();
			throw e;
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	public void procesarContrato(ContratoServicioTecnico contrato) throws Exception, SQLException{		
		Transaction tx = null;
		Session em ;
	//	int hijosactivos= getHijosActivos(contrato);
	
		try{  EstadoSolicitud estadosolictud = new PerEstadoSolicitud().getculminada();
		 EstadoContrato estadocontrato = new PerEstadoContrato().getConcluidoAdministracion();
		
			em =SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			/*Tiene una Factura No Anulada*/
			/*Tiene un Servicio para ser Prestado en Alguna Orden de Trabajo*/
			  //throw new SQLException("Imposible Anular el Documento porque tiene una Orden de Trabajo Activa");
			/* **** **/
			contrato.getSolicitud().setEstadosolicitud(estadosolictud);
			contrato.setEstado(estadocontrato);
			em.update(contrato);
			em.update(contrato.getSolicitud()); 
			//new PerCuotaAPagarCliente().eliminarCuotasApagar(contrato,em,tx);
			 tx.commit(); 
		
		}catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	public Contrato getEstadoContrato(Contrato ctto, LaborOrdenServicio labor){
		ctto = validarDetalle(ctto, labor);
		boolean terminado = true;
		if (ctto.getDetallesContrato().size() > 1){
			for (DetalleContrato item : ctto.getDetallesContrato()) {
				if (item.getPrestado() == null || item.getPrestado() == false){
					terminado = false;
					break;
				} 
			}
			if (terminado){
				culminarContrato(ctto);
			}
		}else
			culminarContrato(ctto);
		return ctto;
	}
	
	private void culminarContrato(Contrato ctto){
		EstadoContrato estado = new PerEstadoContrato().getCulminado();
		ctto.setEstado(estado);
	}
	
	public int getHijosActivos(Contrato ctto) throws Exception{
		
		Transaction tx = null;
		Session em ;
		int hijos = 0;
		
		try {
	
			em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
	
		@SuppressWarnings("unchecked")
		List<DocumentoFiscal> criterio2 = em.createCriteria(DocumentoFiscal.class)
		    		.add(Restrictions.eq("contrato",ctto))
			    	.list();
		hijos = criterio2.size();
		
		tx.commit();
		} catch (Exception e) {e.printStackTrace();
		tx.rollback();
		throw e;
			// TODO: handle exception
		}
		
	
		return hijos;
	}
	//corre
	
	
}

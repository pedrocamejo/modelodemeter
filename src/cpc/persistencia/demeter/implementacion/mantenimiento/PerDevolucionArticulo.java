package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.mantenimiento.DetalleDevolucionArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleEntradaArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaInternaArticulo;
import cpc.modelo.demeter.mantenimiento.DevolucionArticulo;
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.demeter.mantenimiento.MovimientoArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaInternaArticulo;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerDevolucionArticulo extends DaoGenerico<DevolucionArticulo, Integer> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1177858679256231493L;

	public PerDevolucionArticulo() {
		super(DevolucionArticulo.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	
	public void guardarDevolucion( DevolucionArticulo devolucionArticulo, Integer indice, ControlSede control)  throws ExcFiltroExcepcion{

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
	     
		   	if (indice == null){
		   		control.incrementarControlDevolucionArticulo();	
		     	devolucionArticulo.setCodigoMovimiento(control.getPrefijoDevolucionArticulo());
		     	devolucionArticulo.setNumerocontrol(control.getControlDevolucionArticulo());
		     	devolucionArticulo.setEstado(true);
		     	em.saveOrUpdate(control);
		   		em.save(devolucionArticulo);
		   	}else{
		   		em.update(devolucionArticulo);
		   	}
		   	
		   	for (DetalleDevolucionArticulo detalleDevolucionArticulo : devolucionArticulo.getDetalleDevolucionArticulos()) {
		   		new PerArticuloAlmacenCantidad().actualizarCantidades(detalleDevolucionArticulo.getAlmacenOrigen(),detalleDevolucionArticulo.getArticuloVenta(),detalleDevolucionArticulo.getCantidad(),true,em); 	
			}
		   	
		    
			
		   	
		   	
		   	
		   	
		   	
			
		   	tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Solicitud, "+ e.getMessage());
		}
	}

	
	
	
	
	@SuppressWarnings("unchecked")
	public List<DevolucionArticulo> getAllSalidas( List<SalidaExternaArticulo> salidaExternaArticulos){
		Transaction tx = null;
		
		List<DevolucionArticulo> criterio= null;
		
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DevolucionArticulo.class)
				.add(Restrictions.in("salidaExternaArticulo",salidaExternaArticulos))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	
	
	
	
	
}
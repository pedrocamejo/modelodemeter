package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.mantenimiento.DetalleEntradaArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaInternaArticulo;
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.demeter.mantenimiento.MovimientoArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaInternaArticulo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerSalidaInternaArticulo extends DaoGenerico<SalidaInternaArticulo, Integer> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1177858679256231493L;

	public PerSalidaInternaArticulo() {
		super(SalidaInternaArticulo.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	
	public void guardarSalidaInterna( SalidaInternaArticulo salidaInternaArticulo, Integer indice,ControlSede control)  throws ExcFiltroExcepcion{

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
	    	
		   	if (indice == null){
		   		control.incrementarControlConsumoArticulo();	
		     	salidaInternaArticulo.setCodigoMovimiento(control.getPrefijoConsumoArticulo());
		     	salidaInternaArticulo.setNumerocontrol(control.getControlConsumoArticulo());
		     	salidaInternaArticulo.setEstado(true);
		     	em.saveOrUpdate(control);
		   		em.save(salidaInternaArticulo);
		   	}else{
		   		em.update(salidaInternaArticulo);
		   	}
		   	
		   	for (DetalleSalidaInternaArticulo detalleSalidaInternaArticulo : salidaInternaArticulo.getDetalleSalidaInternaArticulos()) {
		   		new PerArticuloAlmacenCantidad().actualizarCantidades(detalleSalidaInternaArticulo.getAlmacenOrigen(),detalleSalidaInternaArticulo.getArticuloVenta(),detalleSalidaInternaArticulo.getCantidad(),false,em); 	
			}
		   	
		    
			
		   	
		   	
		   	
		   	
		   	
			
		   	tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Solicitud, "+ e.getMessage());
		}
	}

}
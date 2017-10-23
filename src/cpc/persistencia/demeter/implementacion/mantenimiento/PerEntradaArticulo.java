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
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.demeter.mantenimiento.MovimientoArticulo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerEntradaArticulo extends DaoGenerico<EntradaArticulo, Integer> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1177858679256231493L;

	public PerEntradaArticulo() {
		super(EntradaArticulo.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	
	public void guardarEntrada( EntradaArticulo entradaArticulo, Integer indice,ControlSede control)  throws ExcFiltroExcepcion{

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
	    	
		   	if (indice == null){
		   		control.incrementarControlEntradaArticulo();	
		     	entradaArticulo.setCodigoMovimiento(control.getPrefijoEntradaArticulo());
		     	entradaArticulo.setNumerocontrol(control.getControlEntradaArticulo());
		     	entradaArticulo.setEstado(true);
		     	em.saveOrUpdate(control);
		   		em.save(entradaArticulo);
		   	}else{
		   		em.update(entradaArticulo);
		   	}
		   	
		   	for (DetalleEntradaArticulo detalleEntradaArticulo : entradaArticulo.getDetalleEntradaArticulos()) {
		   		new PerArticuloAlmacenCantidad().actualizarCantidades(detalleEntradaArticulo.getAlmacenDestino(),detalleEntradaArticulo.getArticuloVenta(),detalleEntradaArticulo.getCantidad(),true,em); 	
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
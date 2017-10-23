package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.mantenimiento.DetalleEntradaArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleTransferenciaArticulo;
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.demeter.mantenimiento.TransferenciaArticulo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerTransferenciaArticulo extends DaoGenerico<TransferenciaArticulo, Integer> implements Serializable{

	
	public PerTransferenciaArticulo() {
		super(TransferenciaArticulo.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 785498345760256459L;

	public void guardarTransferencia( TransferenciaArticulo transferenciaArticulo, Integer indice,ControlSede control)  throws ExcFiltroExcepcion{

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null){	control.incrementarControlTransferenciaArticulo();	
	     transferenciaArticulo.setCodigoMovimiento(control.getPrefijoTransferenciaArticulo());
	     	transferenciaArticulo.setNumerocontrol(control.getControlTransferenciaArticulo());
	     	transferenciaArticulo.setEstado(true);
	     	em.saveOrUpdate(control);
		   		em.save(transferenciaArticulo);
		   	}else{
		   		em.update(transferenciaArticulo);
		   	}
		   	
		 	for (DetalleTransferenciaArticulo detalleTransferenciaArticulo : transferenciaArticulo.getDetalleTransferenciaArticulos()) {
		   		new PerArticuloAlmacenCantidad().actualizarCantidades(detalleTransferenciaArticulo.getAlmacenOrigen(),detalleTransferenciaArticulo.getArticuloVenta(),detalleTransferenciaArticulo.getCantidad(),false,em); 	
			}
		   	
		   	for (DetalleTransferenciaArticulo detalleTransferenciaArticulo : transferenciaArticulo.getDetalleTransferenciaArticulos()) {
		   		new PerArticuloAlmacenCantidad().actualizarCantidades(detalleTransferenciaArticulo.getAlmacenDestino(),detalleTransferenciaArticulo.getArticuloVenta(),detalleTransferenciaArticulo.getCantidad(),true,em); 	
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

package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoDeposito;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.SolicitudExoneracionContrato;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;

public class PerSolicitudExoneracionContrato extends DaoGenerico<SolicitudExoneracionContrato, Integer>{

	public PerSolicitudExoneracionContrato() {
		super(SolicitudExoneracionContrato.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3552892490855017910L;
	public void guardar(SolicitudExoneracionContrato objeto, Integer indice, ControlSede control) throws Exception{
	   	 if (indice == null)
	   		nuevo( objeto, control);
	   	 else
	   		 super.guardar(objeto, indice);
	}
	
	
	public void nuevo( SolicitudExoneracionContrato solicitud, ControlSede control) throws HibernateException,  ExcFiltroExcepcion, ExcEntradaInconsistente {
		solicitud.getContrato().setEstadoExoneracion(new PerEstadoExoneracionContrato().getPorExonerar());
		Transaction tx = null;
		Session em ;
		//Lista de Depositos Luego seran utilizados para hacer el Cierre Diario
	
		solicitud.setSede(control.getSede());
		solicitud.setFechaSolicitud(control.getFechaCierreFactura());
		try{
			solicitud.setNroControl(control.getProximaSerieExoneracioncontrato());
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
	
		    em.save(solicitud);
		    em.update(control);
		
			 
			em.flush();
		    tx.commit(); 
		}catch (Exception e) {
			e.printStackTrace();
		
			tx.rollback();
			throw new  ExcFiltroExcepcion(e);
		}
		}
}

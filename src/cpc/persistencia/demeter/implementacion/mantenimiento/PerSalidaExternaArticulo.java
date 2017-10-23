package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaInternaArticulo;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoDocumento;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerSalidaExternaArticulo extends
		DaoGenerico<SalidaExternaArticulo, Integer> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1177858679256231493L;

	public PerSalidaExternaArticulo() {
		super(SalidaExternaArticulo.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */

	public void guardarSalidaExterna(
			SalidaExternaArticulo salidaExternaArticulo, Integer indice,ControlSede control)
			throws ExcFiltroExcepcion {

		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
		
			if (indice == null) {
				control.incrementarControlSalidaArticulo();	
		     	salidaExternaArticulo.setCodigoMovimiento(control.getPrefijoSalidaArticulo());
		     	salidaExternaArticulo.setNumerocontrol(control.getControlSalidaArticulo());
		     	salidaExternaArticulo.setEstado(true);
		     	em.saveOrUpdate(control);
				em.save(salidaExternaArticulo);
				if (salidaExternaArticulo.getSolicitudServicioTecnico()!=null)
					em.update(salidaExternaArticulo.getSolicitudServicioTecnico());
			} else {
				em.update(salidaExternaArticulo);
				if (salidaExternaArticulo.getSolicitudServicioTecnico()!=null)
					em.update(salidaExternaArticulo.getSolicitudServicioTecnico());
			}

			for (DetalleSalidaExternaArticulo detalleSalidaExternaArticulo : salidaExternaArticulo
					.getDetalleSalidaExternaArticulos()) {
				new PerArticuloAlmacenCantidad().actualizarCantidades(
						detalleSalidaExternaArticulo.getAlmacenOrigen(),
						detalleSalidaExternaArticulo.getArticuloVenta(),
						detalleSalidaExternaArticulo.getCantidad(), false, em);
			}

			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error almacenando Solicitud, "
					+ e.getMessage());
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<SalidaExternaArticulo> getAllSolicitud(SolicitudServicioTecnico solicitudServicioTecnico){
		Transaction tx = null;
		
		List<SalidaExternaArticulo> criterio= null;
		
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(SalidaExternaArticulo.class)
				.add(Restrictions.eq("solicitudServicioTecnico",solicitudServicioTecnico))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
}
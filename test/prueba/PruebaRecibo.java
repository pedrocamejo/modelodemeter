package prueba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
 

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoDeposito;
import cpc.modelo.demeter.administrativo.sigesp.ExtracionDatosSigesp;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.negocio.demeter.administrativo.sigesp.NegocioExtracionDatosSigesp;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerDeposito;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerSolicitudMecanizado;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;

public class PruebaRecibo {
	
	public static void main(String[] args) throws Exception
	{

		/*
		NegocioExtracionDatosSigesp negocio = new NegocioExtracionDatosSigesp();

		TransactionSynchronizationManager.bindResource("obj", new AuditorUsuario());
		ControlSede control = new PerControlSede().buscarId(1);
		Transaction tx = null;
		Session em ;

		try{
			control.incrementarRecibo();
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();

		    em.update(control);
			em.flush();
		    tx.commit(); 
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error al almacenar Recibo");
		}
		System.out.print(control.getControlRecibo());
		*/
	}
	
}

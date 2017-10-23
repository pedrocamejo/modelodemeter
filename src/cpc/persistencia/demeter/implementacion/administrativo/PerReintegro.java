package cpc.persistencia.demeter.implementacion.administrativo;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoDeposito;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.Reintegro;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;

public class PerReintegro extends DaoGenerico<Reintegro, Integer>{

	public PerReintegro( ) {
		super(Reintegro.class);
		// TODO Auto-generated constructor stub
	}

	public void guardar(Reintegro reintegro, Integer id,ControlSede controlSede) throws Exception
	{
		if (reintegro.getId()== null)
		{
			nuevo(reintegro,controlSede);
		}
		else
			super.guardar(reintegro, reintegro.getId());
	}

	private void nuevo(Reintegro reintegro, ControlSede controlSede) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em ;
		em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		reintegro.setControl(controlSede.getProximoControlRecibo());
		for(Recibo recibo :reintegro.getRecibos())
		{
			em.update(recibo);
		}
		em.save(reintegro);
		if(reintegro.getNotaCredito() != null)	
			em.update(reintegro.getNotaCredito());
		em.update(controlSede);
		em.flush();
	    tx.commit(); 
	}
}

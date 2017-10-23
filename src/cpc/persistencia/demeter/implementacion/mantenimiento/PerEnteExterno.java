package cpc.persistencia.demeter.implementacion.mantenimiento;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
 

import cpc.modelo.demeter.mantenimiento.EnteExterno;
import cpc.modelo.demeter.mantenimiento.UsuarioMantenimiento;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerEnteExterno extends DaoGenerico<EnteExterno,Integer> {

	public PerEnteExterno() {
		super(EnteExterno.class);
		// TODO Auto-generated constructor stub
	}
	
	
	public UsuarioMantenimiento Pertenece(UsuarioMantenimiento usuario )
	{
		Session se = SessionDao.getInstance().getCurrentSession( );
		se.beginTransaction().begin();
	    return (UsuarioMantenimiento) se.get(UsuarioMantenimiento.class,usuario.getCedula());
	}


	
}
	


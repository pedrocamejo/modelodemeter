package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.mantenimiento.UsuarioMantenimiento;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerUsuarioMantenimiento extends DaoGenerico<UsuarioMantenimiento,String> {
 
	private static final long serialVersionUID = 1L;

	public PerUsuarioMantenimiento( ) {
		super(UsuarioMantenimiento.class);
		// TODO Auto-generated constructor stub
	}
	
	public List InicarSession(String cedula, String contrasena) {

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx =  em.beginTransaction();
	   List lista =  em.createCriteria(UsuarioMantenimiento.class).
						add(Restrictions.eq("cedula",cedula.trim())).
						add(Restrictions.eq("contrasena",contrasena.trim())).list();
	   tx.commit();
	   
	   return lista;
	   
	}

}

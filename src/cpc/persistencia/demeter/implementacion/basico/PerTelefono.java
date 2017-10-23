package cpc.persistencia.demeter.implementacion.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;




public class PerTelefono extends DaoGenerico<Telefono, Integer>{

	
	private static final long serialVersionUID = 3932352276510262423L;

	public PerTelefono() {
		super(Telefono.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Telefono> obtenerTelefonosDelProductor(Cliente cliente){
		Transaction tx = null;
		List<Telefono> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Telefono.class)
				.add(Restrictions.eq("cliente",cliente))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
		
	}
	
}

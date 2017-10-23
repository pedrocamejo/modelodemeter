package cpc.persistencia.demeter.implementacion.gestion;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.gestion.SabanaMecanizadoAgricola;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerSabanaMecanizadoAgricola extends
		DaoGenerico<SabanaMecanizadoAgricola, Integer> {

	private static final long serialVersionUID = 9163448561567355812L;

	public PerSabanaMecanizadoAgricola() {
		super(SabanaMecanizadoAgricola.class);
	}

	@SuppressWarnings("unchecked")
	public List<SabanaMecanizadoAgricola> getSabanaServicioMecanizacion(
			Date inicio, Date fin, UbicacionSector sector) {
		Transaction tx = null;
		List<SabanaMecanizadoAgricola> criterio = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try {
			Criteria cr = em.createCriteria(SabanaMecanizadoAgricola.class);
			cr.add(Restrictions.between("fechaSolicitud", inicio, fin));
			if (sector != null)
				cr.add(Restrictions.eq("idSector", sector.getId())).list();
			criterio = cr.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

}

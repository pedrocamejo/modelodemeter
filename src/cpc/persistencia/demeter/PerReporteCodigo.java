package cpc.persistencia.demeter;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.ReporteCodigo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerReporteCodigo extends DaoGenerico<ReporteCodigo,Long> 
{

	
	public PerReporteCodigo( ) {
		super(ReporteCodigo.class);
		// TODO Auto-generated constructor stub
	}

	public ReporteCodigo getReporteCodigo(String codigo) throws Exception {
		// TODO Auto-generated method stub
		ReporteCodigo reporte = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		
		try{
			reporte = (ReporteCodigo) em.createCriteria(ReporteCodigo.class)
				.add(Restrictions.eq("codigo", codigo))
				.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			throw new Exception("Error al Generar Consulta "+e.getMessage());
		}
		return reporte;
	}
	

}

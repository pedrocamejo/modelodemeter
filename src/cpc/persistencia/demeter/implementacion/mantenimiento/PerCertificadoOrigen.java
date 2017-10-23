package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.mantenimiento.CertificadoOrigen;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerCertificadoOrigen extends DaoGenerico<CertificadoOrigen,String> {

	public PerCertificadoOrigen( ) {
		super(CertificadoOrigen.class);
		// TODO Auto-generated constructor stub
	}

	public Long contarCertificado(Modelo modelo) throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
		Long numero;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			numero = (Long) em.createQuery("select count(c) " +
								 " from CertificadoOrigen c " +
								 " where c.maquinariaExterna.tipo.modelo=:modelo").
								 setParameter("modelo",modelo).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return numero;
		
	}

	
	
	
}

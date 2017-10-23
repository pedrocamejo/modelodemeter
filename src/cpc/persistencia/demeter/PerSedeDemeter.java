package cpc.persistencia.demeter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.sincronizacion.Exportar;
import cpc.modelo.demeter.sincronizacion.SedeDemeter;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


@XmlRootElement
public class PerSedeDemeter extends DaoGenerico<SedeDemeter,String>
{

	public PerSedeDemeter( ) {
		super(SedeDemeter.class);
		// TODO Auto-generated constructor stub
	}

	
	public String getid_toString(SedeDemeter obj) {
		// TODO Auto-generated method stub
		return obj.getIdSede();
	}

	
	public Integer getid(SedeDemeter objeto) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void sincronizarObjecto(SedeDemeter obj_sede,
			SedeDemeter obj_export, Exportar exportar,
			 cpc.modelo.demeter.sincronizacion.Importar importar) throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	public List<SedeDemeter> getsedesdistintas(SedeDemeter sedeactual) throws ExcFiltroExcepcion{
		List<SedeDemeter> sedes = new ArrayList<SedeDemeter>();
		Session em = SessionDao.getInstance().getCurrentSession();
		
		Transaction tx = null;
		try {
			tx = em.beginTransaction();

			sedes = em.createCriteria(SedeDemeter.class).
					 add(Restrictions.not(Restrictions.eq("idSede", sedeactual.getIdSede()))).list();

			System.out.println(sedes.size());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return sedes ;
	}
	
	@SuppressWarnings("unchecked")
	public SedeDemeter getSede(String idsede) throws ExcFiltroExcepcion{
		SedeDemeter sede = new SedeDemeter();
		Session em = SessionDao.getInstance().getCurrentSession();
		
		Transaction tx = null;
		try {
			tx = em.beginTransaction();

			sede= (SedeDemeter) em.get(SedeDemeter.class, idsede);		
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return sede ;
	}
	
	@SuppressWarnings("unchecked")
	public SedeDemeter getSede(String idsede,Transaction tx) throws ExcFiltroExcepcion{
		SedeDemeter sede = new SedeDemeter();
		Session em = SessionDao.getInstance().getCurrentSession();
		
		
		try {
		
			System.out.println("-->>"+idsede);
			sede= (SedeDemeter) em.get(SedeDemeter.class, idsede);		
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return sede ;
	}
}

package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cpc.modelo.demeter.mantenimiento.DetalleGarantina;
import cpc.modelo.demeter.mantenimiento.MaquinariaExterna;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerDetalleGarantia extends DaoGenerico<DetalleGarantina,Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerDetalleGarantia( ) {
		super(DetalleGarantina.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<DetalleGarantina> detalleGarantia_not_in_Maquinaria(MaquinariaExterna maquinaria)//cosas q no se ha hecho 
	{
		List<DetalleGarantina> entities;
	    Session em =SessionDao.getInstance().getCurrentSession();
        Transaction tx = null;
       	tx = em.beginTransaction();
       	Query q = em.createQuery("select dt from TipoGarantia t join t.detallesGarantia dt " +
				" where dt not in (select o.detalleGarantia from OrdenGarantia o " +
				" where o.maquinaria = :maquinaria and estatus != 2 ) and t.modelo = :modelo");
       	q.setParameter("maquinaria",maquinaria);
       	q.setParameter("modelo",maquinaria.getTipo().getModelo());
        entities = q.list();
        tx.commit();
	    return entities;
	}
	
}

 
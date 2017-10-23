package cpc.persistencia.demeter.implementacion.administrativo;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.AuxiliarAsientoIngreso;
import cpc.modelo.demeter.administrativo.AuxiliarAsientoIngresoPK;
import cpc.modelo.demeter.administrativo.CierreDiario;
import cpc.modelo.demeter.administrativo.CierreDiarioAsiento;
import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;


public class PerAuxiliarAsientoIngreso extends DaoGenerico<AuxiliarAsientoIngreso, AuxiliarAsientoIngresoPK>{

	private static final long serialVersionUID = 2332125844601012743L;

	public PerAuxiliarAsientoIngreso() {
		super(AuxiliarAsientoIngreso.class);
	}	
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getDetalleIngresos(CierreDiario cierre) {
		Transaction tx = null;
		List<AuxiliarAsientoIngreso> criterio= null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(AuxiliarAsientoIngreso.class)
				.add(Restrictions.eq("fecha",cierre.getFecha()))
				.list();
			tx.commit();
			for (AuxiliarAsientoIngreso item :criterio){
				if (item != null){
					renglonAsiento = new CierreDiarioAsiento();
					renglonAsiento.setCierreDiario(cierre);
					if (item.getNombre()!= null)
						renglonAsiento.setRenglon("INGRESO POR "+item.getNombre());
					else
						renglonAsiento.setRenglon("INGRESO DESCONOCIDO "+item.getNombre());
					renglonAsiento.setFecha(cierre.getFecha());
					try{
						renglonAsiento.setCuenta(new PerCuentaContable().buscarId(item.getCuentaContable()));
					}catch (Exception e) {
						renglonAsiento.setCuenta(new CuentaContable());
					}
					if (!item.getTipo()){ //no es Nota de credito
						renglonAsiento.setDebe(0.0);
						renglonAsiento.setHaber(item.getMonto());
					}else{
						renglonAsiento.setDebe(item.getMonto());
						renglonAsiento.setHaber(0.0);
					}
					asiento.add(renglonAsiento);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return asiento;
	}
	
	
}

package cpc.persistencia.demeter.implementacion.administrativo;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.AuxiliarAsientoImpuesto;
import cpc.modelo.demeter.administrativo.AuxiliarAsientoImpuestoPK;
import cpc.modelo.demeter.administrativo.CierreDiario;
import cpc.modelo.demeter.administrativo.CierreDiarioAsiento;
import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;

public class PerAuxiliarAsientoImpuesto extends DaoGenerico<AuxiliarAsientoImpuesto, AuxiliarAsientoImpuestoPK>{

	private static final long serialVersionUID = 2332125844601012743L;

	public PerAuxiliarAsientoImpuesto() {
		super(AuxiliarAsientoImpuesto.class);
	}	
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getDetalleIngresos(CierreDiario cierre, String cuenta) {
		Transaction tx = null;
		List<AuxiliarAsientoImpuesto> criterio= null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(AuxiliarAsientoImpuesto.class)
				.add(Restrictions.eq("fecha",cierre.getFecha()))
				.add(Restrictions.gt("monto",0.0))
				.list();
			tx.commit();
			for (AuxiliarAsientoImpuesto item :criterio){
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setRenglon("IMPUESTO "+item.getNombre());
				renglonAsiento.setFecha(cierre.getFecha());
				try{
					renglonAsiento.setCuenta(new PerCuentaContable().buscarId(cuenta));
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
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return asiento;
	}
	
	
}

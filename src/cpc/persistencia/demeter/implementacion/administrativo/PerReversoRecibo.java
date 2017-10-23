package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.sun.org.apache.bcel.internal.generic.FMUL;

import cpc.modelo.demeter.administrativo.CierreDiario;
import cpc.modelo.demeter.administrativo.CierreDiarioReversoRecibo;
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.administrativo.ReversoFormaPago;
import cpc.modelo.demeter.administrativo.ReversoRecibo;
import cpc.negocio.demeter.administrativo.NegocioRecibo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerReversoRecibo extends DaoGenerico<ReversoRecibo, Integer> {

	public PerReversoRecibo() {
		super(ReversoRecibo.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2197886123712694378L;

	/**
	 * @throws Exception 
	 * 
	 */
	
	public void guardar(ReversoRecibo reversoRecibo) throws Exception{
		if (reversoRecibo.getId()==null)
			nuevoReverso(reversoRecibo);
		else 
			super.guardar(reversoRecibo, reversoRecibo.getId());
	}

	private void nuevoReverso(ReversoRecibo reversoRecibo) throws Exception {
	List<Criterion> a = new ArrayList<Criterion>();
	Recibo reciboafectado = reversoRecibo.getReciboAfectado();
	a.add(Restrictions.eq("fecha", reciboafectado.getFecha()));
	
	EstadoDocumentoFiscal activo = new PerEstadoDocumentoFiscal().getActivo();
	
	Session em =SessionDao.getInstance().getCurrentSession();
	
	Transaction tx = null;
	tx=em.beginTransaction();
	try {
		anularRecibo(reciboafectado, tx, activo, em);
		afectarCierre(reciboafectado, tx, reversoRecibo, em);
		
		em.saveOrUpdate(reversoRecibo);
		
		
		tx.commit();
		
	} catch (ExcFiltroExcepcion e) {
		// TODO Auto-generated catch block
		
		e.printStackTrace();
	}
	
		
	} 
	
	public void anularRecibo (Recibo reciboafectado , Transaction tx, EstadoDocumentoFiscal activo, Session em) throws Exception{

		//Validar facturas si hay una activa Error :-D
			
				for(ReciboDocumentoFiscal documentoFiscal :reciboafectado.getDocumentosFiscales())
				{
					if(documentoFiscal.getDocumentoFiscal().getEstado().equals(activo))
					{
						 throw new  Exception("Existen Documentos Fiscales Activos Asociados al Recibo ");
					}
				}
				
				//desactivo las forma de pago 
				for(FormaPago item: reciboafectado.getFormaspago())
				{
			    	item.setMonto(new Double(0));
			    	item.setEstado(false);
			    }
				
				reciboafectado.setSaldo(0.0);
				reciboafectado.setMonto(0.0);
				reciboafectado.setAnulado(true);
				
				

			    for(FormaPago item: reciboafectado.getFormaspago()){
			    	em.update(item);
			    }
				em.update(reciboafectado);
	}
	
	public void afectarCierre(Recibo reciboafectado , Transaction tx,ReversoRecibo reversoRecibo, Session em){
		CierreDiario cierre=(CierreDiario) em.createCriteria(CierreDiario.class).add(Restrictions.eq("fecha", reciboafectado.getFecha())).uniqueResult();
		if (cierre!=null){
		CierreDiarioReversoRecibo cierrereverso = new CierreDiarioReversoRecibo();
		cierrereverso.setCierreDiario(cierre);
		cierrereverso.setReversoRecibo(reversoRecibo);
		
		cierre.getReversos().add(cierrereverso);
		em.update(cierre);
		}
	}

	public void Anular(ReversoRecibo reversoRecibo) {

		Session em =SessionDao.getInstance().getCurrentSession();
		
		Transaction tx = null;
		tx =em.beginTransaction();
		try {
			
			desafectarcierre(reversoRecibo,em,tx);
			activarrecibo(reversoRecibo,em,tx);
			reversoRecibo.setAnulado(true);
			reversoRecibo.setMontoReversado(new Double(0));
			em.update(reversoRecibo);
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
		System.out.println(e.getMessage());
		}
		
		
	}

	private void activarrecibo(ReversoRecibo reversoRecibo, Session em,
			Transaction tx) {
		 Recibo recibo =reversoRecibo.getReciboAfectado();
		 recibo.setMonto(reversoRecibo.getMontoReversado());
		 recibo.setSaldo(reversoRecibo.getMontoReversado());
		 recibo.setAnulado(false);
		 
		 for (FormaPago fpago : recibo.getFormaspago()) {
			
			 //doble ciclo
			 for (ReversoFormaPago reversoFormaPago : reversoRecibo.getReversoFormaPagos())
			 {
				 //si es la misma forma de pago la actualizamos a su forma previa
				 if  (reversoFormaPago.getFormaPago().getId().equals(fpago.getId()))
				 {
					 fpago.setMonto(reversoFormaPago.getMonto());
					 fpago.setEstado(true);
					 
				 }
				 reversoFormaPago.setEstado(false);
				 reversoFormaPago.setMonto(new Double(0));
			 }
			 
			 
			 em.update(recibo);
			 
		}
		 
		 
		
		// TODO Auto-generated method stub
		
	}

	private void desafectarcierre(ReversoRecibo reversoRecibo, Session em,
			Transaction tx) {
		// TODO Auto-generated method stub
		
	}
}

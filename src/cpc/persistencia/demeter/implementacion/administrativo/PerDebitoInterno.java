package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DebitoInterno;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;


public class PerDebitoInterno extends DaoGenerico<DebitoInterno, Integer>{


	private static final long serialVersionUID = -1530515060555158298L;

	public PerDebitoInterno() {
		super(DebitoInterno.class);
	}
	
	public DebitoInterno getDatos(DebitoInterno entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		DebitoInterno docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (DebitoInterno) em.load(DebitoInterno.class, entrada.getId());

			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public List<DebitoInterno> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<DebitoInterno> facturas = null;
		try{
			facturas = em.createQuery("SELECT  d FROM DebitoInterno d  order by d.id desc").list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	
	public void anular(DebitoInterno docu){
		Transaction tx = null;
		Session em ;
		try{
			DocumentoFiscal factura = docu.getDocumento();
			factura.reversarSaldo(docu.getMonto());
			factura.setCancelada(false);
			docu.setMonto(0);
			docu.setAnulado(true);
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			em.update(docu);
			em.update(factura);
		    em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
	}
	
	public void guardar(DebitoInterno objeto, Integer indice, ControlSede sede)  throws Exception{
	   	 if (indice == null)
	   		nuevo( objeto, sede);
	   	 else
	   		 super.guardar(objeto, indice);
	}
	
	
	
	public void nuevo( DebitoInterno docu, ControlSede control)  throws ExcFiltroExcepcion {
		Transaction tx = null;
		Session em ;
		try{
			control.incrementarDebitoInterno();
			docu.setControl(Formateador.rellenarNumero(control.getNroDebitoInterno(),"0000"));
			docu.setFecha(control.getFechaCierreFactura());
			DocumentoFiscal factura = docu.getDocumento();
			factura.actualizarSaldo(docu.getMonto());
			if (factura.getMontoSaldo()>0)
				factura.setCancelada(false);
			else
				factura.setCancelada(true);
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			em.save(docu);
			em.update(control);
			em.update(factura);
		    em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error al generar debito Interno");
		}
	   	
   }
	

}





















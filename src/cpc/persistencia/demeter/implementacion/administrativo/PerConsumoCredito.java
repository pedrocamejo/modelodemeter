package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cpc.modelo.demeter.administrativo.ConsumoCredito;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.NotaCredito;
import cpc.modelo.demeter.basico.TipoConsumo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerConsumoCredito extends DaoGenerico<ConsumoCredito, Integer> {
	private static final long serialVersionUID = -1530515060555158298L;

	public PerConsumoCredito() {
		super(ConsumoCredito.class);
	}

	public ConsumoCredito getDatos(ConsumoCredito entrada) {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		ConsumoCredito docu = null;
		tx = em.beginTransaction();
		try {
			docu = (ConsumoCredito) em.load(ConsumoCredito.class,
					entrada.getId());

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return docu;
	}

	@SuppressWarnings("unchecked")
	public List<ConsumoCredito> getAll() throws ExcFiltroExcepcion {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<ConsumoCredito> documentos = null;
		try {
			documentos = em.createQuery(
					"SELECT  d FROM ConsumoCredito d  order by d.id desc")
					.list();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		return documentos;
	}

	/*
	 * public void anular(ConsumoCredito docu){ Transaction tx = null; Session
	 * em ; try{ DocumentoFiscal factura = docu.getDocumento();
	 * System.out.println(docu.getMonto());
	 * factura.reversarSaldo(docu.getMonto()); factura.setCancelada(false);
	 * docu.setMonto(0); docu.setAnulado(true); em
	 * =SessionDao.getInstance().getCurrentSession(); tx =
	 * em.beginTransaction(); em.update(docu); em.update(factura); em.flush();
	 * tx.commit(); }catch (Exception e) { e.printStackTrace(); tx.rollback(); }
	 * }
	 */

	public void guardar(ConsumoCredito objeto, Integer indice, ControlSede sede)
			throws Exception {
		if (indice == null)
			nuevo(objeto, sede);
		else
			super.guardar(objeto, indice);
	}

	public void anular(ConsumoCredito objeto) throws Exception {
		Transaction tx = null;
		Session em;
		NotaCredito credito = objeto.getNota();
		double monto = objeto.getMonto();
		double aux = 00.0;
		try {
			aux = credito.getMontoSaldo();
			credito.setMontoSaldo((aux - monto));
			if (objeto.getDocumento() != null) {
				aux = objeto.getDocumento().getMontoSaldo();
				objeto.getDocumento().setMontoSaldo((aux + monto));
			}
			objeto.setAnulado(true);

			em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			em.update(objeto);
			em.update(credito);
			if (objeto.getDocumento() != null) {
				em.update(objeto.getDocumento());
			}
			em.flush();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error al Guardar Consumo credito");
		}

	}

	public void nuevo(ConsumoCredito docu, ControlSede control)
			throws ExcFiltroExcepcion {
		Transaction tx = null;
		Session em;
		try {
			docu.setFecha(control.getFechaCierreFactura());
			NotaCredito nota = docu.getNota();
			nota.actualizarSaldo(-1 * docu.getMonto());
			if (nota.getMontoSaldo() < 0)
				nota.setCancelada(false);
			else
				nota.setCancelada(true);

			DocumentoFiscal factura = docu.getDocumento();
			if (factura != null) {
				factura.actualizarSaldo(docu.getMonto());
				if (factura.getMontoSaldo() > 0)
					factura.setCancelada(false);
				else
					factura.setCancelada(true);
			}
			em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			em.save(docu);
			em.update(nota);
			if (factura != null)
				em.update(factura);
			em.flush();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error al Guardar Consumo credito");
		}

	}
}

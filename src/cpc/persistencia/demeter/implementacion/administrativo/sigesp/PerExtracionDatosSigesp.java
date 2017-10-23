package cpc.persistencia.demeter.implementacion.administrativo.sigesp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.hql.ast.tree.RestrictableStatement;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import util.Seguridad;
import cpc.modelo.demeter.administrativo.sigesp.ExtracionDatosSigesp;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerExtracionDatosSigesp  extends DaoGenerico<ExtracionDatosSigesp, Integer> {

	public PerExtracionDatosSigesp( ) {
		super(ExtracionDatosSigesp.class);
		// TODO Auto-generated constructor stub
	}

	public void guardar(ExtracionDatosSigesp modelo, Document documento) throws Exception {
		// TODO Auto-generated method stub
	
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();

			File archivo = new File(modelo.getFilename());
			FileOutputStream out = new FileOutputStream(archivo);
			
			XMLOutputter xmxl = new XMLOutputter();
			xmxl.output(documento,out);
			
			modelo.setTamano(Seguridad.getMD5(xmxl.outputString(documento)));
			
			em.saveOrUpdate(modelo);
			archivo.createNewFile();
			
			out.flush();
			em.flush();
			tx.commit();
		} catch (ConstraintViolationException e) {
			throw new Exception(
					"Error al intentar Almacenar. Existen valores en el Registro actual que no puede repetirse.",
					e.getCause());

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new Exception("Error al intentar Alamcenar. "+ e.getMessage(), e.getCause());
		}
	}

	public ExtracionDatosSigesp buscar(Integer ano, Integer mes) throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		ExtracionDatosSigesp modelo = null;
		
		try {
			tx = em.beginTransaction();
			modelo = (ExtracionDatosSigesp) em.createCriteria(ExtracionDatosSigesp.class)
						.add(Restrictions.eq("ano",ano))
						.add(Restrictions.eq("mes",mes)).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return modelo;
	}
	
}

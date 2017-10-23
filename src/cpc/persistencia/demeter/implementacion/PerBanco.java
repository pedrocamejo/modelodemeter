package cpc.persistencia.demeter.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.indice.BancoPK;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;

public class PerBanco extends DaoGenerico<Banco, BancoPK>{

	private static final long serialVersionUID = -903194176174411624L;

	public PerBanco() {
		super(Banco.class);
	}
	
	public void guardar(Banco objeto, BancoPK indice)  throws Exception{
	   	 if (indice == null)
	   		nuevo( objeto);
	   	 else
	   		 super.guardar(objeto, indice);
	}
	
	public void nuevo( Banco banco) throws ExcFiltroExcepcion {
		Transaction tx = null;
		Session em ;
		try{
			BancoPK indice = buscalUltimo();
			banco.setCodigoEmpresa(indice.getCodigoEmpresa());
			banco.setId(indice.getId());
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			em.save(banco);
		    em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion();
		}
	}
	
	public BancoPK buscalUltimo() {
		Session em =SessionDao.getInstance().getCurrentSession();
		BancoPK idBanco = null;
		Transaction tx = em.beginTransaction();
		Object valor =  em.createQuery("SELECT  max(id) FROM Banco").uniqueResult();
		Integer valorStr;
		if (valor == null){
			valorStr = 1; 
		}
		else { 
			valorStr = Integer.parseInt((String) valor) + 1;
		}
		idBanco = new BancoPK(Formateador.rellenarNumero(valorStr, "000"));
		tx.commit();
		return idBanco;
	}

	@SuppressWarnings("unchecked")
	public List<Banco> getSoloBancos(){
		Transaction tx = null;
		
		List<Banco> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Banco.class)
				.add(Restrictions.ne("id","---"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public Banco getBanco(String id)
	{
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		Banco banco = null;
		try{
			banco = (Banco)em.createQuery("select b from Banco b where b.id.id=:identificador").setParameter("identificador",id).uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return banco;
	}
}
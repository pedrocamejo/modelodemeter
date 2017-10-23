package cpc.persistencia.demeter.implementacion.basico;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import cpc.modelo.demeter.basico.FuncionTrabajador;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerFuncionTrabajador;

public class PerTrabajador extends DaoGenerico<Trabajador, Integer>{


	/**
	 * 
	 */
	private static final long serialVersionUID = -190262934556626163L;

	public PerTrabajador() {
		super(Trabajador.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Trabajador> getTodos (){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<Trabajador> trabajadores = null;
		tx = em.beginTransaction();
		try{
			trabajadores = em.createCriteria(Trabajador.class)
				.addOrder(Order.asc("nroCedula"))
				.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return trabajadores;
	}
	
	public List<Trabajador> getOperadores(){
		PerFuncionTrabajador persistencia = new PerFuncionTrabajador();
		FuncionTrabajador tipo = new PerFuncionTrabajador().getTipoOperador();
		tipo = persistencia.getDatos(tipo);
		return tipo.getTrabajadores();	
	}
	
	public List<Trabajador> getTecnicosCampo(){
		PerFuncionTrabajador persistencia = new PerFuncionTrabajador();
		FuncionTrabajador tipo = persistencia.getTipoTecnico();
		tipo = persistencia.getDatos(tipo);
		return tipo.getTrabajadores();	
	}
	
	public List<Trabajador> getComunitaria(){
		PerFuncionTrabajador persistencia = new PerFuncionTrabajador();
		FuncionTrabajador tipo = persistencia.getTipoComunitaria();
		tipo = persistencia.getDatos(tipo);
		return tipo.getTrabajadores();	
	}
	
	 
	public List<FuncionTrabajador> getDetalle(Trabajador entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		Trabajador docu = null;
     	tx = em.beginTransaction();
     	List<FuncionTrabajador> funciones = new ArrayList<FuncionTrabajador>(); 
     	try{
			docu = (Trabajador) em.load(Trabajador.class, entrada.getId());
			for (FuncionTrabajador item: docu.getFunciones()){
				funciones.add(item);
			}
			
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return funciones;
	}
	
	public List<Trabajador> getConductores(){
		PerFuncionTrabajador persistencia = new PerFuncionTrabajador();
		FuncionTrabajador tipo = persistencia.getConductor();
		
		tipo = persistencia.getDatos(tipo);
		return tipo.getTrabajadores();	
	}
}

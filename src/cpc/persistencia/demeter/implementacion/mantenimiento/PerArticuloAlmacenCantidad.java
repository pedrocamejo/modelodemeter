package cpc.persistencia.demeter.implementacion.mantenimiento;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;

import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidadPK;
import cpc.modelo.demeter.mantenimiento.ConsumibleEquivalente;
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerArticuloAlmacenCantidad  extends DaoGenerico<ArticuloAlmacenCantidad, ArticuloAlmacenCantidadPK>{

	public PerArticuloAlmacenCantidad() {
		super(ArticuloAlmacenCantidad.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1241631664039452251L;

	public void actualizarCantidades(Almacen almacen,ArticuloVenta articuloVenta, Double cantidad , boolean incrementa , Session em) throws ExcFiltroExcepcion{
		ArticuloAlmacenCantidadPK id = new ArticuloAlmacenCantidadPK(almacen.getId(), articuloVenta.getId()); 
		ArticuloAlmacenCantidad articuloAlmacenCantidad = (cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad) em.createCriteria(ArticuloAlmacenCantidad.class).add(Restrictions.eq("id", id)).uniqueResult();
		if (articuloAlmacenCantidad ==null){
			
			if (cantidad<0) 	throw new ExcFiltroExcepcion("El Articulo la cantidad de la salida no existe en el almacen");
			if (!incrementa) 	throw new ExcFiltroExcepcion("El Articulo no existe en el almacen");
			ArticuloAlmacenCantidad	nuevo = new ArticuloAlmacenCantidad();
			nuevo.setId(id);
			nuevo.setCantidad(cantidad);
			em.save(nuevo);
		} 		else {
			Double cantidadanterior = articuloAlmacenCantidad.getCantidad();
			if (incrementa){
			double cantidadnueva = cantidadanterior + cantidad;
			articuloAlmacenCantidad.setCantidad(cantidadnueva);
			em.update(articuloAlmacenCantidad);	
			}else {
				double cantidadnueva = cantidadanterior - cantidad;
				if (cantidadnueva<0) 	throw new ExcFiltroExcepcion("El Articulo la cantidad de la salida no puede ser mayor a la disponible");
				articuloAlmacenCantidad.setCantidad(cantidadnueva);
				em.update(articuloAlmacenCantidad);		
			} 
			
		}
		
		
		
	}

	public ArticuloAlmacenCantidad VerificarExistencia(ArticuloVenta articuloVenta,
			Almacen almacenOrigen) {
		

		Transaction tx = null;
		
		ArticuloAlmacenCantidad criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = (ArticuloAlmacenCantidad) em.createCriteria(ArticuloAlmacenCantidad.class)
				.add(Restrictions.eq("almacen",almacenOrigen))
				.add(Restrictions.eq("articuloVenta",articuloVenta)).uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
		
		
		
	}

	@SuppressWarnings("unchecked")
	
	public List<ArticuloAlmacenCantidad> getEnExistencia() {
		

		Transaction tx = null;
		
		 List<ArticuloAlmacenCantidad> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio =  em.createCriteria(ArticuloAlmacenCantidad.class)
				.add(Restrictions.not(Restrictions.le("cantidad",0.0)))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
		
		
		
	}
	
	
	
}

package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import cpc.ares.persistencia.PerUsuario;
import cpc.modelo.demeter.mantenimiento.EnteExterno;
import cpc.modelo.demeter.mantenimiento.UsuarioMantenimiento;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEnteExterno;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioEnteExterno implements Serializable{
	
	private static final long serialVersionUID = -7144934362319560530L;
	
	
	private static NegocioEnteExterno		 	negocio;
	private PerEnteExterno						perEnteExterno;
	private List<EnteExterno>					entes;
 
	
	
	private NegocioEnteExterno(){
		perEnteExterno = new PerEnteExterno();
	}

	public  static synchronized NegocioEnteExterno getInstance() {
		if (negocio == null)
			negocio = new NegocioEnteExterno();
		return negocio;
	}

	public List<EnteExterno> getTodos( ) throws ExcFiltroExcepcion{
		List<EnteExterno> lista = null;
		try {
			lista = perEnteExterno.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static NegocioEnteExterno getNegocio() {
		return negocio;
	}
		
	public void guardar(EnteExterno ente ) throws Exception
	{
		// valido que todo este bien si no Genero una Exep y ps Guardo en la capa de Persistencia :D 
	
		if(ente.getDescripcion().trim().length() < 4)
		{
		 		throw new Exception("La Descripcion Debe Tener mas de 4 Caracteres ");
		}
		perEnteExterno.guardar(ente,ente.getId());
		
	}
	
	public void Eliminar(EnteExterno enteExterno)
	{
		try {
			perEnteExterno.borrar(enteExterno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	 
	public UsuarioMantenimiento Perteneceentidad(UsuarioMantenimiento usuario)
	{
		return perEnteExterno.Pertenece(usuario);
	}
	public void eliminarUsuarioMantenimiento(UsuarioMantenimiento usuario) throws Exception
	{
		 
	}
	
}

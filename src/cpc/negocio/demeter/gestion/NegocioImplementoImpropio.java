package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.gestion.EstadoMaquinariaImpropia;
import cpc.modelo.demeter.gestion.ImplementoImpropio;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.sigesp.basico.Categoria;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.Tipo;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoMaquinariaImpropia;
import cpc.persistencia.demeter.implementacion.gestion.PerImplementoImpropio;
import cpc.persistencia.demeter.implementacion.gestion.PerImplementoUnidad;
import cpc.persistencia.sigesp.implementacion.PerCategoria;
import cpc.persistencia.sigesp.implementacion.PerMarca;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cpc.persistencia.sigesp.implementacion.PerTipo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioImplementoImpropio implements Serializable{

	private static NegocioImplementoImpropio 	negocio;
	private PerImplementoImpropio 			perImplementoImpropio;
	private PerMarca 						perMarca;
	private PerCategoria   		  			perCategoria;
	private PerModelo 						perModelo;
	private PerTipo							perTipo;
	private PerImplementoUnidad 			perImplementoUnidad;
	private PerEstadoMaquinariaImpropia 	perEstadoMaquinariaImpropia;
	
	private NegocioImplementoImpropio(){
		perMarca = new PerMarca();
		perCategoria = new PerCategoria();
		perModelo = new PerModelo();
		perTipo = new PerTipo();
		perImplementoImpropio = new PerImplementoImpropio(); 
		perImplementoUnidad = new PerImplementoUnidad();
		perEstadoMaquinariaImpropia = new PerEstadoMaquinariaImpropia();
	}

	public  static synchronized NegocioImplementoImpropio getInstance() {
		if (negocio == null)
			negocio = new NegocioImplementoImpropio();
		return negocio;
	}

	public List<ImplementoImpropio> getTodos() throws ExcFiltroExcepcion{
		List  lista = null;
		try {
			lista = perImplementoImpropio.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public List<Marca> marcas() throws ExcFiltroExcepcion
	{
		return perMarca.getAll();
	}

	public List<Modelo> modelos(Marca marca) throws ExcFiltroExcepcion
	{	
		return perModelo.getPorMarca(marca);
	}

	
	public List<Categoria> categorias() throws ExcFiltroExcepcion
	{
		return perCategoria.getAll();
	}

	public List<Tipo> tipos(Categoria categoria ) throws ExcFiltroExcepcion
	{
		return perTipo.getPorCategoria(categoria);
	}
	
	public ImplementoImpropio getImplemento( String serial )
	{
		return perImplementoImpropio.getImplemento(serial.toUpperCase().trim());
		
	}
	public ImplementoImpropio getimplementoOtroSerial( String serial )
	{
		return perImplementoImpropio.getImplementoSerial(serial.toUpperCase().trim());
		
	}
	
	public void guardar(ImplementoImpropio implemento) throws Exception
	{
		if(implemento.getId() == null )
		{
			// nueva 
			EstadoMaquinariaImpropia estado = perEstadoMaquinariaImpropia.getIngresado();
			String  idSede= (String) SpringUtil.getBean("idsede");
			String  idEmpresa= (String) SpringUtil.getBean("idEmpresa");
			implemento.setSede(idSede);
			implemento.setEstado(estado);
			
			//La Maquinaria Unidad Guardar 
			
			ImplementoUnidad unidad = implemento.getImplementoUnidad();
			unidad.setImplementoImpropio(null);
			perImplementoUnidad.guardar(unidad,unidad.getId());
			unidad.setImplementoImpropio(implemento);
			perImplementoImpropio.guardar(implemento);
			perImplementoUnidad.guardar(unidad,unidad.getId());
			
		}
		else
		{
			//actualizar 
			perImplementoImpropio.guardar(implemento);
		}
	}
	
	public void eliminar(ImplementoImpropio implemento) throws Exception
	{
		perImplementoImpropio.borrar(implemento);
	}

	
	public void CambiarEstado(ImplementoImpropio implemento) throws Exception
	{
		/*	Verificado 
		 * con el estado verificado se va a crear un objeto de tipo MaquinariaUnidad
		 * donde se va a establecer que esa maquinaria es de esta sede y se puede usar 
		 * en las ordenes  puesto que operativio es true 
		 
		 * Migrado 
		 * convertida a bien nacional lo cual quiere decir que si hay un objeto MaquinariaUnidad
		 * este va a tener el activo y la maquinaria unidad :-D 

		 * Desactivado 
		 * si hay un objeto MaquinariaUnidad se pone en Operativo = false 
		 
		 * Desincorporado 
		 * si hay un objeto MaquinariaUnidad se pone en Operativo = false 
		 */

		if (implemento.getEstado().getId().equals(EstadoMaquinariaImpropia.VERIFICADO) || implemento.getEstado().getId().equals(EstadoMaquinariaImpropia.MIGRADO))
		{
			implemento.getImplementoUnidad().setOperativo(true);
			perImplementoImpropio.guardar(implemento);
			perImplementoUnidad.guardar(implemento.getImplementoUnidad(),implemento.getImplementoUnidad().getId());
		}
		else if (implemento.getEstado().getId().equals(EstadoMaquinariaImpropia.DESACTIVADO) || implemento.getEstado().getId().equals(EstadoMaquinariaImpropia.DESINCORPORADO))
		{
			implemento.getImplementoUnidad().setOperativo(false);
			perImplementoImpropio.guardar(implemento);
			perImplementoUnidad.guardar(implemento.getImplementoUnidad(),implemento.getImplementoUnidad().getId());
		}
	}
	
	
	public List<EstadoMaquinariaImpropia > getEstados ()
	{		
		return perImplementoImpropio.getEstados();
	}


	public EstadoMaquinariaImpropia getVerificado() {
		return perEstadoMaquinariaImpropia.getVerificado();
	}
	


	public EstadoMaquinariaImpropia getMigrado() {
		return perEstadoMaquinariaImpropia.getMigrado();
	}

	public EstadoMaquinariaImpropia getIngresado()
	{
		return perEstadoMaquinariaImpropia.getIngresado();
	}

	public EstadoMaquinariaImpropia getDesactivado() {
		return perEstadoMaquinariaImpropia.getDesactivado();
	}

	
	public EstadoMaquinariaImpropia getDesincorporado() {
		return perEstadoMaquinariaImpropia.getDesincorporado();
	}

	
}

package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.gestion.EstadoMaquinariaImpropia;
import cpc.modelo.demeter.gestion.MaquinariaImpropia;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.sigesp.basico.Categoria;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.Tipo;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoMaquinariaImpropia;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaImpropia;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaUnidad;
import cpc.persistencia.sigesp.implementacion.PerCategoria;
import cpc.persistencia.sigesp.implementacion.PerMarca;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cpc.persistencia.sigesp.implementacion.PerTipo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioMaquinariaImpropia implements Serializable{

	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioMaquinariaImpropia 	negocio;
	
	private PerMaquinariaImpropia perMaquinariaImpropia;
	private PerMarca 				perMarca;
	private PerCategoria   		  perCategoria;
	private PerModelo 			perModelo;
	private PerTipo				perTipo;
	private PerMaquinariaUnidad 	perMaquinariaUnidad;
	private PerEstadoMaquinariaImpropia perEstadoMaquinariaImpropia;
	
	private NegocioMaquinariaImpropia(){
		perMarca = new PerMarca();
		perCategoria = new PerCategoria();
		perModelo = new PerModelo();
		perTipo = new PerTipo();
		perMaquinariaImpropia = new PerMaquinariaImpropia(); 
		perMaquinariaUnidad = new PerMaquinariaUnidad();
		perEstadoMaquinariaImpropia = new PerEstadoMaquinariaImpropia();
	}

	public  static synchronized NegocioMaquinariaImpropia getInstance() {
		if (negocio == null)
			negocio = new NegocioMaquinariaImpropia();
		return negocio;
	}

	public List<MaquinariaImpropia> getTodos() throws ExcFiltroExcepcion{
		List  lista = null;
		try {
			lista = perMaquinariaImpropia.getAll();
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
	
	public MaquinariaImpropia getMaquinaria( String serial )
	{
		return perMaquinariaImpropia.getMaquinaria(serial.toUpperCase().trim());
		
	}
	public MaquinariaImpropia getMaquinariaOtroSerial( String serial )
	{
		return perMaquinariaImpropia.getMaquinariaOtroSerial(serial.toUpperCase().trim());
		
	}
	
	public void guardar(MaquinariaImpropia maquinaria) throws Exception
	{
		if(maquinaria.getId() == null )
		{
			// nueva 
			EstadoMaquinariaImpropia estado = perEstadoMaquinariaImpropia.getIngresado();
			String  idSede= (String) SpringUtil.getBean("idsede");
			maquinaria.setSede(idSede);
			maquinaria.setEstado(estado);
			
			//La Maquinaria Unidad Guardar 
			
			MaquinariaUnidad unidad = maquinaria.getMaquinariaUnidad();
			unidad.setMaquinariaImpropia(null);
			perMaquinariaUnidad.guardar(unidad,unidad.getId());
			unidad.setMaquinariaImpropia(maquinaria);
			 perMaquinariaImpropia.guardar(maquinaria);
			 perMaquinariaUnidad.guardar(unidad,unidad.getId());

		}
		else
		{
			//actualizar 
			perMaquinariaImpropia.guardar(maquinaria);
		}
	}
	
	public void eliminar(MaquinariaImpropia maquinaria) throws Exception
	{
		perMaquinariaImpropia.borrar(maquinaria);
	}

	
	public void CambiarEstado(MaquinariaImpropia maquinaria) throws Exception
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

		if (maquinaria.getEstado().getId().equals(EstadoMaquinariaImpropia.VERIFICADO) || maquinaria.getEstado().getId().equals(EstadoMaquinariaImpropia.MIGRADO))
		{
			maquinaria.getMaquinariaUnidad().setOperativo(true);
			perMaquinariaImpropia.guardar(maquinaria);
			perMaquinariaUnidad.guardar(maquinaria.getMaquinariaUnidad(),maquinaria.getMaquinariaUnidad().getId());
		}
		else if (maquinaria.getEstado().getId().equals(EstadoMaquinariaImpropia.DESACTIVADO) || maquinaria.getEstado().getId().equals(EstadoMaquinariaImpropia.DESINCORPORADO))
		{
			maquinaria.getMaquinariaUnidad().setOperativo(false);
			perMaquinariaImpropia.guardar(maquinaria);
			perMaquinariaUnidad.guardar(maquinaria.getMaquinariaUnidad(),maquinaria.getMaquinariaUnidad().getId());
		}
		
	}
	
	
	public List<EstadoMaquinariaImpropia > getEstados ()
	{		
		return perMaquinariaImpropia.getEstados();
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



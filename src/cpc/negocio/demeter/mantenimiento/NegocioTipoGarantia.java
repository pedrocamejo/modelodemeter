package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List; 

import com.sun.org.apache.regexp.internal.recompile;

import cpc.modelo.demeter.basico.CodigoArea;
import cpc.modelo.demeter.mantenimiento.ClaseMaquinaria;
import cpc.modelo.demeter.mantenimiento.Serie;
import cpc.modelo.demeter.mantenimiento.TipoGarantia; 
import cpc.modelo.demeter.mantenimiento.TipoMaquinariaVenta;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.demeter.implementacion.PerCodigoArea;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerClaseMaquinaria;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSerie;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoGarantia; 
import cpc.persistencia.demeter.implementacion.mantenimiento.PertTipoMaquinariaVenta;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioTipoGarantia implements Serializable {

	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioTipoGarantia 	negocio;
	private PerTipoGarantia 			pertipoGarantia;
	private PerModelo					perModelo;
	private PerCodigoArea				perCodigoArea;
	private PerClaseMaquinaria			perClaseMaquinaria;
	private PertTipoMaquinariaVenta     perTipoMaquinariaVenta;
	private PerSerie					perSerie;
	
	
	
	private NegocioTipoGarantia(){
		pertipoGarantia = new PerTipoGarantia();
		perModelo = new PerModelo();
		perCodigoArea = new PerCodigoArea();
		perClaseMaquinaria = new PerClaseMaquinaria();
		perTipoMaquinariaVenta = new PertTipoMaquinariaVenta();
		perSerie = new PerSerie();
		
	}

	public  static synchronized NegocioTipoGarantia getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoGarantia();
		return negocio;
	}
	
	public void Guardar(TipoGarantia tipoGarantia) throws Exception
	{
		pertipoGarantia.guardar(tipoGarantia,tipoGarantia.getId());
	}
	
	
	public void  Borrar(TipoGarantia tipoGarantia) throws Exception
	{
		pertipoGarantia.borrar(tipoGarantia);
		
	}

	public List<TipoGarantia> getTodos() throws ExcFiltroExcepcion{
		List<TipoGarantia> tipoGarantias = null;
		try {
			tipoGarantias  = pertipoGarantia.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return tipoGarantias;
	}
	
	public List<Modelo> getModeloLibres() throws ExcFiltroExcepcion // modelos que estan libres :-D 
	{
		return perModelo.getModeloSinTipoGarantia();
	}
	 
	
	public List<CodigoArea>  getcodigoAreas() throws ExcFiltroExcepcion
	{
		
		return perCodigoArea.getAll();
	}
	
	public List<Modelo>  getmodelos() throws ExcFiltroExcepcion
	{
		return perModelo.getAll();
	}
	
	public List<ClaseMaquinaria> getClaseMaquinaria() throws ExcFiltroExcepcion
	{
		return perClaseMaquinaria.getAll();
	}

	public List<TipoMaquinariaVenta> getTipoMaquinariaVenta() throws ExcFiltroExcepcion
	{
		return perTipoMaquinariaVenta.getAll();
	}
	
	public List<Serie> getSeries() throws ExcFiltroExcepcion
	{
		return perSerie.getAll();
	}
	
	
	
}


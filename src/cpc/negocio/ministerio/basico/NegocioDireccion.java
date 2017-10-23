package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.demeter.basico.TipoCoordenadaGeografica;
import cpc.modelo.demeter.basico.TipoDocumentoTierra;
import cpc.modelo.ministerio.basico.TipoSuperficie;
import cpc.modelo.ministerio.basico.TipoUbicacion;

import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerTipoDocumentoTierra;
import cpc.persistencia.ministerio.basico.PerDireccion;
import cpc.persistencia.ministerio.basico.PerProductor;
import cpc.persistencia.ministerio.basico.PerSector;
import cpc.persistencia.ministerio.basico.PerTipoCoordenada;
import cpc.persistencia.ministerio.basico.PerTipoSuperficie;
import cpc.persistencia.ministerio.basico.PerTipoUbicacion;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioDireccion extends NegocioGenerico<UbicacionDireccion, PerDireccion, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioDireccion 				negocio;
	
	private NegocioDireccion(){
		setPersistencia(new PerDireccion());
	}
	
	public  static synchronized NegocioDireccion getInstance() {
		if (negocio == null)
			negocio = new NegocioDireccion();
		return negocio;
	}
	
	public void setUbicacion(UbicacionDireccion direccion){
		setModelo(getPersistencia().getDatos(direccion));
	}
	
	public List<UbicacionSector> getSectores() throws ExcFiltroExcepcion{
		return new PerSector().getAll(); 
	}

	public List<Productor> getProductores() throws ExcFiltroExcepcion{
		return new PerProductor().getAll(); 
	}
	
	public List<TipoDocumentoTierra> getTiposDocumentosTierra() throws ExcFiltroExcepcion{
		return new PerTipoDocumentoTierra().getAll(); 
	}
	
	public List<TipoCoordenadaGeografica> getTiposCoordenadaGeograficas() throws ExcFiltroExcepcion{
		return new PerTipoCoordenada().getAll(); 
	}
	public List<TipoSuperficie> getSuperficies() throws ExcFiltroExcepcion{
		return new PerTipoSuperficie().getAll(); 
	}
	public List<TipoUbicacion> getTiposUbicaciones() throws ExcFiltroExcepcion{
		return new PerTipoUbicacion().getAll(); 
	}	
	
}

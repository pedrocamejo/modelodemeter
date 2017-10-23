package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.demeter.basico.SectorAgricola;
import cpc.modelo.demeter.basico.TipoCoordenadaGeografica;
import cpc.modelo.demeter.basico.TipoVerificacionSuelo;

import cpc.modelo.ministerio.basico.TipoRiego;
import cpc.modelo.ministerio.basico.TipoSuelo;
import cpc.modelo.ministerio.basico.TipoTenenciaTierra;
import cpc.modelo.ministerio.basico.TipoUsoTierra;
import cpc.modelo.ministerio.basico.TipoVialidad;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerTipoVerificacionSuelo;
import cpc.persistencia.ministerio.basico.PerDireccion;
import cpc.persistencia.ministerio.basico.PerProductor;
import cpc.persistencia.ministerio.basico.PerProductorJuridico;
import cpc.persistencia.ministerio.basico.PerProductorNatural;
import cpc.persistencia.ministerio.basico.PerSectorAgricola;
import cpc.persistencia.ministerio.basico.PerTipoCoordenada;
import cpc.persistencia.ministerio.basico.PerTipoRiego;
import cpc.persistencia.ministerio.basico.PerTipoSuelo;
import cpc.persistencia.ministerio.basico.PerTipoTenenciaTierra;
import cpc.persistencia.ministerio.basico.PerTipoUsoTierra;
import cpc.persistencia.ministerio.basico.PerTipoVialidad;
import cpc.persistencia.ministerio.basico.PerUnidadProductiva;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioUnidadProductiva extends NegocioGenerico<UnidadProductiva, PerUnidadProductiva, Integer>{

	
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioUnidadProductiva 				negocio;
	
	private NegocioUnidadProductiva(){
		setPersistencia(new PerUnidadProductiva());
	}
	
	public  static synchronized NegocioUnidadProductiva getInstance() {
		if (negocio == null)
			negocio = new NegocioUnidadProductiva();
		return negocio;
	}
	
			

	public void guardar() throws Exception {	
		getPersistencia().guardar(getModelo(), getModelo().getId());
	}
	
	public void setUnidadProductiva(UnidadProductiva unidad){
		setModelo(getTiposRiegoUnidadProductiva(unidad));
	}
	
	public UnidadProductiva getTiposRiegoUnidadProductiva(UnidadProductiva unidad){
		return getPersistencia().getDatos(unidad);
	}
	
	public List<TipoRiego> getRiegos() throws ExcFiltroExcepcion{
		return new PerTipoRiego().getAll(); 
	}
	
	public List<TipoSuelo> getSuelos() throws ExcFiltroExcepcion{
		return new PerTipoSuelo().getAll(); 
	}

	public List<TipoTenenciaTierra> getTenenciasTierra() throws ExcFiltroExcepcion{
		return new PerTipoTenenciaTierra().getAll(); 
	}
	
	public List<TipoVialidad> getVialidades() throws ExcFiltroExcepcion{
		return new PerTipoVialidad().getAll(); 
	}
	
	public List<TipoUsoTierra> getUsosTierra() throws ExcFiltroExcepcion{
		return new PerTipoUsoTierra().getAll(); 
	}
	
	public List<UbicacionDireccion> getDirecciones() throws ExcFiltroExcepcion{
		return new PerDireccion().getAll(); 
	}

	public List<Productor> getProductores() throws ExcFiltroExcepcion{
		return new PerProductor().getAll(); 
	}
	
	public List<TipoCoordenadaGeografica> getTiposCoordenadaGeograficas() throws ExcFiltroExcepcion{
		return new PerTipoCoordenada().getAll(); 
	}
	
	public List<SectorAgricola> getSectoresAgricolas() throws ExcFiltroExcepcion{
		return new PerSectorAgricola().getAll(); 
	}
	
	public List<TipoVerificacionSuelo> getTiposVerifiSuelo() throws ExcFiltroExcepcion{
		return new PerTipoVerificacionSuelo().getAll();
	}
	
	public void enriqueserDireccion(){
		getModelo().setUbicacion(new PerDireccion().getDatos(getModelo().getUbicacion()));
	}
	
	public void enriqueserProductor() throws NullPointerException{
		if (getModelo().getProductor().getTipo().isJuridico()){
			getModelo().setProductor(new PerProductorJuridico().getDatos(getModelo().getProductor()));
			
		}else{
			getModelo().setProductor(new PerProductorNatural().getDatos(getModelo().getProductor()));
			
		}
		
	}
}



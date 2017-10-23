package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.basico.ClaseUnidadArrime;
import cpc.modelo.demeter.basico.UnidadArrime;
import cpc.modelo.demeter.basico.TipoUnidadArrime;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerClaseUnidadArrime;
import cpc.persistencia.demeter.implementacion.basico.PerUnidadArrime;
import cpc.persistencia.demeter.implementacion.basico.PerTipoSilo;
import cpc.persistencia.ministerio.basico.PerDireccion;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioUnidadArrime extends NegocioGenerico<UnidadArrime, PerUnidadArrime, Integer>{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6264380226824831678L;
	private static NegocioUnidadArrime 				negocio;
	
	private NegocioUnidadArrime(){
		setPersistencia(new PerUnidadArrime());
	}
	
	public  static synchronized NegocioUnidadArrime getInstance() {
		if (negocio == null)
			negocio = new NegocioUnidadArrime();
		return negocio;
	}

	public List<TipoUnidadArrime> getTiposUnidadesArrime() throws ExcFiltroExcepcion{
		return new PerTipoSilo().getAll(); 
	}
	
	public List<ClaseUnidadArrime> getClasesUnidadesArrime(TipoUnidadArrime tipo) throws ExcFiltroExcepcion{
		return new PerClaseUnidadArrime().getClasesCriterio(tipo); 
	}

	public List<UbicacionDireccion> getDirecciones() throws ExcFiltroExcepcion{
		return new PerDireccion().getAll(); 
	}
	
	public void enriqueserDireccion(){
		getModelo().setDireccion(new PerDireccion().getDatos(getModelo().getDireccion()));
	}
}

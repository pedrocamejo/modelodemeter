package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.demeter.basico.CodigoArea;
import cpc.modelo.ministerio.basico.EstadoCivil;
import cpc.modelo.ministerio.basico.Genero;
import cpc.modelo.ministerio.basico.GradoInstruccion;
import cpc.modelo.ministerio.basico.Nacionalidad;
import cpc.modelo.ministerio.gestion.Representante;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerCodigoArea;
import cpc.persistencia.ministerio.basico.PerEstadoCivil;
import cpc.persistencia.ministerio.basico.PerGenero;
import cpc.persistencia.ministerio.basico.PerGradoInstruccion;
import cpc.persistencia.ministerio.basico.PerNacionalidad;
import cpc.persistencia.ministerio.basico.PerRepresentante;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioRepresentante extends NegocioGenerico<Representante, PerRepresentante, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioRepresentante 				negocio;
	
	
	private NegocioRepresentante(){
		setPersistencia(new PerRepresentante());
	}
	
	public  static synchronized NegocioRepresentante getInstance() {
		if (negocio == null)
			negocio = new NegocioRepresentante();
		return negocio;
	}
	

	public List<CodigoArea> getCodigosArea() throws ExcFiltroExcepcion{
		return new PerCodigoArea().getAll(); 
	}
	
	
	public List<GradoInstruccion> getGradoInstrucciones() throws ExcFiltroExcepcion{
		return new PerGradoInstruccion().getAll(); 
	}
	
	public List<Genero> getGeneros() throws ExcFiltroExcepcion{
		return new PerGenero().getAll(); 
	}
	
	public List<EstadoCivil> getEstadosCiviles() throws ExcFiltroExcepcion{
		return new PerEstadoCivil().getAll(); 
	}
	
	public List<Nacionalidad> getNacionalidades() throws ExcFiltroExcepcion{
		return new PerNacionalidad().getAll(); 
	}
	
	public void setRepresentante(Representante representante){
		super.setModelo(getPersistencia().getDato(representante));
	}
	
}

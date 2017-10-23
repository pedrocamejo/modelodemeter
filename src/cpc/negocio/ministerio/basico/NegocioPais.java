package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.ministerio.dimension.UbicacionEstado;
import cpc.modelo.ministerio.dimension.UbicacionPais;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerEstado;
import cpc.persistencia.ministerio.basico.PerPais;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioPais extends NegocioGenerico<UbicacionPais, PerPais, Integer>{

	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioPais 				negocio;
	
	private NegocioPais(){
		setPersistencia(new PerPais());
	}
	
	public  static synchronized NegocioPais getInstance() {
		if (negocio == null)
			negocio = new NegocioPais();
		return negocio;
	}

	public List<UbicacionEstado> getEstados() throws ExcFiltroExcepcion{
		return new PerEstado().getTodos(getModelo()); 
	}
}

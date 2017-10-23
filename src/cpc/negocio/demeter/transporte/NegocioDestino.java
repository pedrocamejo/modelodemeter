package cpc.negocio.demeter.transporte;

import java.io.Serializable;
import java.util.List;

 

import cpc.modelo.demeter.transporte.Destino;
import cpc.modelo.ministerio.dimension.UbicacionEstado;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.persistencia.demeter.implementacion.transporte.PerDestino;
import cpc.persistencia.ministerio.basico.PerEstado;
import cpc.persistencia.ministerio.basico.PerMunicipio;
import cpc.persistencia.ministerio.basico.PerParroquia;
import cpc.persistencia.ministerio.basico.PerSector;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioDestino  implements Serializable{
	
	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioDestino		 	negocio;
	private PerDestino						perDestino;
	private PerSector						perSector;
	private PerMunicipio					perMunicipio;
	private PerEstado						perEstado;
	private PerParroquia					perParroquia;

	public NegocioDestino(){
		perDestino = new PerDestino();
		perSector = new PerSector();
		perMunicipio = new PerMunicipio();
		perEstado = new PerEstado();
		perParroquia = new PerParroquia();
	}

	public  static synchronized NegocioDestino getInstance() {
		if (negocio == null)
			negocio = new NegocioDestino();
		return negocio;
	}

	public List<Destino> getTodos() throws ExcFiltroExcepcion{
		return perDestino.getAll();
	}

	public static NegocioDestino getNegocio() {
		return negocio;
	}

	public void Eliminar(Destino destino) throws Exception
	{
		perDestino.borrar(destino);
	}
	public void Guardar(Destino destino) throws Exception
	{
		perDestino.guardar(destino,destino.getId());
	}
	
	public List<UbicacionMunicipio> municipios(UbicacionEstado estado) throws ExcFiltroExcepcion
	{
		return perMunicipio.getTodos(estado);
	}
	
	public List<UbicacionParroquia> parroquias(UbicacionMunicipio muni) throws ExcFiltroExcepcion
	{
		return perParroquia.getTodos(muni);
	}
	
	public List<UbicacionSector> sectores(UbicacionParroquia parro) throws ExcFiltroExcepcion
	{
		return perSector.getTodos(parro);
	}

}
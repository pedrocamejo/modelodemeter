package cpc.negocio.demeter.transporte;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.transporte.EstadoSolicitudTransporte;
import cpc.modelo.demeter.transporte.Gerencia;
import cpc.modelo.demeter.transporte.SolicitudTransporte;
import cpc.persistencia.demeter.implementacion.transporte.PerGerencia;
import cpc.persistencia.demeter.implementacion.transporte.PerSolicitudTransporte;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSolicitudTransporte  implements Serializable{
	
	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioSolicitudTransporte  	negocio;
	private PerSolicitudTransporte				perSolicitudTransporte;
	private PerGerencia 						perGerencia;

	public NegocioSolicitudTransporte(){
		perSolicitudTransporte= new PerSolicitudTransporte();
		perGerencia = new PerGerencia();
	}

	public  static synchronized NegocioSolicitudTransporte getInstance() {
		if (negocio == null)
		{
		 
			negocio = new NegocioSolicitudTransporte();
		} 
		return negocio;
	}

	public List<SolicitudTransporte> getTodos( ) throws ExcFiltroExcepcion{
	 
		return  perSolicitudTransporte.getAll( );
	}
 
	
	public void anular(SolicitudTransporte solicitud ) throws Exception
	{
		solicitud.setEstado(EstadoSolicitudTransporte.ANULADA);
		perSolicitudTransporte.guardar(solicitud,solicitud.getIdSolicitud());
	}

	public void guardar(SolicitudTransporte solicitud ) throws Exception
	{
		perSolicitudTransporte.guardar(solicitud,solicitud.getIdSolicitud());
	}

	public void eliminar(SolicitudTransporte solicitud) throws Exception
	{
		perSolicitudTransporte.borrar(solicitud);
	}
	
	public List<Gerencia> getGerencias() throws ExcFiltroExcepcion
	{
		return perGerencia.getAll();
	}

}
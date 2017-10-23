package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.gestion.MotivoAnulacionSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerMotivoAnulacionSolicitud;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioMotivoAnulacionSolicitud implements Serializable{

	
	private static final long serialVersionUID = 2422099110471641881L;
	private static NegocioMotivoAnulacionSolicitud negocio;
	private PerMotivoAnulacionSolicitud 			persistencia;
	private MotivoAnulacionSolicitud				motivoAnulacionSolicitud;
	
	public NegocioMotivoAnulacionSolicitud(){
		persistencia = new PerMotivoAnulacionSolicitud();
	}
	
	public static synchronized NegocioMotivoAnulacionSolicitud getInstance(){
		if (negocio == null)
			negocio = new NegocioMotivoAnulacionSolicitud();
		return negocio;
	}
	

	public List<MotivoAnulacionSolicitud> obetenerTodos() throws ExcFiltroExcepcion{
		return persistencia.getAll();
	}
	

	public void guardar() throws Exception{
		persistencia.guardar(motivoAnulacionSolicitud, motivoAnulacionSolicitud.getId());
	}
	
	public static NegocioMotivoAnulacionSolicitud getNegocio() {
		return negocio;
	}
	public static void setNegocio(NegocioMotivoAnulacionSolicitud negocio) {
		NegocioMotivoAnulacionSolicitud.negocio = negocio;
	}
	public PerMotivoAnulacionSolicitud getPersistencia() {
		return persistencia;
	}
	public void setPersistencia(PerMotivoAnulacionSolicitud persistencia) {
		this.persistencia = persistencia;
	}
	public MotivoAnulacionSolicitud getMotivoAnulacionSolicitud() {
		return motivoAnulacionSolicitud;
	}
	public void setMotivoMotivoAnulacionSolicitud(
			MotivoAnulacionSolicitud  motivoAnulacionSolicitud) {
		this.motivoAnulacionSolicitud = motivoAnulacionSolicitud;
	}
	
	public boolean getusado(MotivoAnulacionSolicitud motivo ){
	return persistencia.usado(motivo);
	}
	
}

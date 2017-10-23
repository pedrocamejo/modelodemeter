package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.gestion.MotivoTransferenciaActivo;
import cpc.persistencia.demeter.implementacion.gestion.PerMotivoTransferenciaActivo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioMotivoTransferenciaActivo implements Serializable{

	private static final long serialVersionUID = -215800630222419254L;
	private static NegocioMotivoTransferenciaActivo negocio;
	private PerMotivoTransferenciaActivo 			persistencia;
	private MotivoTransferenciaActivo 				motivoTransferenciaActivo;
	
	
	public NegocioMotivoTransferenciaActivo() {
		persistencia = new PerMotivoTransferenciaActivo();
	}
	
	public static synchronized NegocioMotivoTransferenciaActivo getInstance(){
		if (negocio == null)
			negocio = new NegocioMotivoTransferenciaActivo();
		return negocio;
	}
	
	
	public List<MotivoTransferenciaActivo> obetenerTodos() throws ExcFiltroExcepcion{
		return persistencia.getAll();
	}
	
	public void guardar() throws Exception{
		persistencia.guardar(motivoTransferenciaActivo, motivoTransferenciaActivo.getId());
	}
	
	public static NegocioMotivoTransferenciaActivo getNegocio() {
		return negocio;
	}
	public static void setNegocio(NegocioMotivoTransferenciaActivo negocio) {
		NegocioMotivoTransferenciaActivo.negocio = negocio;
	}
	public PerMotivoTransferenciaActivo getPersistencia() {
		return persistencia;
	}
	public void setPersistencia(PerMotivoTransferenciaActivo persistencia) {
		this.persistencia = persistencia;
	}
	public MotivoTransferenciaActivo getMotivoTransferenciaActivo() {
		return motivoTransferenciaActivo;
	}
	public void setMotivoTransferenciaActivo(
			MotivoTransferenciaActivo motivoTransferenciaActivo) {
		this.motivoTransferenciaActivo = motivoTransferenciaActivo;
	}
}

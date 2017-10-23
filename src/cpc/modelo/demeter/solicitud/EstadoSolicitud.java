package cpc.modelo.demeter.solicitud;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_estado_solicitud")
public class EstadoSolicitud implements Serializable{
	
	
	public static final int  EN_ESTUDIO = 1;
	public static final int  EN_INSPECCION = 2;
	public static final int  INSPECCIONADO = 3;
	public static final int  NO_FACTIBLE = 4;
	public static final int  APROBADO = 5;
	public static final int  RECHAZADO = 6;
	public static final int  PLANIFICADO = 7;
	public static final int  EJECUCION = 8;
	public static final int  EN_ESPERA = 9;
	public static final int  CASO_ESPECIAL = 10;
	public static final int  SERVICIO_PRESTADO = 11;
	
	
	private static final long serialVersionUID = 2444661970377614230L;
	private int 		idestado;
	private String		descripcion;
	private boolean 	congelado;
	private boolean 	enTramite;
	
	public EstadoSolicitud() {
		super();
	}

	
	public EstadoSolicitud(int idestado, String descripcion, boolean congelado,
			boolean enTramite) {
		super();
		this.idestado = idestado;
		this.descripcion = descripcion;
		this.congelado = congelado;
		this.enTramite = enTramite;
	}
	
	@SequenceGenerator(name="SeqEstadoSolicitud", sequenceName="tbl_dem_estado_solicitud_seq_idestadosolicitud_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqEstadoSolicitud")
	@Column(name="seq_idestadosolicitud")
	public int getIdestado() {
		return idestado;
	}
	public void setIdestado(int idestado) {
		this.idestado = idestado;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Column(name="bol_entcongelado")
	public boolean isCongelado() {
		return congelado;
	}
	public void setCongelado(boolean congelado) {
		this.congelado = congelado;
	}
	
	@Column(name="bol_entramite")
	public boolean isEnTramite() {
		return enTramite;
	}
	public void setEnTramite(boolean enTramite) {
		this.enTramite = enTramite;
	}
	
	
}

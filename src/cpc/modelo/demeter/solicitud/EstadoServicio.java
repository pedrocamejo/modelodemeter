package cpc.modelo.demeter.solicitud;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_estado_servicio", schema="solicitud")
public class EstadoServicio implements Serializable{
	
	public static final int  RECHAZADO_ETCNICAMENTE = 0;
	public static final int  SIN_APROBAR = 1;
	public static final int  APROBADO_SIN_PLANIFICAR = 2;
	public static final int  APROBADO_PLANIFICADO = 3;
	public static final int  EN_EJECUCION = 4;
	public static final int  CULMINADO = 5;
	public static final int  RECHAZADO_EN_ASAMBLEA = 6;
	public static final int  EN_CONTRATO = 7;
	
	
	
	private static final long serialVersionUID = 2444661970377614230L;
	private int 		idestado;
	private String		descripcion;
	private boolean 	listoAdministrativo;
	private boolean 	listoEjecucion;
	
	public EstadoServicio() {
		super();
	}

	
	public EstadoServicio(int idestado, String descripcion, boolean administrativo,
			boolean ejecucion) {
		super();
		this.idestado = idestado;
		this.descripcion = descripcion;
		this.listoAdministrativo = administrativo;
		this.listoEjecucion = ejecucion;
	}
	
	@SequenceGenerator(name="SeqEstadoServicio", sequenceName="solicitud.tbl_dem_estado_servicio_seq_idestaserv_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqEstadoServicio")
	@Column(name="seq_idestaserv")
	public int getIdestado() {
		return idestado;
	}
	public void setIdestado(int idestado) {
		this.idestado = idestado;
	}
	
	@Column(name="str_desestser")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name="bol_lisadm")
	public boolean isListoAdministrativo() {
		return listoAdministrativo;
	}


	public void setListoAdministrativo(boolean listoAdministrativo) {
		this.listoAdministrativo = listoAdministrativo;
	}

	@Column(name="bol_liseje")
	public boolean isListoEjecucion() {
		return listoEjecucion;
	}


	public void setListoEjecucion(boolean listoEjecucion) {
		this.listoEjecucion = listoEjecucion;
	}


}

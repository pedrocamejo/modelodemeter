package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.text.StyledEditorKit.BoldAction;

import cpc.modelo.demeter.basico.TipoSolicitud;




@Audited @Entity
@Table(name="tbl_dem_estado_solicitud", schema = "gestion")
public class EstadoSolicitud implements Serializable{
	
	private static final long 		serialVersionUID = 6504439416785033934L;
	private Integer 				id;
	private String					estado;
	private TipoSolicitud			tipoSolicitud;
	//private Boolean                 activo,prestado,planificado;
	
	
	@Column(name="seq_idestado")
	@SequenceGenerator(name="EstadoSolicitud_Gen", sequenceName="gestion.tbl_dem_estados_solicitud_seq_idestado_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="EstadoSolicitud_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@OneToOne
	@JoinColumn(name="int_tiposol")
	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}
	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	/*public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public Boolean getPrestado() {
		return prestado;
	}
	public void setPrestado(Boolean prestado) {
		this.prestado = prestado;
	}
	public Boolean getPlanificado() {
		return planificado;
	}
	public void setPlanificado(Boolean planificado) {
		this.planificado = planificado;
	}
	*/
	
	
	@Transient 
	public String toString(){
		return getEstado().toUpperCase();
	}
	
}



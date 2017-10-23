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

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import cpc.modelo.demeter.basico.TipoSolicitud;



@Audited @Entity
@Table(name="tbl_dem_motivo_anulacion_solicitud", schema = "gestion")
public class MotivoAnulacionSolicitud implements Serializable{
	
	private static final long 		serialVersionUID = 6504439416785033934L;
	private Integer 				id;
	private String					motivo;
	private TipoSolicitud			tipoSolicitud;
	private boolean  				activo;
	
	@Column(name="seq_idmotivoanulacion")                    
	@SequenceGenerator(name="MotivoAnulacion_Gen", sequenceName="gestion.tbl_dem_motivo_anulacion_solicitud_seq_idmotivoanulacion_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="MotivoAnulacion_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_motivo")
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}	
	
	@OneToOne
	@JoinColumn(name="int_tiposol")
	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}
	
	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	
	@Column(name="bol_activo")
	public boolean getActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}	
	
	
	@Transient 
	public String toString(){
		return getMotivo();
	}
	
	@Transient 
	public String getStrTipoSolicitud(){
		return getTipoSolicitud().getNombre();
	}
	
	@Transient 
	public String getStrEstado(){
		if (getActivo()) return "ACTIVO";
				else return "INACTIVO";
	}
	
}

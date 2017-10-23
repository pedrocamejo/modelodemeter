package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_anulacion_solicitud_mecanizado", schema = "gestion")
public class AnulacionSolicitud implements Serializable{
	
	private static final long serialVersionUID = 6504439416785033934L;
	private Integer 			id;
	private Solicitud           solicitud;
	private String  			usuario;
	private Date          		fechaanulacion;
	private MotivoAnulacionSolicitud motivoanulacionsolicitud;
	
	@Column(name="seq_idanulsolitud")
	@SequenceGenerator(name="AnulacionSolicitud_Gen", sequenceName="gestion.tbl_dem_anulacion_solicitud_mecanizado_seq_idanulsolitud_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="AnulacionSolicitud_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@OneToOne            
	@JoinColumn(name="int_idmotivodeanulacion")
	public MotivoAnulacionSolicitud getmotivoanulacionsolicitud() {
		return motivoanulacionsolicitud;
	}
	public void setmotivoanulacionsolicitud (MotivoAnulacionSolicitud motivoanulacionsolicitud) {
		this.motivoanulacionsolicitud = motivoanulacionsolicitud;
	}
	@OneToOne		
	@JoinColumn(name="int_idsolicitud")
	public Solicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	
	@Column(name="str_usuario")
	public String getusuario() {
		return usuario;
	}
	public void setusuario(String usuario) {
		this.usuario = usuario;
	}	
	@Column(name="dtm_fechaanulacion")
	public Date getfechaanulacion() {
		return fechaanulacion;
	}
	public void setfechaanulacion(Date fechaanulacion) {
		this.fechaanulacion = fechaanulacion;
	}
	
	

}

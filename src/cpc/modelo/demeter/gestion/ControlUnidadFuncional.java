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

import cpc.modelo.ministerio.dimension.UnidadFuncional;


@Audited @Entity
@Table(name="tbl_dem_control_unidad", schema="gestion")
public class ControlUnidadFuncional implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2833125587570025102L;
	private Integer					id;
	private UnidadFuncional 		unidad;
	private String					serie;
	private String					serieSolicitudServicioTecnico;
	private Integer					controlSolicitud;
	private Integer					controlSeguimiento;
	private Integer					controlSolicitudServicioTEcnico;
	private String					serieOrdenTransporte;
	private Integer					controlOrdenTransporte;
	//private Date					fechaServicios;
	
	@Id
	@Column(name="seq_control")
	@SequenceGenerator(name="SeqControlunidaFuncional", sequenceName="gestion.tbl_dem_control_unidad_seq_control_seq", allocationSize=1)
	@GeneratedValue(generator="SeqControlunidaFuncional")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="int_unidad")
	public UnidadFuncional getUnidad() {
		return unidad;
	}
	public void setUnidad(UnidadFuncional unidad) {
		this.unidad = unidad;
	}
	
	@Column(name="str_serie")
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	@Column(name="int_control_solicitud")
	public Integer getControlSolicitud() {
		return controlSolicitud;
	}
	public void setControlSolicitud(Integer controlSolicitud) {
		this.controlSolicitud = controlSolicitud;
	}
	
	@Column(name="int_control_seguimiento")
	public Integer getControlSeguimiento() {
		return controlSeguimiento;
	}
	public void setControlSeguimiento(Integer controlSeguimiento) {
		this.controlSeguimiento = controlSeguimiento;
	}
	
	@Column(name="str_prefijosolicitudserviciotecnico ")
	public String getSerieSolicitudServicioTecnico() {
		return serieSolicitudServicioTecnico;
	}
	public void setSerieSolicitudServicioTecnico(
			String serieSolicitudServicioTecnico) {
		this.serieSolicitudServicioTecnico = serieSolicitudServicioTecnico;
	}
	@Column(name="int_controlsolicitudserviciotecnico")
	public Integer getControlSolicitudServicioTEcnico() {
		return controlSolicitudServicioTEcnico;
	}
	public void setControlSolicitudServicioTEcnico(
			Integer controlSolicitudServicioTEcnico) {
		this.controlSolicitudServicioTEcnico = controlSolicitudServicioTEcnico;
	}
	
	/*@Temporal(TemporalType.DATE)
	@Column(name="ste_fechagestion")
	public Date getFechaServicios() {
		return fechaServicios;
	}
	public void setFechaServicios(Date fechaServicios) {
		this.fechaServicios = fechaServicios;
	}*/

	@Transient
	public String getStrUnidad(){
		if (unidad == null)
			return "";
		else
			return unidad.getDescripcion();
	}
	
	
	@Transient
	public void incrementarSolicitud(){
		controlSolicitud++;
	}
	
	@Transient
	public void incrementarSeguimiento(){
		controlSeguimiento++;
	}
	
	
	@Transient
	public void incrementarSolicitudServicioTEcnico(){
		controlSolicitudServicioTEcnico++;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ControlUnidadFuncional)) {
			return false;
		}
		ControlUnidadFuncional other = (ControlUnidadFuncional) o;
		return true && other.getId().equals(this.getId());
	}
	@Column(name="str_serieordentransporte",length =4)
	public String getSerieOrdenTransporte() {
		return serieOrdenTransporte;
	}
	public void setSerieOrdenTransporte(String serieOrdenTransporte) {
		this.serieOrdenTransporte = serieOrdenTransporte;
	}
	@Column(name="int_controlordentransporte")
	public Integer getControlOrdenTransporte() {
		return controlOrdenTransporte;
	}
	public void setControlOrdenTransporte(Integer controlOrdenTransporte) {
		this.controlOrdenTransporte = controlOrdenTransporte;
	}
	
	@Transient
	public void incrementarOrdenTransporte(){
		controlOrdenTransporte++;
	}
	
}



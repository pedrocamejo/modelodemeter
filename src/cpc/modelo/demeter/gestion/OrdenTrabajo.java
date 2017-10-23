package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import cpc.modelo.demeter.basico.TipoTrabajo;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.sigesp.basico.Sede;



@Audited @Entity
@Table(name="tbl_dem_orden_servicio", schema="gestion")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class OrdenTrabajo implements Serializable {

	private static final long serialVersionUID = 7628328525931007494L;
	
	private Long 						id; 
	private Sede 						sede;
	private UnidadFuncional				unidadFuncional;
	private Date 						fecha;
	
	private TipoTrabajo					tipo;
	private String 						nroControl; 
	private EstadoOrdenTrabajo			estado; 
	private String 						observacion;	
	private Solicitud					solicitud; 
	
	private Boolean						cerrada;
	
	private List<TrabajoRealizado>		trabajosRealizados;
	private List<DetalleMaquinariaOrdenTrabajo>	equipos;
	private List<DetalleOrdenTrabajo>	detalles;
	
	
	public OrdenTrabajo(){
		super();
	}

	 @Id
	 @Column(name="seq_idordenservicio")
	 @SequenceGenerator(name="seqOrdenTrabajo", sequenceName="gestion.tbl_dem_orden_servicio_seq_idordenservicio_seq", allocationSize=1)
	 @GeneratedValue(generator="seqOrdenTrabajo")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_idsede",referencedColumnName="codubifis")
	})
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@ManyToOne
	@JoinColumn(name="int_idtipotrabajo")
	public TipoTrabajo getTipo() {
		return tipo;
	}
	public void setTipo(TipoTrabajo tipo) {
		this.tipo = tipo;
	}
	
	@Column(name="str_nrocon")
	public String getNroControl() {
		return nroControl;
	}
	public void setNroControl(String nroControl) {
		this.nroControl = nroControl;
	}


	@ManyToOne
	@JoinColumn(name="int_idestadoorden")
	public EstadoOrdenTrabajo getEstado() {
		return estado;
	}
	public void setEstado(EstadoOrdenTrabajo estado) {
		this.estado = estado;
	}


	@Column(name="str_observacion")
	@Basic(optional=true)
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@ManyToOne
	@JoinColumn(name="int_idsolicitud")
	@Basic(optional=true)
	public Solicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	/*@ManyToOne
	@Column(name="int_idtipomedidarendimiento")
	@Basic(optional=true)
	public TipoMedidaRendimiento getTipoMedidaRendimiento() {
		return tipoMedidaRendimiento;
	}
	public void setTipoMedidaRendimiento(TipoMedidaRendimiento tipoMedidaRendimiento) {
		this.tipoMedidaRendimiento = tipoMedidaRendimiento;
	}*/

	
	@OneToMany(mappedBy="orden", targetEntity= DetalleOrdenTrabajo.class)
	public List<DetalleOrdenTrabajo> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleOrdenTrabajo> detalles) {
		this.detalles = detalles;
	}


	@ManyToOne
	@JoinColumn(name="int_unidadfuncional")
	public UnidadFuncional getUnidadFuncional() {
		return unidadFuncional;
	}
	public void setUnidadFuncional(UnidadFuncional unidadFuncional) {
		this.unidadFuncional = unidadFuncional;
	}

	@Column(name="bol_cerrada")
	public Boolean getCerrada() {
		return cerrada;
	}
	public void setCerrada(Boolean cerrada) {
		this.cerrada = cerrada;
	}
	
	@OneToMany(targetEntity=DetalleMaquinariaOrdenTrabajo.class, mappedBy="ordenTrabajo")
	public List<DetalleMaquinariaOrdenTrabajo> getEquipos() {
		return equipos;
	}
	public void setEquipos(List<DetalleMaquinariaOrdenTrabajo> equipos) {
		this.equipos = equipos;
	}
	
	@OneToMany(mappedBy="ordenTrabajo", targetEntity=TrabajoRealizado.class)
	public List<TrabajoRealizado> getTrabajosRealizados() {
		return trabajosRealizados;
	}
	public void setTrabajosRealizados(List<TrabajoRealizado> trabajos) {
		this.trabajosRealizados = trabajos;
	}
	
	@Transient
	public String getStrFecha(){
		if (fecha == null) return "";
		return fecha.toString();
	}
	
	@Transient
	public String getStrUnidadFuncional(){
		if (unidadFuncional == null) return "";
		return unidadFuncional.getDescripcion();
	}

	@Transient
	public String getStrEstado(){
		if (estado == null) return "";
		return estado.getDescripcion();
	}
	
	public boolean equals(Object objeto){
		try{
			OrdenTrabajo cuenta = (OrdenTrabajo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

	


	
}
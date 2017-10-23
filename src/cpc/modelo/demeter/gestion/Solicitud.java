package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.PlanServicio;
import cpc.modelo.demeter.basico.TipoSolicitud;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.sigesp.basico.Sede;

@Audited @Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="tbl_dem_solicitud", schema="gestion")
public class  Solicitud implements Serializable {
	
	
	private static final long 	serialVersionUID = -6419479727905453690L;
	private Long						id;
	private Sede 						sede;
	private UnidadFuncional				unidadEjecutora;
	
	private Date						fecha;
	private String 						nroControl;
	private Productor 					productor;
	
	private TipoSolicitud				tipoSolicitud;
	
	private String						observacion;
	private PlanServicio				plan;
	
	private Boolean						prestada;
	private Boolean						planificada;
	private Boolean						aprobada;
	
	private Trabajador					responsable;
	private List<DetalleSolicitud>		detalles;
	private EstadoSolicitud 			estadosolicitud;

	public Solicitud() {
		super();	
	}
	
	
	@Column(name="seq_idsolicitud")
	@SequenceGenerator(name="Solicitud_Gen", sequenceName="tbl_dem_solicitud_seq_idsolicitud_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="Solicitud_Gen")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumns({ 
	    @JoinColumn(name="str_codsed", referencedColumnName="codubifis"), 
	    @JoinColumn(name="str_codemp", referencedColumnName="codemp"), 
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

	@Column(name="str_nrocon")
	public String getNroControl() {
		return nroControl;
	}

	public void setNroControl(String nroControl) {
		this.nroControl = nroControl;
	}
	
	@OneToOne
	@JoinColumn(name="int_tipsol")
	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}
	
	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadejecutora")
	public UnidadFuncional getUnidadEjecutora() {
		return unidadEjecutora;
	}
	public void setUnidadEjecutora(UnidadFuncional unidadEjecutora) {
		this.unidadEjecutora = unidadEjecutora;
	}

	@ManyToOne
	@JoinColumn(name="int_idcliente")
	public Productor getProductor() {
		return productor;
	}
	public void setProductor(Productor productor) {
		this.productor = productor;
	}


	@Column(name="str_observacion")
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}


	@ManyToOne
	@JoinColumn(name="int_idplan")
	public PlanServicio getPlan() {
		return plan;
	}
	public void setPlan(PlanServicio plan) {
		this.plan = plan;
	}

	@Column(name="bol_solicitudprestad")
	public Boolean getPrestada() {
		return prestada;
	}
	public void setPrestada(Boolean prestada) {
		this.prestada = prestada;
	}

	@Column(name="bol_planificada")
	public Boolean getPlanificada() {
		return planificada;
	}
	public void setPlanificada(Boolean planificada) {
		this.planificada = planificada;
	}


	@Column(name="bol_activo")
	public Boolean getAprobada() {
		return aprobada;
	}
	public void setAprobada(Boolean aprobada) {
		this.aprobada = aprobada;
	}

	@ManyToOne
	@JoinColumn(name="int_idtrabajador")
	public Trabajador getResponsable() {
		return responsable;
	}
	public void setResponsable(Trabajador responsable) {
		this.responsable = responsable;
	}

	@OneToMany(mappedBy="solicitud", targetEntity=DetalleSolicitud.class,cascade = CascadeType.ALL , fetch= FetchType.EAGER)
	public List<DetalleSolicitud> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleSolicitud> detalles) {
		this.detalles = detalles;
	}	

	
	@Transient
	public String getStrFecha() {
		return fecha.toString();
	}

	@Transient
	public String getStrProductor() {
		if (productor== null)
			return "";
		return productor.getNombres();
	}
	
	@Transient
	public String getStrUnidadEjecutora() {
		if (unidadEjecutora== null)
			return "";
		return unidadEjecutora.getDescripcion();
	}
	
	@Transient
	public String getStrEstado() {
		if (!aprobada)
			return "Rechazada";
		if (prestada)
			return "Prestada";
		if (planificada)
			return "Planificada";
		return "Sin Prestar";
	}

	@Transient
	public String getStrSede() {
		if (sede== null)
			return "";
		return sede.getNombre();
	}	
	
	@Transient
	public String getStrPlan() {
		if (plan== null)
			return "";
		return plan.getNombre();
	}	

	
	@OneToOne
	@JoinColumn(name="int_estado")
	public EstadoSolicitud getEstadosolicitud() {
		return estadosolicitud;
	}


	public void setEstadosolicitud(EstadoSolicitud estadosolicitud) {
		this.estadosolicitud = estadosolicitud;
	}
	@Transient
	public String getStrEstadoSolicitud(){
		if (estadosolicitud.getEstado()== null)
			return " ";
		else 
		return estadosolicitud.getEstado();
	}
/*
@Transient
public String getStrEstado(){
	return this.getEstadosolicitud().getEstado();
}*/


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aprobada == null) ? 0 : aprobada.hashCode());
		result = prime * result
				+ ((detalles == null) ? 0 : detalles.hashCode());
		result = prime * result
				+ ((estadosolicitud == null) ? 0 : estadosolicitud.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nroControl == null) ? 0 : nroControl.hashCode());
		result = prime * result
				+ ((observacion == null) ? 0 : observacion.hashCode());
		result = prime * result + ((plan == null) ? 0 : plan.hashCode());
		result = prime * result
				+ ((planificada == null) ? 0 : planificada.hashCode());
		result = prime * result
				+ ((prestada == null) ? 0 : prestada.hashCode());
		result = prime * result
				+ ((productor == null) ? 0 : productor.hashCode());
		result = prime * result
				+ ((responsable == null) ? 0 : responsable.hashCode());
		result = prime * result + ((sede == null) ? 0 : sede.hashCode());
		result = prime * result
				+ ((tipoSolicitud == null) ? 0 : tipoSolicitud.hashCode());
		result = prime * result
				+ ((unidadEjecutora == null) ? 0 : unidadEjecutora.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solicitud other = (Solicitud) obj;
		if (aprobada == null) {
			if (other.aprobada != null)
				return false;
		} else if (!aprobada.equals(other.aprobada))
			return false;
		if (detalles == null) {
			if (other.detalles != null)
				return false;
		} else if (!detalles.equals(other.detalles))
			return false;
		if (estadosolicitud == null) {
			if (other.estadosolicitud != null)
				return false;
		} else if (!estadosolicitud.equals(other.estadosolicitud))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nroControl == null) {
			if (other.nroControl != null)
				return false;
		} else if (!nroControl.equals(other.nroControl))
			return false;
		if (observacion == null) {
			if (other.observacion != null)
				return false;
		} else if (!observacion.equals(other.observacion))
			return false;
		if (plan == null) {
			if (other.plan != null)
				return false;
		} else if (!plan.equals(other.plan))
			return false;
		if (planificada == null) {
			if (other.planificada != null)
				return false;
		} else if (!planificada.equals(other.planificada))
			return false;
		if (prestada == null) {
			if (other.prestada != null)
				return false;
		} else if (!prestada.equals(other.prestada))
			return false;
		if (productor == null) {
			if (other.productor != null)
				return false;
		}//puede ser lo que falle  
		/*else if (!productor.equals(other.productor))
			return false;*/
		else if (!productor.getIdentidadLegal().equals(other.productor.getIdentidadLegal()))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		if (sede == null) {
			if (other.sede != null)
				return false;
		} else if (!sede.equals(other.sede))
			return false;
		if (tipoSolicitud == null) {
			if (other.tipoSolicitud != null)
				return false;
		} else if (!tipoSolicitud.equals(other.tipoSolicitud))
			return false;
		if (unidadEjecutora == null) {
			if (other.unidadEjecutora != null)
				return false;
		} else if (!unidadEjecutora.equals(other.unidadEjecutora))
			return false;
		return true;
	}
	
	
	
	




}

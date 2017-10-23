package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.gestion.MotivoTransferenciaActivo;

@Audited @Entity
@Table(name="saf_activoalmacen", schema="sigesp")
public class ActivoAlmacen implements Serializable{

	private static final long serialVersionUID = 7333617141592623813L;
	private Long						id;
	private Activo						activo;
	private Almacen     				almacen;
	private boolean     				trasladado, desincorporado;
	private Date						fechaRegistro, fechaActualizacion;
	private String 						usuario, observacion, motivoDesincorporacion;
	private MotivoTransferenciaActivo 	motivo;
	
	public ActivoAlmacen() {
		super();
	}
	
	public ActivoAlmacen(Activo activo, Almacen almacen,
			boolean trasladado, Date fechaRegistro, Date fechaActualizacion,
			String usuario, String observacion, MotivoTransferenciaActivo estado, boolean desincorporado) {
		super();
		this.activo 			= activo;
		this.almacen 			= almacen;
		this.trasladado 		= trasladado;
		this.fechaRegistro 		= fechaRegistro;
		this.fechaActualizacion = fechaActualizacion;
		this.usuario 			= usuario;
		this.observacion 		= observacion;
		this.motivo 			= estado;
		this.desincorporado 	= desincorporado;
	}

	@SequenceGenerator(name="SeqAlmacenActivo", sequenceName="sigesp.saf_activoalmacen_seq_big_actalmacen_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqAlmacenActivo")
	@Column(name="seq_big_actalmacen")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_codact",referencedColumnName="codact"),
		@JoinColumn(name="str_ideact",referencedColumnName="ideact")
	}) 
	public Activo getActivo() {
		return activo;
	}
	public void setActivo(Activo activo) {
		this.activo = activo;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idalmacen")
	public Almacen getAlmacen() {
		return almacen;
	}
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	@Column(name="bol_trasladado")
	public boolean isTrasladado() {
		return trasladado;
	}
	public void setTrasladado(boolean trasladado) {
		this.trasladado = trasladado;
	}

	@Column(name="tmwtz_fechareg")
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Column(name="tmwtz_fechaact")
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Column(name="str_usuario")
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Column(name="str_observacion")
	public String getObservacion() {
		return observacion;
	}
	
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@ManyToOne
	@JoinColumn(name="int_idmotivotransfe")
	public MotivoTransferenciaActivo getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoTransferenciaActivo motivo) {
		this.motivo = motivo;
	}
	
	@Column(name="bol_desincorporado")
	public boolean isDesincorporado() {
		return desincorporado;
	}

	public void setDesincorporado(boolean desincorporado) {
		this.desincorporado = desincorporado;
	}

	@Column(name="str_motidesincorporacion")
	public String getMotivoDesincorporacion() {
		return motivoDesincorporacion;
	}

	public void setMotivoDesincorporacion(String motivoDesincorporacion) {
		this.motivoDesincorporacion = motivoDesincorporacion;
	}

	@Transient
	public String getDescripcionMotivo(){
		return getMotivo().getDescripcion();
	}
	
	@Transient
	public String getSerialActivo(){
		return getActivo().getSerial();
	}
	
	@Transient
	public String getNombreActivo(){
		return getActivo().getDescripcionLarga();
	}
	
	@Transient
	public String getMarcaActivo(){
		return getActivo().getDescripcionMarca();
	}
	
	@Transient
	public String getModeloActivo(){
		return getActivo().getDescripcionModelo();
	}
	
	@Transient
	public String getNombreAlmacen(){
		return getAlmacen().getNombre();
	}
	
	@Transient
	public String getNombreTipoAlmacen(){
		return getAlmacen().getNombreTipoAlmacen();
	}
}

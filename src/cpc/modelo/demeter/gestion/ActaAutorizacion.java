package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;



import cva.pc.demeter.utilidades.Fecha;


@Audited @Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="saf_acta_autorizacion", schema="sigesp")
public class ActaAutorizacion implements Serializable{

	private static final long serialVersionUID = -2874599657200493002L;
	private Integer 						idActa;
	private String 							numeroActa;
	private TipoActaAutorizacion 			tipoActaAutorizacion;
	private Date							fechaActa;
	private String							observaciones;
	private List<DetalleActaAutorizacion> 	actaActivos;
	private List<DetalleActaSalida> 		detalleActaSalidas;
	
	
	public ActaAutorizacion() {
		super();
	}
	
	public ActaAutorizacion(Integer idActa, String numeroActa,
			TipoActaAutorizacion tipoActaAutorizacion, Date fechaActa,
			String observaciones) {
		super();
		this.idActa = idActa;
		this.numeroActa = numeroActa;
		this.tipoActaAutorizacion = tipoActaAutorizacion;
		this.fechaActa = fechaActa;
		this.observaciones = observaciones;
	}
	
	
	@SequenceGenerator(name="Seq_idActaAutorizacion", sequenceName="sigesp.saf_acta_autorizacion_seq_ser_idactaautori_seq", allocationSize=1)
	@Column(name="seq_ser_idactaautori")
	@Id @GeneratedValue(generator="Seq_idActaAutorizacion")
	public Integer getIdActa() {
		return idActa;
	}
	public void setIdActa(Integer idActa) {
		this.idActa = idActa;
	}
	
	@Column(name="str_nroacta")
	public String getNumeroActa() {
		return numeroActa;
	}
	public void setNumeroActa(String numeroActa) {
		this.numeroActa = numeroActa;
	}
	
	@OneToOne
	@JoinColumn(name="int_idtipoacta")
	public TipoActaAutorizacion getTipoActaAutorizacion() {
		return tipoActaAutorizacion;
	}
	public void setTipoActaAutorizacion(TipoActaAutorizacion tipoActaAutorizacion) {
		this.tipoActaAutorizacion = tipoActaAutorizacion;
	}
	
	@Column(name="dtm_fechaacta")
	public Date getFechaActa() {
		return fechaActa;
	}
	public void setFechaActa(Date fechaActa) {
		this.fechaActa = fechaActa;
	}
	
	@Column(name="str_observaciones")
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="actaAutorizacion", targetEntity=DetalleActaAutorizacion.class)
	public List<DetalleActaAutorizacion> getActaActivos() {
		return actaActivos;
	}

	public void setActaActivos(List<DetalleActaAutorizacion> actaActivos) {
		this.actaActivos = actaActivos;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="actaAutorizacion", targetEntity=DetalleActaAutorizacion.class)
	public List<DetalleActaSalida> getDetalleActaSalidas() {
		return detalleActaSalidas;
	}

	public void setDetalleActaSalidas(List<DetalleActaSalida> detalleActaSalidas) {
		this.detalleActaSalidas = detalleActaSalidas;
	}

	@Transient
	public String getNombreTipoActa(){
		return getTipoActaAutorizacion().getDescripcion();
	}
	
	@Transient
	public String getFechaString(){
		return Fecha.obtenerFecha(getFechaActa());
	}
	
	
	public String toString() {
		return getNumeroActa();
	}
	
	public boolean equals(Object objeto){
		try{
			ActaAutorizacion cuenta = (ActaAutorizacion) objeto;
			if (cuenta.getIdActa().equals(idActa))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
}

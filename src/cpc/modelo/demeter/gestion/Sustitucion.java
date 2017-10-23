package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.mantenimiento.Falla;

@Audited @Entity
@Table(name="tbl_dem_sustitucion", schema = "gestion")
public class Sustitucion implements Serializable{
	
	private static final long 		serialVersionUID = 6504439416785033934L;
	private Integer 				id;
	private Date 					fechaRegistro;	
	private Trabajador  			operadorAnterior;
	private Trabajador  			operadorActual;
	private MaquinariaUnidad  		maquinaAnterior;
	private MaquinariaUnidad  		maquinaActual;
	private ImplementoUnidad  		implemantoAnterior;
	private ImplementoUnidad  		implementoActual;
	private String					causa;
	private boolean 				causaPorFallaMaq;
	private Falla 					falla;
	private String 					observacion;
	private List<SustitucionOrden>  sustitucionesOrden;
	
	@Column(name="seq_idsustitucion")
	@SequenceGenerator(name="Sustitucion_Gen", sequenceName="gestion.tbl_dem_sustitucion_seq_idsustitucion_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="Sustitucion_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="dtm_fecharegistro")
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}	
	
	@ManyToOne
	@JoinColumn(name="int_idoperanterior")
	public Trabajador getOperadorAnterior() {
		return operadorAnterior;
	}
	public void setOperadorAnterior(Trabajador operadorAnterior) {
		this.operadorAnterior = operadorAnterior;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idoperactual")
	public Trabajador getOperadorActual() {
		return operadorActual;
	}
	public void setOperadorActual(Trabajador operadorActual) {
		this.operadorActual = operadorActual;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idmaqanterior")
	public MaquinariaUnidad getMaquinaAnterior() {
		return maquinaAnterior;
	}
	public void setMaquinaAnterior(MaquinariaUnidad maquinaAnterior) {
		this.maquinaAnterior = maquinaAnterior;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idmaqactual")
	public MaquinariaUnidad getMaquinaActual() {
		return maquinaActual;
	}
	public void setMaquinaActual(MaquinariaUnidad maquinaActual) {
		this.maquinaActual = maquinaActual;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idimpanterior")
	public ImplementoUnidad getImplemantoAnterior() {
		return implemantoAnterior;
	}
	public void setImplemantoAnterior(ImplementoUnidad implemantoAnterior) {
		this.implemantoAnterior = implemantoAnterior;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idimpactual")
	public ImplementoUnidad getImplementoActual() {
		return implementoActual;
	}
	public void setImplementoActual(ImplementoUnidad implementoActual) {
		this.implementoActual = implementoActual;
	}
	
	@Column(name="bol_causaporfallamaq")
	public boolean getCausaPorFallaMaq() {
		return causaPorFallaMaq;
	}
	public void setCausaPorFallaMaq(boolean causaPorFallaMaq) {
		this.causaPorFallaMaq = causaPorFallaMaq;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idfallaobjeto")
	public Falla getFalla() {
		return falla;
	}
	public void setFalla(Falla falla) {
		this.falla = falla;
	}
	
	@Column(name="str_causa")
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
	
	@Column(name="str_observacion")
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="sustitucion", targetEntity=SustitucionOrden.class)
	public List<SustitucionOrden> getSustitucionesOrden() {
		return sustitucionesOrden;
	}
	public void setSustitucionesOrden(List<SustitucionOrden> sustitucionesOrden) {
		this.sustitucionesOrden = sustitucionesOrden;
	}
	
	@Transient
	public String getOrdenesAfectadas(){
		StringBuilder cadena = new StringBuilder();
		for (SustitucionOrden so : getSustitucionesOrden()){
			cadena.append(so.getOrdenTrabajo().getNroControl());
			cadena.append("\n");
		}
		return cadena.toString();
	}
	
	public boolean equals(Object objeto){
		try{
			Sustitucion obj = (Sustitucion) objeto;
			return (obj.getId().equals(id));
				
		}catch (Exception e) {
			return false;
		}
	}
	
	
}

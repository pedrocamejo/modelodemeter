package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.basico.CicloProductivo;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.PlanServicio;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;

@Audited @Entity
@Table(name="tbl_dem_orden_servicio_mecanizado", schema="gestion")
@PrimaryKeyJoinColumn(name="int_idordenservicio")
public class OrdenTrabajoMecanizado extends OrdenTrabajo implements Serializable{

	private static final long serialVersionUID = 4416331005981441564L;
	private Rubro							rubro;
	private InstitucionCrediticia			financiamiento;
	private CicloProductivo					ciclo;
	private PlanServicio					plan;
	private UnidadProductiva				unidadProductiva;
	private Productor						productor;
	private Date							inicioServicio;
	private Integer							diasEspera;
	private Contrato			contrato;
	private LaborOrdenServicio				laborOrden;
	private Trabajador						tecnicoCampo;
	private Boolean							produccion;
	private Boolean							actaProduccion;
	private Boolean							transportado;
	private Double							cantidadFisicaTrabajada;
	private Double							cantidadLaborada;
	private Double							cantidadRealProducida;
	
	
	private List<TrabajoRealizadoMecanizado>trabajosRealizadosMecanizado;	
	
	@ManyToOne
	@JoinColumn(name="int_idrubro")
	public Rubro getRubro() {
		return rubro;
	}
	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idcicloproductivo")
	public CicloProductivo getCiclo() {
		return ciclo;
	}
	public void setCiclo(CicloProductivo ciclo) {
		this.ciclo = ciclo;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadproductiva")
	public UnidadProductiva getUnidadProductiva() {
		return unidadProductiva;
	}
	public void setUnidadProductiva(UnidadProductiva unidadProductiva) {
		this.unidadProductiva = unidadProductiva;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idproductor")
	public Productor getProductor() {
		return productor;
	}
	public void setProductor(Productor productor) {
		this.productor = productor;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_inicio")
	public Date getInicioServicio() {
		return inicioServicio;
	}
	public void setInicioServicio(Date inicioServicio) {
		this.inicioServicio = inicioServicio;
	}
	
	@Column(name="int_diasespera")
	@Basic(optional=true)
	public Integer getDiasEspera() {
		return diasEspera;
	}
	public void setDiasEspera(Integer diasEspera) {
		this.diasEspera = diasEspera;
	}
	
	@ManyToOne 
	@JoinColumn(name="int_idcontrato")
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idplan")
	public PlanServicio getPlan() {
		return plan;
	}
	public void setPlan(PlanServicio plan) {
		this.plan = plan;
	}

	@OneToOne(mappedBy="orden", targetEntity=LaborOrdenServicio.class, cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	public LaborOrdenServicio getLaborOrden() {
		return laborOrden;
	}
	public void setLaborOrden(LaborOrdenServicio laborOrden) {
		this.laborOrden = laborOrden;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idfinanciamiento")
	public InstitucionCrediticia getFinanciamiento() {
		return financiamiento;
	}
	public void setFinanciamiento(InstitucionCrediticia financiamiento) {
		this.financiamiento = financiamiento;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtecnico")
	public Trabajador getTecnicoCampo() {
		return tecnicoCampo;
	}
	public void setTecnicoCampo(Trabajador tecnicoCampo) {
		this.tecnicoCampo = tecnicoCampo;
	}
	
	@Column(name="bol_produccion")
	public Boolean getProduccion() {
		return produccion;
	}
	public void setProduccion(Boolean produccion) {
		this.produccion = produccion;
	}
	
	@Column(name="bol_actaproduccion")
	public Boolean getActaProduccion() {
		return actaProduccion;
	}
	public void setActaProduccion(Boolean actaProduccion) {
		this.actaProduccion = actaProduccion;
	}
	
	@Column(name="bol_transportado")
	public Boolean getTransportado() {
		return transportado;
	}
	public void setTransportado(Boolean transportado) {
		this.transportado = transportado;
	}
	
	@Column(name="dbl_trabajofisico")
	public Double getCantidadFisicaTrabajada() {
		return cantidadFisicaTrabajada;
	}
	public void setCantidadFisicaTrabajada(Double cantidadFisicaTrabajada) {
		this.cantidadFisicaTrabajada = cantidadFisicaTrabajada;
	}
	
	@Column(name="dbl_produccionreal")
	public Double getCantidadRealProducida() {
		return cantidadRealProducida;
	}
	public void setCantidadRealProducida(Double cantidadRealProducida) {
		this.cantidadRealProducida = cantidadRealProducida;
	}
	
	
	@Column(name="dbl_trabajolabor")
	public Double getCantidadLaborada() {
		return cantidadLaborada;
	}
	public void setCantidadLaborada(Double cantidadLaborada) {
		this.cantidadLaborada = cantidadLaborada;
	}
	
	@Transient
	public String getStrProductor(){
		if (productor == null) return "";
		return productor.getNombres();
	}
	
	@Transient
	public String getStrRubro(){
		if (rubro == null) return "";
		return rubro.getDescripcion();
	}
	
	@Transient
	public String getStrServicio(){
		try{
			Labor labor = getLaborOrden().getLabor();
			return labor.getServicio().getDescripcion();
		}catch (Exception e) {
			return "";
		}
	}
	
	@Transient
	public String getStrLabor(){
		try{
			Labor labor = getLaborOrden().getLabor();
			return labor.getDescripcion();
		}catch (Exception e) {
			return "";
		}
	}
	
	@Transient
	public String getStrSector(){
		try{
			UbicacionSector sector = getUnidadProductiva().getSector();
			return sector.getNombre();
		}catch (Exception e) {
			return "";
		}
	}
	
	@Transient
	public List<TrabajoRealizadoMecanizado> getTrabajosRealizadosMecanizado(){
		return trabajosRealizadosMecanizado;
	}

	@Transient
	public void setTrabajosRealizadosMecanizado(List<TrabajoRealizadoMecanizado> entrada){
		trabajosRealizadosMecanizado = entrada;
	}
}

package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import cpc.modelo.sigesp.basico.Modelo;

@Audited
@AuditTable(schema="auditoria",value="tbl_dem_TipoGarantia_aud")
@Entity
@Table(name="tbl_dem_TipoGarantia" ,schema="mantenimiento")
//para capturar mas data de la maquinaria  y para tener cuales van hacer los detalles de la garantia 
public class TipoGarantia implements Serializable{
	
	private Integer 	id;
	private String  	descripcion;
	private Modelo  	modelo; // modelo de la maquinaria
	private String 		urlReporte;// reporte para la Garantia 
	private TipoMaquinariaVenta tipo; // agricola via ect 
	
	private Float  		peso;
	private String 		motortipo;
	private String  	potencia;
	private String 		fuerza;
	private String 		colorPrimario;
	private String 		colorSecundario;
	private Serie 		serie;
	
	private ClaseMaquinaria clase;   
	
	private Modelo		modeloMotor;

	
	private List<DetalleGarantina>  detallesGarantia = new ArrayList<DetalleGarantina>();
	
	// detalle de la Garantia lo que la misma posee 
	// por ejemeplo si posee mantenimiento 1 ,2, 3, 4 Puesta en Marcha ect. 
 	

	@Column(name="seq_idTipoGarantia")
	@SequenceGenerator(name="TipoGarantia_Gen", sequenceName="mantenimiento.tbl_dem_TipoGarantia_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="TipoGarantia_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="int_idmodelo",unique=true)
	public Modelo getModelo() {
		return modelo;
	}
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	 
	@Column
	public String getUrlReporte() {
		return urlReporte;
	}
	
	public void setUrlReporte(String urlReporte) {
		this.urlReporte = urlReporte;
	}
 
	@OneToMany(targetEntity=DetalleGarantina.class,orphanRemoval=true,cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="tipoGarantia")
	public List<DetalleGarantina> getDetallesGarantia() {
		return detallesGarantia;
	}
	public void setDetallesGarantia(List<DetalleGarantina> detallesGarantia) {
		this.detallesGarantia = detallesGarantia;
	} 
	
	@Transient
	public String getModeloSTR()
	{
		return this.modelo.getDescripcionModelo();
	}
	@Transient
	public String getMarcaSTR()
	{
		return this.modelo.getDescripcionMarca();
	}


	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="int_idmaquinariaventa")
	public TipoMaquinariaVenta getTipo() {
		return tipo;
	}
	public void setTipo(TipoMaquinariaVenta tipo) {
		this.tipo = tipo;
	}
	
	@Column
	public String getColorPrimario() {
		return colorPrimario;
	}
	public void setColorPrimario(String colorPrimario) {
		this.colorPrimario = colorPrimario;
	}
	
	
	@Column
	public String getColorSecundario() {
		return colorSecundario;
	}
	public void setColorSecundario(String colorSecundario) {
		this.colorSecundario = colorSecundario;
	}
	@Column
	public Float getPeso() {
		return peso;
	}
	public void setPeso(Float peso) {
		this.peso = peso;
	}
	
	@Column
	public String getMotortipo() {
		return motortipo;
	}
	
	public void setMotortipo(String motortipo) {
		this.motortipo = motortipo;
	}
	
	@Column
	public String getPotencia() {
		return potencia;
	}
	public void setPotencia(String potencia) {
		this.potencia = potencia;
	}
	
	@Column
	public String getFuerza() {
		return fuerza;
	}
	public void setFuerza(String fuerza) {
		this.fuerza = fuerza;
	}
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="int_idmaquinariaclase")
	public ClaseMaquinaria getClase() {
		return clase;
	}
	public void setClase(ClaseMaquinaria clase) {
		this.clase = clase;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="int_idmodelomotor")
	public Modelo getModeloMotor() {
		return modeloMotor;
	}
	public void setModeloMotor(Modelo modeloMotor) {
		this.modeloMotor = modeloMotor;
	}

	@OneToOne()
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="int_idnroserie",nullable=false)
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	
	
	@Transient
	public String getColores()
	{
		return colorPrimario+"/"+colorSecundario;
	}
	
} 


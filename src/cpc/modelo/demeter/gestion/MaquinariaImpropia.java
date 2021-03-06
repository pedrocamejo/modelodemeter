package cpc.modelo.demeter.gestion;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.gestion.pk.MaquinariaImpropiapk;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.Categoria;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.Tipo;


@Audited 
@Entity
@Table(name="tbl_dem_maquinaria_impropia", schema="gestion")
@IdClass(MaquinariaImpropiapk.class)
public class MaquinariaImpropia implements Serializable {

	private Integer 			id ;
	private String 				sede;
	
	private String				serialChasis;
	private String				serialOtro;
	private String				procedencia;
	private String				nombre;
   	private Marca				marca;
   	private Modelo      		modelo;
   	private Categoria 			categoria;
   	private Tipo 				tipo;
  	private Almacen     		almacen;
  	
   	private Boolean 			trasladado;
   	private Boolean  			desincorporado;
   	
   	private String 				observacion;
   	
   	private EstadoMaquinariaImpropia estado;
   	
   	private MaquinariaUnidad 	maquinariaUnidad;

   	 
   	

   	@SequenceGenerator(name="SeqMaquinariaImpropia",
    	sequenceName="gestion.tbl_dem_maquinaria_impropia_seq_idmaquinariaimpropia_seq", allocationSize=1)
	@Column(name="seq_idmaquinariaimpropia")
	@Id 
	@GeneratedValue(generator="SeqMaquinariaImpropia")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Id
	@Column(name="sede_id")
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}

	@Column(name="str_serialchasis",unique=true,length=35)
	public String getSerialChasis() {
		return serialChasis;
	}
	public void setSerialChasis(String serialChasis) {
		this.serialChasis = serialChasis;
	}
	
	@Column(name="str_serialotro",unique=true,length=35)
	public String getSerialOtro() {
		return serialOtro;
	}
	
	public void setSerialOtro(String serialOtro) {
		this.serialOtro = serialOtro;
	}
	
	@Column(name="str_procedencia",length=250)	
	public String getProcedencia() {
		return procedencia;
	}
	
	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	@Column(name="str_nombre",length=50)	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@ManyToOne
	@JoinColumn(name="marca")
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	@ManyToOne
	@JoinColumn(name="modelo")
	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	@ManyToOne
	@JoinColumn(name="categoria")
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@ManyToOne
	@JoinColumn(name="tipo")
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	@OneToOne
	@JoinColumn(name="almacen_id")
	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	
	
	@Column(name="bol_trasladado")
	public Boolean getTrasladado() {
		return trasladado;
	}

	public void setTrasladado(Boolean trasladado) {
		this.trasladado = trasladado;
	}

	@Column(name="bol_desincorporado")
	public Boolean getDesincorporado() {
		return desincorporado;
	}

	public void setDesincorporado(Boolean desincorporado) {
		this.desincorporado = desincorporado;
	}


	
	@Transient
	public String getDescripcionMarca(){
		return getMarca().getDescripcion();
	}
	
	@Transient
	public String getDescripcionModelo(){
		return getModelo().getDescripcionModelo();
	}
	
	@Transient
	public String getDescripcionCategoria(){
		return getCategoria().getDescripcionCategoria();
	}
	
	@Transient
	public String getDescripcionTipo(){
		return getTipo().getDescripcionTipo();
	}


	
	@Override
	public String toString (){
		return getDescripcionLarga();
	}

	@Transient
	public String getDescripcionLarga (){
		StringBuilder salida = new StringBuilder();
		salida.append(getNombre().trim());
		salida.append(", SN: ");
		salida.append(getSerialChasis().trim());
		salida.append(", SN: ");
		salida.append(getSerialOtro().trim());
		return salida.toString();
	}
	
	@ManyToOne
	@JoinColumn(name="estado",nullable=false)
	public EstadoMaquinariaImpropia getEstado() {
		return estado;
	}
	public void setEstado(EstadoMaquinariaImpropia estado) {
		this.estado = estado;
	}
	
	@OneToOne(mappedBy="maquinariaImpropia",fetch=FetchType.EAGER)
	public MaquinariaUnidad getMaquinariaUnidad() {
		return maquinariaUnidad;
	}
	public void setMaquinariaUnidad(MaquinariaUnidad maquinariaUnidad) {
		this.maquinariaUnidad = maquinariaUnidad;
	}

	
	@Transient
	public String getStrEstado() {
		return estado.getDescripcion();
	}
	

	
	@Transient
	public String getStrMarca() {
		return marca.getDescripcion();
	}

	@Transient
	public String getStrModelo() {
		return modelo.toString();
	}
	
	@Transient
	public String getStrCategoria() {
		return categoria.getDescripcionCategoria();
	}

	@Transient
	public String getStrTipo() {
		return tipo.getDescripcionTipo();
	}

	@Column(length=255)
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
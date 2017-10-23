package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity; import org.hibernate.envers.Audited;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;



import cpc.modelo.sigesp.indice.ActivoPK;



@Audited @Entity
@Table(name="viw_activo", schema="sigesp", uniqueConstraints={@UniqueConstraint(columnNames={"codemp","codact","ideact"})})
//@IdClass(ActivoPK.class)
public class Activo implements Serializable {

	private static final long serialVersionUID = 9028628801755086979L;
	/*private String		empresa;
	private String		codigoActivo;
	private String		idEjemplarActivo;*/
	private ActivoPK 	id;
	private String		serial;
	private String		chapa;
    private String		nombre;
    private Marca		marca;
    private Modelo      modelo;
    private Categoria 	categoria;
    private Tipo 		tipo;
    private Integer     almacen;
    private Boolean 	trasladado, desincorporado;
    
	
    private ActivoAlmacen activoAlmacen;
    
	public Activo() {
		super();
	}
	
	@EmbeddedId
	public ActivoPK getId() {
		return id;
	}

	public void setId(ActivoPK id) {
		this.id = id;
	}

	@Column(name="seract")
	public String getSerial() {
		return serial;
	}
	
	public void setSerial(String serial) {
		this.serial = serial;
	}

	@Column(name="idchapa")
	public String getChapa() {
		return chapa;
	}

	public void setChapa(String chapa) {
		this.chapa = chapa;
	}
	
	@Column(name="denact")
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

	@Column(name="int_idalmacen")
	@Basic(optional= true)
	public Integer getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Integer almacen) {
		this.almacen = almacen;
	}

	@OneToOne(mappedBy="activo", targetEntity=ActivoAlmacen.class, optional=true)
	public ActivoAlmacen getActivoAlmacen() {
		return activoAlmacen;
	}
	public void setActivoAlmacen(ActivoAlmacen activoAlmacen) {
		this.activoAlmacen = activoAlmacen;
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
	public String getEmpresa() {
		return this.id.getEmpresa();
	}
	
	@Transient
	public String getCodigoActivo() {
		return this.id.getCodigoActivo();
	}
	
	@Transient
	public String getIdEjemplarActivo() {
		return this.id.getIdEjemplarActivo();
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

	public boolean equals(Object objeto){
		try{
			Activo activo = (Activo) objeto;
			if (activo.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	public String toString (){
		return getDescripcionLarga();
	}
	@Transient
	public String getDescripcionLarga (){
		StringBuilder salida = new StringBuilder();
		salida.append(getMarca().getDescripcion().trim());
		salida.append(", ");
		salida.append(getModelo().getDescripcionModelo().trim());
		salida.append(", ");
		salida.append(getNombre().trim());
		salida.append(", SN: ");
		salida.append(getSerial().trim());
		return salida.toString();
	}
}

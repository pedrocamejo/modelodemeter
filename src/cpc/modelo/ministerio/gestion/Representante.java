package cpc.modelo.ministerio.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; 

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.ministerio.basico.EstadoCivil;
import cpc.modelo.ministerio.basico.Genero;
import cpc.modelo.ministerio.basico.GradoInstruccion;
import cpc.modelo.ministerio.basico.Nacionalidad;

@Audited @Entity
@Table(name="tbl_dem_representante", schema="ministerio")
public class Representante implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7806659966807208129L;
	private Integer			id;
	private	String			cedula;
	private String			primerNombre;
	private String			segundoNombre;
	private String			primerApellido;
	private String			segundoApellido;
	
	private Nacionalidad	nacionalidad;
	private Date			fechaNacimiento;
	private Genero			genero;
	private EstadoCivil		estadoCivil;
	private GradoInstruccion gradoInstruccion;
	private String			email;
	
	private List<ProductorJuridico>	productores;
	//private List<Telefono>	telefonos;
	
	
	@SequenceGenerator(name="seqRepresentante", sequenceName="ministerio.tbl_dem_representante_seq_idrepresentante_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="seqRepresentante")
	@Column(name="seq_idrepresentante", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_cedula", unique=true, nullable=false)
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	@Column(name="str_primernombre")
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	
	@Column(name="str_segundonombre")
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	
	@Column(name="str_primerapellido")
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	
	@Column(name="str_segundoapellido")
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_nacimiento")
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	@Column(name="str_email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idgenero")
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idestadocivil")
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idgradoinstruccion")
	public GradoInstruccion getGradoInstruccion() {
		return gradoInstruccion;
	}
	public void setGradoInstruccion(GradoInstruccion gradoInstruccion) {
		this.gradoInstruccion = gradoInstruccion;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idnacionalidad")
	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	@NotAudited
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="tbl_dem_representanteproductorjuridico", schema="ministerio", 
			joinColumns= {@JoinColumn(name="int_idrepresentante", referencedColumnName="seq_idrepresentante")},
			inverseJoinColumns={@JoinColumn(name="int_idproductor", referencedColumnName="int_idproductor")}
	)
	public List<ProductorJuridico> getProductores() {
		return productores;
	}
	public void setProductores(List<ProductorJuridico> productores) {
		this.productores = productores;
	}
	
	@Transient
	public String  getNombres(){
		return primerNombre+" "+segundoNombre;
	}
	
	@Transient
	public String  getApellidos(){
		return primerApellido+" "+segundoApellido;
	}

	@Transient
	public String  getNombreCompleto(){
		return primerNombre+" "+segundoNombre+", "+primerApellido+" "+segundoApellido;
	}
	
	@Transient
	public void addProductor(ProductorJuridico productor){
		boolean valido = true;
		if (productores== null)
			productores = new ArrayList<ProductorJuridico>();
		else{
			for (ProductorJuridico item : productores) {
				if (item != null)
					if (item.getId() == productor.getId()){
						valido =false;
						break;
					}
			}
		}
		if (valido)
			productores.add(productor);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((estadoCivil == null) ? 0 : estadoCivil.hashCode());
		result = prime * result
				+ ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((genero == null) ? 0 : genero.hashCode());
		result = prime
				* result
				+ ((gradoInstruccion == null) ? 0 : gradoInstruccion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nacionalidad == null) ? 0 : nacionalidad.hashCode());
		result = prime * result
				+ ((primerApellido == null) ? 0 : primerApellido.hashCode());
		result = prime * result
				+ ((primerNombre == null) ? 0 : primerNombre.hashCode());
		result = prime * result
				+ ((productores == null) ? 0 : productores.hashCode());
		result = prime * result
				+ ((segundoApellido == null) ? 0 : segundoApellido.hashCode());
		result = prime * result
				+ ((segundoNombre == null) ? 0 : segundoNombre.hashCode());
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
		Representante other = (Representante) obj;
		if (cedula == null) {
			if (other.cedula != null)
				return false;
		} else if (!cedula.equals(other.cedula))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (estadoCivil == null) {
			if (other.estadoCivil != null)
				return false;
		} else if (!estadoCivil.equals(other.estadoCivil))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (genero == null) {
			if (other.genero != null)
				return false;
		} else if (!genero.equals(other.genero))
			return false;
		if (gradoInstruccion == null) {
			if (other.gradoInstruccion != null)
				return false;
		} else if (!gradoInstruccion.equals(other.gradoInstruccion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nacionalidad == null) {
			if (other.nacionalidad != null)
				return false;
		} else if (!nacionalidad.equals(other.nacionalidad))
			return false;
		if (primerApellido == null) {
			if (other.primerApellido != null)
				return false;
		} else if (!primerApellido.equals(other.primerApellido))
			return false;
		if (primerNombre == null) {
			if (other.primerNombre != null)
				return false;
		} else if (!primerNombre.equals(other.primerNombre))
			return false;
	if (productores == null) {
			if (other.productores != null)
				return false;
		} else if (!productores.equals(other.productores))
			return false;
		if (segundoApellido == null) {
			if (other.segundoApellido != null)
				return false;
		} else if (!segundoApellido.equals(other.segundoApellido))
			return false;
		if (segundoNombre == null) {
			if (other.segundoNombre != null)
				return false;
		} else if (!segundoNombre.equals(other.segundoNombre))
			return false;
		return true;
	}
	
}

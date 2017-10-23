package cpc.modelo.ministerio.gestion;

import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.ministerio.basico.EstadoCivil;
import cpc.modelo.ministerio.basico.Genero;
import cpc.modelo.ministerio.basico.GradoInstruccion;
import cpc.modelo.ministerio.basico.Nacionalidad;


@Audited @Entity
@Table(name="tbl_dem_productornatural", schema="ministerio")
@PrimaryKeyJoinColumn(name="int_idproductor")
public class ProductorNatural extends Productor{

	private static final long serialVersionUID = 4599319848697044302L;
	private String				primerNombre;
	private String				segundoNombre;
	private String				primerApellido;
	private String				segundoApellido;
	
	private Boolean				militante;
	private GradoInstruccion	gradoInstruccion;
	private EstadoCivil			estadoCivil;
	private Nacionalidad		nacionalidad;
	private String				email;
	private Genero				genero; 			//(femenino  - falso)
	private Integer				cargaFamiliar;
	
	
	public ProductorNatural(){
		
	}
	
	public ProductorNatural(Productor padre){
		if (padre != null){
			setId(padre.getId()) ;
			setTipo(padre.getTipo());
			setIdentidadLegal(padre.getIdentidadLegal());		
			setNombres(padre.getNombres());			
			setActivo(padre.isActivo());
			setTelefonos(padre.getTelefonos());
			setDireccion(padre.getDireccion());
			setUnidadAsociado(padre.getUnidadAsociado());
			setOrganizado(padre.isOrganizado());
			setFechaIngreso(padre.getFechaIngreso());
			setFechaNacimiento(padre.getFechaNacimiento());  	
			setFinanciamientos(padre.getFinanciamientos());
			setOrganizacion(padre.getOrganizacion());	
			setUnidadesproduccion(padre.getUnidadesproduccion());
		}
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
	
	@Column(name="bol_militante")
	public Boolean isMilitante() {
		return militante;
	}
	public void setMilitante(Boolean militante) {
		this.militante = militante;
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
	@JoinColumn(name="int_idestadocivil")
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idnacionalidad")
	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
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
	
	@Column(name="int_cargafamiliar")
	public Integer getCargaFamiliar() {
		return cargaFamiliar;
	}
	public void setCargaFamiliar(Integer cargaFamiliar) {
		this.cargaFamiliar = cargaFamiliar;
	}
	
	@Transient
	public void actualizarNombres(){
		setNombres(primerNombre+" "+segundoNombre+", "+primerApellido+" "+segundoApellido);
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductorNatural other = (ProductorNatural) obj;
		if (cargaFamiliar == null) {
			if (other.cargaFamiliar != null)
				return false;
		} else if (!cargaFamiliar.equals(other.cargaFamiliar))
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
		if (militante == null) {
			if (other.militante != null)
				return false;
		} else if (!militante.equals(other.militante))
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

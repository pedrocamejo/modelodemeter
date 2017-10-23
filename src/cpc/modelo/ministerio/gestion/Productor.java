package cpc.modelo.ministerio.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; 

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UnidadFuncional;


@Audited @Entity
@Table(name="tbl_dem_productor", schema="ministerio")
@PrimaryKeyJoinColumn(name="seq_idproductor", referencedColumnName= "seq_idcliente")
@Inheritance(strategy = InheritanceType.JOINED)

public class Productor extends Cliente implements Serializable{
	
	private static final long serialVersionUID = -3854043611787163995L;

	private UnidadFuncional			unidadAsociado;
	private Boolean					organizado;
	private Date					fechaIngreso;
	private Date					fechaNacimiento;  	//Nacimiento o creación
	
	private List<InstitucionCrediticia>	financiamientos;
	private Organizacion			organizacion;	
	private List<UnidadProductiva> 	unidadesproduccion;
	private Boolean					agroVenezuela;
	
	public Productor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Productor(Integer id, String identidadLegal, String nombres,
			String direccion) {
		super(id, identidadLegal, nombres, direccion);
		// TODO Auto-generated constructor stub
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadfuncional")
	public UnidadFuncional getUnidadAsociado() {
		return unidadAsociado;
	}
	public void setUnidadAsociado(UnidadFuncional unidadAsociado) {
		this.unidadAsociado = unidadAsociado;
	}
	
	@Column(name="bol_organizado")
	public Boolean isOrganizado() {
		return organizado;
	}
	public void setOrganizado(Boolean organizado) {
		this.organizado = organizado;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_ingreso")
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idorganizacion")
	public Organizacion getOrganizacion() {
		return organizacion;
	}
	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_nacimiento")
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@OneToMany(mappedBy="productor", targetEntity=UnidadProductiva.class)
	public List<UnidadProductiva> getUnidadesproduccion() {
		return unidadesproduccion;
	}
	public void setUnidadesproduccion(List<UnidadProductiva> unidadesproduccion) {
		this.unidadesproduccion = unidadesproduccion;
	}
	@NotAudited
	@ManyToMany
	@JoinTable(
		      name="tbl_dem_productorfinanciamiento", schema="ministerio", 
		      joinColumns= {@JoinColumn(name="int_idproductor", referencedColumnName="seq_idproductor")},
		      inverseJoinColumns={@JoinColumn(name="int_idinstitucionf", referencedColumnName="seq_idinstitucionf")}
	)
	@Basic(optional= true)
	public List<InstitucionCrediticia> getFinanciamientos() {
		return financiamientos;
	}
	public void setFinanciamientos(List<InstitucionCrediticia> financiamientos) {
		this.financiamientos = financiamientos;
	}
	
	
	@Column(name="bol_agrovenezuela")
	public Boolean getAgroVenezuela() {
		return agroVenezuela;
	}
	public void setAgroVenezuela(Boolean agroVenezuela) {
		this.agroVenezuela = agroVenezuela;
	}

	@Transient
	public String getStrOrganizacion() {
		if (organizacion == null)
			return null;
		return organizacion.getDenotacion();
	}
	
	@Transient
	public String getSrtFechaIngreso() {
		if (fechaIngreso == null)
			return "";
		return fechaIngreso.toString();
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Productor other = (Productor) obj;
		if (agroVenezuela == null) {
			if (other.agroVenezuela != null)
				return false;
		} else if (!agroVenezuela.equals(other.agroVenezuela))
			return false;
		if (fechaIngreso == null) {
			if (other.fechaIngreso != null)
				return false;
		} else if (!fechaIngreso.equals(other.fechaIngreso))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (financiamientos == null) {
			if (other.financiamientos != null)
				return false;
		} else if (!financiamientos.equals(other.financiamientos))
			return false;
		if (organizacion == null) {
			if (other.organizacion != null)
				return false;
		} else if (!organizacion.equals(other.organizacion))
			return false;
		if (organizado == null) {
			if (other.organizado != null)
				return false;
		} else if (!organizado.equals(other.organizado))
			return false;
		if (unidadAsociado == null) {
			if (other.unidadAsociado != null)
				return false;
		} else if (!unidadAsociado.equals(other.unidadAsociado))
			return false;
		if (unidadesproduccion == null) {
			if (other.unidadesproduccion != null)
				return false;
		} else if (!unidadesproduccion.equals(other.unidadesproduccion))
			return false;
		return true;
	}
	
	@Transient
	public List<UbicacionDireccion> getDirecciones() {
		List<UbicacionDireccion> direcciones = new ArrayList<UbicacionDireccion>();
		for (UnidadProductiva item : unidadesproduccion) {
			direcciones.add(item.getUbicacion());
		} 
		return direcciones;
	}
	
	public String toString(){
		return getNombres();
	}

	
	
}

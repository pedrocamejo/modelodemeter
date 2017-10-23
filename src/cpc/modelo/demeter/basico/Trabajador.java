package cpc.modelo.demeter.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.CascadeType;

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
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_trabajador", schema = "basico")
public class Trabajador implements Serializable{
	
	private static final long serialVersionUID = -6855874707078008282L;
	
	private Integer					id; 
	private String					nroCedula; 
	private String					nombre; 
	private String					apellido; 
	private TipoTrabajador			tipoTrabajador;
	private CargoTrabajador			cargo;
	private List<FuncionTrabajador>	funciones;
	
	private String					direccion;
	private String					tlfFijo;
	private String					tlfMovil;
	

	
	public Trabajador() {
		super();
	}

	@SequenceGenerator(name="SeqTrabajador", sequenceName="basico.tbl_dem_trabajador_seq_idtrabajador_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="SeqTrabajador")
	@Column(name="seq_idtrabajador")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_cedula")
	public String getNroCedula() {
		return nroCedula;
	}
	public void setNroCedula(String nro_cedula) {
		this.nroCedula = nro_cedula;
	}
	
	@Column(name="str_nombres")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="str_apellidos")
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Column(name="str_direccion")
	@Basic(optional=true)
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name="str_telfijo")
	@Basic(optional=true)
	public String getTlfFijo() {
		return tlfFijo;
	}

	public void setTlfFijo(String tlfFijo) {
		this.tlfFijo = tlfFijo;
	}

	@Column(name="str_telmovil")
	@Basic(optional=true)
	public String getTlfMovil() {
		return tlfMovil;
	}

	public void setTlfMovil(String tlfMovil) {
		this.tlfMovil = tlfMovil;
	}


	@ManyToOne
	@JoinColumn(name="int_tipo")
	public TipoTrabajador getTipoTrabajador() {
		return tipoTrabajador;
	}
	public void setTipoTrabajador(TipoTrabajador tipoTrabajador) {
		this.tipoTrabajador = tipoTrabajador;
	} 
	

	@ManyToOne
	@JoinColumn(name="int_idcargo")
	public CargoTrabajador getCargo() {
		return cargo;
	}
	public void setCargo(CargoTrabajador cargo) {
		this.cargo = cargo;
	}

//@NotAudited
/*	@ManyToMany
	@JoinTable(name="tbl_dem_trabajador_funcion", schema="basico", 
		joinColumns={@JoinColumn(name="int_idtrabajador")},
		inverseJoinColumns={@JoinColumn(name="int_idfunciontraba")})*/

@ManyToMany(fetch=FetchType.EAGER,targetEntity=FuncionTrabajador.class)

@JoinTable(name="tbl_dem_trabajador_funcion", schema="basico", 
joinColumns={@JoinColumn(name="int_idtrabajador")},
inverseJoinColumns={@JoinColumn(name="int_idfunciontraba")})

/*@ManyToMany
@JoinColumn(name ="seq_idtrabajador",referencedColumnName="int_idtrabajador")*/
	public List<FuncionTrabajador> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<FuncionTrabajador> funciones) {
		this.funciones = funciones;
	}
	
	@Transient
	public String getStrTipoTrabajador() {
		if (tipoTrabajador == null)
			return "";
		return tipoTrabajador.getDescripcion();
	}
	
	@Transient
	public String getStrCargo() {
		if (cargo == null)
			return "";
		return cargo.getDescripcion();
	}
	
	public String toString(){
		StringBuilder salida = new StringBuilder();
		salida.append(nombre);
		salida.append(", ");
		salida.append(apellido);
		return salida.toString().toUpperCase();
	}

	@Transient
	public String getNombreCompleto(){
		return toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result
				+ ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result
				+ ((funciones == null) ? 0 : funciones.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((nroCedula == null) ? 0 : nroCedula.hashCode());
		result = prime * result
				+ ((tipoTrabajador == null) ? 0 : tipoTrabajador.hashCode());
		result = prime * result + ((tlfFijo == null) ? 0 : tlfFijo.hashCode());
		result = prime * result
				+ ((tlfMovil == null) ? 0 : tlfMovil.hashCode());
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
		Trabajador other = (Trabajador) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (funciones == null) {
			if (other.funciones != null)
				return false;
		} else if (!funciones.equals(other.funciones))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nroCedula == null) {
			if (other.nroCedula != null)
				return false;
		} else if (!nroCedula.equals(other.nroCedula))
			return false;
		if (tipoTrabajador == null) {
			if (other.tipoTrabajador != null)
				return false;
		} else if (!tipoTrabajador.equals(other.tipoTrabajador))
			return false;
		if (tlfFijo == null) {
			if (other.tlfFijo != null)
				return false;
		} else if (!tlfFijo.equals(other.tlfFijo))
			return false;
		if (tlfMovil == null) {
			if (other.tlfMovil != null)
				return false;
		} else if (!tlfMovil.equals(other.tlfMovil))
			return false;
		return true;
	}
	
	
}
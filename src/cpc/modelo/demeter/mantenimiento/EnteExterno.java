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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
 

import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
@Audited
@AuditTable(schema="auditoria",value="tbl_dem_ente_externo_aud")
@Entity
@Table(name="tbl_dem_ente_externo", schema = "mantenimiento")
public class EnteExterno implements Serializable{


	private Integer id;
	private String descripcion;
	private String direccion;
	private String telefono1;
	private String telefono2;
	
	private List<UsuarioMantenimiento> usuarios = new ArrayList<UsuarioMantenimiento>();

	 
	/**
	 * @param id
	 * @param descripcion
	 */
	public EnteExterno(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	/**
	 * @param id
	 * @param descripcion
	 * @param usuarios
	 */
	public EnteExterno(Integer id, String descripcion,
			List<UsuarioMantenimiento> usuarios) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	//	this.usuarios = usuarios;
	}

	public EnteExterno() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column(name="seq_identeexterno")
	@SequenceGenerator(name="Ente_Externo", sequenceName="mantenimiento.tbl_dem_ente_externo_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="Ente_Externo")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column(name="descripcion",length=255)
	@Type(type="java.lang.String")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER ,targetEntity=UsuarioMantenimiento.class,mappedBy="ente")
	public List<UsuarioMantenimiento> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<UsuarioMantenimiento> usuarios) {
		this.usuarios = usuarios;
	}
	
	@Column(name="direccion")
	public String getDireccion() {
		return direccion;
	}

	public String getTelefono1() {
		return telefono1;
	}
	
	@Column(name="telefono2")
	public String getTelefono2() {
		return telefono2;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Column(name="telefono1")
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
}

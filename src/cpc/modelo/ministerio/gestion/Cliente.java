package cpc.modelo.ministerio.gestion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.basico.TipoProductor;

@Audited @Entity
@Table(name = "tbl_dem_clientes")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "Cliente.findAdministrativos", query = "SELECT d FROM ClienteAdministrativo s INNER JOIN s.cliente d")
public class Cliente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1171243940079189114L;

	private Integer id;
	private TipoProductor tipo;
	private String identidadLegal; // Cédula o RIF
	private String nombres; // Razón social o nombre

	private boolean activo;

	private List<Telefono> telefonos;
	private String direccion;

	private ClienteAdministrativo clienteAdministrativo;

	public Cliente() {
		super();
	}

	public Cliente(Integer id, TipoProductor tipo, String identidadLegal,
			String nombres) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.identidadLegal = identidadLegal;
		this.nombres = nombres;
	}

	
	public Cliente(Integer id, String identidadLegal) {
		super();
		this.id = id;
		this.identidadLegal = identidadLegal;
	}

	
	
	public Cliente(Integer id, String identidadLegal, String nombres,
			String direccion) {
		super();
		this.id = id;
		this.identidadLegal = identidadLegal;
		this.nombres = nombres;
		this.direccion = direccion;
	}

	@Id
	@SequenceGenerator(name = "seqCliente", sequenceName = "public.tbl_dem_clientes_seq_idcliente_seq", allocationSize = 1)
	@GeneratedValue(generator = "seqCliente")
	@Column(name = "seq_idcliente")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "int_tipocliente")
	public TipoProductor getTipo() {
		return tipo;
	}

	public void setTipo(TipoProductor tipo) {
		this.tipo = tipo;
	}

	@Column(name = "str_cedurif", unique = true, nullable = false)
	public String getIdentidadLegal() {
		return identidadLegal;
	}

	public void setIdentidadLegal(String identidadLegal) {
		this.identidadLegal = identidadLegal;
	}

	@Column(name = "str_nombre")
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@Column(name = "bol_activo")
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
	@NotAudited
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch=FetchType.EAGER, targetEntity = Telefono.class)
	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	@Column(name = "str_email")
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Transient
	public String getStrTipo() {
		if (tipo == null)
			return null;
		else
			return tipo.getDescripcion();
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "cliente", targetEntity = ClienteAdministrativo.class)
	public ClienteAdministrativo getClienteAdministrativo() {
		return clienteAdministrativo;
	}

	public void setClienteAdministrativo(
			ClienteAdministrativo clienteAdministrativo) {
		this.clienteAdministrativo = clienteAdministrativo;
	}

	@Transient
	public String getStrTelefonos() {
		if (telefonos == null)
			return "";
		StringBuilder salida = new StringBuilder();

		for (Telefono item : telefonos) {
			if (item.getNumero().trim() != "" || item.getCodigoArea() != null) {
				String tmpNumero = "";
				if (item.getNumero().trim() != "")
					tmpNumero = item.getNumero().trim();
				String tmpCodigoArea = "";
				if (item.getCodigoArea() != null)
					tmpCodigoArea = item.getCodigoArea().getCodigoArea();

				salida.append(String.format("(%s) %s, ", tmpCodigoArea,
						tmpNumero));
			} else {
				salida.append("");
			}
		}
		if (salida.length() > 2)
			salida.delete(salida.length() - 2, salida.length());
		return salida.toString();
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (activo != other.activo)
			return false;
		if (clienteAdministrativo == null) {
			if (other.clienteAdministrativo != null)
				return false;
		} else if (!clienteAdministrativo.equals(other.clienteAdministrativo))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identidadLegal == null) {
			if (other.identidadLegal != null)
				return false;
		} else if (!identidadLegal.equals(other.identidadLegal))
			return false;
		if (nombres == null) {
			if (other.nombres != null)
				return false;
		} else if (!nombres.equals(other.nombres))
			return false;
		if (telefonos == null) {
			if (other.telefonos != null)
				return false;
		} else if (!telefonos.equals(other.telefonos))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	

}

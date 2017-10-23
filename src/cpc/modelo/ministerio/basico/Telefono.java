package cpc.modelo.ministerio.basico;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.CodigoArea;
import cpc.modelo.ministerio.gestion.Cliente;

@Audited @Entity
@Table(name="tbl_dem_telefono", schema="ministerio")
public class Telefono implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -547207462754230171L;
	public static final int MOVIL= 0;
	public static final int FIJO = 1;
	public static final int TELEFAX = 2;
	
	
	private Integer			id;
	private CodigoArea	 	codigoArea;
	private String 			numero;
	private Boolean			celular;
	
	
	private Cliente 		cliente;

	public Telefono(CodigoArea codigoArea, String numero) {
		super();
		this.codigoArea = codigoArea;
		this.numero = numero;
		if (this.codigoArea.getTipo() == 0)
			celular=true;
		else 
			celular=false;
	}
	
	public Telefono() {
		super();
	}
	
	@SequenceGenerator(name="SeqTelefono", sequenceName="ministerio.tbl_dem_telefono_seq_idtelefono_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="SeqTelefono")
	@Column(name="seq_idtelefono", unique=true, nullable=false)	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

	@OneToOne
	@JoinColumn(name="int_idcodare")
	public CodigoArea getCodigoArea() {
		return codigoArea;
	}
	public void setCodigoArea(CodigoArea codigoArea) {
		this.codigoArea = codigoArea;
	}
	
	@Column(name="str_numtel", nullable=false)
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	
	@ManyToOne
	@JoinTable(
		      name="tbl_dem_productortelefono", schema="ministerio", 
		      joinColumns= {@JoinColumn(name="int_idtelefono", referencedColumnName="seq_idtelefono")},
		      inverseJoinColumns={@JoinColumn(name="int_idproductor", referencedColumnName="seq_idcliente")}
	)
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente productor) {
		this.cliente = productor;
	}
	
	@Column(name="bol_celular")
	public Boolean isCelular() {
		return celular;
	}

	public void setCelular(Boolean celular) {
		this.celular = celular;
	}
	
	@Transient
	public String getTextCodigoArea(){
		return codigoArea.getCodigoArea();
	}

	@Transient
	public String getTipoTelefono(){
		return codigoArea.getTipo() == 0? "Celular":"Residencial";
	}
	
	@Transient
	public String getNombreDueño(){
		return cliente.getNombres();
	}
	
	@Transient
	public String getNumeroCompleto(){
		return codigoArea.getCodigoArea() + "-"+numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result
				+ ((codigoArea == null) ? 0 : codigoArea.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Telefono other = (Telefono) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (codigoArea == null) {
			if (other.codigoArea != null)
				return false;
		} else if (!codigoArea.equals(other.codigoArea))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	
	

}

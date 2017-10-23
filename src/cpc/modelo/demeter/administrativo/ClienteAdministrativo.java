package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import cpc.modelo.ministerio.gestion.Cliente;
import cva.pc.demeter.utilidades.Formateador;


@Audited
@Entity
@Table(name="tbl_dem_clienteadministrativo", schema="administracion")
@NamedQueries({
	@NamedQuery(name = "ClienteAdministrativo.findNuevos", 
			query = "SELECT d FROM ClienteAdministrativo s RIGHT JOIN s.cliente d where s.cuentaCobro = null and d.id <> 0")
	}
)
public class ClienteAdministrativo implements Serializable{
	
	private static final long serialVersionUID = -8090367580477637863L;
	private Integer id;
	private Cliente cliente;
	private String 	cuentaPago;
	private String 	cuentaCobro;

	
	public ClienteAdministrativo(){
	}
	
	public ClienteAdministrativo(Integer id){
		this.id = id;
	}
	
	public ClienteAdministrativo(Cliente cliente){
		this.cliente = cliente;
	}

	@Column(name="str_ctapago", nullable=true)
	public String getCuentaPago() {
		return cuentaPago;
	}


	public void setCuentaPago(String cuentaPago) {
		this.cuentaPago = cuentaPago;
	}

	@Column(name="str_ctacobro", nullable=true)
	public String getCuentaCobro() {
		return cuentaCobro;
	}


	public void setCuentaCobro(String cuentaCobro) {
		this.cuentaCobro = cuentaCobro;
	}

	@SequenceGenerator(name="seqIdCliAdm", sequenceName="administracion.tbl_dem_clienteadministrativo_seq_idclienteadm_seq", allocationSize=1)
	@Id @GeneratedValue(generator="seqIdCliAdm")
	@Column(name="seq_idclienteadm")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="int_idcliente")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		
	}

	@Transient
	public String getNombreCliente(){
		return cliente.getNombres();
	}
	
	@Transient
	public String getCedulaCliente(){
		return cliente.getIdentidadLegal();
	}
	
	public String toString(){
		return Formateador.rellenarBlanco(cliente.getIdentidadLegal(),20, false)+cliente.getNombres();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteAdministrativo other = (ClienteAdministrativo) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} 
		else if (!cliente.getIdentidadLegal().equals(other.cliente.getIdentidadLegal()))
			return false;
		if (cuentaCobro == null) {
			if (other.cuentaCobro != null)
				return false;
		} else if (!cuentaCobro.equals(other.cuentaCobro))
			return false;
		if (cuentaPago == null) {
			if (other.cuentaPago != null)
				return false;
		} else if (!cuentaPago.equals(other.cuentaPago))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

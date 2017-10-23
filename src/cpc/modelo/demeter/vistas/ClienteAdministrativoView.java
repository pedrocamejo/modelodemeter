package cpc.modelo.demeter.vistas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="clienteadministrativo_view")
///@Immutable
public class ClienteAdministrativoView {

	private Integer id;
	private String rif;
	private String nombre;
	private String cuentaPago;
	private String cuentaCobro;
	
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column
	public String getRif() {
		return rif;
	}
	public void setRif(String rif) {
		this.rif = rif;
	}
	
	@Column
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="cuenta_pago")
	public String getCuentaPago() {
		return cuentaPago;
	}
	public void setCuentaPago(String cuentaPago) {
		this.cuentaPago = cuentaPago;
	}

	@Column(name="cuenta_cobro")
	public String getCuentaCobro() {
		return cuentaCobro;
	}
	public void setCuentaCobro(String cuentaCobro) {
		this.cuentaCobro = cuentaCobro;
	}
	
}


 




 
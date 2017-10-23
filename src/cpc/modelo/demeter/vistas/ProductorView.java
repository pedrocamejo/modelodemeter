package cpc.modelo.demeter.vistas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="productor_view")
public class ProductorView {
	
	private Integer id;
	private Integer tipo_id;
	private String nombre;
	private String tipo;
	private String rif;
	private String ingreso;
	private String organizacion;

	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column
	public Integer getTipo_id() {
		return tipo_id;
	}
	public void setTipo_id(Integer tipo_id) {
		this.tipo_id = tipo_id;
	}

	@Column
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Column
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Column
	public String getRif() {
		return rif;
	}
	public void setRif(String rif) {
		this.rif = rif;
	}
	@Column
	public String getIngreso() {
		return ingreso;
	}
	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}
	@Column
	public String getOrganizacion() {
		return organizacion;
	}
	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}
}

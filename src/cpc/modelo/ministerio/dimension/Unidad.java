package cpc.modelo.ministerio.dimension;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class Unidad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -250857945004910434L;
	private Integer		            id;
	private String		            nombre;
	private String					descripcion;
	private TipoUnidad		        tipo;
	private UbicacionDireccion		ubicacion;
	private boolean					sede;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipounidad")
	public TipoUnidad getTipo() {
		return tipo;
	}
	public void setTipo(TipoUnidad tipo) {
		this.tipo = tipo;
	}	
	
	@OneToOne
	@JoinColumn(name="int_iddireccion")
	public UbicacionDireccion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(UbicacionDireccion ubicacion) {
		this.ubicacion = ubicacion;
	}	
	
	@Column(name="bol_sede")
	public boolean isSede() {
		return sede;
	}
	public void setSede(boolean sede) {
		this.sede = sede;
	}	
	
}

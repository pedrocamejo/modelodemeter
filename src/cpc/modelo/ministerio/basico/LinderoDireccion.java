package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cpc.modelo.ministerio.dimension.UbicacionDireccion;


@Audited @Entity
@Table(name="tbl_dem_linderodireccion", schema="ministerio")
public class LinderoDireccion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7931107811147655483L;
	private Integer				id;
	private Integer				orientacion;
	private String				nombre;
	private UbicacionDireccion	direccion;
	
	@Id
	@Column(name="seq_idlindero")
	@SequenceGenerator(name="SeqLindero", sequenceName="ministerio.tbl_dem_linderodireccion_seq_idlindero_seq")
	@GeneratedValue(generator="SeqLindero")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "int_idorientacion")
	public Integer getOrientacion() {
		return orientacion;
	}
	public void setOrientacion(Integer orientacion) {
		this.orientacion = orientacion;
	}
	
	@Column(name="str_lindero")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@ManyToOne
	@JoinColumn(name="int_iddireccion")
	public UbicacionDireccion getDireccion() {
		return direccion;
	}
	public void setDireccion(UbicacionDireccion direccion) {
		this.direccion = direccion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((orientacion == null) ? 0 : orientacion.hashCode());
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
		LinderoDireccion other = (LinderoDireccion) obj;
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
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (orientacion == null) {
			if (other.orientacion != null)
				return false;
		} else if (!orientacion.equals(other.orientacion))
			return false;
		return true;
	}
	


	
	
}

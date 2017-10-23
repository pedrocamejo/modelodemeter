package cpc.modelo.ministerio.gestion;

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
@Table(name="tbl_dem_direccionpropietario", schema="ministerio")
public class DireccionPropietario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4922338785855850937L;
	private Integer				id;
	private Cliente				propietario;
	private UbicacionDireccion	direccion;
	
	@Id
	@Column(name="seq_iddireprop")
	@SequenceGenerator(name="seqPropietario", sequenceName="ministerio.tbl_dem_direccionpropietario_seq_iddireprop_seq", initialValue=1)
	@GeneratedValue(generator="seqPropietario")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idcliente")
	public Cliente getPropietario() {
		return propietario;
	}
	public void setPropietario(Cliente propietario) {
		this.propietario = propietario;
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
		result = prime * result
				+ ((propietario == null) ? 0 : propietario.hashCode());
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
		DireccionPropietario other = (DireccionPropietario) obj;
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
		if (propietario == null) {
			if (other.propietario != null)
				return false;
		} else if (!propietario.equals(other.propietario))
			return false;
		return true;
	}

	
	

}

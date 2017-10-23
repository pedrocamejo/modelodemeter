package cpc.modelo.ministerio.dimension;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_unidadfuncional", schema="ministerio")
public class UnidadFuncional implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6248567110296109640L;
	private Integer					id;
	private String					descripcion;
	private TipoUnidadFuncional		tipo;
	private UbicacionDireccion		ubicacion;
	private boolean					sede;

	
	@Id
	@Column(name="seq_idunidadfuncional")
	@SequenceGenerator(name="seqUnidadFuncional", sequenceName="ministerio.tbl_dem_unidadfuncional_seq_idunidadfuncional_seq", allocationSize=1)
	@GeneratedValue(generator="seqUnidadFuncional")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public TipoUnidadFuncional getTipo() {
		return tipo;
	}
	public void setTipo(TipoUnidadFuncional tipo) {
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
	
	@Transient
	public String getStrTipo() {
		if (tipo == null)
			return null;
		return tipo.getDescripcion();
	}
	
	@Transient
	public String getStrUbicacion() {
		if (ubicacion == null)
			return null;
		return ubicacion.toString();
	}

	public String toString(){
		return descripcion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (sede ? 1231 : 1237);
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result
				+ ((ubicacion == null) ? 0 : ubicacion.hashCode());
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
		UnidadFuncional other = (UnidadFuncional) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sede != other.sede)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (ubicacion == null) {
			if (other.ubicacion != null)
				return false;
		} else if (!ubicacion.equals(other.ubicacion))
			return false;
		return true;
	}
	

}

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

import cpc.modelo.demeter.basico.TipoCoordenadaGeografica;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;

@Audited @Entity
@Table(name="tbl_dem_coordenadageografica", schema="ministerio")
public class CoordenadaGeografica implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1010356052882691084L;
	private Integer						id;
	private UbicacionDireccion			direccion;
	private TipoCoordenadaGeografica	tipo;
	private	Integer						husoUTM;
	private Double						ejeX;
	private Double						ejeY;
	
	@Id
	@Column(name="seq_idcoordenada")
	@SequenceGenerator(name="SeqCoordenada", sequenceName="ministerio.tbl_dem_coordenadageografica_seq_idcoordenada_seq")
	@GeneratedValue(generator="SeqCoordenada")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_iddireccion")
	public UbicacionDireccion getDireccion() {
		return direccion;
	}
	public void setDireccion(UbicacionDireccion direccion) {
		this.direccion = direccion;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipocoordenada")
	public TipoCoordenadaGeografica getTipo() {
		return tipo;
	}
	public void setTipo(TipoCoordenadaGeografica tipo) {
		this.tipo = tipo;
	}
	
	@Column(name="int_husoutm")
	public Integer getHusoUTM() {
		return husoUTM;
	}
	public void setHusoUTM(Integer husoUTM) {
		this.husoUTM = husoUTM;
	}
	
	@Column(name="bint_ejex")
	public Double getEjeX() {
		return ejeX;
	}
	public void setEjeX(Double ejeX) {
		this.ejeX = ejeX;
	}
	
	@Column(name="bint_ejey")
	public Double getEjeY() {
		return ejeY;
	}
	public void setEjeY(Double ejeY) {
		this.ejeY = ejeY;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((ejeX == null) ? 0 : ejeX.hashCode());
		result = prime * result + ((ejeY == null) ? 0 : ejeY.hashCode());
		result = prime * result + ((husoUTM == null) ? 0 : husoUTM.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		CoordenadaGeografica other = (CoordenadaGeografica) obj;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (ejeX == null) {
			if (other.ejeX != null)
				return false;
		} else if (!ejeX.equals(other.ejeX))
			return false;
		if (ejeY == null) {
			if (other.ejeY != null)
				return false;
		} else if (!ejeY.equals(other.ejeY))
			return false;
		if (husoUTM == null) {
			if (other.husoUTM != null)
				return false;
		} else if (!husoUTM.equals(other.husoUTM))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	
}

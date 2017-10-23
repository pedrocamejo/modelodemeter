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

import cpc.modelo.ministerio.basico.TipoSuperficie;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;


@Audited @Entity
@Table(name="tbl_dem_superficieunidad", schema="ministerio")
public class SuperficieUnidad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3470678442103350682L;
	private Integer				id;
	private TipoSuperficie		tipoSuperficie;
	private UbicacionDireccion	unidadProductiva;
	private double				superficie;
	
	@Id
	@Column(name="seq_idsuperficieunidad")
	@SequenceGenerator(name="SeqSuperficie", sequenceName="ministerio.tbl_dem_superficieunidad_seq_idsuperficieunidad_seq")
	@GeneratedValue(generator="SeqSuperficie")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="seq_idtiposuperficie")
	public TipoSuperficie getTipoSuperficie() {
		return tipoSuperficie;
	}
	public void setTipoSuperficie(TipoSuperficie tipoSuperficie) {
		this.tipoSuperficie = tipoSuperficie;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadproductiva")
	public UbicacionDireccion getUnidadProductiva() {
		return unidadProductiva;
	}
	public void setUnidadProductiva(UbicacionDireccion unidadProductiva) {
		this.unidadProductiva = unidadProductiva;
	}
	
	@Column(name="dbl_superficie")
	public double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(superficie);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((tipoSuperficie == null) ? 0 : tipoSuperficie.hashCode());
		result = prime
				* result
				+ ((unidadProductiva == null) ? 0 : unidadProductiva.hashCode());
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
		SuperficieUnidad other = (SuperficieUnidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(superficie) != Double
				.doubleToLongBits(other.superficie))
			return false;
		if (tipoSuperficie == null) {
			if (other.tipoSuperficie != null)
				return false;
		} else if (!tipoSuperficie.equals(other.tipoSuperficie))
			return false;
		if (unidadProductiva == null) {
			if (other.unidadProductiva != null)
				return false;
		} else if (!unidadProductiva.equals(other.unidadProductiva))
			return false;
		return true;
	}

	
}

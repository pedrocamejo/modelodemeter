package cpc.modelo.ministerio.gestion;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cpc.modelo.ministerio.basico.TipoRiego;

@Audited @Entity
@Table(name="tbl_dem_tiporiegounidadproductiva", schema="ministerio")
public class UnidadProductivaTipoRiego implements Serializable{

	
	private static final long serialVersionUID = -8442969452493190689L;
	private Integer				id;
	private TipoRiego			tipoRiego;
	private UnidadProductiva	unidadProductiva;
	private Double				superficie;
	
	
	public UnidadProductivaTipoRiego() {
		
	}
	
	
	
	public UnidadProductivaTipoRiego(TipoRiego tipoRiego,
			UnidadProductiva unidadProductiva, Double superficie) {
		super();
		this.tipoRiego = tipoRiego;
		this.unidadProductiva = unidadProductiva;
		this.superficie = superficie;
	}



	@Id
	@Column(name="seq_idtiporiegunidprod")
	@SequenceGenerator(name="SeqRiegoUnidad", sequenceName="ministerio.tbl_dem_tiporiegounidadproductiva_seq_idtiporiegunidprod_seq")
	@GeneratedValue(generator="SeqRiegoUnidad")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtiporiego")
	public TipoRiego getTipoRiego() {
		return tipoRiego;
	}
	public void setTipoRiego(TipoRiego tipoRiego) {
		this.tipoRiego = tipoRiego;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadproductiva")
//	@Basic(fetch=FetchType.EAGER)
	public UnidadProductiva getUnidadProductiva() {
		return unidadProductiva;
	}
	public void setUnidadProductiva(UnidadProductiva unidadProductiva) {
		this.unidadProductiva = unidadProductiva;
	}
	
	@Column(name="dbl_superficie")
	public Double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}
	
	public boolean equal(Object objeto){
		UnidadProductivaTipoRiego obj = (UnidadProductivaTipoRiego) objeto;
		try{
			
			if (obj.getTipoRiego().equals(this.tipoRiego) && obj.getUnidadProductiva().equals(this.unidadProductiva))
				return true;
			
			else 
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String toString() {		
		return this.unidadProductiva + " - "+ this.tipoRiego;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((superficie == null) ? 0 : superficie.hashCode());
		result = prime * result
				+ ((tipoRiego == null) ? 0 : tipoRiego.hashCode());
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
		UnidadProductivaTipoRiego other = (UnidadProductivaTipoRiego) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (superficie == null) {
			if (other.superficie != null)
				return false;
		} else if (!superficie.equals(other.superficie))
			return false;
		if (tipoRiego == null) {
			if (other.tipoRiego != null)
				return false;
		} else if (!tipoRiego.equals(other.tipoRiego))
			return false;
		if (unidadProductiva == null) {
			if (other.unidadProductiva != null)
				return false;
		} else{
	//		hagamoslo a mano a ver donde falla
			
			if (!unidadProductiva.getProductor().getIdentidadLegal().equals(other.unidadProductiva.getProductor().getIdentidadLegal()))
			return false;
			
			if (!unidadProductiva.getId().equals(other.unidadProductiva.getId()))
				return false;
		/*	if (!unidadProductiva.getUbicacion().getId().equals(other.unidadProductiva.getUbicacion().getId())){
				Integer a = unidadProductiva.getUbicacion().getId();
				Integer b = other.unidadProductiva.getUbicacion().getId();
				return false;
			}
				
			if (!unidadProductiva.getUbicacion().toString().equals(other.unidadProductiva.getUbicacion().toString()))
				return false;
			*/
		
		}
		return true;
	}
	

}

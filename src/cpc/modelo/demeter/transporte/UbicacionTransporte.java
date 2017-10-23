package cpc.modelo.demeter.transporte;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import cpc.modelo.ministerio.dimension.UbicacionEje;
import cpc.modelo.ministerio.dimension.UbicacionEstado;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.modelo.ministerio.dimension.UbicacionSector;

@Audited
@Entity
@Table(name = "tbl_dem_ubicaciontransporte", schema = "transporte")
public class UbicacionTransporte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6754388201228306590L;
	private Integer id;
	private UbicacionMunicipio ubicacionMunicipio;
	private UbicacionParroquia ubicacionParroquia;
	private UbicacionSector ubicacionSector;
	private String direccion;
	private String referencia;

	@Id
	@Column(name = "seq_idubicaciontransporte")
	@SequenceGenerator(name = "seq_ubicaciontransporte", sequenceName = "transporte.tbl_dem_ubicaciontransporte_seq_idubicaciontransporte_seq", allocationSize = 1)
	@GeneratedValue(generator = "seq_ubicaciontransporte")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "int_idmunicipio")
	public UbicacionMunicipio getUbicacionMunicipio() {
		return ubicacionMunicipio;
	}

	public void setUbicacionMunicipio(UbicacionMunicipio ubicacionMunicipio) {
		this.ubicacionMunicipio = ubicacionMunicipio;
	}

	@ManyToOne
	@JoinColumn(name = "int_idparroquia")
	public UbicacionParroquia getUbicacionParroquia() {
		return ubicacionParroquia;
	}

	public void setUbicacionParroquia(UbicacionParroquia ubicacionParroquia) {
		this.ubicacionParroquia = ubicacionParroquia;
	}

	@ManyToOne
	@JoinColumn(name = "int_idsector")
	public UbicacionSector getUbicacionSector() {
		return ubicacionSector;
	}

	public void setUbicacionSector(UbicacionSector ubicacionSector) {
		this.ubicacionSector = ubicacionSector;
	}

	@Column(name = "str_direccion")
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "str_referencia")
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
	public String toString() {
		return "Municipio= " + ubicacionMunicipio
				+ ", Parroquia= " + ubicacionParroquia
				+ ", Sector= " + ubicacionSector + ", direccion= "
				+ direccion + ", referencia= " + referencia ;
	}

	@Transient
	public String getNombreMunicipio() {
		if (ubicacionMunicipio != null)
			return ubicacionMunicipio.getNombre();
		else
			return "";
	}

	@Transient
	public String getNombreParroquia() {

		if (ubicacionParroquia != null)
			return ubicacionParroquia.getNombre();
		else
			return "";
	};

	@Transient
	public String getNombreSector() {
		if (ubicacionSector != null)
			return ubicacionSector.getNombre();
		else
			return "";

	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((referencia == null) ? 0 : referencia.hashCode());
		result = prime
				* result
				+ ((ubicacionMunicipio == null) ? 0 : ubicacionMunicipio
						.hashCode());
		result = prime
				* result
				+ ((ubicacionParroquia == null) ? 0 : ubicacionParroquia
						.hashCode());
		result = prime * result
				+ ((ubicacionSector == null) ? 0 : ubicacionSector.hashCode());
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
		UbicacionTransporte other = (UbicacionTransporte) obj;
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
		if (referencia == null) {
			if (other.referencia != null)
				return false;
		} else if (!referencia.equals(other.referencia))
			return false;
		if (ubicacionMunicipio == null) {
			if (other.ubicacionMunicipio != null)
				return false;
		} else if (!ubicacionMunicipio.equals(other.ubicacionMunicipio))
			return false;
		if (ubicacionParroquia == null) {
			if (other.ubicacionParroquia != null)
				return false;
		} else if (!ubicacionParroquia.equals(other.ubicacionParroquia))
			return false;
		if (ubicacionSector == null) {
			if (other.ubicacionSector != null)
				return false;
		} else if (!ubicacionSector.equals(other.ubicacionSector))
			return false;
		return true;
	}

}

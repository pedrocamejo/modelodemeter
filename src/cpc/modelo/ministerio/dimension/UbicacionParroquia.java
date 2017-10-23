package cpc.modelo.ministerio.dimension;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_parroquia", schema="ministerio")
public class UbicacionParroquia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6110329461849271866L;
	private Integer							id;
	private	String							nombre;
	private UbicacionMunicipio				municipio;
	private List<UbicacionSector>		sectores;
	
	@Id
	@Column(name="seq_idparroquia")
	@SequenceGenerator(name="seqParroquia", sequenceName="ministerio.tbl_dem_parroquia_seq_idparroquia_seq", allocationSize=1)
	@GeneratedValue(generator="seqParroquia")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idmunicipio")
	public UbicacionMunicipio getMunicipio() {
		return municipio;
	}
	public void setMunicipio(UbicacionMunicipio municipio) {
		this.municipio = municipio;
	}
	
	@OneToMany(mappedBy="parroquia", targetEntity=UbicacionSector.class)
	public List<UbicacionSector> getSectores() {
		return sectores;
	}
	public void setSectores(List<UbicacionSector> sectores) {
		this.sectores = sectores;
	}
	
	@Transient
	public String getStrPais(){
		if (municipio == null)
			return null;
		return municipio.getStrPais();
	}
	
	@Transient
	public String getStrEstado(){
		if (municipio == null)
			return null;
		return municipio.getStrEstado();
	}
	
	@Transient
	public String getStrMunicipio(){
		if (municipio == null)
			return null;
		return municipio.getNombre();
	}
	
	public String toString(){
		return nombre;
	}
	
	public boolean equals(Object objeto){
		try{
			UbicacionParroquia cuenta = (UbicacionParroquia) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

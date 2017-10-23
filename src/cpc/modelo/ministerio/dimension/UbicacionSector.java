package cpc.modelo.ministerio.dimension;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_sector", schema="ministerio")
public class UbicacionSector implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 434683336074853696L;
	private Integer						id;
	private String						nombre;
	private UbicacionParroquia			parroquia;
	private List<UbicacionDireccion>	direcciones;
	
	@Id
	@Column(name="seq_idsector")
	@SequenceGenerator(name="seqSector", sequenceName="ministerio.tbl_dem_sector_seq_idsector_seq", allocationSize=1)
	@GeneratedValue(generator="seqSector")
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
	@JoinColumn(name="int_idparroquia")
	public UbicacionParroquia getParroquia() {
		return parroquia;
	}
	public void setParroquia(UbicacionParroquia parroquia) {
		this.parroquia = parroquia;
	}
	
	@OneToMany(mappedBy="sector", targetEntity=UbicacionDireccion.class, fetch=FetchType.EAGER)
	public List<UbicacionDireccion> getDirecciones() {
		return direcciones;
	}
	public void setDirecciones(List<UbicacionDireccion> direcciones) {
		this.direcciones = direcciones;
	}
	
	@Transient
	public String getStrPais(){
		if (parroquia == null)
			return null;
		return parroquia.getStrPais();
	}
	
	@Transient
	public String getStrEstado(){
		if (parroquia == null)
			return null;
		return parroquia.getStrEstado();
	}
	
	@Transient
	public String getStrMunicipio(){
		if (parroquia == null)
			return null;
		return parroquia.getStrMunicipio();
	}
	
	@Transient
	public String getStrParroquia(){
		if (parroquia == null)
			return null;
		return parroquia.getNombre();
	}
	
	public String toString(){
		return nombre;
	}
	public boolean equals(Object objeto){
		try{
			UbicacionSector cuenta = (UbicacionSector) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	/*
	@Transient
	public String getStrCodigo(){
		
		return id.toString();
	}*/
}

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
@Table(name="tbl_dem_municipio", schema="ministerio")
public class UbicacionMunicipio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7613882589615709525L;
	private Integer						id;
	private String						nombre;
	private UbicacionEstado				estado;
	private List<UbicacionParroquia>	parroquias;
	
	@Id
	@Column(name="seq_idmunicipio")
	@SequenceGenerator(name="seqMunicipio", sequenceName="ministerio.tbl_dem_municipio_seq_idmunicipio_seq", allocationSize=1)
	@GeneratedValue(generator="seqMunicipio")
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
	@JoinColumn(name="int_idestado")
	public UbicacionEstado getEstado() {
		return estado;
	}
	public void setEstado(UbicacionEstado estado) {
		this.estado = estado;
	}
	
	@OneToMany(mappedBy="municipio", targetEntity=UbicacionParroquia.class)
	public List<UbicacionParroquia> getParroquias() {
		return parroquias;
	}
	public void setParroquias(List<UbicacionParroquia> parroquias) {
		this.parroquias = parroquias;
	}
	
	@Transient
	public String getStrPais(){
		if (estado == null)
			return null;
		return estado.getStrPais();
	}
	
	@Transient
	public String getStrEstado(){
		if (estado == null)
			return null;
		return estado.getNombre();
	}
	
	public String toString(){
		return nombre;
	}
	
	public boolean equals(Object objeto){
		try{
			UbicacionMunicipio cuenta = (UbicacionMunicipio) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

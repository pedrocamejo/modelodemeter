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
@Table(name="tbl_dem_estado", schema="ministerio")
public class UbicacionEstado implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3455085135908567880L;

	//private EstadoPK  id;
	
	private Integer						id;
	private String 						nombre;
	private List<UbicacionMunicipio> 	municipios;
	private UbicacionEje				eje;
	private UbicacionPais				pais;
	
	@Id
	@Column(name="seq_idestado")
	@SequenceGenerator(name="seqEstado", sequenceName="ministerio.tbl_dem_estado_seq_idestado_seq", allocationSize=1)
	@GeneratedValue(generator="seqEstado")
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
	
	@OneToMany(mappedBy="estado", targetEntity=UbicacionMunicipio.class)
	public List<UbicacionMunicipio> getMunicipios() {
		return municipios;
	}
	public void setMunicipios(List<UbicacionMunicipio> municipios) {
		this.municipios = municipios;
	}
	
	@ManyToOne
	@JoinColumn(name="int_ideje")
	public UbicacionEje getEje() {
		return eje;
	}
	public void setEje(UbicacionEje eje) {
		this.eje = eje;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idpais")
	public UbicacionPais getPais() {
		return pais;
	}
	public void setPais(UbicacionPais pais) {
		this.pais = pais;
	}
	
	@Transient
	public String getStrPais(){
		if (pais!= null)
			return pais.getNombre();
		else return null;
	}
	
	public String toString(){
		return nombre;
	}
	
	@Transient
	public String getStrEje(){
		if (eje!= null)
			return eje.getNombre();
		else return null;
	}
	public boolean equals(Object objeto){
		try{
			UbicacionEstado cuenta = (UbicacionEstado) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
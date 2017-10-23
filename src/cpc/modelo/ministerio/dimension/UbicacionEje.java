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
@Table(name="tbl_dem_eje", schema="ministerio")
public class UbicacionEje implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2950344324214903046L;
	private Integer					id;
	private String					nombre;
	private UbicacionPais			pais;
	private List<UbicacionEstado>	estados;
	
	@Id
	@Column(name="seq_ideje")
	@SequenceGenerator(name="seqEje", sequenceName="ministerio.tbl_dem_eje_seq_ideje_seq", allocationSize=1)
	@GeneratedValue(generator="seqEje")
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
	@JoinColumn(name="int_idpais")
	public UbicacionPais getPais() {
		return pais;
	}
	public void setPais(UbicacionPais pais) {
		this.pais = pais;
	}
	
	@OneToMany(mappedBy="eje", targetEntity=UbicacionEstado.class)
	public List<UbicacionEstado> getEstados() {
		return estados;
	}
	public void setEstados(List<UbicacionEstado> estados) {
		this.estados = estados;
	}
	
	@Transient
	public String getStrPais(){
		if (pais != null)
			return pais.getNombre();
		else return null;
	}
	
	public String toString(){
		return nombre;
	}

	public boolean equals(Object objeto){
		try{
			UbicacionEje cuenta = (UbicacionEje) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

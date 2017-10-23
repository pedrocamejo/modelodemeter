package cpc.modelo.ministerio.dimension;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_pais", schema="ministerio")
public class UbicacionPais implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2670345222584719684L;
	private Integer					id;
	private String					nombre;
	private boolean					local;
	
	
	private List<UbicacionEstado>	estados;
	
	@Id
	@Column(name="seq_idpais")
	@SequenceGenerator(name="seqPais", sequenceName="ministerio.tbl_dem_pais_seq_idpais_seq", allocationSize=1)
	@GeneratedValue(generator="seqPais")
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
	
	@OneToMany(mappedBy="pais", targetEntity=UbicacionEstado.class)
	public List<UbicacionEstado> getEstados() {
		return estados;
	}
	public void setEstados(List<UbicacionEstado> estados) {
		this.estados = estados;
	}
	
	@Column(name="bol_local")
	public boolean isLocal() {
		return local;
	}
	public void setLocal(boolean local) {
		this.local = local;
	}

	public String toString(){
		return nombre;
	}
	
	public boolean equals(Object objeto){
		try{
			UbicacionPais cuenta = (UbicacionPais) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
}

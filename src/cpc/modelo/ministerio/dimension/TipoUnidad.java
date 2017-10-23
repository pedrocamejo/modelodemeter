package cpc.modelo.ministerio.dimension;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipounidad", schema="ministerio")
public class TipoUnidad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1021016143986140090L;
	private Integer 	id;
	private String		descripcion;
	private boolean		administrativo;
	
	
	@SequenceGenerator(name="SeqTipoUnidad", sequenceName="ministerio.tbl_dem_tipounidad_seq_idtipounidad_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="SeqTipoUnidad")
	@Column(name="seq_idtipounidad", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_administrativo")
	public boolean isAdministrativo() {
		return administrativo;
	}
	public void setAdministrativo(boolean administrativo) {
		this.administrativo = administrativo;
	}
	
	public String toString(){
		return descripcion;
	}
	


}

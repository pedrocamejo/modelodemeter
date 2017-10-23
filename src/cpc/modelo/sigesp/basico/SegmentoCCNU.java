package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Audited @Entity
@Table(name="espc_unspsc_segmentos",schema="sigesp")
public class SegmentoCCNU implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6623012878122348295L;
	private Long 		id;
	private String		descripcion;
	private boolean		articulo;
	private boolean		uso;
	
	List<FamiliaCCNU>  	familias;
	
	
	@Id
	@Column(name="big_codsegmento")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_articulo")
	public boolean isArticulo() {
		return articulo;
	}
	public void setArticulo(boolean articulo) {
		this.articulo = articulo;
	}
	
	@Column(name="bol_uso")
	public boolean isUso() {
		return uso;
	}
	public void setUso(boolean uso) {
		this.uso = uso;
	}
	
	@OneToMany(mappedBy="segmentoCCNU", targetEntity=FamiliaCCNU.class)
	public List<FamiliaCCNU> getFamilias() {
		return familias;
	}
	public void setFamilias(List<FamiliaCCNU> familias) {
		this.familias = familias;
	}
	
	public String toString(){
		return descripcion;
	}
}

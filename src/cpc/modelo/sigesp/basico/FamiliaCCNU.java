package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Audited @Entity
@Table(name="espc_unspsc_familias",schema="sigesp")
public class FamiliaCCNU implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5005257302611253013L;
	private Long			id;
	private String 			descripcion;
	private SegmentoCCNU	segmentoCCNU;
	
	private List<ClaseCCNU>	clases;
	
	@Id
	@Column(name="big_codfamilia")
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
	
	@ManyToOne
	@JoinColumn(name="big_idcodsegmento")
	public SegmentoCCNU getSegmentoCCNU() {
		return segmentoCCNU;
	}
	public void setSegmentoCCNU(SegmentoCCNU segmentoCCNU) {
		this.segmentoCCNU = segmentoCCNU;
	}
	
	@OneToMany(mappedBy="familia", targetEntity=ClaseCCNU.class)
	public List<ClaseCCNU> getClases() {
		return clases;
	}
	public void setClases(List<ClaseCCNU> clases) {
		this.clases = clases;
	}
	
	public String toString(){
		return descripcion;
	}
}

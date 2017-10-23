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
@Table(name="espsc_unspsc_clases",schema="sigesp")
public class ClaseCCNU implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7879202055569592772L;
	private Long 		id;
	private String		descripcion;
	private FamiliaCCNU familia;
	
	private List<ProductoCCNU> productos;
	
	@Id
	@Column(name="big_codclase")
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
	@JoinColumn(name="big_idcodfamilia")
	public FamiliaCCNU getFamilia() {
		return familia;
	}
	public void setFamilia(FamiliaCCNU familia) {
		this.familia = familia;
	}
	
	@OneToMany(mappedBy="clase", targetEntity=ProductoCCNU.class)
	public List<ProductoCCNU> getProductos() {
		return productos;
	}
	public void setProductos(List<ProductoCCNU> productos) {
		this.productos = productos;
	}
	
	public String toString(){
		return descripcion;
	}
}

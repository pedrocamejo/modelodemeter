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
import javax.persistence.Transient;

@Audited @Entity
@Table(name="espc_unspsc_productos",schema="sigesp")
public class ProductoCCNU implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -416921231769817814L;
	private Long		id;
	private String		descripcion;
	private ClaseCCNU	clase;
	
	private List<Articulo>  articulos;
	
	public ProductoCCNU() {
		super();
	}
	
	public ProductoCCNU(Long[] codigos) {
		super();
	}
	
	@Id
	@Column(name="big_codproducto")
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
	@JoinColumn(name="big_idcodclase")
	public ClaseCCNU getClase() {
		return clase;
	}
	public void setClase(ClaseCCNU clase) {
		this.clase = clase;
	}
	
	@OneToMany(mappedBy="codigoCCNU", targetEntity=Articulo.class)
	public List<Articulo> getArticulos() {
		return articulos;
	}
	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}
	
	@Transient
	public String getStrClase(){
		return clase.getDescripcion();
	}
	
	@Transient
	public String getStrFamilia(){
		return clase.getFamilia().getDescripcion();
	}
	
	@Transient
	public String getStrSegmento(){
		return clase.getFamilia().getSegmentoCCNU().getDescripcion();
	}
	
	@Transient
	public String getStrCodigo(){
		return clase.getId().toString();
	}
	
	public String toString(){
		return descripcion;
	}
}

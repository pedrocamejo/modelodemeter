package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import cpc.modelo.demeter.mantenimiento.Sistema;

import cpc.modelo.sigesp.indice.ArticuloPK;

@Audited @Entity
@Table(name="siv_articulos", schema="sigesp" )
@IdClass(ArticuloPK.class)
public class Articulo implements Serializable{
	
	
	private static final long serialVersionUID = -8516451627469559192L;
	private String					empresa;
	private String					id;
	
	private String 					descripcion;
	private ProductoCCNU			codigoCCNU;
	
	
	private Marca		marca;
	private String					modelo;
	private String					codigoFabricante;
	private Sistema	Sistema;
	//private UnidadEquivalente		unidadEquivalente;
	
	@Id
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="str_denart")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToOne
	@JoinColumn(name="big_idcodproducto")
	public ProductoCCNU getCodigoCCNU() {
		return codigoCCNU;
	}
	public void setCodigoCCNU(ProductoCCNU codigoCCNU) {
		this.codigoCCNU = codigoCCNU;
	}
	
	@ManyToOne
	@JoinColumn(name="str_marca")
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	@Column(name="str_modelo")
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	@Column(name="str_codfabricante")
	public String getCodigoFabricante() {
		return codigoFabricante;
	}
	public void setCodigoFabricante(String codigoFabricante) {
		this.codigoFabricante = codigoFabricante;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idsistema")
	public Sistema getSistema() {
		return Sistema;
	}
	public void setSistema(Sistema sistema) {
		Sistema = sistema;
	}
	
	
	/*@OneToOne(mappedBy="articulo", targetEntity=UnidadEquivalente.class, cascade=CascadeType.MERGE)
	public UnidadEquivalente getUnidadEquivalente() {
		return unidadEquivalente;
	}
	public void setUnidadEquivalente(UnidadEquivalente unidadEquivalente) {
		this.unidadEquivalente = unidadEquivalente;
	}
	*/
	
}

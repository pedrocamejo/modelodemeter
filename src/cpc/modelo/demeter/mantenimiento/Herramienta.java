package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.ArticuloVenta;
@Audited
@Entity

@Table(name="tbl_dem_herramienta", schema="mantenimiento")
//@PrimaryKeyJoinColumn(name="int_idherramienta")
public class Herramienta  implements Serializable{

	
	


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2419841241522745887L;
	//private ArticuloVenta articuloVenta;
	private Integer id;
	private ArticuloVenta articuloVenta;
	
	//
	private List<HerramientaEquivalente> herramientaEquivalente;
	
	/*@OneToOne
	@JoinColumn(name="int_idarticulo")
	
	public ArticuloVenta getArticuloVenta() {
		return articuloVenta;
	}
	public void setArticuloVenta(ArticuloVenta articuloVenta) {
		this.articuloVenta = articuloVenta;
	}
	*/
	
	
	@SequenceGenerator(name="seqherramienta", sequenceName="mantenimiento.tbl_dem_herramienta_seq_idherramienta_seq", allocationSize=1)
	@Id @GeneratedValue(generator="seqherramienta")
	@Column(name="seq_idherramienta")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	@OneToOne( targetEntity =ArticuloVenta.class)
	@JoinColumn(name="int_idarticulo")
		public ArticuloVenta getArticuloVenta() {
		return articuloVenta;
	}
	public void setArticuloVenta(ArticuloVenta articuloVenta) {
		this.articuloVenta = articuloVenta;
	}
	
	@OneToMany( mappedBy = "herramientaOri" , targetEntity = HerramientaEquivalente.class ,fetch=FetchType.EAGER,cascade =CascadeType.ALL)
	public List<HerramientaEquivalente> getHerramientaEquivalente() {
		return herramientaEquivalente;
	}
	public void setHerramientaEquivalente(List<HerramientaEquivalente> herramientaEquivalentes) {
		this.herramientaEquivalente = herramientaEquivalentes;
	}
	
	
	@Transient
	public String getDenominacionFabricante(){
		return getArticuloVenta().getDenominacionFabricante();
				
	}

	@Transient
	public String getCodigoCCNU(){
		return getArticuloVenta().getCodigoCCNU();
				
	}

	@Transient
	public String getCodigoSIGESP(){
		return getArticuloVenta().getCodigoSIGESP();
				
	}

	@Transient
	public String getCodigoFabricante(){
		return getArticuloVenta().getCodigoFabricante();
				
	}
	
	@Transient
	@Override
	public String toString() {
		return  getCodigoFabricante()+" "+getCodigoSIGESP();
	}


}
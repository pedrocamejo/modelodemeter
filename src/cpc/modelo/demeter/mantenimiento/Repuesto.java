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

@Table(name="tbl_dem_repuesto", schema="mantenimiento")
//@PrimaryKeyJoinColumn(name="int_idrepuesto")
public class Repuesto  implements Serializable{




	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4469467030357411311L;
	//private ArticuloVenta articuloVenta;
	private Integer id;
	private ArticuloVenta articuloVenta;
	
	//
	private List<RepuestoEquivalente> repuestoEquivalente;
	
	/*@OneToOne
	@JoinColumn(name="int_idarticulo")
	
	public ArticuloVenta getArticuloVenta() {
		return articuloVenta;
	}
	public void setArticuloVenta(ArticuloVenta articuloVenta) {
		this.articuloVenta = articuloVenta;
	}
	*/
	
	
	@SequenceGenerator(name="seqrepuesto", sequenceName="mantenimiento.tbl_dem_repuesto_seq_idrepuesto_seq", allocationSize=1)
	@Id @GeneratedValue(generator="seqrepuesto")
	@Column(name="seq_idrepuesto")
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
	
	@OneToMany( mappedBy = "repuestoOri" , targetEntity = RepuestoEquivalente.class ,fetch=FetchType.EAGER,cascade =CascadeType.ALL)
	public List<RepuestoEquivalente> getRepuestoEquivalente() {
		return repuestoEquivalente;
	}
	public void setRepuestoEquivalente(List<RepuestoEquivalente> repuestoEquivalentes) {
		this.repuestoEquivalente = repuestoEquivalentes;
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

package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.sigesp.basico.ProductoCCNU;

@Audited @Entity
@Table(name="tbl_dem_respuestos_e_insumos", schema = "bien_produccion")
public class RepuestoEInsumo implements Serializable{

	private static final long serialVersionUID = -7900557382129793790L;
	private Integer							id;
	private ProductoCCNU					tipo;
	private UnidadMedida					unidadMinima;
	//private List<UnidadEquivalente>			articulos;
	//private List<ActividadMantenimiento>	actividadesMantenimiento;
	
	
	@Column(name="seq_ser_respinsu")
	@SequenceGenerator(name="SeqRepuestoInsumo", sequenceName="bien_produccion.tbl_dem_respuestos_e_insumos_seq_ser_respinsu_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="SeqRepuestoInsumo")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="big_idcodproducto")
	public ProductoCCNU getTipo() {
		return tipo;
	}
	public void setTipo(ProductoCCNU tipo) {
		this.tipo = tipo;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidmed")
	public UnidadMedida getUnidadMinima() {
		return unidadMinima;
	}
	public void setUnidadMinima(UnidadMedida unidadMinima) {
		this.unidadMinima = unidadMinima;
	}
	
	/*@OneToMany(mappedBy="tipo", targetEntity=UnidadEquivalente.class)
	public List<UnidadEquivalente> getArticulos() {
		return articulos;
	}
	public void setArticulos(List<UnidadEquivalente> articulos) {
		this.articulos = articulos;
	}*/
	public String toString(){
		return tipo+" - "+unidadMinima;
	}	
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="tbl_dem_actividad_respuesto", schema="bien_produccion",
               joinColumns=
               @JoinColumn(name="int_respinsu", referencedColumnName="seq_ser_respinsu"),
         inverseJoinColumns=
               @JoinColumn(name="int_actimant", referencedColumnName="seq_ser_actimant")
    )
	/*public List<ActividadMantenimiento> getActividadesMantenimiento() {
		return actividadesMantenimiento;
	}
	public void setActividadesMantenimiento(
			List<ActividadMantenimiento> actividadMantenimiento) {
		this.actividadesMantenimiento = actividadMantenimiento;
	}*/

	public boolean equals(Object objeto){
		try{
			RepuestoEInsumo cuenta = (RepuestoEInsumo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

package cpc.modelo.demeter.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;



@Audited @Entity
@Table(name="tbl_dem_servicio", schema="basico")
public class Servicio implements Serializable, Comparable<Labor>{
	
	private static final long 	serialVersionUID = -1750814937441885420L;
	
	private Integer 			id;
	private String 				descripcion;   
	private TipoServicio		tipoServicio;
	private TipoUnidadMedida	tipoUnidadMedida;
	private Boolean				manejaPases;
	private Boolean				manejaCantidades;
	private Boolean				produccion;
	private TipoUnidadMedida 	tipoUnidadProduccion;
	private TipoUnidadMedida 	tipoUnidadTrabajo;
	
	private List<Labor>			labores;
	
	public Servicio() {
		super();
	}
	
	
	@Column(name="seq_idservicio")
	@SequenceGenerator(name="seqServicio", sequenceName="basico.tbl_dem_servicio_seq_idservicio_seq", allocationSize=1)
	@Id @GeneratedValue(generator="seqServicio")
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

	@ManyToOne
	@JoinColumn(name="int_idtiposervicio")
	public TipoServicio getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	@ManyToOne
	@JoinColumn(name="seq_idtipounidadmedida")
	public TipoUnidadMedida getTipoUnidadMedida() {
		return tipoUnidadMedida;
	}

	public void setTipoUnidadMedida(TipoUnidadMedida tipoUnidadMedida) {
		this.tipoUnidadMedida = tipoUnidadMedida;
	}

	@OneToMany(mappedBy="servicio", targetEntity=Labor.class)
	public List<Labor> getLabores() {
		return labores;
	}
	public void setLabores(List<Labor> labores) {
		this.labores = labores;
	}
	
	@Column(name="bol_pases")
	public Boolean getManejaPases() {
		return manejaPases;
	}
	public void setManejaPases(Boolean manejaPases) {
		this.manejaPases = manejaPases;
	}
	
	@Column(name="bol_cantidades")
	public Boolean getManejaCantidades() {
		return manejaCantidades;
	}
	public void setManejaCantidades(Boolean manejaCantidades) {
		this.manejaCantidades = manejaCantidades;
	}
	
	
	@Column(name="bol_produccion")
	public Boolean getProduccion() {
		return produccion;
	}
	public void setProduccion(Boolean produccion) {
		this.produccion = produccion;
	}

	@ManyToOne
	@JoinColumn(name="seq_idtipounidadproduccion")
	public TipoUnidadMedida getTipoUnidadProduccion() {
		return tipoUnidadProduccion;
	}
	public void setTipoUnidadProduccion(TipoUnidadMedida tipoUnidadProduccion) {
		this.tipoUnidadProduccion = tipoUnidadProduccion;
	}

	@ManyToOne
	@JoinColumn(name="seq_idtipounidadtrabajo")
	public TipoUnidadMedida getTipoUnidadTrabajo() {
		return tipoUnidadTrabajo;
	}
	public void setTipoUnidadTrabajo(TipoUnidadMedida tipoUnidadTrabajo) {
		this.tipoUnidadTrabajo = tipoUnidadTrabajo;
	}
	
	public String toString(){
		if (tipoServicio!=null)
			return tipoServicio.getDescripcion()+" - "+descripcion;
		return descripcion;
	}

	@Transient
	public String getStrTipoServicio() {
		if (tipoServicio==null)
			return null;
		else
			return tipoServicio.getDescripcion();
	}

	@Transient
	public String getStrTipoUnidad() {
		if (tipoUnidadMedida==null)
			return null;
		else
			return tipoUnidadMedida.getDescripcion();
	}
	
	public boolean equals(Object objeto){
		try{
			Servicio cuenta = (Servicio) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}


	public int compareTo(Labor o) {
		if (o == null) return -1;
		return this.id - o.getId();
	}
}

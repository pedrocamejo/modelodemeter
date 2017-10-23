package cpc.modelo.demeter.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Audited @Entity
@Table(name="tbl_dem_unidad_medidas")
public class UnidadMedida implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2892496044835640778L;
	private Integer				id;
	private String				descripcion;
	private String 				abreviatura;
	private boolean 			compuesto;
	private TipoUnidadMedida	tipo;
	
	private List<UnidadMedida>  unidades;

	public UnidadMedida() {
		super();
	}
	
	public UnidadMedida(int id, String descripcion, String abreviatura,
			boolean compuesto, TipoUnidadMedida tipo) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.abreviatura = abreviatura;
		this.compuesto = compuesto;
		this.tipo = tipo;
	}

	@SequenceGenerator(name="SeqUnidadMedida", sequenceName="tbl_dem_unidad_medidas_seq_idumedida_seq", allocationSize=1)
	@Column(name="seq_idumedida")
	@Id @GeneratedValue(generator="SeqUnidadMedida")
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

	@Column(name="str_abreviatura")
	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@Column(name="bol_compuesto")
	public boolean isCompuesto() {
		return compuesto;
	}

	public void setCompuesto(boolean compuesto) {
		this.compuesto = compuesto;
	}
	
	@OneToOne
	@JoinColumn(name="int_idtipounidadmedida")
	public TipoUnidadMedida getTipo() {
		return tipo;
	}

	public void setTipo(TipoUnidadMedida tipo) {
		this.tipo = tipo;
	}


	public String toString() {	
		return this.descripcion.toUpperCase()+" ["+this.abreviatura.toUpperCase()+"]";
	}

/*
 * 		   

 */
	@ManyToMany
	@JoinTable(name="tbl_dem_unidad_compuesta", 
			joinColumns= {@JoinColumn(name="int_idumedidap", referencedColumnName="seq_idumedida")},
			inverseJoinColumns={@JoinColumn(name="int_idumedidah", referencedColumnName="seq_idumedida")}	)
	public List<UnidadMedida> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<UnidadMedida> unidades) {
		this.unidades = unidades;
	}		      
	
	@Transient
	public String getStrTipo() {
		if (tipo == null)
			return null;
		return tipo.getDescripcion();
	}
	
	public boolean equals(Object objeto){
		try{
			UnidadMedida cuenta = (UnidadMedida) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}

package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.ministerio.dimension.UbicacionDireccion;

@Audited @Entity
@Table(name="tbl_dem_silo", schema="basico")
public class UnidadArrime implements Serializable{
	
	private static final long serialVersionUID = -5497624472255357047L;
	private Integer				id;
	private String				descripcion;
	private UbicacionDireccion	direccion;
	private TipoUnidadArrime	tipo;
	private ClaseUnidadArrime	clase;
	private Boolean				publico;
	private Double				capacidadOperativa; 
	
	 @Id
	 @Column(name="seq_idsilo")
	 @SequenceGenerator(name="seqSilo", sequenceName="basico.tbl_dem_silo_seq_idsilo_seq", allocationSize=1)
	 @GeneratedValue(generator="seqSilo")
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
	@JoinColumn(name="int_iddireccion")
	public UbicacionDireccion getDireccion() {
		return direccion;
	}
	public void setDireccion(UbicacionDireccion direccion) {
		this.direccion = direccion;
	}
	
	@Column(name="bol_publico")
	public Boolean getPublico() {
		return publico;
	}
	public void setPublico(Boolean publico) {
		this.publico = publico;
	}	
	
	@ManyToOne
	@JoinColumn(name="int_idtipo")
	public TipoUnidadArrime getTipo() {
		return tipo;
	}
	public void setTipo(TipoUnidadArrime tipo) {
		this.tipo = tipo;
	}

	@ManyToOne
	@JoinColumn(name="int_idclase")
	public ClaseUnidadArrime getClase() {
		return clase;
	}
	public void setClase(ClaseUnidadArrime clase) {
		this.clase = clase;
	}

	@Column(name="dbl_capacidadoper")
	public Double getCapacidadOperativa() {
		return capacidadOperativa;
	}
	public void setCapacidadOperativa(Double capacidadOperativa) {
		this.capacidadOperativa = capacidadOperativa;
	}
	
	public String toString(){
		return descripcion;
	}
	
	@Transient
	public String getStrUbicacionFisica(){
		if (direccion == null)
			return null;
		return direccion.toString();
	}
	
	@Transient
	public String getStrEstado(){
		return publico? "Publico" : "Privado";
	}
	
	@Transient
	public String getStrTipoSilo(){
		if (tipo == null)
			return null;
		return tipo.toString();
	}
	
	
	public boolean equals(Object objeto){
		try{
			UnidadArrime cuenta = (UnidadArrime) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}

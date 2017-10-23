package cpc.modelo.demeter.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_tiporubro", schema="basico")
public class TipoRubro implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6201944426440840133L;
	private Integer 			id;
	private String				nombre;
	private SectorAgricola  	sectorAgricola;
	
	private List<Rubro> rubros;
	
	
	public TipoRubro() {
		super();
	}
	
	@Id
	@Column(name="seq_idtiporubro")
	@SequenceGenerator(name="SeqTipoRubro", sequenceName="basico.tbl_dem_tiporubro_seq_idtiporubro_seq")
	@GeneratedValue(generator="SeqTipoRubro")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idsectoragricola")
	public SectorAgricola getSectorAgricola() {
		return sectorAgricola;
	}

	public void setSectorAgricola(SectorAgricola sectorAgricola) {
		this.sectorAgricola = sectorAgricola;
	} 

	@OneToMany(cascade=CascadeType.ALL, mappedBy="tipoRubro", targetEntity=Rubro.class)
	public List<Rubro> getRubros() {
		return rubros;
	}

	public void setRubros(List<Rubro> rubros) {
		this.rubros = rubros;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoRubro cuenta = (TipoRubro) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

	

}

package cpc.modelo.sigesp.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name = "saf_tipo_almacen", schema = "sigesp")
public class TipoAlmacen implements Serializable{

	private static final long serialVersionUID = 1183848687967834664L;
	private Integer		id;
	private String		nombre, descripcion;
//	private UnidadCentralizada unidadCentralizada;
	private ClaseAlmacen claseAlmacen;
	private boolean		operativo, taller, mobiliario, transitorio;

	/*
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="codemp",referencedColumnName="codemp",insertable = false, updatable = false),
		@JoinColumn(name="coduac",referencedColumnName="coduac",insertable = false, updatable = false)
	})
	public UnidadCentralizada getUnidadCentralizada() {
		return unidadCentralizada;
	}

	public void setUnidadCentralizada(UnidadCentralizada unidadCentralizada) {
		this.unidadCentralizada = unidadCentralizada;
	}
	
	@Transient
	public String getNombreUnidadCentral(){
		return getUnidadCentralizada().getNombre();
	}
	
*/
	public TipoAlmacen() {
		super();
	}
	
	public TipoAlmacen(Integer id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	@SequenceGenerator(name="Seq_idtipoalmacen", sequenceName="sigesp.saf_tipo_almacen_ser_idtipalm_seq", allocationSize=1)
	@Column(name="ser_idtipalm")
	@Id @GeneratedValue(generator="Seq_idtipoalmacen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "str_nombre", nullable=false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="str_descripcion", nullable=false)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_operativo")
	public boolean isOperativo() {
		return operativo;
	}
	
	
	public void setOperativo(boolean operativo) {
		this.operativo = operativo;
	}

	@Column(name="bol_taller")
	public boolean isTaller() {
		return taller;
	}

	public void setTaller(boolean taller) {
		this.taller = taller;
	}
	
	@Column(name="bol_mobiliario")
	public boolean isMobiliario() {
		return mobiliario;
	}

	public void setMobiliario(boolean mobiliario) {
		this.mobiliario = mobiliario;
	}

	@Column(name="bol_transitorio")
	public boolean isTransitorio() {
		return transitorio;
	}

	public void setTransitorio(boolean transitorio) {
		this.transitorio = transitorio;
	}

	public String toString() {
		return getNombre();
	}
	
	public boolean equals(Object objeto){
		try{
			TipoAlmacen tipoAlmacen = (TipoAlmacen) objeto;
			if (tipoAlmacen.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
		
	}
@ManyToOne
@JoinColumn(name="int_idclasealmacen",referencedColumnName="seq_idclasealmacen")
	public ClaseAlmacen getClaseAlmacen() {
		return claseAlmacen;
	}

	public void setClaseAlmacen(ClaseAlmacen claseAlmacen) {
		this.claseAlmacen = claseAlmacen;
	}
	
	
	@Transient
	public String getNombreClase(){
		return claseAlmacen.getNombreClase()+" "+claseAlmacen.getdescripcion();
		
	}
	
}

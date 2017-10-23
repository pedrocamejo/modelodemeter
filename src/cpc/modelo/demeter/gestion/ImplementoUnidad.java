package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.sigesp.basico.Activo;


@Audited @Entity
@Table(name="tbl_dem_implemento_unidad", schema="gestion")
public class ImplementoUnidad implements Serializable{

	private static final long serialVersionUID = -2129220914764776075L;
	private Integer					 id;
	private Activo					 activo;
	private ImplementoImpropio 		 implementoImpropio;
	private UnidadFuncional			 unidad;
	private Boolean					 operativo;
	
	@Id @Column(name="seq_idimplemento")
	@SequenceGenerator(name="seqImplementoUnidad", sequenceName="gestion.tbl_dem_implemento_unidad_seq_idimplemento_seq", allocationSize=1)
	@GeneratedValue(generator="seqImplementoUnidad")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="str_codempmaq",referencedColumnName="codemp"),
		@JoinColumn(name="str_idactivomaq",referencedColumnName="codact"),
		@JoinColumn(name="str_idejemplarmaq",referencedColumnName="ideact")
	})
	public Activo getActivo() {
		return activo;
	}
	public void setActivo(Activo activo) {
		this.activo = activo;
	}
	
	@ManyToOne
	@JoinColumn(name="int_unidadfuncional")
	public UnidadFuncional getUnidad() {
		return unidad;
	}
	public void setUnidad(UnidadFuncional unidad) {
		this.unidad = unidad;
	}
	
	@Column(name="bol_activo")
	@Basic(optional=false)
	public Boolean getOperativo() {
		return operativo;
	}
	public void setOperativo(Boolean operativo) {
		this.operativo = operativo;
	}
	
	public boolean equals(Object objeto){
		try{
			ImplementoUnidad obj = (ImplementoUnidad) objeto;
			if (obj.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	public String toString(){
		if (activo != null)
		return activo.toString();
		else return implementoImpropio.toString();
	}
	
	@Transient
	public String getCodigoActivo(){
		if (activo != null)
		return activo.getCodigoActivo();
		else return " ";
	}
	
	@Transient
	public String getIdEjemplarActivo(){
		if (activo == null)
			return null;
		return activo.getIdEjemplarActivo();
	}
	
	@Transient
	public String getNombre(){
		if (activo != null)
			return activo.getNombre();
		else 
			return implementoImpropio.getNombre();
	}
	
	@Transient
	public String getMarca(){
		if (activo != null){
		if (activo.getMarca() == null)
			return "";
		return activo.getMarca().getDescripcion();
			}
		if (implementoImpropio!=null)
			{
			if (implementoImpropio.getStrMarca()==null)
				return " ";
				else return implementoImpropio.getStrMarca();		
			}
		else
		return " ";
	}

	@Transient
	public String getModelo(){
		if (activo != null){
		if (activo.getModelo() == null)
			return "";
		else
			return activo.getModelo().getDescripcionModelo();
		}
		if (implementoImpropio!=null){
			if (implementoImpropio.getModelo()!=null)
				return implementoImpropio.getStrModelo();
			else return " ";
		}
		else return " ";
	}
	
	@Transient
	public String getSerial(){
		if (activo == null&&implementoImpropio==null)
			return null;
		else {
			if (activo!=null)
			return activo.getSerial();
			else return implementoImpropio.getSerialChasis();
			
		}
		
	}
	
	@Transient
	public String getNombreCompleto(){
		if (activo == null||implementoImpropio==null)
			return null;
		else {
			if (activo!=null)
			return activo.getDescripcionLarga();
			else return implementoImpropio.getDescripcionLarga();
		}
		
	}	
	
	@OneToOne(cascade=CascadeType.ALL,optional=true)
	@JoinColumns(value={
			@JoinColumn(name="implemento_id"), 
			@JoinColumn(name="sede_id")
		})
	public ImplementoImpropio getImplementoImpropio() {
		return implementoImpropio;
	}

	
	public void setImplementoImpropio(ImplementoImpropio implementoImpropio) {
		this.implementoImpropio = implementoImpropio;
	}

	
}

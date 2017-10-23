package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;

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
import cva.pc.demeter.utilidades.Formateador;

@Audited @Entity
@Table(name="tbl_dem_maquinaria_unidad", schema="gestion")
public class MaquinariaUnidad implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6203201016175245353L;
	private Integer			 id;
	private Activo			 activo;
	private MaquinariaImpropia maquinariaImpropia;
	private UnidadFuncional unidad;
	private Boolean			 operativo;
	
	@Id @Column(name="seq_idmaquinaria")
	@SequenceGenerator(name="seqMaquinariaUnidad", sequenceName="gestion.tbl_dem_maquinaria_unidad_seq_idmaquinaria_seq", allocationSize=1)
	@GeneratedValue(generator="seqMaquinariaUnidad")
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
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumns(value={
			//@JoinColumn(name="maquinariaimpropia_id"),
			@JoinColumn(name="int_idmaquinaimpropia"), 
			@JoinColumn(name="sede_id")
		})
	public MaquinariaImpropia getMaquinariaImpropia() {
		return maquinariaImpropia;
	}
	public void setMaquinariaImpropia(MaquinariaImpropia maquinariaImpropia) {
		this.maquinariaImpropia = maquinariaImpropia;
	}
	
	public boolean equals(Object objeto){
		try{
			MaquinariaUnidad obj = (MaquinariaUnidad) objeto;
			if (obj.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	
	@Override
	public String toString(){
		if (activo!=null)
		{
			return activo.toString();
		}
		else
		{
			return maquinariaImpropia.toString();
		}
		
	}
	
	@Transient
	public String getCodigoActivo(){
		if (activo!=null)
		{
			return activo.getCodigoActivo();
		}
		else
		{
		 return Formateador.rellenarNumero(maquinariaImpropia.getId(),"ND-0000000000");
		}
	}
	
	@Transient
	public String getIdEjemplarActivo(){
		if (activo!=null)
		{
			return activo.getIdEjemplarActivo();
		}
		else
		{
			return Formateador.rellenarNumero(maquinariaImpropia.getId(),"ND-0000000000");
		}
	}
	
	@Transient
	public String getNombre(){
		if (activo!=null)
		{
			return activo.getNombre();}
		else 
			return maquinariaImpropia.getNombre();

	}
	
	@Transient
	public String getMarca(){
		if (activo!=null)
		{
			if (activo.getMarca() == null)
				return "";
			else return activo.getMarca().getDescripcion();
		}
		else
		{
			if (maquinariaImpropia.getMarca() == null)
				return "";
			else return maquinariaImpropia.getMarca().getDescripcion();
		}
	}

	@Transient
	public String getModelo(){
		if (activo!=null)
		{
			if (activo.getModelo() == null)
			return "";
			else return activo.getModelo().getDescripcionModelo();
		}
		else
		{
			if (maquinariaImpropia.getModelo() == null)
			return "";
			else return maquinariaImpropia.getModelo().getDescripcionModelo();

		}
	}
	
	@Transient
	public String getSerial(){
		if (activo!=null)
		{
			return activo.getSerial();
		}
		else return maquinariaImpropia.getSerialChasis();
		
		
	}
	
	@Transient
	public String getNombreCompleto(){
		if (activo!=null)
		{
			return activo.toString();
		}
		else
		{
			return Formateador.rellenarNumero(maquinariaImpropia.getId(),"ND-0000000000");
		}
		
	}
	 
	
}

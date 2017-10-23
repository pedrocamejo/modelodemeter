package cpc.modelo.demeter.basico;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_tipo_servicio")
public class TipoServicio implements Serializable, Comparable<Labor> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4049159200796027277L;
	private Integer	id;
	private String 	nombre;
	private String 	descripcion;
	private boolean activo;
	
	private Boolean	mecanizado;
	private Boolean	transporte;
	private Boolean	transporteCosecha;
	private Boolean	lineaAmarilla;
	
	
	public TipoServicio(){
		
	}
	
	public TipoServicio(Integer id, String nombre, String descripcion,
			boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.activo = activo;
	}


	@Column(name="seq_idtiposervicio")
	@SequenceGenerator(name="seqTipSer", sequenceName="tbl_dem_tiposervicio_seq_idtiposervicio_seq", allocationSize =1)
	@Id @GeneratedValue(generator="seqTipSer")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_activo")
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Column(name="bol_mecanizado")
	public Boolean getMecanizado() {
		return mecanizado;
	}
	public void setMecanizado(Boolean mecanizado) {
		this.mecanizado = mecanizado;
	}

	@Column(name="bol_transporte")
	public Boolean getTransporte() {
		return transporte;
	}
	public void setTransporte(Boolean transporte) {
		this.transporte = transporte;
	}

	@Column(name="bol_transcosecha")
	public Boolean getTransporteCosecha() {
		return transporteCosecha;
	}
	public void setTransporteCosecha(Boolean transporteCosecha) {
		this.transporteCosecha = transporteCosecha;
	}

	@Column(name="bol_lineaamarilla")
	public Boolean getLineaAmarilla() {
		return lineaAmarilla;
	}
	public void setLineaAmarilla(Boolean lineaAmarilla) {
		this.lineaAmarilla = lineaAmarilla;
	} 

	@Transient
	public String getStrActivo() {
		return activo ? "Activo" :"Inactivo";
		
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoServicio cuenta = (TipoServicio) objeto;
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

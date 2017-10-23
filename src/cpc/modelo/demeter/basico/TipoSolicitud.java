package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipo_solicitud")
public class TipoSolicitud implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2179266419912889615L;
	private Integer			id;
	private String			nombre;
	private boolean			usaFicha;
	private boolean			activo;
	private String			serie;
	
	public TipoSolicitud() {
		super();
	}
	
	
	@SequenceGenerator(name="SeqTipoSolicitud", sequenceName="tbl_dem_tipo_solicitud_seq_idtiposervicio_seq", initialValue=1)
	@Column(name="seq_idtiposolicitud")
	@Id @GeneratedValue(generator="SeqTipoSolicitud")
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

	@Column(name="bol_maneja_tecnico")
	public boolean isUsaFicha() {
		return usaFicha;
	}


	public void setUsaFicha(boolean ficha) {
		this.usaFicha = ficha;
	}

	@Column(name="bol_activo")
	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Column(name="str_serie")
	public String getSerie() {
		return serie;
	}


	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	@Override
	public String toString() {	
		return this.nombre;
	}

	public boolean equals(Object objeto){
		try{
			TipoSolicitud cuenta = (TipoSolicitud) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
	
}

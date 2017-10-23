package cpc.modelo.demeter.sincronizacion;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;


@XmlRootElement
@Entity
//@Cache (usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "tbl_dem_sedeDemeter",schema="sincronizacion")
public class SedeDemeter implements Serializable{
	
	
	private String idSede;
	private String sede;
	private boolean padre;
	private Integer  controlSolicitudMecanizado;
	private String   prefijoSolicitudMecanizado;
	@Id
	public String getIdSede() {
		return idSede;
	}
	
	
	@Column(nullable=false)
	public String getSede() {
		return sede;
	}
	public void setIdSede(String idSede) {
		this.idSede = idSede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}

	@Column(nullable=false)
	public boolean isPadre() {
		return padre;
	}


	public void setPadre(boolean padre) {
		this.padre = padre;
	}

	@Column(nullable=false,name="int_controlsolicitudmecanizado")
	public Integer getControlSolicitudMecanizado() {
		return controlSolicitudMecanizado;
	}


	public void setControlSolicitudMecanizado(Integer controlSolicitudMecanizado) {
		this.controlSolicitudMecanizado = controlSolicitudMecanizado;
	}

	@Column(nullable=false,name="str_prefijosolicitudmecanizado")
	public String getPrefijoSolicitudMecanizado() {
		return prefijoSolicitudMecanizado;
	}


	public void setPrefijoSolicitudMecanizado(String prefijoSolicitudMecanizado) {
		this.prefijoSolicitudMecanizado = prefijoSolicitudMecanizado;
	}

	@Transient
	public Integer incrementarSolicitud() {
		
		return controlSolicitudMecanizado++;
	}




	
	
	
	

}

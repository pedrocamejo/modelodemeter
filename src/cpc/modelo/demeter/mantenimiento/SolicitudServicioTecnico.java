package cpc.modelo.demeter.mantenimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.CicloProductivo;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.gestion.Solicitud;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;

@Audited 
@Entity
@PrimaryKeyJoinColumn(name="int_idsolicitud")
@Table(name="tbl_dem_solicitudserviciotecnico", schema="mantenimiento")
public class SolicitudServicioTecnico extends Solicitud{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4613805401968569479L;
	private Servicio				servicio;
	private InstitucionCrediticia	financiamiento;
	private UbicacionSector			sector;
 
    private Modelo                  modelo;  
    private String                  serial;
  


	
	@ManyToOne
	@JoinColumn(name="int_idservicio")
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	
	
	@ManyToOne
	@JoinColumn(name="int_idfinanciamiento")
	public InstitucionCrediticia getFinanciamiento() {
		return financiamiento;
	}
	public void setFinanciamiento(InstitucionCrediticia financiamiento) {
		this.financiamiento = financiamiento;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idsector")
	public UbicacionSector getSector() {
		return sector;
	}

	public void setSector(UbicacionSector sector) {
		this.sector = sector;
	}
	
	
	@OneToOne
	@JoinColumns({ 
	    @JoinColumn(name="str_idmarca", referencedColumnName="str_idmarca", insertable=true, updatable=true), 
	    @JoinColumn(name="str_idmodelo", referencedColumnName="str_idmodelo", insertable=true, updatable=true),  
	})
	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	
	 @Column(name="str_serial")
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	

	@Transient
	public boolean isManejaCantidad(){
		if (servicio == null)
			return false;
		return servicio.getManejaCantidades();
	}
	
	@Transient
	public boolean isManejaPases(){
		if (servicio == null)
			return false;
		return servicio.getManejaPases();
	}
	
	@Transient
	public String getStrServicio() {
		if (servicio== null)
			return "";
		return servicio.getDescripcion();
	}
	
	
	
}
package cpc.modelo.demeter.administrativo;




import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity; 

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

 


import cpc.modelo.demeter.transporte.UbicacionTransporte; 
import cva.pc.demeter.utilidades.Fecha;


@Audited
@Entity
@Table(name="tbl_dem_cotizaciontransporte", schema="administracion")
@PrimaryKeyJoinColumn(name="int_idcontrato")

public class CotizacionTransporte extends Contrato {

	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7983595042870431956L;
	private boolean				expediente;
	private UbicacionTransporte 	direccionOrigen;
	private UbicacionTransporte 	direccionLlegada;
	private String 					motivoRechazo;
	private String 					observacion;	
    
	
	@Transient
	public Date getFechaHasta(){
	Date fecha = getFechaDesde();
	Calendar calendario = Calendar.getInstance();
	calendario.setTime(getFechaDesde());
	calendario.add(Calendar.DATE, getDiasVigencia());
	fecha = calendario.getTime();
	
	return fecha;
	}
	
	@Column(name="bol_expediente")
	public boolean getExpediente() {
		return expediente;
	}

	public void setExpediente(boolean expediente) {
		this.expediente = expediente;
	}
	
	@Transient
	public String getFechaHastaString(){
		Date fecha = getFechaDesde();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(getFechaDesde());
		calendario.add(Calendar.DATE, getDiasVigencia());
		fecha = calendario.getTime();
		
		return Fecha.obtenerFecha(fecha);
		}
	

	@Transient
	public String getNombreCliente(){
		
		if (getPagador()== null)
			return "";
		return getPagador().getNombres();
	}
    @OneToOne
	@JoinColumn(name ="int_iddireccionorigen" )
	public UbicacionTransporte getDireccionOrigen() {
		return direccionOrigen;
	}

	public void setDireccionOrigen(UbicacionTransporte direccionOrigen) {
		this.direccionOrigen = direccionOrigen;
	}
	@OneToOne
	@JoinColumn(name ="int_iddireccionllegada" )
	public UbicacionTransporte getDireccionLlegada() {
		return direccionLlegada;
	}

	public void setDireccionLlegada(UbicacionTransporte direccionLlegada) {
		this.direccionLlegada = direccionLlegada;
	}
	@Column(name="str_motivorechazo",length=255)
	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	@Column(name="str_observacion",length=255)
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	
	
	
}
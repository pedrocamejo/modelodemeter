package cpc.modelo.demeter.gestion;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Entity; 

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.CicloProductivo;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.UnidadProductiva;




@Audited @Entity
@PrimaryKeyJoinColumn(name="int_idsolicitud")
@Table(name="tbl_dem_solicitudmecanizado", schema="gestion")
public class SolicitudMecanizado extends Solicitud{
	
	private static final long serialVersionUID = -9213696831954964929L;
	private UnidadProductiva 		direccion;
	private Rubro					rubro;
	private Servicio				servicio;
	private CicloProductivo			ciclo;
	private InstitucionCrediticia	financiamiento;
	private Date						fechaAtencion;

	@ManyToOne
	@JoinColumn(name="int_idunidadproduccion")
	public UnidadProductiva getDireccion() {
		return direccion;
	}
	public void setDireccion(UnidadProductiva direccion) {
		this.direccion = direccion;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idrubro")
	@Basic(optional=true)
	public Rubro getRubro() {
		return rubro;
	}
	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	@ManyToOne
	@JoinColumn(name="int_idservicio")
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	@ManyToOne
	@JoinColumn(name="int_idciclo")
	public CicloProductivo getCiclo() {
		return ciclo;
	}
	public void setCiclo(CicloProductivo ciclo) {
		this.ciclo = ciclo;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idfinanciamiento")
	public InstitucionCrediticia getFinanciamiento() {
		return financiamiento;
	}
	public void setFinanciamiento(InstitucionCrediticia financiamiento) {
		this.financiamiento = financiamiento;
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
	
	@Transient
	public String getStrRubro() {
		if (rubro== null)
			return "";
		return rubro.getDescripcion();
	}

	@Transient
	public String getStrCiclo() {
		if (ciclo== null)
			return "";
		return ciclo.getDescripcion();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ciclo == null) ? 0 : ciclo.hashCode());
		result = prime * result
				+ ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result
				+ ((financiamiento == null) ? 0 : financiamiento.hashCode());
		result = prime * result + ((rubro == null) ? 0 : rubro.hashCode());
		result = prime * result
				+ ((servicio == null) ? 0 : servicio.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitudMecanizado other = (SolicitudMecanizado) obj;
		if (ciclo == null) {
			if (other.ciclo != null)
				return false;
		} else if (!ciclo.equals(other.ciclo))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (financiamiento == null) {
			if (other.financiamiento != null)
				return false;
		} else if (!financiamiento.equals(other.financiamiento))
			return false;
		if (rubro == null) {
			if (other.rubro != null)
				return false;
		} else if (!rubro.equals(other.rubro))
			return false;
		if (servicio == null) {
			if (other.servicio != null)
				return false;
		} else if (!servicio.equals(other.servicio))
			return false;
		return true;
	}
	
	@Column(name = "dtm_fechaatencion")
	public Date getFechaAtencion() {
		return fechaAtencion;
	}
	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}
	
	@Transient
	public HashMap<String, Double> getMapaTotales() {
		HashMap<String, Double> mapa = new HashMap<String, Double>();
		Double MontoTotal = new Double(0);
		Double MontoImponible = new Double(0);
		Double MontoImpuesto = new Double(0);
		Double MontoExcento = new Double(0);
		List<DetalleSolicitud> detalles = getDetalles();
		for (DetalleSolicitud detalleSolicitud : detalles) {
			Double precio = detalleSolicitud.getProducto().getPrecioBase(getProductor().getTipo());
			Labor labor = (Labor) detalleSolicitud.getProducto();
			UnidadMedida unidadCobro = labor.getMedidaCobro();
			Double CantidadCobrar = new Double(0);
			List<UnidadSolicitada> solicitado = detalleSolicitud.getSolicitado();
			for (UnidadSolicitada unidadSolicitada : solicitado) {
				if (unidadSolicitada.getUnidad().getDescripcion().equals(unidadCobro.getDescripcion()))
				{CantidadCobrar=unidadSolicitada.getCantidad();
				if (isManejaPases()){
					if (detalleSolicitud.getPases()!=null)
					CantidadCobrar=detalleSolicitud.getPases()*CantidadCobrar;
				}
					
				}
			}
			if (labor.getImpuesto().getPorcentaje()==new Double(0.0))
			MontoExcento=MontoExcento+(CantidadCobrar*precio);
			else {
				MontoImponible=MontoImponible+(CantidadCobrar*precio);
				MontoImpuesto=MontoImpuesto+ ((CantidadCobrar*(precio*labor.getImpuesto().getPorcentaje()/100)));
			}
			
			
		}
		mapa.put("MontoImponible", MontoImponible);
		mapa.put("MontoImpuesto",MontoImpuesto);
		mapa.put("MontoExcento",MontoExcento);
		MontoTotal=MontoExcento+MontoImponible+MontoImpuesto;
		mapa.put("MontoTotal",MontoTotal);
		return mapa;
		
	}


	
}

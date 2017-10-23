package cpc.modelo.demeter.administrativo;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity; import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
 
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
 
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
 


@Audited
@Entity
@Table(name="tbl_dem_contratoserviciotecnico", schema="administracion")
@PrimaryKeyJoinColumn(name="int_idcontrato")

public class ContratoServicioTecnico extends Contrato {

	
	private static final long serialVersionUID = -3320335685957826247L;
	private SolicitudServicioTecnico		solicitud;
	private List<DetalleContratoSalidaArticulo> detalleContratoSalidaArticulos;
	private List<DetalleContratoDevolucionArticulo>  detalleContratoDevolucionArticulos;
	
	
	
	@OneToOne 
	@JoinColumn(name="int_idsolicitudserviciotecnico")
	@Basic(optional=true)
	public SolicitudServicioTecnico getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(SolicitudServicioTecnico solicitud) {
		this.solicitud = solicitud;
	}
	
	@Transient
	public String getStrSolicitud(){
		if (solicitud == null)
			return "";
		return solicitud.getNroControl();
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="contratoServicioTecnico", targetEntity=DetalleContratoSalidaArticulo.class, fetch=FetchType.EAGER)		
	public List<DetalleContratoSalidaArticulo> getDetalleContratoSalidaArticulos() {
		return detalleContratoSalidaArticulos;
	}
	public void setDetalleContratoSalidaArticulos(
			List<DetalleContratoSalidaArticulo> detalleContratoSalidaArticulos) {
		this.detalleContratoSalidaArticulos = detalleContratoSalidaArticulos;
	}
	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="contratoServicioTecnico", targetEntity=DetalleContratoDevolucionArticulo.class, fetch=FetchType.EAGER)
	@Basic(optional=true)
	public List<DetalleContratoDevolucionArticulo> getDetalleContratoDevolucionArticulos() {
		return detalleContratoDevolucionArticulos;
	}
	public void setDetalleContratoDevolucionArticulos(
			List<DetalleContratoDevolucionArticulo> detalleContratoDevolucionArticulos) {
		this.detalleContratoDevolucionArticulos = detalleContratoDevolucionArticulos;
	}
	
	@Transient
	public String getNombreCliente(){
		if (solicitud == null)
			return "";
		if (solicitud.getProductor() == null)
			return "";
		return solicitud.getProductor().getNombres();
	}
	
	
}
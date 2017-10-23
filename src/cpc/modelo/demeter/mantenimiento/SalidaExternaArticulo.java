package cpc.modelo.demeter.mantenimiento;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.Cotizacion;
import cpc.modelo.ministerio.gestion.Cliente;

@Audited
@Entity
@Table(name="tbl_dem_salidaexterna_articulo",schema="mantenimiento")
@PrimaryKeyJoinColumn(name="int_idmovimientoarticulo")
public class SalidaExternaArticulo extends MovimientoArticulo  implements Serializable{
	
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6029137233943493691L;
	/**
	 * 
	 */
    private SolicitudServicioTecnico solicitudServicioTecnico;
    private Cotizacion cotizacion;
    private ClienteAdministrativo destinatario;
	
	@OneToOne 
	@JoinColumn (name="int_idcliente")
	public ClienteAdministrativo getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(ClienteAdministrativo destinatario) {
		this.destinatario = destinatario;
	}
	
	
	@OneToOne 
	@JoinColumn(name="int_idsolicitudservicotecnico")
	@Basic(optional=true)
	public SolicitudServicioTecnico getSolicitudServicioTecnico() {
		return solicitudServicioTecnico;
	}
	public void setSolicitudServicioTecnico(
			SolicitudServicioTecnico solicitudServicioTecnico) {
		this.solicitudServicioTecnico = solicitudServicioTecnico;
	}
	
	
	
	@OneToOne 
	@JoinColumn(name="int_idcotizacionservicotecnico")
	@Basic(optional=true)
	public Cotizacion getCotizacion() {
		return cotizacion;
	}
	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}
	
	
	
	
	
}

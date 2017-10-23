package cpc.modelo.demeter.administrativo;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.ministerio.gestion.UnidadProductiva;



@Audited @Entity
@Table(name="tbl_dem_contratomecanizado", schema="administracion")
@PrimaryKeyJoinColumn(name="int_idcontrato")

public class ContratoMecanizado extends Contrato {

	
	private static final long serialVersionUID = -3320335685957826247L;
	private SolicitudMecanizado		solicitud;
	private UnidadProductiva	 	unidadProductiva;
	
	
	@OneToOne 
	@JoinColumn(name="int_idsolicitudmecanizado")
	@Basic(optional=true)
	public SolicitudMecanizado getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(SolicitudMecanizado solicitud) {
		this.solicitud = solicitud;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadproductiva")
	public UnidadProductiva getUnidadProductiva() {
		return unidadProductiva;
	}
	public void setUnidadProductiva(UnidadProductiva unidadProductiva) {
		this.unidadProductiva = unidadProductiva;
	}
	
	@Transient
	public String getStrSolicitud(){
		if (solicitud == null)
			return "";
		return solicitud.getNroControl();
	}
	
	@Transient
	public String getNombreCliente(){
		if (unidadProductiva == null)
			return "";
		if (unidadProductiva.getProductor() == null)
			return "";
		return unidadProductiva.getProductor().getNombres();
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ContratoMecanizado)) {
			return false;
		}
		ContratoMecanizado other = (ContratoMecanizado) o;
		return true && other.getId().equals(this.getId());
	}
	
}

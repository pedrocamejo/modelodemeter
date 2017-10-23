package cpc.modelo.demeter.administrativo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity; import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

 
import cpc.modelo.demeter.mantenimiento.SalidaExternaArticulo;
import cpc.modelo.sigesp.basico.Sede;
 
import cva.pc.demeter.utilidades.Fecha;


@Audited
@Entity
@Table(name="tbl_dem_cotizacion", schema="administracion")
@PrimaryKeyJoinColumn(name="int_idcontrato")

public class Cotizacion extends Contrato {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7590339026573703472L;
	private SalidaExternaArticulo salidaExternaArticulo;
	private boolean expediente;
	private TipoCotizacion tipoCotizacion; // Objecto para especificar si la cotizacion es por mecanizado o es por vialidad agricola 
	private Sede 	sede;

	
	
	@OneToOne 
	@JoinColumn(name="int_idsalidaexternaarticulo")
	
	public SalidaExternaArticulo getSalidaExternaArticulo() {
		return salidaExternaArticulo;
	}

	public void setSalidaExternaArticulo(SalidaExternaArticulo salidaExternaArticulo) {
		this.salidaExternaArticulo = salidaExternaArticulo;
	}

	@Transient
	public Date getFechaHasta(){
	Date fecha = getFecha();
	Calendar calendario = Calendar.getInstance();
	calendario.setTime(getFecha());
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
		Date fecha = getFecha();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(getFecha());
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

	
	@OneToOne(fetch=FetchType.EAGER,targetEntity=TipoCotizacion.class)
	public TipoCotizacion getTipoCotizacion() {
		return tipoCotizacion;
	}

	public void setTipoCotizacion(TipoCotizacion tipoCotizacion) {
		this.tipoCotizacion = tipoCotizacion;
	}
	

	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="str_codemp", referencedColumnName="codemp", insertable=false, updatable=false),
	    @JoinColumn(name="str_sede", referencedColumnName="codubifis", insertable=false, updatable=false),
	})
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	/*
	 * 
	 * alter table administracion.tbl_dem_cotizacion
	 * add column str_codemp character(4);
	 * 
	 * alter table administracion.tbl_dem_cotizacion
	 * add column str_sede character(4);
	 * 
	 * alter table auditoria.tbl_dem_cotizacion_aud
	 * add column str_codemp character(4);
	 * 
	 * alter table auditoria.tbl_dem_cotizacion_aud
	 * add column str_sede character(4);

	 * */
}

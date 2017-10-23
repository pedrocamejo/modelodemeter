package cpc.modelo.demeter.administrativo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Sede;
import cva.pc.demeter.utilidades.Fecha;
import cpc.modelo.demeter.administrativo.ArchivoContrato;;

@Audited
@Entity
@Table(name="tbl_dem_cotizacionvialidad", schema="administracion")
@PrimaryKeyJoinColumn(name="int_idcontrato")

public class CotizacionVialidad extends Contrato {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5578442358327128799L;
	private Cliente beneficiario;
    private List<ArchivoContrato> archivoContrato;
	private UbicacionDireccion ubicacion;
	private Sede 	sede;
	
	
    @ManyToOne
    @JoinColumn(name="int_idbeneficiario")
	public Cliente getBeneficiario() {
		return beneficiario;
	}


	public void setBeneficiario(Cliente beneficiario) {
		this.beneficiario = beneficiario;
	}

	@OneToMany(mappedBy="contrato",targetEntity=ArchivoContrato.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public List<ArchivoContrato> getArchivoContrato() {
		return archivoContrato;
	}


	public void setArchivoContrato(List<ArchivoContrato> archivoContrato) {
		this.archivoContrato = archivoContrato;
	}
	
	
	@OneToOne
	@JoinColumn(name="int_iddireccion")
	public UbicacionDireccion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(UbicacionDireccion ubicacion) {
		this.ubicacion = ubicacion;
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

	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="str_codemp", referencedColumnName="codemp"),
	    @JoinColumn(name="str_sede", referencedColumnName="codubifis"),
	})
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	/*
	 *  alter table administracion.tbl_dem_cotizacionvialidad
	 *	add column str_codemp character(4);
	 *	
	 *	alter table administracion.tbl_dem_cotizacionvialidad
	 *   add column str_sede character(4);
	 * 
	 *   alter table auditoria.tbl_dem_cotizacionvialidad_aud
	 *   add column str_codemp character(4);
	 *   
	 *   alter table  auditoria.tbl_dem_cotizacionvialidad_aud
	 *    add column str_sede character(4);
	 * */

}
package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
//import javax.persistence.Transient;

import cpc.modelo.sigesp.basico.UnidadAdministrativa;
//import cva.pc.demeter.utilidades.Fecha;

@Audited @Entity
@PrimaryKeyJoinColumn(name = "int_idmoviact")
@Table(name= "saf_traslado", schema = "sigesp")
public class TrasladoActivo extends Movimiento implements Serializable{
	
	private static final long serialVersionUID = -3658566186292231524L;
	//private ActaAutorizacion 	actaAutorizacion;
	private String 				responsableTraslado, observaciones, cedulaResponsable, vehiculo, placaVehiculo;

	public TrasladoActivo() {
		super();
	}

	public TrasladoActivo(Date fecha, TipoMovimiento tipomovimiento,
			UnidadAdministrativa unidadAdministrativa, String usuario) {
		super(fecha, tipomovimiento, unidadAdministrativa, usuario);
	}
	
	public TrasladoActivo(Date fecha, TipoMovimiento tipomovimiento, String usuario) {
		super(fecha, tipomovimiento, usuario);
	}

	/*@OneToOne
	@JoinColumn(name="int_idactaautori")
	public ActaAutorizacion getActaAutorizacion() {
		return actaAutorizacion;
	}

	public void setActaAutorizacion(ActaAutorizacion actaAutorizacion) {
		this.actaAutorizacion = actaAutorizacion;
	}*/

	@Column(name="str_resptraslado")
	public String getResponsableTraslado() {
		return responsableTraslado;
	}

	public void setResponsableTraslado(String responsableTraslado) {
		this.responsableTraslado = responsableTraslado;
	}

	@Column(name="str_observaciones")
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	/*@Transient
	public String getNumeroActaAutorizacion(){
		return getActaAutorizacion().getNumeroActa();
	}
	
	@Transient
	public String getFechaActaAutorizacion(){
		return Fecha.obtenerFecha(getActaAutorizacion().getFechaActa());
	}*/
	
	@Column(name="str_cedresptrasl")
	public String getCedulaResponsable() {
		return cedulaResponsable;
	}

	public void setCedulaResponsable(String cedulaResponsable) {
		this.cedulaResponsable = cedulaResponsable;
	}

	@Column(name="str_vehiculo")
	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	@Column(name="str_placa")
	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public boolean equals(Object objeto){
		try{
			TrasladoActivo cuenta = (TrasladoActivo) objeto;
			if (cuenta.getIdmovimiento().equals(getIdmovimiento()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

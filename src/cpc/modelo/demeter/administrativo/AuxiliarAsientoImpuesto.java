package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="viw_detalle_asiento_impuestos", schema="administracion")
@IdClass(AuxiliarAsientoImpuestoPK.class)
public class AuxiliarAsientoImpuesto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 634128807220649585L;
	private Date 				fecha;
	private Boolean				tipo;
	private double				monto;
	private String				nombre;
	
	@Id
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Id
	public Boolean getTipo() {
		return tipo;
	}
	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}
	
	
	@Column(name="ingreso")
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Column(name="str_descripcion")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

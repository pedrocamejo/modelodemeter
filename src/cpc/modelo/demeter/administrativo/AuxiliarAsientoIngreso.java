package cpc.modelo.demeter.administrativo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="viw_detalle_asiento_ingresos", schema="administracion")
@IdClass(AuxiliarAsientoIngresoPK.class)
public class AuxiliarAsientoIngreso {
	private Date 				fecha;
	private Boolean				tipo;
	private String				cuentaContable;
	private String				cuentaPresupuestaria;
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
	
	@Id
	public String getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	
	@Column(name="str_presupuestoingreso")
	public String getCuentaPresupuestaria() {
		return cuentaPresupuestaria;
	}
	public void setCuentaPresupuestaria(String cuentaPresupuestaria) {
		this.cuentaPresupuestaria = cuentaPresupuestaria;
	}
	
	@Column(name="ingreso")
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Column(name="str_nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

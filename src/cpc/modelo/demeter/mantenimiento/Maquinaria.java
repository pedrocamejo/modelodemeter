package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.Trabajador;


@Audited @Entity
@Table(name = "tbl_dem_maquinaria", schema = "mantenimiento")
@PrimaryKeyJoinColumn(name="int_idobjeto")
	
public class Maquinaria extends ObjetoMantenimiento implements Serializable {
	
	private static final long serialVersionUID = 1884995101343064453L;

	private double					caballoFuerza; 
	private String					serialCarroceria; 
	private String					serialMotor;
	private String					placa;
	private TipoMedidaRendimiento	tipoMedidaRendimiento;
	private Trabajador				operador;
		
	
	public Maquinaria() {
		super();
	}
	
	

	@Column(name="dbl_caballofuerza")
	public double getCaballoFuerza() {
		return caballoFuerza;
	}

	public void setCaballoFuerza(double caballoFuerza) {
		this.caballoFuerza = caballoFuerza;
	}

	@Column(name="str_serialcarroceria")
	public String getSerialCarroceria() {
		return serialCarroceria;
	}

	public void setSerialCarroceria(String serialCarroceria) {
		this.serialCarroceria = serialCarroceria;
	}

	@Column(name="str_serialmotor")
	public String getSerialMotor() {
		return serialMotor;
	}

	public void setSerialMotor(String serialMotor) {
		this.serialMotor = serialMotor;
	}

	@Column(name="str_placa")
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String strPlaca) {
		placa = strPlaca;
	}

	@Transient
	public Trabajador getOperador() {
		return operador;
	}

	public void setOperador(Trabajador operador) {
		this.operador = operador;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipomedidarendimiento")
	public TipoMedidaRendimiento getTipoMedidaRendimiento() {
		return tipoMedidaRendimiento;
	}

	public void setTipoMedidaRendimiento(TipoMedidaRendimiento tipoMedidaRendimiento) {
		this.tipoMedidaRendimiento = tipoMedidaRendimiento;
	}

	
	
	
}
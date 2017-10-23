package cpc.modelo.demeter.mantenimiento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionId;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.sun.org.apache.regexp.internal.recompile;

import cva.pc.componentes.CompEncabezado;



@Entity
@Audited
@AuditTable(schema="auditoria",value="tbl_dem_CertificadoOrigen_aud")
@Table(name = "tbl_dem_CertificadoOrigen", schema = "mantenimiento")
public class CertificadoOrigen {

	
	private  String 				nroControl; // que genera 
	private  PlantaDistribuidora 	plantaOrigen;
	private  MaquinariaExterna		maquinariaExterna;
	private  Date 					fechaGeneracion; // fecha de generacion del documento
	private  String					nroSeguridad; // que genera el sistema 
	private  String 				nroFactura;
	private  Integer				status = 0; // 0 GENERADA  1 Procesada  
	
	@Id
	@Column(name="str_idnrocontrol")
	public String getNroControl() {
		return nroControl;
	}
	public void setNroControl(String nroControl) {
		this.nroControl = nroControl;
	}
	
	@OneToOne
	@JoinColumn(name="int_idPlantadistribuidora",nullable=false)
	public PlantaDistribuidora getPlantaOrigen() {
		return plantaOrigen;
	}
	public void setPlantaOrigen(PlantaDistribuidora plantaOrigen) {
		this.plantaOrigen = plantaOrigen;
	}
	
	@OneToOne
	@JoinColumn(name="int_idMaquinariaExterna",unique=true)
	public MaquinariaExterna getMaquinariaExterna() {
		return maquinariaExterna;
	}
	public void setMaquinariaExterna(MaquinariaExterna maquinariaExterna) {
		this.maquinariaExterna = maquinariaExterna;
	}
	
	@Column
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	
	@Column(name="str_nro_seguridad",unique=true)
	public String getNroSeguridad() {
		return nroSeguridad;
	}
	public void setNroSeguridad(String nroSeguridad) {
		this.nroSeguridad = nroSeguridad;
	}
	
	@Column(name="int_status",nullable=false)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="str_nroFactura")
	public String getNroFactura() {
		return nroFactura;
	}
	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}
	
	
	@Transient
	public String getStr_PlantaOrigen()
	{
		return (plantaOrigen !=null ? plantaOrigen.getNombre():"");
	}

	@Transient
	public String getStr_SerialCarroceria()
	{
		return maquinariaExterna.getSerialcarroceria();
	}

	@Transient
	public String getStr_SerialMotor()
	{
		return maquinariaExterna.getSerialMotor();
	}
	@Transient
	public String getStrPropietario()
	{
		return maquinariaExterna.getStrPropietario();
	}

	@Transient
	public String getEstatus()
	{
		if(status.equals(0))
		{
			return "GENERADA";
		}
		else if (status.equals(1))
		{
			return "PROCESADA";
		}
		return "";
	}
}

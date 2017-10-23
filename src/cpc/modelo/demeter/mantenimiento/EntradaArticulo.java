package cpc.modelo.demeter.mantenimiento;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Audited
@Entity
@PrimaryKeyJoinColumn(name="int_idmovimientoarticulo")
@Table(name="tbl_dem_entrada_articulo",schema="mantenimiento")

public class EntradaArticulo extends MovimientoArticulo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6837188312163222300L;
	
	String transporte,placa,responsable;
	@Column(name="str_transporte")
	public String getTransporte() {
		return transporte;
	}

	public void setTransporte(String transporte) {
		this.transporte = transporte;
	}
	@Column(name="str_placa")
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	@Column(name="str_responsable")
	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		
		this.responsable = responsable;
	}

}

package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.Trabajador;
@Audited
@Entity
@Table(name="tbl_dem_transferencia_articulo",schema="mantenimiento")
@PrimaryKeyJoinColumn(name="int_idmovimientoarticulo")
public class TransferenciaArticulo extends MovimientoArticulo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -200754410740519420L;
	private Trabajador trabajadorSolicitante;
	
	
	@OneToOne
	@JoinColumn(name="int_idtrabajador")
	public Trabajador getTrabajadorSolicitante() {
		return trabajadorSolicitante;
	}
	public void setTrabajadorSolicitante(Trabajador trabajador) {
		this.trabajadorSolicitante = trabajador;
	} 
	
	
	
	
	

}

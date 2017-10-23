package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.sigesp.basico.Activo;

@Audited
@Entity
@Table(name="tbl_dem_salidainterna_articulo",schema="mantenimiento")
@PrimaryKeyJoinColumn(name="int_idmovimientoarticulo")

public class SalidaInternaArticulo extends MovimientoArticulo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4133497403785622974L;

	/**
	 * 
	 */
	
	
	private Trabajador trabajador;
	private Activo     activo;

	@OneToOne
	@JoinColumn(name="int_idtrabajador")
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="codemp",referencedColumnName="codemp"),
		@JoinColumn(name="codact",referencedColumnName="codact"),
		@JoinColumn(name="ideact",referencedColumnName="ideact")
	})
	public Activo getActivo() {
		return activo;
	}
	public void setActivo(Activo activo) {
		this.activo = activo;
	}
		
	
	
}

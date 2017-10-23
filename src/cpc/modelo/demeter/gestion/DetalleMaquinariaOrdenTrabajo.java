package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Trabajador;

@Audited @Entity
@Table(name="tbl_dem_detalle_maquinaria_orden_trabajo", schema="gestion")
public class DetalleMaquinariaOrdenTrabajo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1642606956321447969L;
	private Long 				id;
	private OrdenTrabajo		ordenTrabajo;
	private MaquinariaUnidad	maquinaria;
	private ImplementoUnidad	implemento;
	private Trabajador			operador;
	private Boolean				operativa;
	
	

	@Id @Column(name="seq_idmaquinaorden")
	@SequenceGenerator(name="seqMaquinaOrden", sequenceName="gestion.tbl_dem_detalle_maquinaria_orden_trabajo_seq_idmaquinaorden_seq", allocationSize=1)
	@GeneratedValue(generator="seqMaquinaOrden")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idordenservicio")
	public OrdenTrabajo getOrdenTrabajo() {
		return ordenTrabajo;
	}
	public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idmaquinaria")
	public MaquinariaUnidad getMaquinaria() {
		return maquinaria;
	}
	public void setMaquinaria(MaquinariaUnidad maquinaria) {
		this.maquinaria = maquinaria;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idimplemento")
	public ImplementoUnidad getImplemento() {
		return implemento;
	}
	public void setImplemento(ImplementoUnidad implemento) {
		this.implemento = implemento;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idoperador")
	public Trabajador getOperador() {
		return operador;
	}
	public void setOperador(Trabajador operador) {
		this.operador = operador;
	}

	@Column(name="bol_operativo")
	public Boolean getOperativa() {
		return operativa;
	}
	public void setOperativa(Boolean operativa) {
		this.operativa = operativa;
	}
	
	@Transient
	public String getOrdenTrabajoStr() {	
		return ordenTrabajo.getNroControl().toUpperCase();
	}
	
	@Transient
	public String getDetalleMaquinaria() {	
		if (maquinaria != null)  
			return maquinaria.toString();
		else
			return null;
	}	
	
	@Transient
	public String getDetalleImplemento() {
		if (implemento != null)  
			return implemento.toString();
		else
			return null;
	}	

}

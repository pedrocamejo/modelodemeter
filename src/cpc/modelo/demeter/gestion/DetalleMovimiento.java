package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import cpc.modelo.sigesp.basico.Activo;

@Audited @Entity
@Table(name="saf_detalle_movimiento", schema = "sigesp")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="int_idmovimiento")
public abstract class DetalleMovimiento implements Serializable{

	private static final long serialVersionUID = -4739230529471863532L;
	private Long 						id;
	private Movimiento	 				movimiento;
	private Activo						activo;
	private String 						observaciones;
	private MotivoTransferenciaActivo 	motivo;
	
	public DetalleMovimiento() {
		super();
	}
	
	public DetalleMovimiento(Movimiento movimiento, Activo activo) {
		super();
		this.movimiento = movimiento;
		this.activo = activo;
	}
	
	@SequenceGenerator(name="SeqDetalleMovimiento", sequenceName = "sigesp.saf_detalle_movimiento_seq_ser_iddetamovi_seq", allocationSize = 1)
	@Id @GeneratedValue(generator= "SeqDetalleMovimiento")
	@Column(name = "seq_ser_iddetamovi")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idmovimiento")
	public Movimiento getMovimiento() {
		return movimiento;
	}
	
	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}
		
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_codact",referencedColumnName="codact"),
		@JoinColumn(name="str_ideact",referencedColumnName="ideact")
	})
	public Activo getActivo() {
		return activo;
	}
	public void setActivo(Activo activo) {
		this.activo = activo;
	}
	
	@Column(name="str_observacion")
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	@OneToOne
	@JoinColumn(name="int_idindiesta")
	public MotivoTransferenciaActivo getMotivo() {
		return motivo;
	}
	public void setMotivo(MotivoTransferenciaActivo motivo) {
		this.motivo = motivo;
	}
	
	@Transient
	public String getNombreActivo(){
		return getActivo().getNombre();
	}
	
	@Transient
	public String getSerialActivo(){
		return getActivo().getSerial();
	}
	
	@Transient
	public String getDescripcionMotivo(){
		return getMotivo().getDescripcion();
	}
	
	public boolean equals(Object objeto){
		try{
			DetalleMovimiento cuenta = (DetalleMovimiento) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}

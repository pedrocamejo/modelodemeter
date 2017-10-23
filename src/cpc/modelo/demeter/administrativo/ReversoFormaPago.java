package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; 

import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Banco;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;

@Audited 
@Entity
@Table(name="tbl_dem_reversoformapago", schema="administracion")
@Inheritance(strategy=InheritanceType.JOINED)
public class ReversoFormaPago implements Serializable  {
	
	
	
	private Integer 			id;
	private ReversoRecibo 		reversoRecibo;
	private	 FormaPago			formaPago;
	private Double 			monto;
	private Boolean         estado = true; 
    /* true activa al anular un recibo por x causa esto se pone en false
    y se puede agregar otra forma de pago con las mismas caracteristicas 
     en el caso de los cheques se debe seleccionar mas no agregar uno nuevo 
	*/
	
	public ReversoFormaPago() {
		super();
	} 
 

	
	
	@SequenceGenerator(name="seqRevForPag", sequenceName="administracion.tbl_dem_reversoformapago_seq_idreversoformapago_seq",  allocationSize=1 )
	@Id 
	@GeneratedValue( generator="seqRevForPag")
	@Column(name="seq_idreversoformapago",columnDefinition="integer")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="int_idformapago")
	public FormaPago getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	
	
	@ManyToOne 
	@JoinColumn(name="int_idreversorecibo")
	public ReversoRecibo getReversoRecibo() {
		return reversoRecibo;
	}
	public void setReversoRecibo(ReversoRecibo reversoRecibo) {
		this.reversoRecibo = reversoRecibo;
	}

	@Column(name="dbl_monto")
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	

	
	
	@Transient
	public String getStrMonto() {
		//DecimalFormat format = new DecimalFormat("###,###,###,###,###.00");
		//return format.format(monto);
		return Formateador.formatearMoneda(Math.abs(monto));
	}
	
	
	
	@Column(name="bol_estado")
	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
    

	
	
	
}
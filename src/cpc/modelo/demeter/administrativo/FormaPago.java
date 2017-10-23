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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.ministerio.gestion.Cliente;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;

@Audited 
@Entity
@Table(name="tbl_dem_formapago", schema="administracion")
@Inheritance(strategy=InheritanceType.JOINED)
public class FormaPago implements Serializable  {
	
	private static final long serialVersionUID = 71886003207151878L;
	
	private Integer 		id;
	private Recibo 			recibo;
	private Date			fecha;
	private	 Date			fechaRecepcion;
	private Double 			monto;
	private boolean	    depositado;
	private	 TipoFormaPago	tipoFormaPago;
    private Boolean        estado = true; 
    /* true activa al anular un recibo por x causa esto se pone en false
    y se puede agregar otra forma de pago con las mismas caracteristicas 
     en el caso de los cheques se debe seleccionar mas no agregar uno nuevo 
	*/
	
	public FormaPago() {
		super();
	} 
 

	public FormaPago(Integer id,Long idRecibo,String identidadLegal, Date fecha, Date fechaRecepcion, 
				Double monto)
	{
		super();
		recibo = new Recibo();
		recibo.setId(idRecibo);
		recibo.setCliente(new Cliente());
		recibo.getCliente().setIdentidadLegal(identidadLegal);
		
		this.id = id;
		this.fecha = fecha;
		this.fechaRecepcion = fechaRecepcion;
		this.monto = monto;
	
	
	}
 

	public FormaPago(double monto, TipoFormaPago tipoPago, Date fecha) {
		super();
		this.monto = monto;
		this.tipoFormaPago = tipoPago;
		this.fecha = fecha;
	}
	
	@SequenceGenerator(name="seqForPag", sequenceName="administracion.tbl_dem_formapago_seq_idformapago_seq",  allocationSize=1 )
	@Id 
	@GeneratedValue( generator="seqForPag")
	@Column(name="seq_idformapago",columnDefinition="integer")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="int_idtipoformapago")
	public TipoFormaPago getTipoFormaPago() {
		return tipoFormaPago;
	}
	public void setTipoFormaPago(TipoFormaPago tipoPago) {
		this.tipoFormaPago = tipoPago;
	}
	
	@Column(name="dbl_monto")
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public java.util.Date getFecha() {
		return fecha;
	}
	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}
	 

	@ManyToOne
	@JoinColumn(name="int_idrecibo")
	public Recibo getRecibo() {
		return recibo;
	}
	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}

	
	@Column(name="bol_verificado")
	public boolean isDepositado() {
		return depositado;
	}
	public void setDepositado(boolean depositado) {
		this.depositado = depositado;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecharecep")
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}  
	
	
	@Transient
	public String getStrMonto() {
		//DecimalFormat format = new DecimalFormat("###,###,###,###,###.00");
		//return format.format(monto);
		
		return Formateador.formatearMoneda(Math.abs(monto));
	}
	
	@Transient
	public String getStrTipoFormaPago() {
		return tipoFormaPago.getDescripcion();
	}
	
	
	 
	
	@Transient
	public String getStrFecha() {
		 return Fecha.obtenerFecha(fecha);
	}
 
	@Transient
	public String getStrRecibo() {
		return recibo.getControl();
	}

	
	@Column
	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
    
	@Transient
	public String getStrBanco()
	{
		return " S/N ";
	}
	
	@Transient
	public String getStrCuenta()
	{
		return " S/N ";
	}
	@Transient
	public String getStrDocumento()
	{
		return "S/N";
	}
	
	
	
}

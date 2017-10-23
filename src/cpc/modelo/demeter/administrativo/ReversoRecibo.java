package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;

@Audited
@Entity
@Table(name="tbl_dem_reversorecibo", schema="administracion")
public class ReversoRecibo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -279624206336231151L;
	private Integer	 	id ;
	private Recibo 		reciboAfectado;
	private String  	observacion;
	private boolean   anulado;
	private Double     montoReversado;
	private Date       fecha;
	private String 	    serie;
	private List<ReversoFormaPago> reversoFormapagos;
	
	
																	   
	@SequenceGenerator(name="SeqReverso", sequenceName="administracion.tbl_dem_reversorecibo_seq_idreverso_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqReverso")
	@Column(name="seq_idreverso")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="int_idrecibo")
	@Fetch(FetchMode.JOIN)
	public Recibo getReciboAfectado() {
		return reciboAfectado;
	}
	public void setReciboAfectado(Recibo reciboAfectado) {
		this.reciboAfectado = reciboAfectado;
	}
	
	@Column(name="str_observacion")
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	@Column(name="bol_anulado")
	public boolean isAnulado() {
		return anulado;
	}
	
	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}
	
	@Column(name ="dbl_monto")
	public Double getMontoReversado() {
		return montoReversado;
	}
	public void setMontoReversado(Double montoReversado) {
		this.montoReversado = montoReversado;
	}
	@Temporal(TemporalType.DATE)
	@Column (name ="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="reversoRecibo", targetEntity=ReversoFormaPago.class,fetch= FetchType.EAGER)
	public List<ReversoFormaPago> getReversoFormaPagos() {
		return reversoFormapagos;
	}
	public void setReversoFormaPagos(List<ReversoFormaPago> reversoFormapagos) {
		this.reversoFormapagos = reversoFormapagos;
	}
	
	@Column (name ="str_serie")
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	@Transient
	public String getStrPagador(){
		if (reciboAfectado.getCliente() != null)
			return reciboAfectado.getCliente().getNombres();
		else
			return "";
	}
	
	@Transient
	public String getStrIdentidadLegalPagador(){
		if (reciboAfectado.getCliente() != null)
			return reciboAfectado.getCliente().getIdentidadLegal();
		else
			return "";
	}
	
	

	@Transient
	public String getStrMontoReversado()
	{
		//DecimalFormat format = new DecimalFormat("####,###,###,###,###.00");
		//return format.format(montoReversado);
		return Formateador.formatearMoneda(Math.abs(montoReversado));
	}
	
	@Transient
	public String getStrEstado()
	{
		return (anulado ? "ANULADO":"ACTIVA");
	}
	@Transient
	public String getStrFecha(){
		return Fecha.obtenerFecha(fecha);
	}
	@Transient
	public String getStrNrocontrol(){
		if (serie == null && id == null)
			return "";
		if(serie == null && id != null)
			return Formateador.rellenarNumero(id,"00000");
		
		if (id==null) id = new Integer(0);
		
			return serie+ Formateador.rellenarNumero(id,"00000");
		
	}
	
	@Transient
	public String getStrReciboAfectado(){
		if (reciboAfectado!=null)
		return reciboAfectado.getControl();
		else return"";
	}

}

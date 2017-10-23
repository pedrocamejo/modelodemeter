package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;


@Audited @Entity
@Table(name="tbl_dem_cierrediarioreversorecibo", schema="administracion")
public class CierreDiarioReversoRecibo implements Serializable{

	private static final long serialVersionUID = -4799101057764678536L;
	private Integer 				id;
	private CierreDiario			cierreDiario;
	private ReversoRecibo			reversoRecibo;
	
	
	@SequenceGenerator(name="SeqCierreReverso", sequenceName="administracion.tbl_dem_cierrediarioreversorecibo_seq_idcierrereverso_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqCierreReverso")
	@Column(name="seq_idcierrereverso")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idcierre")
	public CierreDiario getCierreDiario() {
		return cierreDiario;
	}
	public void setCierreDiario(CierreDiario cierreDiario) {
		this.cierreDiario = cierreDiario;
	}
	@OneToOne
	@JoinColumn(name="int_idreversorecibo")
	public ReversoRecibo getReversoRecibo() {
		return reversoRecibo;
	}
	public void setReversoRecibo(ReversoRecibo reversoRecibo) {
		this.reversoRecibo = reversoRecibo;
	}
	
	
	@Transient
	public String getStrControlReverso(){
		if (reversoRecibo!=null)
		return reversoRecibo.getStrNrocontrol();
		else return"";
	}
	
	@Transient
	public String getStrFechaReverso(){
		if (reversoRecibo!=null)
		return reversoRecibo.getStrFecha();
		else return"";
	}
	
	@Transient
	public String getStrReciboAfectado(){
		if (reversoRecibo!=null)
		return reversoRecibo.getStrReciboAfectado();
		else return"";
	}
	
	@Transient
	public String getStrMontoReversado(){
		if (reversoRecibo!=null)
		return reversoRecibo.getStrMontoReversado();
		else return"";
	}
	
	@Transient
	public String getStrEstadoReverso(){
		if (reversoRecibo!=null)
		return reversoRecibo.getStrEstado();
		else return"";
	}
	
	
}
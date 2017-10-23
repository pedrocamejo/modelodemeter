package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cva.pc.demeter.utilidades.Formateador;

@Entity
@Audited
@Table(name="tbl_dem_reciboNotaCargo", schema="administracion")

public class ReciboNotaCargo implements Serializable{
	
	

	/**
	 * 
	 */
	
	private Integer id;
	private Recibo recibo;
	private NotaCargo notaCargo;
	private Double  monto;
	
	@SequenceGenerator(name="seq_idrecibonotacargo", sequenceName="administracion.tbl_dem_recibonotacargo_seq_idrecibonotacargo_seq", allocationSize=1)
	@Id 
	@GeneratedValue(generator="seq_idrecibonotacargo")
	@Column(name="seq_idrecibonotacargo")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
 
	@ManyToOne
	@JoinColumn(name="int_idrecibo")
	public Recibo getRecibo() {
		return recibo;
	}
	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}
	 
	@ManyToOne
	@JoinColumn(name="int_idnotacargo")
	public NotaCargo getNotaCargo() {
		return notaCargo;
	}
	public void setNotaCargo(NotaCargo notaCargo) {
		this.notaCargo = notaCargo;
	}

	@Column(name="dbl_monto")
	public Double getMonto() {
		return monto;
	}

	
	public void setMonto(Double monto) {
		this.monto = monto;
	}

	@Transient
	public String getStrMonto()
	{   //DecimalFormat format = new DecimalFormat("###,###,###,###,###.00");
		//return format.format(monto);
		return Formateador.formatearMoneda(Math.abs(monto));
		
	} 
}
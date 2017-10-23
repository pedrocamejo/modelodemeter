package cpc.modelo.demeter.administrativo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
 
import cpc.modelo.sigesp.basico.Banco;

@Audited
@Entity
@Table(name="tbl_dem_cheque", schema="administracion")
public class Cheque {
	
	private Integer 	    id;
	private String		    nroCheque; // nro del cheque 
	private String          nroCuenta; //nro de cuenta del cheque 
	private Double          monto; // monto del cheque 

	
	@SequenceGenerator(name="SeqCheque", sequenceName="administracion.tbl_dem_Cheque_seq", allocationSize=1)
	@Id
	@Column(name="seq_idcheque")
	@GeneratedValue(generator="SeqCheque")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(nullable=false)
	public String getNroCheque() {
		return nroCheque;
	}
	public void setNroCheque(String nroCheque) {
		this.nroCheque = nroCheque;
	}
	
	@Column(nullable=false)
	public String getNroCuenta() {
		return nroCuenta;
	}
	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	} 
	   
	@Column(nullable=false)
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	 
	@Transient
	public String getStrMonto()
	{
		return monto.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nroCheque == null) ? 0 : nroCheque.hashCode());
		result = prime * result
				+ ((nroCuenta == null) ? 0 : nroCuenta.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cheque other = (Cheque) obj;
		if (nroCheque == null) {
			if (other.nroCheque != null)
				return false;
		} else if (!nroCheque.equals(other.nroCheque))
			return false;
		if (nroCuenta == null) {
			if (other.nroCuenta != null)
				return false;
		} else if (!nroCuenta.equals(other.nroCuenta))
			return false;
		return true;
	}
	
	
	
	
}

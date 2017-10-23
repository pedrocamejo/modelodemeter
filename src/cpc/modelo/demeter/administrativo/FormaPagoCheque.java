package cpc.modelo.demeter.administrativo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.sigesp.basico.Banco;
import cva.pc.componentes.CompEncabezado;



@Audited
@Entity
@Table(name="tbl_dem_forma_pago_cheque", schema="administracion")


public class FormaPagoCheque  extends FormaPago {

	
	private Cheque cheque;
	

	public FormaPagoCheque(Integer id,Long idRecibo,String identidadLegal, Date fecha, Date fechaRecepcion, Double monto,Integer idcheque,String nroCheque,String nroCuenta,Double chequemonto)
	{
		super(id,idRecibo,identidadLegal,fecha,fechaRecepcion,monto);
		cheque = new Cheque();
		cheque.setId(idcheque);
		cheque.setNroCheque(nroCheque);
		cheque.setNroCuenta(nroCuenta);
		cheque.setMonto(chequemonto);
	}
	
 

	public FormaPagoCheque() {
		super();
		// TODO Auto-generated constructor stub
	}



	public FormaPagoCheque(Integer id, Long idRecibo, String identidadLegal,
			Date fecha, Date fechaRecepcion, Double monto) {
		super(id, idRecibo, identidadLegal, fecha, fechaRecepcion, monto);
		// TODO Auto-generated constructor stub
	}



	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="int_idCheque",columnDefinition="integer")
	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	
	@Transient
	public String getCuenta()
	{
		return this.cheque.getNroCuenta();
	}
	
	@Transient
	public String getDocumento()
	{
		return this.cheque.getNroCheque();
	}

	 @Transient
	public String detalle()
	{
		 String detalle = "";
		 if(cheque == null)  {
			 return "CHEQUE NO ASOCIADO ";
		 }
		 detalle = "NRO ("+cheque.getNroCheque()+ ") Nro Cuenta ("+cheque.getNroCuenta()+")";
		 return detalle;
	 }
	 
	
}

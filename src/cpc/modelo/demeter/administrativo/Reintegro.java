package cpc.modelo.demeter.administrativo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

import cpc.modelo.ministerio.gestion.Cliente;
import cva.pc.demeter.utilidades.Formateador;

@Entity
@Audited
@Table(name="tbl_dem_reintegros",schema="administracion")
public class Reintegro {
	
	
	
	private   Integer 		id;
	private   Cliente 		cliente;
	private   List<Recibo>  recibos = new ArrayList<Recibo>();
	private   NotaCredito	notaCredito;
	private   Double 		montoTotal;
	private   String 		control;
	private   Integer 		tipo; // 1 si es con nota de credito 2 si es solamente con recibos  
	
	
	public static Integer REINTEGRO_CREDITO = 1;
	public static Integer REINTEGRO_RECIBOS = 2;
	
	
	@SequenceGenerator(name="SeqReintegro", sequenceName="administracion.tbl_dem_reintegro_seq", allocationSize=1)
	@Id
	@GeneratedValue(generator="SeqReintegro")
	@Column(name="seq_idreintegro")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="int_idcliente")
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@ManyToMany(targetEntity=Recibo.class,fetch=FetchType.EAGER)
	@AuditJoinTable
	@JoinTable(schema="administracion",name="tbl_dem_recibosReintegro",
	joinColumns= {@JoinColumn(name="int_idreintegro")},
	inverseJoinColumns={@JoinColumn(name="int_idrecibo")})
	public List<Recibo> getRecibos() {
		return recibos;
	}
	public void setRecibos(List<Recibo> recibos) {
		this.recibos = recibos;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="int_notaCredito")
	public NotaCredito getNotaCredito() {
		return notaCredito;
	}
	public void setNotaCredito(NotaCredito notaCredito) {
		this.notaCredito = notaCredito;
	}
	
	@Column
	public Double getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	@Column(name="str_control")
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	
	@Column(name="int_tipo",nullable=false)
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	@Transient
	public String getStrTipo()
	{
		return (tipo == REINTEGRO_RECIBOS ? "Recibos":" Notas Creditos ");
	}
	
	@Transient 
	public String getStrMontoTotal()
	{
	// return new DecimalFormat("###,###,###,###.00").format(montoTotal);
		return Formateador.formatearMoneda(Math.abs(montoTotal));
	}

	
}

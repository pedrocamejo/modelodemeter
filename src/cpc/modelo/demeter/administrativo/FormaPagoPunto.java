package cpc.modelo.demeter.administrativo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.envers.Audited;
import cpc.modelo.sigesp.basico.Banco;

@Audited
@Entity
@Table(name="tbl_dem_Forma_Pago_punto", schema="administracion")
public class FormaPagoPunto extends FormaPago {

	private String 			nrotarjeta;
	private String 			documento;
	private Banco			banco;
	private String	 		tipo; // credito o debito 
	
	public static List<String> TIPO_FORMA_PAGO = Arrays.asList("CREDITO","DEBITO");

	public FormaPagoPunto(Integer id, Long idRecibo,String identidadLegal, Date fecha, Date fechaRecepcion,
			Double monto, String nrotarjeta,Banco banco, String tipo) 
	{
		super(id, idRecibo, identidadLegal, fecha, fechaRecepcion, monto);
		this.setNrotarjeta(nrotarjeta);
		this.banco = banco;
		this.setTipo(tipo);
	}
	

	@Transient
	public Integer tipo_index_item()
	{	
		return TIPO_FORMA_PAGO.indexOf(tipo);
	}
	
	public FormaPagoPunto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="str_idcodemp", referencedColumnName="codemp"),
	    @JoinColumn(name="str_idcodban", referencedColumnName="codban"),
	})
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	 
	 
	@Override
	@Transient
	public String getStrBanco() {
		// TODO Auto-generated method stub
		return banco.getDescripcion();
	}


	@Column(length=35)
	public String getNrotarjeta() {
		return nrotarjeta;
	}


	public void setNrotarjeta(String nrotarjeta) {
		this.nrotarjeta = nrotarjeta;
	}

	@Column(length=10)
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(length=35)
	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}


	@Override
	@Transient
	public String getStrDocumento() {
		// TODO Auto-generated method stub
		return documento;
	}

 
	
	

	
	
	
}


/*
 * 
 * 
 * 
uinique campos banco and nrotarjeta and estado  to do 

CREATE TABLE administracion.tbl_dem_Forma_Pago_punto
(
  seq_idformapago bigint NOT NULL,
  nrotarjeta character varying(35),
  documento character varying(35),
  tipo character varying(10),
  str_idcodemp character varying(255),
  str_idcodban character varying(255),
  CONSTRAINT tbl_dem_Forma_Pago_punto_pkey PRIMARY KEY (seq_idformapago ),
  CONSTRAINT fkformapago FOREIGN KEY (seq_idformapago)
      REFERENCES administracion.tbl_dem_formapago (seq_idformapago),
  CONSTRAINT fkformapagobanco FOREIGN KEY (str_idcodemp, str_idcodban)
      REFERENCES sigesp.scb_banco (codemp, codban)

 )




CREATE TABLE auditoria.tbl_dem_Forma_Pago_punto_aud
(
  seq_idformapago bigint NOT NULL,
  rev integer NOT NULL,
  nrotarjeta character varying(35),
  tipo character varying(10),
  documento character varying(35),
  str_idcodemp character varying(255),
  str_idcodban character varying(255),
  CONSTRAINT tbl_dem_Forma_Pago_punto_aud_pkey PRIMARY KEY (seq_idformapago , rev ),
  CONSTRAINT fk9da26ec86e91cdc5 FOREIGN KEY (seq_idformapago, rev)
      REFERENCES auditoria.tbl_dem_formapago_aud (seq_idformapago, rev) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
 )
 
 Falta el Tipo de forma de pago DAOS Servicios Controlador y Vistas :_D 

tipo de la Forma de Pago 


select setval('administracion.tbl_dem_tipo_forma_pago_seq_idtipoformapago_seq',8);

insert into administracion.tbl_dem_tipo_forma_pago (
	seq_idtipoformapago,str_descripcion,bol_usadocu, bol_usacuen,	bol_usaban, bol_nota,
	bol_deposito, str_cuentacontable)
  values(nextval('administracion.tbl_dem_tipo_forma_pago_seq_idtipoformapago_seq'),
	'PUNTO DE VENTA',true,false,true,false,false, '')




 * 
 * */

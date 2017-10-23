package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="saf_tipo_acta", schema="sigesp")
public class TipoActaAutorizacion implements Serializable{

	private static final long serialVersionUID = 6242232582494189785L;
	private Integer idTipoActa;
	private String 	descripcion;
	private boolean despacho, prestamo, entrada;
	
	public TipoActaAutorizacion() {
		super();
	}
	
	public TipoActaAutorizacion(Integer idTipoActa, String descripcion,
			boolean despacho, boolean prestamo) {
		super();
		this.idTipoActa = idTipoActa;
		this.descripcion = descripcion;
		this.despacho = despacho;
		this.prestamo = prestamo;
	}
	
	
	@SequenceGenerator(name="Seq_idTipoActa", sequenceName="sigesp.saf_tipo_acta_seq_ser_idtipoacta_seq", allocationSize=1)
	@Column(name="seq_ser_idtipoacta")
	@Id @GeneratedValue(generator="Seq_idTipoActa")
	public Integer getIdTipoActa() {
		return idTipoActa;
	}
	public void setIdTipoActa(Integer idTipoActa) {
		this.idTipoActa = idTipoActa;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_despacho")
	public boolean isDespacho() {
		return despacho;
	}
	public void setDespacho(boolean despacho) {
		this.despacho = despacho;
	}
	
	@Column(name="bol_prestamo")
	public boolean isPrestamo() {
		return prestamo;
	}
	public void setPrestamo(boolean prestamo) {
		this.prestamo = prestamo;
	}

	@Column(name="bol_moventrada")
	public boolean isEntrada() {
		return entrada;
	}

	public void setEntrada(boolean entrada) {
		this.entrada = entrada;
	}
	public boolean equals(Object objeto){
		try{
			TipoActaAutorizacion cuenta = (TipoActaAutorizacion) objeto;
			if (cuenta.getIdTipoActa().equals(idTipoActa))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}

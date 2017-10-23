package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.CotizacionVialidad;

@Audited
@Entity
@Table(name = "tbl_dem_archivocontrato", schema = "administracion")
public class ArchivoContrato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4902846817645750710L;
	private Integer id;
	private byte[] archivo;
	private String nombreArchivo;
	private Contrato contrato;
	
	
	@Id
	@Column(name = "seq_idarchivocontrato")
														                  
	@SequenceGenerator(name = "seq_idarchivocontrato", sequenceName = "administracion.tbl_dem_archivocontrato_seq_idarchivocontrato_seq", allocationSize = 1)
	@GeneratedValue(generator = "seq_idarchivocontrato")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "archivo")
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	@Column(name = "str_nombrearchivo")
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	@ManyToOne
	@JoinColumn(name="int_idcontrato")
	@Basic(fetch=FetchType.EAGER)
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
}

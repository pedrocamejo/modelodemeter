package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipodocumentolegal", schema="administracion")
public class TipoDocumentoFiscal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8259740690011176932L;
	private Integer	id;
	private String	descripcion;
	private boolean tipoFactura;
	private boolean haber;
	
	public TipoDocumentoFiscal() {
		super();
	}
	
	public TipoDocumentoFiscal(int id, String descripcion, boolean tipoFactura,
			boolean haber) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.tipoFactura = tipoFactura;
		this.haber = haber;
	}
	
	@SequenceGenerator(name="SeqTipDocFis", sequenceName="administracion.tbl_dem_tipodocumentolegal_seq_idtipfac_seq", allocationSize=1)
	@Id @Column(name="seq_idtipfac") @GeneratedValue(generator="SeqTipDocFis")
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_tipfact")
	public boolean isTipoFactura() {
		return tipoFactura;
	}
	public void setTipoFactura(boolean tipoFactura) {
		this.tipoFactura = tipoFactura;
	}
	
	@Column(name="bol_haber")
	public boolean isHaber() {
		return haber;
	}
	public void setHaber(boolean haber) {
		this.haber = haber;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoDocumentoFiscal cuenta = (TipoDocumentoFiscal) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
}

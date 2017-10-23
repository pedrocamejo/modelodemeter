package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//recibidas, emitidas, contabilizada, anulada

@Audited @Entity
@Table(name="tbl_dem_estadodocumentofiscal", schema="administracion")
public class EstadoDocumentoFiscal implements Serializable{

	/**
	 * 
	**/
	private static final long serialVersionUID = -6013838193020766198L;
	private Integer	id;
	private	String 	descripcion;
	private boolean anulado;
	
	public EstadoDocumentoFiscal() {
		super();
	}
	
	public EstadoDocumentoFiscal(int id, String descripcion, boolean pagado,
			boolean credito) {
		super();
		this.id = id;
		this.descripcion = descripcion;

	}

	public EstadoDocumentoFiscal(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;

	}
	
	@SequenceGenerator(name="seqEstDocFis", sequenceName="", allocationSize=1)
	@Column(name="seq_idestdocfis")
	@Id @GeneratedValue(generator="seqEstDocFis")
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

	
	public String toString(){
		return descripcion;
	}

	@Column(name="bol_anulada")
	public boolean isAnulado() {
		return anulado;
	}

	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}
	
	public boolean equals(Object objeto){
		try{
			EstadoDocumentoFiscal cuenta = (EstadoDocumentoFiscal) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
}

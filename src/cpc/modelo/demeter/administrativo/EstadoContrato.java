package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_estado_contrato", schema="administracion")
public class EstadoContrato implements Serializable{

	public static int ESTADO_POR_FIRMAR = 1;
	public static int ESTADO_ACTIVO = 2;
	public static int ESTADO_EN_EJECUCION = 3;
	public static int ESTADO_CULMINADO = 4;
	public static int ESTADO_ANULADO = 5;
	
	
	private static final long serialVersionUID = -6013838193020766198L;
	private Integer	id;
	private	String 	descripcion;
	
	
	public EstadoContrato() {
		super();
	}
	
	public EstadoContrato(int id, String descripcion, boolean pagado,
			boolean credito) {
		super();
		this.id = id;
		this.descripcion = descripcion;

	}

	public EstadoContrato(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;

	}
	
	@Id
	@Column(name="seq_idestadocontrato")
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
//	@JoinColumn()
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString(){
		return descripcion.toUpperCase();
	}

	public boolean equals(Object objeto){
		try{
			EstadoContrato cuenta = (EstadoContrato) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
		
}

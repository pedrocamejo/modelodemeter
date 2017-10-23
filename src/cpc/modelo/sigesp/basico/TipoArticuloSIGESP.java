package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Audited @Entity
@Table (name="siv_tipoarticulo", schema = "sigesp", uniqueConstraints={@UniqueConstraint(columnNames={"codtipart"})})
public class TipoArticuloSIGESP implements Serializable{

	private static final long serialVersionUID = -1061357187186742780L;
	private String 		codigo;
	private String 		descripcion;
	
	
	public TipoArticuloSIGESP() {
		super();
	}
	
	@Column(name = "codtipart")
	@Id
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}	
		
	@Column(name="dentipart")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean equals(Object objeto) {
		try{
			TipoArticuloSIGESP tipo = (TipoArticuloSIGESP) objeto;
			if (tipo.getCodigo().equals(codigo))
				return true;
			else
				return false;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String toString() {
		return descripcion.toUpperCase();
	}
}

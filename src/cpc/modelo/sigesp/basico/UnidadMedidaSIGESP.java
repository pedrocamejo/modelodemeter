package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Audited @Entity
@Table (name="siv_unidadmedida", schema = "sigesp", uniqueConstraints={@UniqueConstraint(columnNames={"codunimed"})})
public class UnidadMedidaSIGESP implements Serializable{

	private static final long serialVersionUID = -638240194458686052L;
	private String 		codigo;
	private String 		descripcion;
	
	
	public UnidadMedidaSIGESP() {
		super();
	}
	
	@Column(name = "codunimed")
	@Id
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}	
		
	@Column(name="denunimed")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean equals(Object objeto) {
		try{
			UnidadMedidaSIGESP tipo = (UnidadMedidaSIGESP) objeto;
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

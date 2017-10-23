package cpc.modelo.demeter;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "ReporteCodigos")
public class ReporteCodigo   {

	private Long 	 	id;
	private String	 	username;
	private String 		codigo;
	private Date		fecha;
	
	@SequenceGenerator(name = "reportecodigo", sequenceName = "reportecodigo_seq", allocationSize = 1)
	@Id
	@GeneratedValue(generator = "reportecodigo")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable=false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable=false,unique=true)
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(nullable=false)
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}	 
}

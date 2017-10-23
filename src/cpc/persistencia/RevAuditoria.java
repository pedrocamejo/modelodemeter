package cpc.persistencia;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(RevList.class)
public class RevAuditoria extends DefaultRevisionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String ip;
	

	@Column(name="usuario")
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Column(name="ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
}

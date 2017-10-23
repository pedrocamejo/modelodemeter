package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@AuditTable(schema="auditoria",value="tbl_dem_usuario_mantenimiento_aud")
@Entity
@Table(name="tbl_dem_usuario_mantenimiento", schema = "mantenimiento")
public class UsuarioMantenimiento implements Serializable {

		private String cedula;
		private String nombres;
		private String apellidos;
		private EnteExterno ente;
		private String contrasena;
		/**
		 * 
		 */
		public UsuarioMantenimiento() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 * @param cedula
		 * @param nombres
		 * @param apellidos
		 * @param ente
		 * @param contrasena
		 */
		public UsuarioMantenimiento(String cedula, String nombres,
				String apellidos, EnteExterno ente, String contrasena) {
			super();
			this.cedula = cedula;
			this.nombres = nombres;
			this.apellidos = apellidos;
			this.ente = ente;
			this.contrasena = contrasena;
		}
		
		@Id
		@Column(name="cedula")
		@Type(type="java.lang.String")
		public String getCedula() {
			return cedula;
		}
		public void setCedula(String cedula) {
			this.cedula = cedula;
		}
		
		@Column(name="nombres")
		public String getNombres() {
			return nombres;
		}
		public void setNombres(String nombres) {
			this.nombres = nombres;
		}
		
		@Column(name="apellidos")
		public String getApellidos() {
			return apellidos;
		}
		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}
	 
		@OneToOne(cascade=CascadeType.MERGE)
		@JoinColumn(name="enteExterno")
		public EnteExterno getEnte() {
			return ente;
		}
		public void setEnte(EnteExterno ente) {
			this.ente = ente;
		}
		
		@Column(name="contrasena")
		public String getContrasena() {
			return contrasena;
		}
		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
		}
		
		
}

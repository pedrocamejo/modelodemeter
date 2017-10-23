package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.basico.Trabajador;
@Audited
@Entity
@Table(name="tbl_dem_devolucion_articulo",schema="mantenimiento")
@PrimaryKeyJoinColumn(name="int_idmovimientoarticulo")
public class DevolucionArticulo extends MovimientoArticulo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7819444194627425131L;
	private ClienteAdministrativo destinatario;
	private Trabajador trabajador;
	private SalidaExternaArticulo salidaExternaArticulo;
	private SalidaInternaArticulo salidaInternaArticulo;
	
	
	@OneToOne
	@JoinColumn(name="int_idtrabajador")
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	
	
	@OneToOne 
	@JoinColumn (name="int_idcliente")
		
		public ClienteAdministrativo getDestinatario() {
			return destinatario;
		}
		public void setDestinatario(ClienteAdministrativo destinatario) {
			this.destinatario = destinatario;
		}
		

		@OneToOne
		@JoinColumn(name="int_idsalida")
		public SalidaExternaArticulo getSalidaExternaArticulo() {
			return salidaExternaArticulo;
		}
		public void setSalidaExternaArticulo(SalidaExternaArticulo salidaExternaArticulo) {
			this.salidaExternaArticulo = salidaExternaArticulo;
		}
		
		@OneToOne
		@JoinColumn(name="int_idconsumo")
		public SalidaInternaArticulo getSalidaInternaArticulo() {
			return salidaInternaArticulo;
		}
		public void setSalidaInternaArticulo(SalidaInternaArticulo salidaInternaArticulo) {
			this.salidaInternaArticulo = salidaInternaArticulo;
		}
}

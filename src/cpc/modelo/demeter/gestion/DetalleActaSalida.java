package cpc.modelo.demeter.gestion;

import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import cpc.modelo.sigesp.basico.UnidadAdministrativa;

@Audited @Entity
@PrimaryKeyJoinColumn(name="big_iddetacta")
@Table(name="saf_detalle_acta_salida", schema="sigesp")
public class DetalleActaSalida extends DetalleActaAutorizacion {

	private static final long serialVersionUID = 8168304368303842719L;
	private UnidadAdministrativa unidadAdministrativaDestino;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp", insertable = false, updatable = false),
		@JoinColumn(name="str_coduniadm",referencedColumnName="coduniadm", insertable = false, updatable = false)
	})
	public UnidadAdministrativa getUnidadAdministrativaDestino() {
		return unidadAdministrativaDestino;
	}
	public void setUnidadAdministrativaDestino(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativaDestino = unidadAdministrativa;
	}
	
	public boolean equals(Object objeto){
		try{
			DetalleActaSalida cuenta = (DetalleActaSalida) objeto;
			if (cuenta.getIdDetalleActaAutorizacion().equals(getIdDetalleActaAutorizacion()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}

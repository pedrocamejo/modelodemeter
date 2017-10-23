package cpc.persistencia.demeter.implementacion.gestion;

import cpc.modelo.demeter.gestion.MotivoTransferenciaActivo;
import cpc.persistencia.DaoGenerico;

public class PerMotivoTransferenciaActivo extends DaoGenerico<MotivoTransferenciaActivo, Integer>{

	private static final long serialVersionUID = -7919804930923683604L;

	public PerMotivoTransferenciaActivo() {
		super(MotivoTransferenciaActivo.class);
	}

}

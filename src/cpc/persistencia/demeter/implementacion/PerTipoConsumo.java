package cpc.persistencia.demeter.implementacion;






import cpc.modelo.demeter.basico.TipoConsumo;
import cpc.persistencia.DaoGenerico;

public class PerTipoConsumo extends DaoGenerico<TipoConsumo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -903194176174411624L;


	public PerTipoConsumo() {
		super(TipoConsumo.class);
	}
	
}

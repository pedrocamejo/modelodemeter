package cpc.persistencia.ministerio.basico;



import cpc.modelo.ministerio.basico.EstadoCivil;
import cpc.persistencia.DaoGenerico;


public class PerEstadoCivil extends DaoGenerico<EstadoCivil, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerEstadoCivil() {
		super(EstadoCivil.class);
	}
		
}

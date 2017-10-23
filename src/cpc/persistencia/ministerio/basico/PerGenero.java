package cpc.persistencia.ministerio.basico;



import cpc.modelo.ministerio.basico.Genero;
import cpc.persistencia.DaoGenerico;




public class PerGenero extends DaoGenerico<Genero, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerGenero() {
		super(Genero.class);
	}
	
	
}

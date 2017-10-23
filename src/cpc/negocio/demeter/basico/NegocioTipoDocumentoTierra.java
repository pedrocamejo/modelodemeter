package cpc.negocio.demeter.basico;




import cpc.modelo.demeter.basico.TipoDocumentoTierra;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerTipoDocumentoTierra;


public class NegocioTipoDocumentoTierra extends NegocioGenerico<TipoDocumentoTierra, PerTipoDocumentoTierra, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoDocumentoTierra 				negocio;
	
	private NegocioTipoDocumentoTierra(){
		setPersistencia(new PerTipoDocumentoTierra());
	}
	
	public  static synchronized NegocioTipoDocumentoTierra getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoDocumentoTierra();
		return negocio;
	}
	

	
}

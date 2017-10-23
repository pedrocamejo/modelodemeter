package cpc.negocio.ministerio.basico;

import java.util.List;

import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.TipoFinanciamientoCrediticio;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerInstitucionCrediticia;
import cpc.persistencia.ministerio.basico.PerTipoFinanciamientoExt;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioInstitucionFinanciera extends NegocioGenerico<InstitucionCrediticia, PerInstitucionCrediticia, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioInstitucionFinanciera 				negocio;
	
	private NegocioInstitucionFinanciera(){
		setPersistencia(new PerInstitucionCrediticia());
	}
	
	public  static synchronized NegocioInstitucionFinanciera getInstance() {
		if (negocio == null)
			negocio = new NegocioInstitucionFinanciera();
		return negocio;
	}

	
	public List<TipoFinanciamientoCrediticio> getTiposFinanciamientos() throws ExcFiltroExcepcion{
		return new PerTipoFinanciamientoExt().getAll(); 
	}

	
}

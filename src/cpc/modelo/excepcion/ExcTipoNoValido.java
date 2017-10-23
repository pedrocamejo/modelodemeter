package cpc.modelo.excepcion;

public class ExcTipoNoValido extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 97363356582738027L;
	private String mensaje; 
	
	public ExcTipoNoValido(String cadena){
		super();
		mensaje = cadena;
	}
	
	
	public String getMessage() {
		return mensaje;
	}

}

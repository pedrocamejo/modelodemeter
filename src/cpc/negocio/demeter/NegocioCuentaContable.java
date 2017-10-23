package cpc.negocio.demeter;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioCuentaContable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8163057577474731335L;
	private static NegocioCuentaContable 	negocio;
	private PerCuentaContable				persistencia;
	private CuentaContable					cuentaContable;
	
	
	private NegocioCuentaContable(){

		persistencia = new PerCuentaContable(); 
	}

	public  static synchronized NegocioCuentaContable getInstance() {
		if (negocio == null)
			negocio = new NegocioCuentaContable();
		return negocio;
	}
	
	public void guardar(boolean nuevo){
		persistencia.guardar(cuentaContable, cuentaContable.getId(), nuevo);
	}
	
	public void eliminar() throws Exception{
		persistencia.borrar(cuentaContable);
	}
	
	public List<CuentaContable> getTodos() {
		List<CuentaContable> controlSede = null;
		try {
			controlSede = persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return controlSede;
	}

	public PerCuentaContable getPersistencia() {
		return persistencia;
	}

	public void setPersistencia(PerCuentaContable persistencia) {
		this.persistencia = persistencia;
	}

	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	

	
	
	
}

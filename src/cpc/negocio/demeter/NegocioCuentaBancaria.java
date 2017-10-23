package cpc.negocio.demeter;

import java.io.Serializable;
import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.basico.CuentaBancaria;
import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.modelo.sigesp.basico.TipoCuenta;
import cpc.persistencia.demeter.implementacion.PerBanco;
import cpc.persistencia.demeter.implementacion.PerCuentaBancaria;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;
import cpc.persistencia.demeter.implementacion.PerTipoCuentaBancaria;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioCuentaBancaria implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5928521773597079444L;
	private static NegocioCuentaBancaria 	negocio;
	private PerCuentaBancaria				persistencia;
	private CuentaBancaria 					CuentaBanco;
	private PerBanco 						perBanco;
	
	private NegocioCuentaBancaria(){
		perBanco = new PerBanco();
		persistencia = new PerCuentaBancaria(); 
	}

	public  static synchronized NegocioCuentaBancaria getInstance() {
		if (negocio == null)
			negocio = new NegocioCuentaBancaria();
		return negocio;
	}
	
	public void guardar()  throws Exception{
		persistencia.guardar(CuentaBanco, CuentaBanco.getId());
	}
	
	public void eliminar() throws Exception{
		persistencia.borrar(CuentaBanco);
	}
	
	public List<CuentaBancaria> getTodos() {
		List<CuentaBancaria> controlSede = null;
		try {
			controlSede = persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return controlSede;
	}

	public PerCuentaBancaria getPersistencia() {
		return persistencia;
	}

	public void setPersistencia(PerCuentaBancaria persistencia) {
		this.persistencia = persistencia;
	}

	public CuentaBancaria getCuentaBanco() {
		return CuentaBanco;
	}

	public void setCuentaBanco(CuentaBancaria cuentaBanco) {
		CuentaBanco = cuentaBanco;
	}

	public List<Banco> getBancos() throws ExcFiltroExcepcion{
		PerBanco persi  = new PerBanco();
		List<Banco> lista = persi.getAll();
		persi.getEm().evict(persi);// Rhonal No entiendo para que esta esta linea aqui :-D 
		return lista;
	}
	
	public List<CuentaContable> getCuentasContables() throws ExcFiltroExcepcion{
		return new PerCuentaContable().getAll();
	}
	
	public List<TipoCuenta> getTiposCuentas() throws ExcFiltroExcepcion{
		return new PerTipoCuentaBancaria().getAll();
	}
	
	public List<CuentaBancaria>  CuentaBancarias(String idbanco) // cuentas bancarias por codigo de banco 
	{
		Banco banco = perBanco.getBanco(idbanco);
		List<CuentaBancaria> lista = null;
		if(banco != null)
		{
			lista = persistencia.getCuentasBancaria(banco);
		}
		return lista;
	}
	
}

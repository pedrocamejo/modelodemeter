package cpc.negocio.demeter;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.administrativo.CuentasTipoServicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.modelo.sigesp.basico.Sede;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;
import cpc.persistencia.demeter.implementacion.PerCuentasTipoServicio;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioCuentasTipoServicio implements Serializable{
	
	private static final long serialVersionUID = 581215316125392474L;
	private static NegocioCuentasTipoServicio 	negocio;
	private PerCuentasTipoServicio			    persistenciaCuentasTipoServicio;
	private CuentasTipoServicio					objeto;
	
	
	private NegocioCuentasTipoServicio(){
		persistenciaCuentasTipoServicio = new PerCuentasTipoServicio(); 
	}

	public  static synchronized NegocioCuentasTipoServicio getInstance() {
		if (negocio == null)
			negocio = new NegocioCuentasTipoServicio();
		return negocio;
	}
	
	public void guardar() throws Exception{		
		persistenciaCuentasTipoServicio.guardar(objeto, objeto.getId());
	}
	
	public void eliminar() throws Exception{
		persistenciaCuentasTipoServicio.borrar(objeto);
	}
	
	public List<CuentasTipoServicio> getTodos(Sede sede) {
		List<CuentasTipoServicio> cuentasTipoServicios = null;
		cuentasTipoServicios = persistenciaCuentasTipoServicio.getBySede();
		return cuentasTipoServicios;
	}

	public PerCuentasTipoServicio getPersistenciaCuentasTipoServicio() {
		return persistenciaCuentasTipoServicio;
	}

	public void setPersistenciaBanco(PerCuentasTipoServicio persistencia) {
		this.persistenciaCuentasTipoServicio = persistencia;
	}

	public CuentasTipoServicio getCuentasTipoServicio() {
		return objeto;
	}

	public void setCuentasTipoServicio(CuentasTipoServicio tipoServicio) {
		this.objeto = tipoServicio;
	}

	public List<TipoServicio> getTipoServicios() throws ExcFiltroExcepcion{
		return new PerTipoServicio().getAll();
	}
	
	public List<CuentaContable> getCuentasContables() throws ExcFiltroExcepcion{
		return new PerCuentaContable().getAll();
	}
	
	
}

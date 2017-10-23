package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.indice.BancoPK;
import cpc.persistencia.demeter.implementacion.PerBanco;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioBanco implements Serializable{
	
	private static final long serialVersionUID = 444809669431530858L;
	private static NegocioBanco 	negocio;
	private PerBanco				persistenciaBanco;
	private Banco					banco;
	
	
	private NegocioBanco(){

		persistenciaBanco = new PerBanco(); 
	}

	public  static synchronized NegocioBanco getInstance() {
		if (negocio == null)
			negocio = new NegocioBanco();
		return negocio;
	}
	
	public void guardar() throws Exception{
		BancoPK indice = null;
		if (banco.getId() != null)
			indice = new BancoPK(banco.getCodigoEmpresa(), banco.getId());
		persistenciaBanco.guardar(banco, indice);
	}
	
	public void eliminar() throws Exception{
		persistenciaBanco.borrar(banco);
	}
	
	public List<Banco> getTodos() {
		List<Banco> controlSede = null;
		try {
			controlSede = persistenciaBanco.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return controlSede;
	}

	public PerBanco getPersistenciaBanco() {
		return persistenciaBanco;
	}

	public void setPersistenciaBanco(PerBanco persistenciaControlSede) {
		this.persistenciaBanco = persistenciaControlSede;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	
	
	
}

package cpc.negocio.sigesp;

import java.io.Serializable;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.persistencia.demeter.implementacion.PerSede;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSede implements Serializable{
	
	private static final long serialVersionUID = 3522271158206042426L;
	private static NegocioSede 		negocio;
	private PerSede					perSede;
	private List<Sede>			    sedes;
	private Sede				    sede;
	
	private NegocioSede(){
		perSede = new PerSede(); 
	}

	public  static synchronized NegocioSede getInstance() {
		if (negocio == null)
			negocio = new NegocioSede();
		return negocio;
	}

	public List<Sede> getTodos() throws ExcFiltroExcepcion{
		List<Sede> cargos = null;
		try {
			cargos = perSede.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return cargos;
	}

	public static NegocioSede getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioSede negocio) {
		NegocioSede.negocio = negocio;
	}

	public List<Sede> getSedes() {
		return sedes;
	}

	public void setSedes(List<Sede> sedes) {
		this.sedes = sedes;
	}
	public Sede getSede(SedePK indice) {
		return perSede.buscarId(indice);
	}
	
	public Sede getSede(String codigoEmpresa, String id) {
		return perSede.buscarId(new SedePK(codigoEmpresa, id));
	}

	public Sede getSedeDefecto(){
		
		String  IdEmpresa= (String) SpringUtil.getBean("idEmpresa");;
		String  IdSede= (String) SpringUtil.getBean("idsede"); 
		return NegocioSede.getInstance().getSede(IdEmpresa,IdSede); 
	}	
	
	public Sede getSede() {
		return sede;
	}
	
	public void setSede(Sede cargo) {
		this.sede = cargo;
	}
	public void guardar()  throws Exception{
		this.perSede.guardar(sede, sede.getId());
	}
	
	public void eliminar() throws Exception{
		this.perSede.borrar(sede);
	}
}

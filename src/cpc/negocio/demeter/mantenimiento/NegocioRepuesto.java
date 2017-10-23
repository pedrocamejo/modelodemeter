package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import org.hibernate.cache.ReadWriteCache.Item;

import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.mantenimiento.Repuesto;
import cpc.modelo.demeter.mantenimiento.RepuestoEquivalente;
import cpc.modelo.demeter.mantenimiento.RepuestoEquivalentePK;
import cpc.modelo.demeter.mantenimiento.EstadoFuncional;
import cpc.negocio.demeter.administrativo.NegocioFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerRepuesto;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEstadoFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioRepuesto {
	private static NegocioRepuesto 	negocio;
	private PerRepuesto			perrepuesto;
	private List<Repuesto>			    repuestos;
	private Repuesto repuesto       ;
	
	
	
	private NegocioRepuesto(){
		/*SessionDao dao = SessionDao.getInstance();
		dao.test();
		dao.newDaoGenerico(new PerFactura());*/ 
		perrepuesto = new PerRepuesto(); 
	}
	
	public static NegocioRepuesto getInstance() {
		if (negocio == null)
			negocio = new NegocioRepuesto();
		return negocio;
		
	}

	public PerRepuesto getPerRepuesto() {
		return perrepuesto;
	}
	public void setPerRepuesto(PerRepuesto perrepuesto) {
		this.perrepuesto = perrepuesto;
	}

	public List<Repuesto> getTodos() throws ExcFiltroExcepcion {
		
		return new PerRepuesto().getAll();
	}
	public void setRepuestos(List<Repuesto> repuestos) {
		this.repuestos = repuestos;
	}
	public Repuesto getRepuesto() {
		return repuesto;
	}
	public void setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}
  	
	public List<RepuestoEquivalente> getRepuestoEquivalentes(Repuesto repuesto) {
		return new PerRepuesto().getEquivalente(repuesto);
	}
	
	public void guardar(Repuesto repuesto) throws Exception{
	//	boolean a = repuesto.getrepuestoEquivalente().isEmpty();
	//	boolean b = (repuesto.getrepuestoEquivalente().size()==0);
		boolean c = repuesto.getRepuestoEquivalente()==null;
		
		
		if (!c){
			for (RepuestoEquivalente item :repuesto.getRepuestoEquivalente()){
				item.setRepuestoOri(repuesto);
				item.setId(new RepuestoEquivalentePK(item.getRepuestoEq().getId(), item.getRepuestoOri().getId()));
				}
			perrepuesto.guardar(repuesto );
		}else
			perrepuesto.guardar(repuesto );
	}
	
	public List<ArticuloVenta> getArticulosRepuesto() throws ExcFiltroExcepcion {
		return new PerArticuloVenta().getAllArticuloRepuesto();
	}
	
	public List<ArticuloVenta> getArticulosSinRepuesto() throws ExcFiltroExcepcion {
		return new PerArticuloVenta().getAll();
	}
	
	public Repuesto getRepuestoArticulo(ArticuloVenta articulo) throws ExcFiltroExcepcion {
		return perrepuesto.getRepuestoarticulo(articulo);
	}
	
	
}
package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import org.hibernate.cache.ReadWriteCache.Item;

import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.mantenimiento.Consumible;
import cpc.modelo.demeter.mantenimiento.ConsumibleEquivalente;
import cpc.modelo.demeter.mantenimiento.ConsumibleEquivalentePK;
import cpc.modelo.demeter.mantenimiento.EstadoFuncional;
import cpc.negocio.demeter.administrativo.NegocioFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerConsumible;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEstadoFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioConsumible {
	private static NegocioConsumible 	negocio;
	private PerConsumible			perConsumible;
	private List<Consumible>			    consumibles;
	private Consumible consumible       ;
	
	
	
	private NegocioConsumible(){
		/*SessionDao dao = SessionDao.getInstance();
		dao.test();
		dao.newDaoGenerico(new PerFactura());*/ 
		perConsumible = new PerConsumible(); 
	}
	
	public static NegocioConsumible getInstance() {
		if (negocio == null)
			negocio = new NegocioConsumible();
		return negocio;
		
	}

	public PerConsumible getPerConsumible() {
		return perConsumible;
	}
	public void setPerConsumible(PerConsumible perConsumible) {
		this.perConsumible = perConsumible;
	}

	public List<Consumible> getTodos() throws ExcFiltroExcepcion {
		
		return new PerConsumible().getAll();
	}
	public void setConsumibles(List<Consumible> consumibles) {
		this.consumibles = consumibles;
	}
	public Consumible getConsumible() {
		return consumible;
	}
	public void setConsumible(Consumible consumible) {
		this.consumible = consumible;
	}
  	
	public List<ConsumibleEquivalente> getConsumibleEquivalentes(Consumible consumible) {
		return new PerConsumible().getEquivalente(consumible);
	}
	
	public void guardar(Consumible consumible) throws Exception{
	//	boolean a = consumible.getConsumibleEquivalente().isEmpty();
	//	boolean b = (consumible.getConsumibleEquivalente().size()==0);
		boolean c = consumible.getConsumibleEquivalente()==null;
		
		
		if (!c){
			for (ConsumibleEquivalente item :consumible.getConsumibleEquivalente()){
				item.setConsumibleOri(consumible);
				item.setId(new ConsumibleEquivalentePK(item.getConsumibleEq().getId(), item.getConsumibleOri().getId()));
				}
			perConsumible.guardar(consumible );
		}else
			perConsumible.guardar(consumible );
	}
	
	public List<ArticuloVenta> getArticulosConsumible() throws ExcFiltroExcepcion {
		return new PerArticuloVenta().getAllArticuloConsumible();
	}
	
	public List<ArticuloVenta> getArticulosSinConsumible() throws ExcFiltroExcepcion {
		return new PerArticuloVenta().getAll();
	}
	
	public Consumible getConsumibleArticulo(ArticuloVenta articulo) throws ExcFiltroExcepcion {
		return perConsumible.getConsumiblearticulo(articulo);
	}
	
	
}

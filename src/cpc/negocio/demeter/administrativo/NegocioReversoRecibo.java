package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.ReversoRecibo;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerRecibo;
import cpc.persistencia.demeter.implementacion.administrativo.PerReversoRecibo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioReversoRecibo implements Serializable{
	
	public void guardar(ReversoRecibo reversoRecibo) throws Exception{
		reversoRecibo.setSerie(getControlSede().getSerie());
		new PerReversoRecibo().guardar(reversoRecibo);
		
	}
	
	public ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	public void anular(ReversoRecibo reversoRecibo){
		new PerReversoRecibo().Anular(reversoRecibo);
	}
	
	public List<ReversoRecibo> getTodos() throws ExcFiltroExcepcion{
		return new PerReversoRecibo().getcritaeriaAll();
	}

	public List<Recibo> getRecibos() throws ExcFiltroExcepcion{
		return new PerRecibo().getAll();
	}
	
	public List<Recibo> getRecibosActivos() throws ExcFiltroExcepcion{
		return new PerRecibo().getAllActivosSaldoCompleto();
	}
}

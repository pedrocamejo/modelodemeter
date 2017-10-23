package cpc.test;


import cpc.modelo.demeter.administrativo.CuentasTipoServicio;
import cpc.modelo.sigesp.basico.Sede;
import cpc.negocio.demeter.NegocioCuentasTipoServicio;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class pruebaCuentasTipoServicio {

	
	public static void main(String[] args) throws ExcFiltroExcepcion {
		NegocioCuentasTipoServicio negocio = NegocioCuentasTipoServicio.getInstance();
		try{
			for (CuentasTipoServicio cuenta : negocio.getTodos(new Sede("0001","0001","",null,false))) {
				System.out.println(cuenta.getSede().getId().getCodigoEmpresa()+ " - "+ cuenta.getSede().getId().getId() +" - " +cuenta.getSede().getNombre()+" - "+cuenta.getTipoServicio().getNombre()+" - "+cuenta.getCuentaContableIngresosSede());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*CuentasTipoServicio cta = new CuentasTipoServicio();
		cta.setTipoServicio(new TipoServicio(36,"","",true));
		cta.setCuentaContableIngresosSede("514030101010002");
		cta.setCuentaPresupuestariaIngresosSede("303020102");
		cta.setSede(new Sede("0001","0001","",null,false));
		negocio.setCuentasTipoServicio(cta);
		negocio.guardar();*/
		
	}


	
}

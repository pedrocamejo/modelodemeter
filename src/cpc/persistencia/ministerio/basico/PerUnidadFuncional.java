package cpc.persistencia.ministerio.basico;


import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;




public class PerUnidadFuncional extends DaoGenerico<UnidadFuncional, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerUnidadFuncional() {
		super(UnidadFuncional.class);
	}
	
	public void guardar(UnidadFuncional unidad, Integer id, String serie) throws Exception{
		if (id == null){
			nuevo(unidad, serie);
		}else
			super.guardar(unidad, id);
	}

	
	private void nuevo(UnidadFuncional unidad, String serie) throws ExcFiltroExcepcion{
		Transaction tx = null;
		Session em ;
		try{
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
		    ControlUnidadFuncional control = new ControlUnidadFuncional();
		    control.setUnidad(unidad);
		    control.setControlSeguimiento(1);
		    control.setControlSolicitud(1);
		    control.setControlSolicitudServicioTEcnico(1);
		    control.setSerie(serie);
		    control.setSerieSolicitudServicioTecnico(serie+"-ST");
			em.save(unidad);
			em.save(control);
		    tx.commit(); 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando unidad, "+ e.getMessage());
		}
	}
	
	
}

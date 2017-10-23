package cpc.test;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

public class Prueba {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		 SessionFactory sf = new Configuration().configure().buildSessionFactory();
		 Session session = sf.openSession();
		 Transaction t =  session.beginTransaction();
		 
		 AuditReader reader = AuditReaderFactory.get(session);
		
		 System.out.println(reader.getRevisionDate(21814));
		 System.out.println(reader.getRevisionDate(21815));
		 System.out.println(reader.getRevisionDate(21816));
		 System.out.println(reader.getRevisionDate(21817));
		 System.out.println(reader.getRevisionDate(21818));
		 System.out.println(reader.getRevisionDate(21819));
		 System.out.println(reader.getRevisionDate(21820));
		 System.out.println(reader.getRevisionDate(21821));
		 System.out.println(reader.getRevisionDate(21822));
		 System.out.println(reader.getRevisionDate(21823));
		 System.out.println(reader.getRevisionDate(21824));
		 System.out.println(reader.getRevisionDate(21825));
		 System.out.println(reader.getRevisionDate(21841));
		 System.out.println(reader.getRevisionDate(21842));
		 System.out.println(reader.getRevisionDate(21843));

		 //reader.createQuery().forEntitiesAtRevision(DocumentoFiscal.class,21815).
		 
		
		  
		 t.commit();
		  
		  
			/*	 ProjectionList  projections2 = Projections.projectionList() ;
				 projections2.add(  projections.add(Projections.groupProperty("contratos.nroControl")));
				 projections2.add(  projections.add(Projections.groupProperty("contratos.serie")));
				 
				 ProjectionList  projections3 = Projections.projectionList() ;
				 
				 projections3.add(projections.add(Projections.groupProperty("beneficiarios.nombres")));
					*/	 

			/*	 
				 projections.add(Projections.property("id"));
				 projections.add(Projections.property("serie"));
				 projections.add(Projections.property("nroControl"));
				 projections.add(Projections.property("nroDocumento"));
				 projections.add(Projections.property("fecha"));
				 projections.add(Projections.property("credito"));
				 projections.add(Projections.property("cancelada"));
				 projections.add(Projections.property("estado"));
				// projections.add(Projections.property("beneficiario"));
				 projections.add(Projections.property("direccionFiscal"));
			//	 projections.add(Projections.property("recibos"));
				// projections.add(Projections.property("detalles"));
				 projections.add(Projections.property("montoBase"));
				 projections.add(Projectio
		
		
	/*	MaquinariaUnidad unidad = new MaquinariaUnidad();
		PerMaquinariaUnidad pe = new PerMaquinariaUnidad();
		PerUnidadFuncional per2 = new PerUnidadFuncional();

		UnidadFuncional u  = per2.getAll().get(0);
		
		unidad.setOperativo(false);
		unidad.setUnidad(u);
		
		TransactionSynchronizationManager.bindResource("obj", new AuditorUsuario());
		pe.guardar(unidad,null);
		
		
		/*
		NegocioMaquinariaImpropia servicio = NegocioMaquinariaImpropia.getInstance();
		MaquinariaImpropia maquinaria = new MaquinariaImpropia();
 
		List<Marca> marcas = servicio.marcas();
		Marca marca = (Marca) marcas.get(0);
		List<Modelo> modelos = servicio.modelos(marca);
		Modelo modelo = modelos.get(0);
		

		List<Categoria> categorias = servicio.categorias();
		Categoria categoria = (Categoria) categorias.get(0);
		List<Tipo> tipos = servicio.tipos(categoria);
		Tipo tipo = tipos.get(0);

		
		maquinaria.setSerialChasis("123456");
		maquinaria.setSerialOtro("546978");
		maquinaria.setProcedencia("1111");
		maquinaria.setNombre("Holaaa");
		maquinaria.setMarca(marca);
		maquinaria.setModelo(modelo);
		maquinaria.setCategoria(categoria);
		maquinaria.setTipo(tipo);
 
		
		
		try {
			
			TransactionSynchronizationManager.bindResource("obj", new AuditorUsuario());

			
			servicio.guardar(maquinaria);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
 
	}

}

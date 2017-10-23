package cpc.negocio.ministerio.basico;

import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.basico.CodigoArea;
import cpc.modelo.demeter.basico.SectorAgricola;
import cpc.modelo.demeter.vistas.ProductorView;
import cpc.modelo.excepcion.ExcTipoNoValido;
import cpc.modelo.ministerio.basico.EstadoCivil;
import cpc.modelo.ministerio.basico.Genero;
import cpc.modelo.ministerio.basico.GradoInstruccion;
import cpc.modelo.ministerio.basico.Nacionalidad;
import cpc.modelo.ministerio.basico.TipoProductor;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.Organizacion;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.ProductorJuridico;
import cpc.modelo.ministerio.gestion.ProductorNatural;
import cpc.modelo.ministerio.gestion.Representante;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerCodigoArea;
import cpc.persistencia.ministerio.basico.PerEstadoCivil;
import cpc.persistencia.ministerio.basico.PerGenero;
import cpc.persistencia.ministerio.basico.PerGradoInstruccion;
import cpc.persistencia.ministerio.basico.PerInstitucionCrediticia;
import cpc.persistencia.ministerio.basico.PerNacionalidad;
import cpc.persistencia.ministerio.basico.PerOrganizacion;
import cpc.persistencia.ministerio.basico.PerProductor;
import cpc.persistencia.ministerio.basico.PerProductorJuridico;
import cpc.persistencia.ministerio.basico.PerProductorNatural;
import cpc.persistencia.ministerio.basico.PerRepresentante;
import cpc.persistencia.ministerio.basico.PerSectorAgricola;
import cpc.persistencia.ministerio.basico.PerTipoProductor;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cpc.persistencia.ministerio.basico.PerUnidadProductiva;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioProductor extends NegocioGenerico<Productor, PerProductor, Integer>{

	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioProductor 				negocio;
	private PerProductor		persistenciaproductor;
	private ProductorJuridico	productorJuridico;
	private ProductorNatural	productorNatural;
	private List<Productor>		productores;
	private boolean				tipoJuridico;
	
	private NegocioProductor(){
		setPersistencia(new PerProductor());
	}
	
	public  static synchronized NegocioProductor getInstance() {
		if (negocio == null)
			negocio = new NegocioProductor();
		return negocio;
	}
	
	
	public Productor nuevo() throws ExcFiltroExcepcion{
		try{
			TipoProductor tipoProductor = getTiposProductores().get(0);
			if (tipoProductor.isJuridico()){
				productorJuridico = new ProductorJuridico();
				productorJuridico.setTipo(tipoProductor);
				setTipoJuridico(true);
				setModelo(productorJuridico);
				productorJuridico.setTipo(tipoProductor);
				return productorJuridico;
			}else{
				productorNatural = new ProductorNatural();
				productorNatural.setTipo(tipoProductor);
				setTipoJuridico(false);
				setModelo(productorNatural);
				productorNatural.setTipo(tipoProductor);
				return productorNatural;
			}
		}catch (NullPointerException e) {
			e.printStackTrace();
			throw new ExcFiltroExcepcion("No existen tipos de productores validos");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void guardar(Integer dato) {
		//getModelo().setUnidadAsociado(getUnidad());
		if (getModelo() instanceof ProductorJuridico) {
			new PerProductorJuridico().guardar(productorJuridico, dato);
		}else
			new PerProductorNatural().guardar(productorNatural, dato);
		
	}
	
	public void borrar() throws Exception {	
		
			if (getModelo() instanceof ProductorJuridico) {
				new PerProductorJuridico().borrar(productorJuridico);
			}else
				new PerProductorNatural().borrar(productorNatural);
		
	}
	
	public void setModelo(Productor modelo) {
		if (modelo != null){
			tipoJuridico = modelo.getTipo().isJuridico();
			super.setModelo(modelo);
			System.out.printf("datos superclase id = %d nombres=%s\n",super.getModelo().getId(),super.getModelo().getNombres());
			if (modelo instanceof ProductorJuridico) {
				productorJuridico = (ProductorJuridico) modelo;
			}else if (modelo instanceof ProductorNatural) {
				productorNatural = (ProductorNatural) modelo;
				System.out.printf("datos del natural id = %d 1 nombre=%s %s\n",modelo.getId(),productorNatural.getPrimerNombre(),((ProductorNatural)getModelo()).getPrimerNombre());
			}else{
				if (tipoJuridico){
					this.productorJuridico = new ProductorJuridico(super.getModelo());
					super.setModelo(productorJuridico);
				}else{
					this.productorNatural = new ProductorNatural(super.getModelo());
					super.setModelo(productorNatural);
				}
			}
			System.out.printf("datos de super y sub clase id = %d nombres=%s\n",super.getModelo().getId(),super.getModelo().getNombres());
		}
	}
	
	public ProductorNatural getModeloNatural() throws ExcTipoNoValido {
		if (tipoJuridico)
			throw new ExcTipoNoValido("El tipo no es Natural");
		return this.productorNatural;
	}
	
	public ProductorJuridico getModeloJuridico()  throws ExcTipoNoValido {
		if (!tipoJuridico)
			throw new ExcTipoNoValido("El tipo no es Juridico");
		return this.productorJuridico;
	}
	
	public void enriqueserDatos() throws NullPointerException{
		if (tipoJuridico){
			setModelo(new PerProductorJuridico().getDatos(productorJuridico));
		}else{
			ProductorNatural docu = new PerProductorNatural().getDatos(productorNatural);
			setModelo(docu);
		}
		
	}
	
	public List<UnidadProductiva> getUnidadProductivas() throws ExcFiltroExcepcion{
		return new PerUnidadProductiva().getAll(); 
	}
	
	public List<InstitucionCrediticia> getFinanciamientos() throws ExcFiltroExcepcion{
		return new PerInstitucionCrediticia().getAll(); 
	}

	public List<CodigoArea> getCodigosArea() throws ExcFiltroExcepcion{
		return new PerCodigoArea().getAll(); 
	}
	
	public List<Organizacion> getOrganizaciones() throws ExcFiltroExcepcion{
		return new PerOrganizacion().getAll(); 
	}
	
	public List<GradoInstruccion> getGradoInstrucciones() throws ExcFiltroExcepcion{
		return new PerGradoInstruccion().getAll(); 
	}
	
	public List<Genero> getGeneros() throws ExcFiltroExcepcion{
		return new PerGenero().getAll(); 
	}
	
	public List<EstadoCivil> getEstadosCiviles() throws ExcFiltroExcepcion{
		return new PerEstadoCivil().getAll(); 
	}
	
	public List<Nacionalidad> getNacionalidades() throws ExcFiltroExcepcion{
		return new PerNacionalidad().getAll(); 
	}
	
	public List<SectorAgricola> getSectoresAgricolas() throws ExcFiltroExcepcion{
		return new PerSectorAgricola().getAll(); 
	}
	
	public List<TipoProductor> getTiposProductores() throws ExcFiltroExcepcion{
		return new PerTipoProductor().getAll(); 
	}

	public List<Representante> getRepresentantes() throws ExcFiltroExcepcion{
		return new PerRepresentante().getAll(); 
	}
	
	public boolean isTipoJuridico() {
		return tipoJuridico;
	}

	public void setTipoJuridico(boolean tipo) {
		this.tipoJuridico = tipo;
	}

	public ProductorJuridico getProductorJuridico() {
		return productorJuridico;
	}

	public ProductorNatural getProductorNatural() {
		return productorNatural;
	}
	
	public UnidadFuncional getUnidad(){
		Integer  IdUnidad= (Integer) SpringUtil.getBean("idUnidadFuncional");
		return new PerUnidadFuncional().buscarId(IdUnidad);
	}
	
	public List<Productor> getTodosProductores() throws ExcFiltroExcepcion {
		return new PerProductor().getAllproductores();   
	}
	public List<ProductorView> getProductoresView() throws ExcFiltroExcepcion {
		return new PerProductor().getProductoresView();   
	}
 
}
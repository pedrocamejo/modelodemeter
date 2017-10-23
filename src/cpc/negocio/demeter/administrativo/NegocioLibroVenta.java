package cpc.negocio.demeter.administrativo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.Transient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.joda.time.DateTime;
 
import com.sun.media.sound.EmergencySoundbank;
import com.sun.org.apache.bcel.internal.generic.DCONST;
import com.sun.org.apache.regexp.internal.recompile;
import com.sun.org.apache.xml.internal.utils.StringBufferPool;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.administrativo.LibroVenta;
import cpc.modelo.demeter.administrativo.LibroVentaDetalle;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerDetalleLibroVenta;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerLibroVenta;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcEntradaInvalida;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;
import cva.pc.demeter.utilidades.Real;

public class NegocioLibroVenta implements Serializable {

	private static final long serialVersionUID = -5115216857139327297L;
	private static NegocioLibroVenta negocio;
	private PerLibroVenta persistencia;
	private PerControlSede perControlSede;
	private LibroVenta libro;

	private NegocioLibroVenta() {

		persistencia = new PerLibroVenta();
		perControlSede = new PerControlSede();
	}

	public static synchronized NegocioLibroVenta getInstance() {
		if (negocio == null)
			negocio = new NegocioLibroVenta();
		return negocio;
	}

	public void guardar() throws Exception {
		persistencia.guardar(libro, libro.getId());
	}

	public void eliminar() throws Exception {
		persistencia.borrar(libro);
	}

	public List<LibroVenta> getTodos() {
		List<LibroVenta> controlSede = null;
		try {
			controlSede = persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return controlSede;
	}

	public PerLibroVenta getPersistencia() {
		return persistencia;
	}

	public void setPersistencia(PerLibroVenta persistencia) {
		this.persistencia = persistencia;
	}

	public LibroVenta getLibro() {
		return libro;
	}

	public LibroVenta getLibro(Integer mes, Integer ano) {
		return persistencia.getLibroActivo(ano, mes);
	}

	public void setLibro(LibroVenta libro) {
		if (libro != null)
			if (libro.getId() != null)
				this.libro = persistencia.getLibro(libro);
			else
				this.libro = libro;
		else
			this.libro = new LibroVenta();
	}

	public LibroVenta nuevoLibro(int ano, int mes) throws ExcEntradaInvalida,
			ExcEntradaInconsistente {
		setLibro(persistencia.nuevoLibro(mes, ano));
		return getLibro();
	}

	public List<LibroVentaDetalle> getDetalleLibroVenta(LibroVenta libro) {
		return new PerDetalleLibroVenta().getDetalleLibro(libro);
	}

	public Element getLibroXml(Integer mes, Integer ano)
			throws ExcEntradaInconsistente {
		return persistencia.getLibroXml(mes, ano);
	}

	public void setDeclarado(Integer mes, Integer ano) throws Exception {
		persistencia.declararLibro(getLibro(mes, ano));
	}

	public List<Object> getLibrodeventasVsFacturado(Date inicio, Date fin) {
		List<Object> reporte = new ArrayList<Object>();

		// sacamos el resultado del intervalo continuo
		Double globalServicios = obtenerTotalServicios(inicio, fin);
		Double globalProductos = obtenerTotalProductos(inicio, fin);

		// comparamos que las fechas esten en el mismo año
		int añoinicio = obtenerAnio(inicio);
		int añofin = obtenerAnio(fin);
		// if (añoinicio==añofin){
		int mesinicio = inicio.getMonth();
		int mesfin = fin.getMonth();
		int mesMax = obtenerMEsesDiferencia(inicio, fin) + mesinicio;

		while (mesinicio <= mesMax) {
			Date inicioperido = new GregorianCalendar(añoinicio, mesinicio, 1).getTime();
			int mesperiodo = inicioperido.getMonth();
			int añoperido = obtenerAnio(inicioperido);

			int ultimodia = obtenerUltimoDiaMes(añoinicio, mesinicio);
			Date finperido = new GregorianCalendar(añoinicio, mesinicio,ultimodia).getTime();

			LibroVenta libro = getLibro(mesperiodo + 1, añoperido);
			Double totalProductos = obtenerTotalProductos(inicioperido,finperido);
			Double totalServicios = obtenerTotalServicios(inicioperido,finperido);

			Object[] mes = new Object[11];
			mes[0] = libro;
			mes[1] = totalProductos;
			mes[2] = totalServicios;
			mes[3] = mesperiodo;
			mes[4] = añoperido;
			mes[5] = globalProductos;
			mes[6] = globalServicios;
			mes[7] = obtenerTotalProductos(inicio, finperido);
			mes[8] = obtenerTotalServicios(inicio, finperido);
			mes[9] = obtenerTotalProductosCredito(inicioperido, finperido);
			mes[10] = obtenerTotalServiciosCredito(inicioperido, finperido);
			reporte.add(mes);
			mesinicio++;

		}
		return reporte;
	}

	public int obtenerUltimoDiaMes(int anio, int mes) {

		Calendar calendario = Calendar.getInstance();
		calendario.set(anio, mes, 1);
		return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	public int obtenerAnio(Date date) {
		if (date != null) {
			String formato = "yyyy";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			return Integer.parseInt(dateFormat.format(date));
		}
		return 0;
	}

	public int obtenerMEsesDiferencia(Date inico, Date fin) {
		int año1 = obtenerAnio(inico);
		int año2 = obtenerAnio(fin);
		@SuppressWarnings("deprecation")
		int mes1 = inico.getMonth();
		@SuppressWarnings("deprecation")
		int mes2 = fin.getMonth();
		int diferenciameses = mes1 - mes2;
		int direfenciaaños = año1 - año2;

		return Math.abs((diferenciameses) + (direfenciaaños * 12));

	}

	public Double obtenerTotalServicios(Date inicio, Date fin) {
		Double totalServicios = new Double(0);
		List<Object> serviciosGlobal = NegocioFactura.getInstance()
				.getServicosFacturados2(inicio, fin);
		for (Object object : serviciosGlobal) {
			Object[] sumas = (Object[]) object;
			Double cantidad = (Double) sumas[0];
			Double precioUnitario = (Double) sumas[3];
			Impuesto alicuota = (Impuesto) sumas[2];

			Double neto = cantidad
					* precioUnitario
					* (new Double(1.00) + (alicuota.getPorcentaje() / new Double(
							100.00)));
			neto = Real.redondeoMoneda(neto);
			totalServicios = Real.redondeoMoneda(neto + totalServicios);
		}
		return totalServicios;
	}

	public Double obtenerTotalProductos(Date inicio, Date fin) {
		Double totalProductos = new Double(0);
		List<Object> productosGlobal = NegocioFactura.getInstance()
				.getProductosFacturados2(inicio, fin);
		for (Object object : productosGlobal) {
			Object[] sumap = (Object[]) object;
			Double cantidad = (Double) sumap[0];
			Double precioUnitario = (Double) sumap[3];
			Impuesto alicuota = (Impuesto) sumap[2];

			Double neto = (cantidad * precioUnitario * (new Double(1.00) + (alicuota
					.getPorcentaje() / new Double(100.00))));
			neto = Real.redondeoMoneda(neto);
			totalProductos = Real.redondeoMoneda(neto + totalProductos);
		}
		return totalProductos;
	}
	
	public Double obtenerTotalProductosCredito(Date inicio, Date fin) {
		Double totalProductos = new Double(0);
		List<Object> productosGlobal = NegocioFactura.getInstance()
				.getProductosSinDebito(inicio, fin);
		for (Object object : productosGlobal) {
			Object[] sumap = (Object[]) object;
			Double cantidad = (Double) sumap[0];
			Double precioUnitario = (Double) sumap[3];
			Impuesto alicuota = (Impuesto) sumap[2];

			Double neto = (cantidad * precioUnitario * (new Double(1.00) + (alicuota
					.getPorcentaje() / new Double(100.00))));
			neto = Real.redondeoMoneda(neto);
			totalProductos = Real.redondeoMoneda(neto + totalProductos);
		}
		return totalProductos;
	}
	public Double obtenerTotalServiciosCredito(Date inicio, Date fin) {
		Double totalServicios = new Double(0);
		List<Object> serviciosGlobal = NegocioFactura.getInstance()
				.getServiciosSinDebito(inicio, fin);
		for (Object object : serviciosGlobal) {
			Object[] sumas = (Object[]) object;
			Double cantidad = (Double) sumas[0];
			Double precioUnitario = (Double) sumas[3];
			Impuesto alicuota = (Impuesto) sumas[2];

			Double neto = cantidad
					* precioUnitario
					* (new Double(1.00) + (alicuota.getPorcentaje() / new Double(
							100.00)));
			neto = Real.redondeoMoneda(neto);
			totalServicios = Real.redondeoMoneda(neto + totalServicios);
		}
		return totalServicios;
	}
	
	public String XmlSigespLibro(Integer id ) throws Exception{
		LibroVenta libro = persistencia.getLibro(id);
		List<Integer> ids = new ArrayList<Integer>();
		List<Map> documentos = persistencia.getLibroDetalle(id);
		for (Map mapa:  documentos) { ids.add((Integer) mapa.get("seq_iddocumento")); }
		List<Map> detalleDocumento = persistencia.getDetalleDocumentoFiscal(ids);
		List<Map> detalleImpuestos = persistencia.getDetalleImpuesto(ids);
		List<Map> recibosAsociados = persistencia.getRecibosAsociados(ids);
		ControlSede sede = perControlSede.getControlSede(); 

		Element root = new Element("sede");
		Document doc = new Document(root);
		root.setAttribute("id",libro.getId().toString());
		root.setAttribute("ano",Integer.toString(libro.getAno()));
		root.setAttribute("mes",Integer.toString(libro.getMes()));
		root.setAttribute("montoBase",libro.getMontoBase().toString());
		root.setAttribute("montoTotal",libro.getMontoTotal().toString());
		root.setAttribute("cantidadDocumentos",Integer.toString(libro.getCantidadDocumentos()));
		root.setAttribute("declarado",libro.isDeclarado()? "DECLARADO": "NO-DECLARADO");
		root.setAttribute("sede",sede.getSede().getNombre());
		root.setAttribute("sede-codigo",sede.getSede().getId().getId());

		Element xmlDocumentos = new Element("documentos");
 		for(Map map : documentos){
			Element xmlDocumento = new Element("documento");
			String iddocumento =  map.get("seq_iddocumento").toString();
			String documento = map.get("str_serie").toString() +  Formateador.rellenarNumero(Integer.parseInt(map.get("int_nrocontrol").toString()),"00000");
			xmlDocumento.setAttribute("documento",documento);
			xmlDocumento.setAttribute("tipo-documento",map.get("int_idtipodocumento").toString());
			xmlDocumento.setAttribute("serie",map.get("str_serie").toString());
			xmlDocumento.setAttribute("control",map.get("int_nrocontrol").toString());
			xmlDocumento.setAttribute("fecha",map.get("dtm_fecha").toString());
			xmlDocumento.setAttribute("montoBase",map.get("dbl_montobase").toString());
			xmlDocumento.setAttribute("montoTotal",map.get("dbl_montototal").toString());
			xmlDocumento.setAttribute("rif",map.get("str_cedurif").toString());
			xmlDocumento.setAttribute("nombre",map.get("str_nombre").toString());
			
			Element xmlDetalles = new Element("detalles");

			for(Map mapDetalle : detalleDocumento){
				String idDetalleDocumento = mapDetalle.get("int_iddocumento").toString();
				if(idDetalleDocumento.equals(iddocumento)){
					Element xmlDetalle = new Element("detalle");
					xmlDetalle.setAttribute("id",mapDetalle.get("seq_iddetalle").toString());
					xmlDetalle.setAttribute("servicio",mapDetalle.get("str_descripcion").toString());
					xmlDetalle.setAttribute("precio",mapDetalle.get("precio").toString());
					xmlDetalle.setAttribute("cuenta",mapDetalle.get("cuenta").toString());

					xmlDetalles.addContent(xmlDetalle);
				}
			}

			Element xmlRecibos = new Element("recibos");

			for(Map maprecibo : recibosAsociados){
				String idreciboDocumento = maprecibo.get("int_iddocumentofiscal").toString();
 				if(idreciboDocumento.equals(iddocumento)){
					Element xmlrecibo = new Element("recibo");
					xmlrecibo.setAttribute("monto",maprecibo.get("monto").toString());
					xmlrecibo.setAttribute("fecha",maprecibo.get("dtm_fecha").toString());
					xmlrecibo.setAttribute("monto",maprecibo.get("dbl_monto").toString());
					xmlrecibo.setAttribute("nrocontrol",maprecibo.get("str_control").toString());
					xmlRecibos.addContent(xmlrecibo);
				}
			}

			Element xmlimpuestos = new Element("impuestos");

			for(Map impuesto : detalleImpuestos){
				String impuesto_id = impuesto.get("id").toString();
 				if(impuesto_id.equals(iddocumento)){
					Element xmlImpuesto = new Element("impuesto");
					xmlImpuesto.setAttribute("porcentaje",impuesto.get("porcentaje").toString());
					xmlImpuesto.setAttribute("base",impuesto.get("base").toString());
					xmlImpuesto.setAttribute("monto",impuesto.get("monto").toString());
					xmlImpuesto.setAttribute("descripcion",impuesto.get("descripcion").toString());
					xmlimpuestos.addContent(xmlImpuesto);
				}
			}
			
			xmlDocumento.addContent(xmlDetalles);
			xmlDocumento.addContent(xmlRecibos);
			xmlDocumento.addContent(xmlimpuestos);
			xmlDocumentos.addContent(xmlDocumento);
 		}
 	 
 		root.addContent(xmlDocumentos);
		XMLOutputter xmloutput = new XMLOutputter();
		xmloutput.setFormat(Format.getRawFormat());
		return xmloutput.outputString(doc);
	}
	
	
	public ByteArrayOutputStream XmlSigespLibroByte(Integer id ) throws Exception{
		String xml = XmlSigespLibro(id);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ZipOutputStream zipout = new  ZipOutputStream(out);
		ZipEntry entry = new ZipEntry("demeter-"+formatter.format(new Date())+".xml");
		zipout.putNextEntry(entry);
		zipout.write(xml.getBytes());
		zipout.closeEntry();
		zipout.close();
		return out;
	}
}




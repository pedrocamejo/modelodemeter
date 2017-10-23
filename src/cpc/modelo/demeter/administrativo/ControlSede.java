package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cva.pc.demeter.excepciones.ExcAccesoInvalido;

@SuppressWarnings("serial")
@Audited @Entity
@Table(name="tbl_dem_control_sede", schema="administracion")
public class ControlSede implements Serializable {
	

	private Integer					id;
	private Sede 					sede;
	private String					serie;
	private String  				prefijo;
	
	
	private Integer					controlFactura;
	private Integer					controlCliente;
	
	private Integer					controlReintegro;
	private String 					prefijoReintegro; //REIN 
	
	private Long					controlRecibo;
	private Integer					controlNotaCredito;
	private Integer					controlNotaDebito;
	private Integer					controlContrato;

	private Integer					nroNotaCredito;
	private Integer					nroNotaDebito;
	private Integer					nroFactura;
	private Integer					nroDocumento;
	private Integer					nroDebitoInterno;
	
	private Boolean					controlUnico;
	
	private String					cuentaPresupuestariaIngresosSede;
	private String					cuentaContableIngresosSede;
	private String					cuentaContableDescuentoSede;
	private String					cuentaContableImpuestoSede;
	private String					cuentaContableCaja;
	private String					cuentaContableAdelanto;
	
	private String					cuentaCLienteCobro;
	private String					cuentaCLientePago;
	private String					mascaraCliente;
	private Date					fechaCierreFactura;
	private Date					fechaInicio;
	private Double	 				porcentajeDescuento;
	private UnidadAdministrativa 	unidadAdministrativa;
	private String					prefijoRecepcionActivo, prefijoSalidaActivo, prefijoTransferenciaActivo;
	private Integer					controlRecepcionActivo, controlSalidaActivo, controlTransferenciaActivo;
	private String 					abogado; 
	private String 					cedulaCoordinador;
	private String					nombreCoordinadorSede;
	private String					direccionSede;
	private String					impreAbogado;
	
	private String					prefijoEntradaArticulo, prefijoSalidaArticulo, prefijoTransferenciaArticulo,prefijoDevolucionArticulo,prefijoConsumoArticulo;
	private Integer					controlEntradaArticulo, controlSalidaArticulo, controlTransferenciaArticulo,controlDevolucionArticulo,controlConsumoArticulo;
	
	private String					PrefijoCotizacion,PrefijoContratoServicioTecnico;
	private Integer					controlCotizacion,ControlContratoServicioTecnico;
	private String					PrefijoCotizacionTransporte;
	private Integer					controlCotizaciontransporte;
	
	private String					PrefijoCotizacionVialidad;
	private Integer					controlCotizacionVialidad;
	
	private String					prefijoExoneracionContrato;
	private Integer					controlExoneracionContrato;
	
	private Integer				controlNotaCargo;				
	
	@Id
	@Column(name="seq_control")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumns({
	    @JoinColumn(name="str_codemp", referencedColumnName="codemp", insertable=false, updatable=false),
	    @JoinColumn(name="str_sede", referencedColumnName="codubifis", insertable=false, updatable=false),
	})
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	
	@Column(name = "int_debitointerno")
	public Integer getNroDebitoInterno() {
		return nroDebitoInterno;
	}
	public void setNroDebitoInterno(Integer nroDebitoInterno) {
		this.nroDebitoInterno = nroDebitoInterno;
	}
	
	@Column(name="str_serie")
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	

	
	@Column(name="spi_cuenta")
	public String getCuentaPresupuestariaIngresosSede() {
		return cuentaPresupuestariaIngresosSede;
	}
	public void setCuentaPresupuestariaIngresosSede(
			String cuentaPresupuestariaIngresosSede) {
		this.cuentaPresupuestariaIngresosSede = cuentaPresupuestariaIngresosSede;
	}
	
	@Column(name="sc_cuentaingreso")
	public String getCuentaContableIngresosSede() {
		return cuentaContableIngresosSede;
	}
	public void setCuentaContableIngresosSede(String cuentaContableIngresosSede) {
		this.cuentaContableIngresosSede = cuentaContableIngresosSede;
	}

	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_fecha_cierre_cont")
	public Date getFechaCierreFactura() {
		return fechaCierreFactura;
	}
	public void setFechaCierreFactura(Date fechaCierreFactura) {
		this.fechaCierreFactura = fechaCierreFactura;
	}
	
	@Column(name="dbl_porcdesc")
	public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	public void setPorcentajeDescuento(Double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	

	
	@Column(name="str_formatocliente")
	public String getMascaraCliente() {
		return mascaraCliente;
	}
	public void setMascaraCliente(String mascaraCliente) {
		this.mascaraCliente = mascaraCliente;
	}
	
	
	@Column(name="str_cuentaclientepago")
	public String getCuentaCLientePago() {
		return cuentaCLientePago;
	}
	public void setCuentaCLientePago(String cuentaCLientePago) {
		this.cuentaCLientePago = cuentaCLientePago;
	}
	
	@Column(name="str_cuentadescuento")
	public String getCuentaContableDescuentoSede() {
		return cuentaContableDescuentoSede;
	}
	
	public void setCuentaContableDescuentoSede(String cuentaContableDescuentoSede) {
		this.cuentaContableDescuentoSede = cuentaContableDescuentoSede;
	}
	@Column(name="str_cuentaimpuesto")
	public String getCuentaContableImpuestoSede() {
		return cuentaContableImpuestoSede;
	}
	public void setCuentaContableImpuestoSede(String cuentaContableImpuestoSede) {
		this.cuentaContableImpuestoSede = cuentaContableImpuestoSede;
	}
	
	@Column(name="str_cuentaclientecobro")
	public String getCuentaCLienteCobro() {
		return cuentaCLienteCobro;
	}
	public void setCuentaCLienteCobro(String cuentaCLienteCobro) {
		this.cuentaCLienteCobro = cuentaCLienteCobro;
	}
	
	@Column(name="int_controlcliente")
	public Integer getControlCliente() {
		return controlCliente;
	}
	public void setControlCliente(Integer controlCliente) {
		this.controlCliente = controlCliente;
	}
	
	@Column(name="int_control_rec")
	public Long getControlRecibo() {
		return controlRecibo;
	}
	public void setControlRecibo(Long controlRecibo) {
		this.controlRecibo = controlRecibo;
	}
	
	@Column(name="int_nro_control")
	public Integer getControlFactura() {
		return controlFactura;
	}
	public void setControlFactura(Integer control) {
		this.controlFactura = control;
	}
	

	@Column(name="int_controlnotacredito")
	public Integer getControlNotaCredito() {
		return controlNotaCredito;
	}
	public void setControlNotaCredito(Integer controlNotaCredito) {
		this.controlNotaCredito = controlNotaCredito;
	}
	
	@Column(name="int_controlnotadebito")
	public Integer getControlNotaDebito() {
		return controlNotaDebito;
	}
	public void setControlNotaDebito(Integer controlNotaDebito) {
		this.controlNotaDebito = controlNotaDebito;
	}
	
	@Column(name="int_notacredito")
	public Integer getNroNotaCredito() {
		return nroNotaCredito;
	}
	public void setNroNotaCredito(Integer nroNotaCredito) {
		this.nroNotaCredito = nroNotaCredito;
	}
	
	@Column(name="int_notadebito")
	public Integer getNroNotaDebito() {
		return nroNotaDebito;
	}
	public void setNroNotaDebito(Integer nroNotaDebito) {
		this.nroNotaDebito = nroNotaDebito;
	}
	
	@Column(name="int_factura")
	public Integer getNroFactura() {
		return nroFactura;
	}
	public void setNroFactura(Integer nroFactura) {
		this.nroFactura = nroFactura;
	}
	
	@Column(name="str_cuentacaja")
	public String getCuentaContableCaja() {
		return cuentaContableCaja;
	}
	public void setCuentaContableCaja(String cuentaContableCaja) {
		this.cuentaContableCaja = cuentaContableCaja;
	}
	
	@Column(name="str_cuentaadelanto")
	public String getCuentaContableAdelanto() {
		return cuentaContableAdelanto;
	}
	public void setCuentaContableAdelanto(String cuentaContableAdelanto) {
		this.cuentaContableAdelanto = cuentaContableAdelanto;
	}
	
	@Column(name="int_controlcontrato")
	public Integer getControlContrato() {
		return controlContrato;
	}
	public void setControlContrato(Integer controlContrato) {
		this.controlContrato = controlContrato;
	}
	
	
	@Column(name="str_prefijo")
	public String getPrefijo() {
		return prefijo;
	}
	
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_coduniadm",referencedColumnName="coduniadm")
	})
	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	
	@Column(name="str_prefijoentrada")
	public String getPrefijoRecepcionActivo() {
		return prefijoRecepcionActivo;
	}
	public void setPrefijoRecepcionActivo(String prefijoRecepcionActivo) {
		this.prefijoRecepcionActivo = prefijoRecepcionActivo;
	}
	
	@Column(name="str_prefijosalida")
	public String getPrefijoSalidaActivo() {
		return prefijoSalidaActivo;
	}
	public void setPrefijoSalidaActivo(String prefijoSalidaActivo) {
		this.prefijoSalidaActivo = prefijoSalidaActivo;
	}
	
	@Column(name="str_prefijotrasnfer")
	public String getPrefijoTransferenciaActivo() {
		return prefijoTransferenciaActivo;
	}
	public void setPrefijoTransferenciaActivo(String prefijoTransferenciaActivo) {
		this.prefijoTransferenciaActivo = prefijoTransferenciaActivo;
	}
	
	@Column(name="int_controlentrada")
	public Integer getControlRecepcionActivo() {
		return controlRecepcionActivo;
	}
	public void setControlRecepcionActivo(Integer controlRecepcionActivo) {
		this.controlRecepcionActivo = controlRecepcionActivo;
	}
	
	@Column(name="int_controlsalida")
	public Integer getControlSalidaActivo() {
		return controlSalidaActivo;
	}
	public void setControlSalidaActivo(Integer controlSalidaActivo) {
		this.controlSalidaActivo = controlSalidaActivo;
	}
	
	@Column(name="int_idcontroltransfer")
	public Integer getControlTransferenciaActivo() {
		return controlTransferenciaActivo;
	}
	public void setControlTransferenciaActivo(Integer controlTransferenciaActivo) {
		this.controlTransferenciaActivo = controlTransferenciaActivo;
	}
	
	@Column(name="int_idcontroldocumento")
	public Integer getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(Integer nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	
	@Column(name="bol_controlunico")
	public Boolean getControlUnico() {
		return controlUnico;
	}
	public void setControlUnico(Boolean controlUnico) {
		this.controlUnico = controlUnico;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_inicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	@Column(name="str_prefijoentradaarticulo")
	public String getPrefijoEntradaArticulo() {
		return prefijoEntradaArticulo;
	}
	public void setPrefijoEntradaArticulo(String prefijoRecepcionArticulo) {
		this.prefijoEntradaArticulo = prefijoRecepcionArticulo;
	}
	@Column(name="str_prefijosalidaarticulo")
	public String getPrefijoSalidaArticulo() {
		return prefijoSalidaArticulo;
	}
	public void setPrefijoSalidaArticulo(String prefijoSalidaArticulo) {
		this.prefijoSalidaArticulo = prefijoSalidaArticulo;
	}
	@Column(name="str_prefijotransferenciaarticulo")
	public String getPrefijoTransferenciaArticulo() {
		return prefijoTransferenciaArticulo;
	}
	
	public void setPrefijoTransferenciaArticulo(String prefijoTransferenciaArticulo) {
		this.prefijoTransferenciaArticulo = prefijoTransferenciaArticulo;
	}
	@Column(name="str_prefijodevolucionarticulo")
	public String getPrefijoDevolucionArticulo() {
		return prefijoDevolucionArticulo;
	}
	public void setPrefijoDevolucionArticulo(String prefijoDevolucionArticulo) {
		this.prefijoDevolucionArticulo = prefijoDevolucionArticulo;
	}
	@Column(name="str_prefijoconsumoarticulo")
	public String getPrefijoConsumoArticulo() {
		return prefijoConsumoArticulo;
	}
	public void setPrefijoConsumoArticulo(String prefijoConsumoArticulo) {
		this.prefijoConsumoArticulo = prefijoConsumoArticulo;
	}
	@Column(name="int_controlentradaarticulo")
	public Integer getControlEntradaArticulo() {
		return controlEntradaArticulo;
	}
	public void setControlEntradaArticulo(Integer controlRecepcionArticulo) {
		this.controlEntradaArticulo = controlRecepcionArticulo;
	}
	@Column(name="int_controlsalidaarticulo")
	public Integer getControlSalidaArticulo() {
		return controlSalidaArticulo;
	}
	public void setControlSalidaArticulo(Integer controlSalidaArticulo) {
		this.controlSalidaArticulo = controlSalidaArticulo;
	}
	@Column(name="int_controltransferenciaarticulo")
	public Integer getControlTransferenciaArticulo() {
		return controlTransferenciaArticulo;
	}
	public void setControlTransferenciaArticulo(Integer controlTransferenciaArticulo) {
		this.controlTransferenciaArticulo = controlTransferenciaArticulo;
	}
	@Column(name="int_controldevolucionarticulo")
	public Integer getControlDevolucionArticulo() {
		return controlDevolucionArticulo;
	}
	public void setControlDevolucionArticulo(Integer controlDevolucionArticulo) {
		this.controlDevolucionArticulo = controlDevolucionArticulo;
	}
	@Column(name="int_controlconsumoarticulo")
	public Integer getControlConsumoArticulo() {
		return controlConsumoArticulo;
	}
	public void setControlConsumoArticulo(Integer controlConsumoArticulo) {
		this.controlConsumoArticulo = controlConsumoArticulo;
	}
	@Column(name="str_prefijocotizacion")
	public String getPrefijoCotizacion() {
		return PrefijoCotizacion;
	}
	public void setPrefijoCotizacion(String prefijoCotizacion) {
		PrefijoCotizacion = prefijoCotizacion;
	}
	@Column(name="str_prefijocontratoserviciotecnico")
	public String getPrefijoContratoServicioTecnico() {
		return PrefijoContratoServicioTecnico;
	}
	public void setPrefijoContratoServicioTecnico(
			String prefijoContratoServicioTecnico) {
		PrefijoContratoServicioTecnico = prefijoContratoServicioTecnico;
	}
	@Column(name="int_controlcotizacion")
	public Integer getControlCotizacion() {
		return controlCotizacion;
	}
	public void setControlCotizacion(Integer controlCotizacion) {
		this.controlCotizacion = controlCotizacion;
	}
	
	
	@Column(name="str_prefijocotizaciontransporte")
	public String getPrefijoCotizacionTransporte() {
		return PrefijoCotizacionTransporte;
	}
	public void setPrefijoCotizacionTransporte(String prefijoCotizacionTransporte) {
		PrefijoCotizacionTransporte = prefijoCotizacionTransporte;
	}
	@Column(name="int_controlcotizaciontransporte")
	public Integer getControlCotizaciontransporte() {
		return controlCotizaciontransporte;
	}
	public void setControlCotizaciontransporte(Integer controlCotizaciontransporte) {
		this.controlCotizaciontransporte = controlCotizaciontransporte;
	}
	@Column(name="int_controlcontratoserviciotecnico")
	public Integer getControlContratoServicioTecnico() {
		return ControlContratoServicioTecnico;
	}
	public void setControlContratoServicioTecnico(
			Integer controlContratoServicioTecnico) {
		ControlContratoServicioTecnico = controlContratoServicioTecnico;
	}
	
	
	@Column(name="int_controlReintegro")
	public Integer getControlReintegro() {
		return controlReintegro;
	}
	public void setControlReintegro(Integer controlReintegro) {
		this.controlReintegro = controlReintegro;
	}
	@Column(name="str_prefijocotizacionvialidad")
	public String getPrefijoCotizacionVialidad() {
		return PrefijoCotizacionVialidad;
	}
	public void setPrefijoCotizacionVialidad(String prefijoCotizacionVialidad) {
		PrefijoCotizacionVialidad = prefijoCotizacionVialidad;
	}
	@Column(name="int_controlcotizacionvialidad")
	public Integer getControlCotizacionVialidad() {
		return controlCotizacionVialidad;
	}
	public void setControlCotizacionVialidad(Integer controlCotizacionVialidad) {
		this.controlCotizacionVialidad = controlCotizacionVialidad;
	}
	
	@Column(name="str_prefijoexoneracioncontrato")
	public String getPrefijoExoneracionContrato() {
		return prefijoExoneracionContrato;
	}
	public void setPrefijoExoneracionContrato(String PrefijoExoneracionContrato) {
		this.prefijoExoneracionContrato = PrefijoExoneracionContrato;
	}
	@Column(name="int_controlexoneracioncontrato")
	public Integer getControlExoneracionContrato() {
		return controlExoneracionContrato;
	}
	public void setControlExoneracionContrato(Integer controlExoneracionContrato) {
		this.controlExoneracionContrato = controlExoneracionContrato;
	}
	
	@Transient
	public Integer getDocumentoFactura() throws ExcAccesoInvalido{
		if (controlUnico == null)
			throw new ExcAccesoInvalido("No se ha definido el tipo de documento a utilizar");
		if (controlUnico){
			if (nroDocumento == null)
				throw new ExcAccesoInvalido("Nro de Documento comun no definido");
			return nroDocumento++;
		}
		if (nroFactura == null)
			throw new ExcAccesoInvalido("Nro de Factura comun no definido");
		return	nroFactura++;
	}

	@Transient
	public Integer getDocumentoNotaCedito() throws ExcAccesoInvalido{
		if (controlUnico == null)
			throw new ExcAccesoInvalido("No se ha definido el tipo de documento a utilizar");
		if (controlUnico){
			if (nroDocumento == null)
				throw new ExcAccesoInvalido("Nro de Documento comun no definido");
			return nroDocumento++;
		}else{
			if (nroNotaCredito == null)
				throw new ExcAccesoInvalido("Nro de Nota Credito comun no definido");
			return nroNotaCredito++;
		}
	}
	
	@Transient
	public Integer getDocumentoNotaDebito() throws ExcAccesoInvalido{
		if (controlUnico == null)
			throw new ExcAccesoInvalido("No se ha definido el tipo de documento a utilizar");
		if (controlUnico){
			if (nroDocumento == null)
				throw new ExcAccesoInvalido("Nro de Documento comun no definido");
			return nroDocumento++;
		}else{
			if (nroNotaDebito == null)
				throw new ExcAccesoInvalido("Nro de Nota de Debito comun no definido");
			return nroNotaDebito++;
		}
	}
	@Column(name="str_abogado")
	public String getAbogado() {
		return abogado;
	}
	

	@Column(name="str_prefijoreintegro")
	public String getPrefijoReintegro() {
		return prefijoReintegro;
	}
	public void setPrefijoReintegro(String prefijoReintegro) {
		this.prefijoReintegro = prefijoReintegro;
	}
	
	public void setAbogado(String abogado) {
		this.abogado = abogado;
	}
	
	@Column(name="str_cedulacoordinadorsede")
	public String getCedulaCoordinador() {
		return cedulaCoordinador;
	}
	public void setCedulaCoordinador(String cedulaCoordinador) {
		this.cedulaCoordinador = cedulaCoordinador;
	}
	
	@Column(name="str_coordinadorsede")
	public String getNombreCoordinadorSede() {
		return nombreCoordinadorSede;
	}
	public void setNombreCoordinadorSede(String nombreCoordinadorSede) {
		this.nombreCoordinadorSede = nombreCoordinadorSede;
	}
	
	@Column(name="str_direccionsede")
	public String getDireccionSede() {
		return direccionSede;
	}
	public void setDireccionSede(String direccionSede) {
		this.direccionSede = direccionSede;
	}
	
	@Column(name="str_impreabogado")
	public String getImpreAbogado() {
		return impreAbogado;
	}
	public void setImpreAbogado(String impreAbogado) {
		this.impreAbogado = impreAbogado;
	}
	@Transient
	public void incrementarCliente(){
		controlCliente++;
	}
	
	@Transient
	public void incrementarRecibo(){
		controlRecibo++;
	}

	@Transient
	public void incrementarDebitoInterno(){
		nroDebitoInterno++;
	}
	
	
	@Transient
	public String getStrSede(){
		return sede.getNombre();
	}
	
	@Transient
	public void incrementarControlFactura(){
		controlFactura++;
	}
	
	@Transient
	public void incrementarControlContrato(){
		controlContrato++;
	}
	
	@Transient
	public void incrementarControlNotaCredito(){
		controlNotaCredito++;
	}
	
	@Transient
	public void incrementarControlNotaDebito(){
		controlNotaDebito++;
	}

	@Transient
	public void incrementarContrato(){
		controlContrato++;
	}
	
	@Transient
	public String getProximaSerieContrato(){
		controlContrato++;
		return getPrefijo()+controlContrato;
	}
	
	@Transient
	public String getProximaSerieCotizacion(){
		controlCotizacion++;
		return getPrefijoCotizacion()+controlCotizacion;
	}
	
	@Transient
	public String getProximoControlRecibo(){
		controlReintegro++;
		return getPrefijoReintegro()+controlReintegro;
	}
	 
	
	@Transient
	public String getProximaSerieContratoServicioTecnico(){
		ControlContratoServicioTecnico++;
		return getPrefijoContratoServicioTecnico()+ControlContratoServicioTecnico;
	}
	@Transient
	public String getProximaSerieExoneracioncontrato(){
		controlExoneracionContrato++;
		return getPrefijoExoneracionContrato()+controlExoneracionContrato;
	}
	
	@Transient
	public void incrementarControlRecepcionActivo(){
		controlRecepcionActivo++;
	}
	
	@Transient
	public void incrementarControlSalidaActivo(){
		controlSalidaActivo++;
	}
	
	@Transient
	public void incrementarControlTransferenciaActivo(){
		controlTransferenciaActivo++;
	}

	@Transient
	public void incrementarControlEntradaArticulo(){
		controlEntradaArticulo++;
	}

	@Transient
	public void incrementarControlSalidaArticulo(){
		controlSalidaArticulo++;
	}
	
	@Transient
	public void incrementarControlConsumoArticulo(){
		controlConsumoArticulo++;
	}
	
	@Transient
	public void incrementarControlDevolucionArticulo(){
		controlDevolucionArticulo++;
	}
	
	@Transient
	public void incrementarControlTransferenciaArticulo(){
		controlTransferenciaArticulo++;
	}
	
	@Transient
	public void incrementarControlCotizacion(){
		controlCotizacion++;
	}
	@Transient
	public void incrementarControlContratoServicioTecnico(){
		ControlContratoServicioTecnico++;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ControlSede)) {
			return false;
		}
		ControlSede other = (ControlSede) o;
		return true && other.getId().equals(this.getId());
	}
	

	@Transient
	public String getProximaSerieCotizacionTransporte(){
		controlCotizaciontransporte++;
		return getPrefijoCotizacionTransporte()+controlCotizaciontransporte;
	}

	@Transient
	public String getProximaSerieCotizacionVialidad(){
		controlCotizacionVialidad++;
		return getPrefijoCotizacionVialidad()+controlCotizacionVialidad;
	}
	
	@Column(name="int_controlnotacargo")
	public Integer getControlNotaCargo() {
		return controlNotaCargo;
	}
	public void setControlNotaCargo(Integer controlNotaCargo) {
		this.controlNotaCargo = controlNotaCargo;
	}
	
	@Transient
	public Integer incrementarControlNotaCargo(){
		return controlNotaCargo++;
	}
	
	
}



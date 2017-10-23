package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="viw_sabana_labores", schema="gestion")
public class SabanaMecanizadoAgricola implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String 	nombresCliente; 
	private String 	cedulaRif;
	private String 	descripcionRubro;
	private String 	descripcionSector;
	private String 	descripcionParroquia; 
	private String 	descripcionMunicipio; 
	private String 	descripcionLabor; 
	private String 	descripcionServicio; 
	private String 	descripcionUnidadMedida; 
	private String 	abreviaturaUnidadMedida;
	private String  nroSolicitud; 
	private Date 	fechaSolicitud; 
	private Double 	cantidadFisicaSolicitada; 
	private Double 	cantidadPasesSolicitud; 
	private Double 	cantidadLaborsolicitud; 
	private String 	nroOrdenServicio; 
	private Date 	dateInicioOrden; 
	private String 	descripcionEstadoOrden; 
	private Double 	cantidadSolicitadaOrdenServicio; 
	private String 	serieContrato; 
	private Long 	nroControlContrato; 
	private String 	serieFactura;
	private Long 	nroControlfactura;
	private Date 	fechaFactura; 
	private Double 	cantidadFacturada;
	private Double 	montoBaseFacturado; 
	private Double 	montoTotalFacturado;
	private Double	produccionReal;
	private Double	cantidaFisicoLaborada;
	private Double	cantidalaborEjecutada;
	private String	nombresTecnicoCampo;
	private String	notasCampo;
	private Double	cantidadCreditoNota, cantidadDebitoNota;
	private Double	montoBaseNota, montoTotalNota;
	private String	operadores;
	private String	maquinarias;
	
	private Integer	idSector, idParroquia, idMunicipio, idUnidadFuncional, idUnidadProductiva;
	private Integer	idCliente;
	private Integer	idRubro, idCicloProductivo;
	private Integer idEstadoOrden;
	private Long	idSolicitud, idOrdenTrabajo, idFactura, idContrato; 			
	private Integer idServicio, idLabor, idUnidadMedida; 
	private Integer idFinanciamiento, idTecnicoCampo;
	 
	private Boolean	esProduccion, esTransportado;
	private Integer idGenero, cargaFamiliar, idOrganizacion, idTipoOrganizacion;
	private String genero, fuenteFinanciamiento, organizacion, tipoOrganizacion;
	
	@Column(name="str_nombre")
	public String getNombresCliente() {
		return nombresCliente;
	}
	public void setNombresCliente(String nombresCliente) {
		this.nombresCliente = nombresCliente;
	}

	@Column(name="str_cedurif")
	public String getCedulaRif() {
		return cedulaRif;
	}
	public void setCedulaRif(String cedulaRif) {
		this.cedulaRif = cedulaRif;
	}

	@Column(name="rubro")
	public String getDescripcionRubro() {
		return descripcionRubro;
	}
	public void setDescripcionRubro(String descripcionRubro) {
		this.descripcionRubro = descripcionRubro;
	}

	@Column(name="sector")
	public String getDescripcionSector() {
		return descripcionSector;
	}
	public void setDescripcionSector(String descripcionSector) {
		this.descripcionSector = descripcionSector;
	}

	@Column(name="parroquia")
	public String getDescripcionParroquia() {
		return descripcionParroquia;
	}
	public void setDescripcionParroquia(String descripcionParroquia) {
		this.descripcionParroquia = descripcionParroquia;
	}

	@Column(name="municipio")
	public String getDescripcionMunicipio() {
		return descripcionMunicipio;
	}
	public void setDescripcionMunicipio(String descripcionMunicipio) {
		this.descripcionMunicipio = descripcionMunicipio;
	}

	@Column(name="labor")
	public String getDescripcionLabor() {
		return descripcionLabor;
	}
	public void setDescripcionLabor(String descripcionLabor) {
		this.descripcionLabor = descripcionLabor;
	}

	
	
	@Column(name="servicio")
	public String getDescripcionServicio() {
		return descripcionServicio;
	}
	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	@Column(name="unidadmedida")
	public String getDescripcionUnidadMedida() {
		return descripcionUnidadMedida;
	}
	public void setDescripcionUnidadMedida(String descripcionUnidadMedida) {
		this.descripcionUnidadMedida = descripcionUnidadMedida;
	}

	@Column(name="str_abreviatura")
	public String getAbreviaturaUnidadMedida() {
		return abreviaturaUnidadMedida;
	}
	public void setAbreviaturaUnidadMedida(String abreviaturaUnidadMedida) {
		this.abreviaturaUnidadMedida = abreviaturaUnidadMedida;
	}

	@Column(name="nrosol")
	public String getNroSolicitud() {
		return nroSolicitud;
	}
	public void setNroSolicitud(String nroSolicitud) {
		this.nroSolicitud = nroSolicitud;
	}

	@Column(name="fechasol")
	@Temporal(TemporalType.DATE)
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	@Column(name="cantfisisol")
	public Double getCantidadFisicaSolicitada() {
		return cantidadFisicaSolicitada;
	}
	public void setCantidadFisicaSolicitada(Double cantidadFisicaSolicitada) {
		this.cantidadFisicaSolicitada = cantidadFisicaSolicitada;
	}

	@Column(name="cantpassol")
	public Double getCantidadPasesSolicitud() {
		return cantidadPasesSolicitud;
	}
	public void setCantidadPasesSolicitud(Double cantidadPasesSolicitud) {
		this.cantidadPasesSolicitud = cantidadPasesSolicitud;
	}

	@Column(name="cantlabsol")
	public Double getCantidadLaborsolicitud() {
		return cantidadLaborsolicitud;
	}
	public void setCantidadLaborsolicitud(Double cantidadLaborsolicitud) {
		this.cantidadLaborsolicitud = cantidadLaborsolicitud;
	}
	
	@Column(name="nroorden")
	public String getNroOrdenServicio() {
		return nroOrdenServicio;
	}
	public void setNroOrdenServicio(String nroOrdenServicio) {
		this.nroOrdenServicio = nroOrdenServicio;
	}

	
	@Column(name="dte_inicio")
	@Temporal(TemporalType.DATE)
	public Date getDateInicioOrden() {
		return dateInicioOrden;
	}
	public void setDateInicioOrden(Date dateInicioOrden) {
		this.dateInicioOrden = dateInicioOrden;
	}

	@Column(name="estadoorden")
	public String getDescripcionEstadoOrden() {
		return descripcionEstadoOrden;
	}
	public void setDescripcionEstadoOrden(String descripcionEstadoOrden) {
		this.descripcionEstadoOrden = descripcionEstadoOrden;
	}

    @Column(name="cantsolordserv")
	public Double getCantidadSolicitadaOrdenServicio() {
		return cantidadSolicitadaOrdenServicio;
	}
	public void setCantidadSolicitadaOrdenServicio(
			Double cantidadSolicitadaOrdenServicio) {
		this.cantidadSolicitadaOrdenServicio = cantidadSolicitadaOrdenServicio;
	}

	@Column(name="seriectto")
	public String getSerieContrato() {
		return serieContrato;
	}
	public void setSerieContrato(String serieContrato) {
		this.serieContrato = serieContrato;
	}

	@Column(name="contctto")
	public Long getNroControlContrato() {
		return nroControlContrato;
	}
	public void setNroControlContrato(Long nroControlContrato) {
		this.nroControlContrato = nroControlContrato;
	}

	@Column(name="seriefact")
	public String getSerieFactura() {
		return serieFactura;
	}
	public void setSerieFactura(String serieFactura) {
		this.serieFactura = serieFactura;
	}

	@Column(name="nroconfac")
	public Long getNroControlfactura() {
		return nroControlfactura;
	}
	public void setNroControlfactura(Long nroControlfactura) {
		this.nroControlfactura = nroControlfactura;
	}

	@Column(name="fechafact")
	@Temporal(TemporalType.DATE)
	public Date getFechaFactura() {
		return fechaFactura;
	}
	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	@Column(name="cantfact")	
	public Double getCantidadFacturada() {
		return cantidadFacturada;
	}
	public void setCantidadFacturada(Double cantidadFacturada) {
		this.cantidadFacturada = cantidadFacturada;
	}

	@Column(name="basefact")
	public Double getMontoBaseFacturado() {
		return montoBaseFacturado;
	}
	public void setMontoBaseFacturado(Double montoBaseFacturado) {
		this.montoBaseFacturado = montoBaseFacturado;
	}

	@Column(name="montofact")
	public Double getMontoTotalFacturado() {
		return montoTotalFacturado;
	}
	public void setMontoTotalFacturado(Double montoTotalFacturado) {
		this.montoTotalFacturado = montoTotalFacturado;
	}

	@Column(name="dbl_produccionreal")
	public Double getProduccionReal() {
		return produccionReal;
	}
	public void setProduccionReal(Double produccionReal) {
		this.produccionReal = produccionReal;
	}

	@Column(name="dbl_trabajofisico")
	public Double getCantidaFisicoLaborada() {
		return cantidaFisicoLaborada;
	}
	public void setCantidaFisicoLaborada(Double cantidaFisicoLaborada) {
		this.cantidaFisicoLaborada = cantidaFisicoLaborada;
	}

	@Column(name="dbl_trabajolabor")
	public Double getCantidalaborEjecutada() {
		return cantidalaborEjecutada;
	}
	public void setCantidalaborEjecutada(Double cantidalaborEjecutada) {
		this.cantidalaborEjecutada = cantidalaborEjecutada;
	}

	@Column(name="tecnico")
	public String getNombresTecnicoCampo() {
		return nombresTecnicoCampo;
	}
	public void setNombresTecnicoCampo(String nombresTecnicoCampo) {
		this.nombresTecnicoCampo = nombresTecnicoCampo;
	}

	@Column(name="notasasociadas")
	public String getNotasCampo() {
		return notasCampo;
	}
	public void setNotasCampo(String notasCampo) {
		this.notasCampo = notasCampo;
	}

	@Column(name="cantCredito")
	public Double getCantidadCreditoNota() {
		return cantidadCreditoNota;
	}
	public void setCantidadCreditoNota(Double cantidadCreditoNota) {
		this.cantidadCreditoNota = cantidadCreditoNota;
	}

	@Column(name="cantdebito")
	public Double getCantidadDebitoNota() {
		return cantidadDebitoNota;
	}
	public void setCantidadDebitoNota(Double cantidadDebitoNota) {
		this.cantidadDebitoNota = cantidadDebitoNota;
	}

	@Column(name="montobasenotas")
	public Double getMontoBaseNota() {
		return montoBaseNota;
	}
	public void setMontoBaseNota(Double montoBaseNota) {
		this.montoBaseNota = montoBaseNota;
	}

	@Column(name="montototalnotas")
	public Double getMontoTotalNota() {
		return montoTotalNota;
	}
	public void setMontoTotalNota(Double montoTotalNota) {
		this.montoTotalNota = montoTotalNota;
	}

    
	
    @Column(name="operadores")
    public String getOperadores() {
		return operadores;
	}
	public void setOperadores(String operadores) {
		this.operadores = operadores;
	}
	
	@Column(name="maquinarias")
	public String getMaquinarias() {
		return maquinarias;
	}
	public void setMaquinarias(String maquinarias) {
		this.maquinarias = maquinarias;
	}
	
	@Column(name="seq_idsector")
	public Integer getIdSector() {
		return idSector;
	}
	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}

	@Column(name="seq_idparroquia")
	public Integer getIdParroquia() {
		return idParroquia;
	}
	public void setIdParroquia(Integer idParroquia) {
		this.idParroquia = idParroquia;
	}

	@Column(name="seq_idmunicipio")
	public Integer getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	@Column(name="int_unidadfuncional")
	public Integer getIdUnidadFuncional() {
		return idUnidadFuncional;
	}
	public void setIdUnidadFuncional(Integer idUnidadFuncional) {
		this.idUnidadFuncional = idUnidadFuncional;
	}

	@Column(name="int_idunidadproductiva")
	public Integer getIdUnidadProductiva() {
		return idUnidadProductiva;
	}
	public void setIdUnidadProductiva(Integer idUnidadProductiva) {
		this.idUnidadProductiva = idUnidadProductiva;
	}

	@Column(name="seq_idcliente")
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	@Column(name="seq_idrubro")
	public Integer getIdRubro() {
		return idRubro;
	}
	public void setIdRubro(Integer idRubro) {
		this.idRubro = idRubro;
	}

	@Column(name="int_idcicloproductivo")
	public Integer getIdCicloProductivo() {
		return idCicloProductivo;
	}
	public void setIdCicloProductivo(Integer idCicloProductivo) {
		this.idCicloProductivo = idCicloProductivo;
	}

	@Column(name="int_idestadoorden")
	public Integer getIdEstadoOrden() {
		return idEstadoOrden;
	}
	public void setIdEstadoOrden(Integer idEstadoOrden) {
		this.idEstadoOrden = idEstadoOrden;
	}
	
	@Column(name="int_idsolicitud")
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	@Id
	@Column(name="int_idordenservicio")
	public Long getIdOrdenTrabajo() {
		return idOrdenTrabajo;
	}
	public void setIdOrdenTrabajo(Long idOrdenTrabajo) {
		this.idOrdenTrabajo = idOrdenTrabajo;
	}

	@Column(name="seq_iddocumento")
	public Long getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}

	@Column(name="seq_idcontrato")
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	@Column(name="seq_idservicio")
	public Integer getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	@Column(name="int_idlabor")
	public Integer getIdLabor() {
		return idLabor;
	}
	public void setIdLabor(Integer idLabor) {
		this.idLabor = idLabor;
	}

	@Column(name="int_idunidadmedida")
	public Integer getIdUnidadMedida() {
		return idUnidadMedida;
	}
	public void setIdUnidadMedida(Integer idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	@Column(name="int_idfinanciamiento")
	public Integer getIdFinanciamiento() {
		return idFinanciamiento;
	}
	public void setIdFinanciamiento(Integer idFinanciamiento) {
		this.idFinanciamiento = idFinanciamiento;
	}

	@Column(name="int_idtecnico")
	public Integer getIdTecnicoCampo() {
		return idTecnicoCampo;
	}
	public void setIdTecnicoCampo(Integer idTecnicoCampo) {
		this.idTecnicoCampo = idTecnicoCampo;
	}

	@Column(name="bol_produccion")
	public Boolean getEsProduccion() {
		return esProduccion;
	}
	public void setEsProduccion(Boolean esProduccion) {
		this.esProduccion = esProduccion;
	}

	@Column(name="bol_transportado")
	public Boolean getEsTransportado() {
		return esTransportado;
	}
	public void setEsTransportado(Boolean esTransportado) {
		this.esTransportado = esTransportado;
	}

	@Column(name="idgenero")
	public Integer getIdGenero() {
		return idGenero;
	}
	
	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}
	
	@Column(name="cargafamiliar")
	public Integer getCargaFamiliar() {
		return cargaFamiliar;
	}
	
	public void setCargaFamiliar(Integer cargaFamiliar) {
		this.cargaFamiliar = cargaFamiliar;
	}
	
	@Column(name="idorganizacion")
	public Integer getIdOrganizacion() {
		return idOrganizacion;
	}
	
	public void setIdOrganizacion(Integer idOrganizacion) {
		this.idOrganizacion = idOrganizacion;
	}
	
	@Column(name="idtipoorganizacion")
	public Integer getIdTipoOrganizacion() {
		return idTipoOrganizacion;
	}
	
	public void setIdTipoOrganizacion(Integer idTipoOrganizacion) {
		this.idTipoOrganizacion = idTipoOrganizacion;
	}
	
	@Column(name="generoProductor")
	public String getGenero() {
		return genero;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	@Column(name="fuentefinanciamiento")
	public String getFuenteFinanciamiento() {
		return fuenteFinanciamiento;
	}
	
	public void setFuenteFinanciamiento(String fuenteFinanciamiento) {
		this.fuenteFinanciamiento = fuenteFinanciamiento;
	}
	
	@Column(name="organizacion")
	public String getOrganizacion() {
		return organizacion;
	}
	
	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}
	
	@Column(name="tipoorganizacion")
	public String getTipoOrganizacion() {
		return tipoOrganizacion;
	}
	
	public void setTipoOrganizacion(String tipoOrganizacion) {
		this.tipoOrganizacion = tipoOrganizacion;
	}

	/*
	tbl_dem_documentofiscaldetalle.dbl_preciounitario * tbl_dem_documentofiscaldetalle.dbl_cantidad * (1::numeric + tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) AS montofact, 
	tbl_dem_orden_servicio_mecanizado.dbl_produccionreal, 
	tbl_dem_orden_servicio_mecanizado.dbl_trabajofisico, 
	tbl_dem_orden_servicio_mecanizado.dbl_trabajolabor, 
	(tbl_dem_trabajador.str_nombres::text || ', '::text) || tbl_dem_trabajador.str_apellidos::text AS tecnico, 
	detallenota.dbl_cantidad AS cantnota, 
	detallenota.dbl_preciounitario * detallenota.dbl_cantidad AS basenota, 
	detallenota.dbl_preciounitario * detallenota.dbl_cantidad * (1::numeric + tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) AS montonota, 
	tbl_dem_documentofiscaldetalle.dbl_cantidad + detallenota.dbl_cantidad AS canttotaladm, 
	tbl_dem_documentofiscaldetalle.dbl_preciounitario * tbl_dem_documentofiscaldetalle.dbl_cantidad * (1::numeric + tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) + detallenota.dbl_preciounitario * detallenota.dbl_cantidad * (1::numeric + tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) AS montototal, 
	tbl_dem_sector.seq_idsector, 
	tbl_dem_parroquia.seq_idparroquia, 
	tbl_dem_municipio.seq_idmunicipio, 
	tbl_dem_clientes.seq_idcliente, 
	tbl_dem_rubro.seq_idrubro, 
	tbl_dem_orden_servicio.int_unidadfuncional, 
	tbl_dem_orden_servicio.int_idestadoorden, 
	tbl_dem_solicituddetalle.int_idsolicitud, 
	tbl_dem_orden_servicio_mecanizado.int_idordenservicio, 
	tbl_dem_servicio.seq_idservicio, 
	tbl_dem_detalle_orden_servicio.int_idlabor, 
	tbl_dem_detalle_orden_servicio.int_idunidadmedida, 
	tbl_dem_contrato.seq_idcontrato, 
	tbl_dem_documentofiscal.seq_iddocumento, 
	tbl_dem_orden_servicio_mecanizado.int_idcicloproductivo, 
	tbl_dem_orden_servicio_mecanizado.int_idunidadproductiva, 
	tbl_dem_orden_servicio_mecanizado.int_diasespera, 
	tbl_dem_orden_servicio_mecanizado.int_idfinanciamiento, 
	tbl_dem_orden_servicio_mecanizado.int_idtecnico, 
	tbl_dem_orden_servicio_mecanizado.bol_produccion, 
	tbl_dem_orden_servicio_mecanizado.bol_actaproduccion, 
	tbl_dem_orden_servicio_mecanizado.bol_transportado
*/
	/*
	
	tbl_dem_parroquia.str_descripcion AS parroquia, 
	tbl_dem_municipio.str_descripcion AS municipio, 
	tbl_dem_producto.str_descripcion AS labor, 
	tbl_dem_servicio.str_descripcion AS servicio, 
	tbl_dem_unidad_medidas.str_descripcion AS unidadmedida, 
	tbl_dem_unidad_medidas.str_abreviatura, 
	tbl_dem_solicitud.str_nrocon AS nrosol, 
	tbl_dem_solicitud.dtm_fecha AS fechasol, 
	tbl_dem_solicituddetalle.dbl_cantidad AS cantfisisol, 
	tbl_dem_solicituddetalle.dbl_pase AS cantpassol, 
	tbl_dem_solicituddetalle.dbl_cantidad * tbl_dem_solicituddetalle.dbl_pase AS cantlabsol, 
	tbl_dem_orden_servicio.str_nrocon AS nroorden, 
	tbl_dem_orden_servicio_mecanizado.dte_inicio, 
	tbl_dem_estadoordentrabajo.str_descripcion AS estadoorden, 
	tbl_dem_detalle_orden_servicio.dbl_cantidadsolicitada AS cantsolordserv, 
	tbl_dem_contrato.str_serie AS seriectto, tbl_dem_contrato.int_nrocontrol AS contctto, 
	tbl_dem_documentofiscal.str_serie AS seriefact, 
	tbl_dem_documentofiscal.int_nrocontrol AS nroconfac, 
	tbl_dem_documentofiscal.dtm_fecha AS fechafact, 
	tbl_dem_documentofiscaldetalle.dbl_cantidad AS cantfact, 
	tbl_dem_documentofiscaldetalle.dbl_preciounitario * 
	tbl_dem_documentofiscaldetalle.dbl_cantidad AS basefact, 
	tbl_dem_documentofiscaldetalle.dbl_preciounitario * tbl_dem_documentofiscaldetalle.dbl_cantidad * (1::numeric + tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) AS montofact, 
	tbl_dem_orden_servicio_mecanizado.dbl_produccionreal, 
	tbl_dem_orden_servicio_mecanizado.dbl_trabajofisico, 
	tbl_dem_orden_servicio_mecanizado.dbl_trabajolabor, 
	(tbl_dem_trabajador.str_nombres::text || ', '::text) || tbl_dem_trabajador.str_apellidos::text AS tecnico, 
	detallenota.dbl_cantidad AS cantnota, 
	detallenota.dbl_preciounitario * detallenota.dbl_cantidad AS basenota, 
	detallenota.dbl_preciounitario * detallenota.dbl_cantidad * (1::numeric + tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) AS montonota, tbl_dem_documentofiscaldetalle.dbl_cantidad + detallenota.dbl_cantidad AS canttotaladm, tbl_dem_documentofiscaldetalle.dbl_preciounitario * tbl_dem_documentofiscaldetalle.dbl_cantidad * (1::numeric + tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) + detallenota.dbl_preciounitario * detallenota.dbl_cantidad * (1::numeric + tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) AS montototal, tbl_dem_sector.seq_idsector, tbl_dem_parroquia.seq_idparroquia, tbl_dem_municipio.seq_idmunicipio, tbl_dem_clientes.seq_idcliente, tbl_dem_rubro.seq_idrubro, tbl_dem_orden_servicio.int_unidadfuncional, tbl_dem_orden_servicio.int_idestadoorden, tbl_dem_solicituddetalle.int_idsolicitud, tbl_dem_orden_servicio_mecanizado.int_idordenservicio, tbl_dem_servicio.seq_idservicio, tbl_dem_detalle_orden_servicio.int_idlabor, tbl_dem_detalle_orden_servicio.int_idunidadmedida, tbl_dem_contrato.seq_idcontrato, tbl_dem_documentofiscal.seq_iddocumento, tbl_dem_orden_servicio_mecanizado.int_idcicloproductivo, tbl_dem_orden_servicio_mecanizado.int_idunidadproductiva, tbl_dem_orden_servicio_mecanizado.int_diasespera, tbl_dem_orden_servicio_mecanizado.int_idfinanciamiento, tbl_dem_orden_servicio_mecanizado.int_idtecnico, tbl_dem_orden_servicio_mecanizado.bol_produccion, tbl_dem_orden_servicio_mecanizado.bol_actaproduccion, tbl_dem_orden_servicio_mecanizado.bol_transportado
	*/

}

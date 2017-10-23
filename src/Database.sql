CREATE SEQUENCE seq_tbl_dem_tipo_precio
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 5
  CACHE 1;
ALTER TABLE tbl_dem_tipo_precio OWNER TO demeter_sa;

CREATE TABLE administracion.tbl_dem_tipodocumentolegal(
	  seq_idtipfac serial NOT NULL,
	  str_descripcion character varying(50),
	  bol_tipfact	boolean,
	  bol_haber	boolean,	
	  
	  CONSTRAINT pk_tbl_dem_tipodocumentolegal PRIMARY KEY (seq_idtipfac)
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_tipodocumentolegal OWNER TO demeter_sa;


CREATE TABLE administracion.tbl_dem_estadodocumentofiscal
(
	  seq_idestdocfis serial NOT NULL,
	  str_descripcion character varying(30),
	  CONSTRAINT pk_tbl_dem_estadodocumentofiscal PRIMARY KEY (seq_idestdocfis)
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_estadodocumentofiscal OWNER TO demeter_sa;

CREATE TABLE administracion.tbl_dem_formapago
(
	  seq_idformapago bigserial NOT NULL,
	  int_idtipoformapago integer,
	  dtm_fecha date,
	  dbl_monto double precision,
	  CONSTRAINT pk_tbl_dem_formapago PRIMARY KEY (seq_idformapago),
	  CONSTRAINT fk_tbl_dem_formapago_tip FOREIGN KEY (int_idtipoformapago)
	      REFERENCES administracion.tbl_dem_tipo_forma_pago (seq_idtipoformapago) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_formapago OWNER TO demeter_sa;

 
CREATE TABLE administracion.tbl_dem_formapagocheque
(
	  int_idformapago bigint NOT NULL,
	  str_idcodemp character(4) NOT NULL,
	  str_idcodban character(3) NOT NULL,
	  str_numcue character varying(30),
	  str_numdoc character varying(30),
	  CONSTRAINT pk_tbl_dem_formapagocheque PRIMARY KEY (int_idformapago),
	  CONSTRAINT fk_tbl_dem_formapagocheque FOREIGN KEY (int_idformapago)
	      REFERENCES administracion.tbl_dem_formapago (seq_idformapago) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_tbl_dem_formapagocheque_banco FOREIGN KEY (str_idcodemp,str_idcodban)
	      REFERENCES sigesp.scb_banco (codemp, codban) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_formapagocheque OWNER TO sigesp;


ALTER TABLE solicitud.tbl_dem_aprobacion
	ADD  codemp character(4) default '0001';


ALTER TABLE solicitud.tbl_dem_estado_servicio SeqCierreDiario
	ADD bol_lisadm boolean default false,
	ADD bol_liseje boolean default false;  


CREATE TABLE public.tbl_dem_tipoplan
(
	  seq_idtipoplan serial NOT NULL,
	  str_nombre character varying(100),
	  bol_ordinal boolean,
	  CONSTRAINT pk_tipoplan PRIMARY KEY (seq_idtipoplan)
)
WITHOUT OIDS;
ALTER TABLE public.tbl_dem_tipoplan OWNER TO demeter_sa;



alter TABLE public.tbl_dem_solicitud
	add codemp character(4) DEFAULT '0001'::bpchar;


alter TABLE public.tbl_dem_plan add CONSTRAINT fk_tbl_dem_plan_tipo FOREIGN KEY (int_tipoplan)
      REFERENCES public.tbl_dem_tipoplan (seq_idtipoplan) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE mantenimiento.tbl_dem_bien_produccion add codemp character(4) DEFAULT '0001'::bpchar;   


CREATE OR REPLACE VIEW solicitud.viw_detalle_servicio_solicitud AS 
	SELECT
	  solicitud.tbl_dem_servicio_solicitud.int_idsolicitud,
	  int_idservicio,
	  int_rubro, 
	  int_idumedida,
	  int_cantpasaco * dbl_cantidad as cantidad
	FROM solicitud.tbl_dem_servicio_solicitud 
	INNER JOIN
	solicitud.tbl_dem_ficha_mecanizado_detalle ON int_idservsoli = seq_idservsoli
	INNER JOIN
	solicitud.tbl_dem_ficha_mecanizado_unidad_detalle ON int_iddetfichamec = seq_iddetfichamec
	INNER JOIN 
	public.tbl_dem_solicitud_mecanizado ON public.tbl_dem_solicitud_mecanizado.int_idsolicitud = solicitud.tbl_dem_servicio_solicitud.int_idsolicitud

	UNION ALL

	SELECT
	  int_idsolicitud,
	  int_idservicio,
	  NULL as int_rubro, 
	  int_idumedida,
	  dbl_cantidad as cantidad
	FROM 
	public.tbl_dem_detalle_ficha_linea_amarilla

	UNION ALL

	SELECT 
	  int_idsolicitud,
	  int_idservicio,
	  NULL as int_rubro,
	  int_idumedida, 
	  dbl_cantidad as cantidad
	  FROM   public.tbl_dem_detalle_solicitud_otros

	UNION ALL

	SELECT 
	  tbl_dem_solicitud_transporte.int_idsen la facturaolicitud,
	  tbl_dem_solicitud_transporte.int_idservicio,
	  int_idrubro, 
	  int_idumedidah as int_idumedida,
	  dbl_distancia as cantidad
	
	FROM public.tbl_dem_solicitud_transporte 
	INNER JOIN 
	public.tbl_dem_detalle_solicitud_transporte ON tbl_dem_detalle_solicitud_transporte.int_idsolicitud = tbl_dem_solicitud_transporte.int_idsolicitud
	inner join 
	public.tbl_dem_solicitud_transporte_rubro ON int_iddetsoltra = seq_iddetsoltra
	INNER JOIN 
	 public.tbl_dem_servicios_prestar
	 ON tbl_dem_servicios_prestar.int_idservicio = tbl_dem_solicitud_transporte.int_idservicio
	INNER JOIN 
	 tbl_dem_unidad_compuesta ON int_idumedida = int_idumedidap
	 INNER JOIN 
	tbl_dem_unidad_medidas ON int_idumedidah = seq_idumedida
	Where int_idtipounidadmedida =  1

	UNION ALL
	SELECT 
	  tbl_dem_solicitud_transporte.int_idsolicitud,
	  tbl_dem_solicitud_transporte.int_idservicio,
	  int_idrubro, 
	  int_idumedidah as int_idumedida,
	  dbl_cantidad as cantidad
	FROM public.tbl_dem_solicitud_transporte 
	INNER JOIN 
	public.tbl_dem_detalle_solicitud_transporte ON tbl_dem_detalle_solicitud_transporte.int_idsolicitud = tbl_dem_solicitud_transporte.int_idsolicitud
	inner join 
	public.tbl_dem_solicitud_transporte_rubro ON int_iddetsoltra = seq_iddetsoltra
	INNER JOIN 
	 public.tbl_dem_servicios_prestar
	 ON tbl_dem_servicios_prestar.int_idservicio = tbl_dem_solicitud_transporte.int_idservicio
	INNER JOIN 
	 tbl_dem_unidad_compuesta ON int_idumedida = int_idumedidap
	 INNER JOIN 
	tbl_dem_unidad_medidas ON int_idumedidah = seq_idumedida
	Where int_idtipounidadmedida =  3;


CREATE OR REPLACE VIEW solicitud.viw_servicio_solicitud AS 
	select		monto.setConstraint("no negative,no zero : Monto no valido");
	int_idsolicitud,
	int_idservicio,
	int_idestaserv,
	int_idnivelprio,
	int_idaprobacion
	FROM solicitud.tbl_dem_servicio_solicitud
	INNER JOIN solicitud.tbl_dem_servicio_solicitud_aprobado
	ON int_idservsoli = seq_idservsoli
	
	UNION ALL
	
	SELECT
	int_idsolicitud,
	int_idservicio,
	2 as int_idestaserv, 
	null as int_idnivelprio,
	null as int_idaprobacion
	FROM 
	public.tbl_dem_solicitud_transporte
	
	UNION ALLen la factura
	
	SELECT
	int_idsolicitud,
	int_idservicio,
	2 as int_idestaserv, 
	null as int_idnivelprio,
	null as int_idaprobacion
	FROM 
	tbl_dem_detalle_solicitud_otros;



alter table tbl_dem_servicios_prestar add  str_formula character varying(100) DEFAULT 'precio *'::character varying;

CREATE OR REPLACE VIEW public.viw_unidadesxservicio AS 
	select int_idservicio, int_idumedidah as int_idumedida
	FROM tbl_dem_servicios_prestar
	JOIN tbl_dem_unidad_medidas ON int_idumedida = seq_idumedida
	JOIN tbl_dem_unidad_compuesta ON int_idumedida = int_idumedidap
	where bol_compuesto 
	UNION ALL
	select int_idservicio, int_idumedida
	FROM tbl_dem_servicios_prestar
	JOIN tbl_dem_unidad_medidas ON int_idumedida = seq_idumedida
	where not bol_compuesto; 

      
DROP VIEW public.viw_servicios; 
	
	
CREATE OR REPLACE VIEW public.viw_servicios AS 
 SELECT tbl_dem_servicios.seq_idservicio, tbl_dem_servicios.str_nomserv, tbl_dem_servicios.bol_agrupa, tbl_dem_servicios.int_idpadre, tbl_dem_servicios.int_idtiposervicio, tbl_dem_servicios.int_nivel, tbl_dem_servicios_agrupadores.int_idumedida, 0 AS dbl_precio, 1 AS int_tipoprecio, NULL::"unknown" AS str_nombre, NULL::"unknown" AS str_nrodocumentoprecio, NULL::"unknown" AS dtm_fechadocumentoprecio, 0 AS dbl_descuento, NULL::"unknown" AS dtm_fechadescuento, tbl_dem_tipo_unidad_medida.str_descripcion, NULL::"unknown" AS str_abreviatura, tbl_dem_tipo_servicio.str_nombre AS nomtipser, NULL::"unknown" AS int_idtipounidadmedida, tbl_dem_tipo_unidad_medida.bol_compuesto, '' as str_formula
   FROM tbl_dem_servicios
   JOIN tbl_dem_tipo_servicio ON tbl_dem_tipo_servicio.seq_idtiposervicio = tbl_dem_servicios.int_idtiposervicio
   JOIN tbl_dem_servicios_agrupadores ON tbl_dem_servicios.seq_idservicio = tbl_dem_servicios_agrupadores.int_idservicio
   JOIN tbl_dem_tipo_unidad_medida ON tbl_dem_servicios_agrupadores.int_idumedida = tbl_dem_tipo_unidad_medida.seq_idtipounidadmedida
UNION ALL 
 SELECT tbl_dem_servicios.seq_idservicio, tbl_dem_servicios.str_nomserv, tbl_dem_servicios.bol_agrupa, tbl_dem_servicios.int_idpadre, tbl_dem_servicios.int_idtiposervicio, tbl_dem_servicios.int_nivel, tbl_dem_servicios_hojas.int_idumedida, tbl_dem_servicios_hojas.dbl_precio, tbl_dem_servicios_hojas.int_tipoprecio, tbl_dem_tipo_precio.str_nombre, tbl_dem_servicios_hojas.str_nrodocumentoprecio, tbl_dem_servicios_hojas.dtm_fechadocumentoprecio, tbl_dem_servicios_hojas.dbl_descuento, tbl_dem_servicios_hojas.dtm_fechadescuento, tbl_dem_unidad_medidas.str_descripcion, tbl_dem_unidad_medidas.str_abreviatura, tbl_dem_tipo_servicio.str_nombre AS nomtipser, tbl_dem_unidad_medidas.int_idtipounidadmedida, tbl_dem_unidad_medidas.bol_compuesto, str_formula
   FROM tbl_dem_servicios
   JOIN tbl_dem_tipo_servicio ON tbl_dem_tipo_servicio.seq_idtiposervicio = tbl_dem_servicios.int_idtiposervicio
   JOIN tbl_dem_servicios_prestar tbl_dem_servicios_hojas ON tbl_dem_servicios.seq_idservicio = tbl_dem_servicios_hojas.int_idservicio
   JOIN tbl_dem_tipo_precio ON tbl_dem_servicios_hojas.int_tipoprecio = tbl_dem_tipo_precio.seq_idtipoprecio
   JOIN tbl_dem_unidad_medidas ON tbl_dem_servicios_hojas.int_idumedida = tbl_dem_unidad_medidas.seq_idumedida;

alter table administracion.tbl_dem_control_sede add  CONSTRAINT fk_tbl_dem_control_series2
unique(seq_control);

ALTER TABLE administracion.tbl_dem_control_sede add str_cuentaclientepago character(25);
/****************************Script nuevo*************************************/
/*************demeter****************/
ALTER table administracion.tbl_dem_control_sede add str_cuentaCaja character varying(25);
ALTER table administracion.tbl_dem_control_sede drop dte_fecha_cierre_dia;
ALTER table administracion.tbl_dem_control_sede add int_notacredito integer;
ALTER table administracion.tbl_dem_control_sede add int_notadebito integer;
ALTER TABLE administracion.tbl_dem_cierrediario add int_control integer
ALTER TABLE administracion.tbl_dem_cierrediario add int_control integer;


ALTER TABLE administracion.tbl_dem_control_sede
  add int_factura integer,
  add int_controlnotadebito integer,
  add int_controlnotacredito integer;
  
alter table administracion.tbl_dem_control_sede 
add int_controlcontrato integer default 0;

ALTER TABLE administracion.tbl_dem_control_sede
ADD int_idcontroldocumento int,
ALTER TABLE public.viw_servicios OWNER TO demeter_sa;


CREATE TABLE administracion.tbl_dem_documentofiscal
(
  seq_iddocumento serial NOT NULL,
  int_idcontrato integer,
  int_idpagador integer NOT NULL,
  str_serie character varying(6),
  int_nrocontrol integer NOT NULL,
  dtm_fecha date,
  bol_credito boolean,
  int_estado integer NOT NULL,
  int_iddireccion integer,
  dbl_montobase numeric(12,4),
  dbl_impuesto numeric(12,4),
  dbl_montototal numeric(12,4),
  bol_cancelada boolean,
  CONSTRAINT pk_tbl_dem_documentofiscal PRIMARY KEY (seq_iddocumento),
  CONSTRAINT fk_tbl_dem_documentofiscal_cliente FOREIGN KEY (int_idpagador)
      REFERENCES public.tbl_dem_clientes (seq_idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_documentofiscal_ctto FOREIGN KEY (int_idcontrato)
      REFERENCES administracion.tbl_dem_contrato (seq_idcontrato) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_documentofiscal_direccion FOREIGN KEY (int_iddireccion)
      REFERENCES public.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_documentofiscal_estado FOREIGN KEY (int_estado)
      REFERENCES administracion.tbl_dem_estadodocumentofiscal (seq_idestdocfis) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_documentofiscal OWNER TO sigesp;


CREATE TABLE administracion.tbl_dem_documentofiscaldetalle
(
  seq_iddetalle serial NOT NULL,
  int_iddocumento integer NOT NULL,
  int_idservicio integer NOT NULL,
  dbl_cantidad numeric(12,4),
  dbl_preciounitario numeric(12,4),
  int_idtipoimpuesto integer NOT NULL,
  CONSTRAINT pk_tbl_dem_documentofiscaldetalle PRIMARY KEY (seq_iddetalle), 
  CONSTRAINT fk_tbl_dem_documentofiscaldetalle_documento FOREIGN KEY (int_iddocumento)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_documentofiscaldetalle_servicio FOREIGN KEY (int_idservicio)
      REFERENCES tbl_dem_servicios (seq_idservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_documentofiscaldetalle_impuesto FOREIGN KEY (int_idtipoimpuesto)
      REFERENCES administracion.tbl_dem_tipo_impuesto (seq_idtipoimpuesto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_documentofiscaldetalle OWNER TO sigesp;

CREATE TABLE administracion.tbl_dem_recibo
(
  seq_idrecibo bigserial NOT NULL,
  int_iddocumento integer NOT NULL,
  str_control character varying(15) NOT NULL,
  dbl_monto numeric(12,4),
  dtm_fecha date,
  CONSTRAINT pk_tbl_dem_recibo PRIMARY KEY (seq_idrecibo),
  CONSTRAINT fk_tbl_dem_recibo_documento FOREIGN KEY (int_iddocumento)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_recibo OWNER TO sigesp;


alter table gestion.tbl_dem_orden_servicio  add int_idcontrato integer;


CREATE TABLE administracion.tbl_dem_formapago
(
  seq_idformapago bigserial NOT NULL,
  int_idrecibo	bigint not null,
  int_idtipoformapago integer not null,
  dtm_fecha date,
  dbl_monto numeric(12,2),
  str_idcodemp character(4) default '0001',
  str_idcodban character(3) default '---',
  str_numcue character(25),
  str_numdoc character(30),
  CONSTRAINT pk_tbl_dem_formapago PRIMARY KEY (seq_idformapago),
  CONSTRAINT fk_tbl_dem_formapago_tip FOREIGN KEY (int_idtipoformapago)
      REFERENCES administracion.tbl_dem_tipo_forma_pago (seq_idtipoformapago) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_formapago_rec FOREIGN KEY (int_idrecibo)
      REFERENCES administracion.tbl_dem_recibo (seq_idrecibo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_formapago OWNER TO demeter_sa;



ALTER TABLE administracion.tbl_dem_recibo
Add str_codemp character(4) default '0001',
add str_sede character(4) NULL;


alter TABLE administracion.tbl_dem_recibo
add str_concepto varchar(30);

ALTER TABLE administracion.tbl_dem_recibo add bol_anulado boolean;

alter TABLE administracion.tbl_dem_documentofiscaldetalle add str_comdes varchar(30);

ALTER TABLE administracion.tbl_dem_documentofiscal add dbl_descuento numeric(12,4), add dbl_pordesc numeric(12,4),
add dbl_porimp numeric(12,4);


CREATE TABLE administracion.tbl_dem_control_sede
(
  seq_control serial NOT NULL,
  str_codemp character(4) NOT NULL DEFAULT '0001'::bpchar,
  str_sede character(4) NOT NULL,
  str_serie character(2) NOT NULL,
  int_nro_control integer NOT NULL,
  dbl_porcdesc numeric(12,4),
  dte_fecha_cierre_cont date,
  dte_fecha_cierre_dia date,
  int_control_rec bigint,
  sc_cuenta character(25),
  spi_cuenta character(25),
  str_cuentacliente character(25),
  str_formatocliente character(10),
  int_controlcliente character(10),
  CONSTRAINT pk_tbl_dem_control_series PRIMARY KEY (str_codemp, str_sede),
  CONSTRAINT fk_tbl_dem_control_series FOREIGN KEY (str_codemp, str_sede)
      REFERENCES sno_ubicacion_fisica (codemp, codubifis) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



ALTER TABLE administracion.tbl_dem_documentofiscal add str_observacion text;

alter TABLE tbl_dem_servicios_prestar add int_idtipoimpuesto integer default 1, 
add CONSTRAINT fk_tbl_dem_servicios_prestar_imp FOREIGN KEY (int_idtipoimpuesto)
      REFERENCES administracion.tbl_dem_tipo_impuesto (seq_idtipoimpuesto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
CREATE TABLE administracion.tbl_dem_clienteadministrativo
(
  seq_idclienteadm serial not null,
  int_idcliente integer NOT NULL,
  str_ctapago character varying(25),
  str_ctacobro character varying(25),
  CONSTRAINT tbl_dem_clienteadministrativo_pkey PRIMARY KEY (seq_idclienteadm),
  CONSTRAINT fk_tbl_dem_clienteadministrativo_pkey FOREIGN KEY (int_idcliente)
      REFERENCES public.tbl_dem_clientes (seq_idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_clienteadministrativo OWNER TO sigesp;


alter TABLE administracion.tbl_dem_documentofiscal add
<<<<<<< .mine
dbl_saldo numeric(12,4);

alter table administracion.tbl_dem_clienteadministrativo add
CONSTRAINT fk_tbl_dem_clienteadministrativo_cli UNIQUE (seq_idclienteadm);


CREATE TABLE administracion.tbl_dem_cierrediario
(
  seq_idcierre serial not null,
  dtm_fecha date not null,
  dbl_ingfac numeric(12,4),
  dbl_impfac numeric(12,4),
  dbl_cuecob numeric(12,4),
  dbl_monrec numeric(12,4),
  dbl_cuepag numeric(12,4),
  dbl_mondep numeric(12,4),
  str_observacion text,
  CONSTRAINT pk_tbl_dem_cierrediario PRIMARY KEY (seq_idcierre)
)
WITHOUT OIDS;

alter table administracion.tbl_dem_control_sede add  CONSTRAINT fk_tbl_dem_control_series2
unique(seq_control);


CREATE TABLE administracion.tbl_dem_cierrediariofactura
(
  seq_idcierredocumento	 serial not null, 
  int_idcierre integer NOT NULL,
  int_iddocumento integer NOT NULL,
  dbl_ingfac numeric(12,4),
  dbl_impfac numeric(12,4),
  dbl_cuecob numeric(12,4),
  int_control integer,
  int_idclienteadm integer,
  CONSTRAINT pk_tbl_dem_cierrediariofactura PRIMARY KEY (seq_idcierredocumento),
  CONSTRAINT fk_tbl_dem_cierrediariofactura UNIQUE (int_idcierre, int_iddocumento),
  CONSTRAINT fk_tbl_dem_cierrediariofactura1 FOREIGN KEY (int_idcierre)
      REFERENCES administracion.tbl_dem_cierrediario (seq_idcierre) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediariofactura2 FOREIGN KEY (int_iddocumento)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediariofactura3 FOREIGN KEY (int_control)
      REFERENCES administracion.tbl_dem_control_sede (seq_control) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediariofactura4 FOREIGN KEY (int_idclienteadm)
      REFERENCES administracion.tbl_dem_clienteadministrativo (seq_idclienteadm) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_cierrediariofactura OWNER TO sigesp;




CREATE TABLE administracion.tbl_dem_cierrediariorecibo
(
  seq_idcierrecibo serial not null, 
  int_idcierre integer NOT NULL,
  int_idformapago bigint NOT NULL,
  int_control integer,
  int_idclienteadm integer,
  CONSTRAINT pk_tbl_dem_cierrediariorecibo PRIMARY KEY (seq_idcierrecibo),
  CONSTRAINT fk_tbl_dem_cierrediariorecibo unique (int_idcierre, int_idformapago),
  CONSTRAINT fk_tbl_dem_cierrediariorecibo1 FOREIGN KEY (int_idcierre)
      REFERENCES administracion.tbl_dem_cierrediario (seq_idcierre) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediariorecibo2 FOREIGN KEY (int_idformapago)
      REFERENCES administracion.tbl_dem_formapago (seq_idformapago) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediariorecibo3 FOREIGN KEY (int_control)
      REFERENCES administracion.tbl_dem_control_sede (seq_control) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediariorecibo4 FOREIGN KEY (int_idclienteadm)
      REFERENCES administracion.tbl_dem_clienteadministrativo (seq_idclienteadm) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_cierrediariorecibo OWNER TO sigesp;


ALTER TABLE administracion.tbl_dem_control_sede add str_cuentaclientepago character(25);



CREATE TABLE administracion.tbl_dem_contrato
(
  seq_idcontrato serial NOT NULL,
  int_idplanfinanciamiento integer NOT NULL,
  int_idpagador integer NOT NULL,
  int_idsolicitud bigint NOT NULL,
  str_serie character varying(6),
  int_nrocontrol integer NOT NULL,
  dtm_fecha date,
  dtm_fechadesde date,
  int_estado integer NOT NULL,
  int_diasvigencia integer NOT NULL,
  dbl_monto numeric(12,4),
  CONSTRAINT tbl_dem_contrato_pkey PRIMARY KEY (seq_idcontrato),
  CONSTRAINT fk_tbl_dem_contrato_est FOREIGN KEY (int_estado)
      REFERENCES administracion.tbl_dem_estado_contrato (seq_idestadocontrato) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_contrato_fin FOREIGN KEY (int_idplanfinanciamiento)
      REFERENCES administracion.tbl_dem_plan_financiamiento (seq_idplanfinanciamiento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_contrato_solicitud FOREIGN KEY (int_idsolicitud)
      REFERENCES public.tbl_dem_solicitud (seq_idsolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE administracion.tbl_dem_detallecontrato
(
	seq_iddetalle bigserial NOT NULL,  
	int_idcontrato integer NOT NULL,
  	int_idservsoli integer NOT NULL,
  	int_idumedidacobro integer,
  	dbl_cantidad numeric(12,4),
  	dbl_preciounidad numeric(12,4),
  	dbl_subtotal numeric(12,4),  
  CONSTRAINT tbl_dem_contrato_servicio_pkey PRIMARY KEY (seq_iddetalle),
  CONSTRAINT fk_tbl_dem_contrato_servicio_cto FOREIGN KEY (int_idcontrato)
      REFERENCES administracion.tbl_dem_contrato (seq_idcontrato) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_contrato_servicio_ser FOREIGN KEY (int_idservsoli)
      REFERENCES solicitud.tbl_dem_servicio_solicitud (seq_idservsoli) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_contrato_unidad_medida FOREIGN KEY (int_idumedidacobro)
  	  REFERENCES public.tbl_dem_unidad_medidas (seq_idumedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT unk_tbl_dem_detallecontrato UNIQUE (int_idcontrato, int_idservsoli);
  
  CREATE OR REPLACE VIEW solicitud.viw_solicitudes_planificadas AS 
   SELECT DISTINCT viw_servicios_planificados_und_prod.seq_idsolicitud,viw_servicios_planificados_und_prod.int_idplansema, 
                   viw_servicios_planificados_und_prod.seq_idplansema,viw_servicios_planificados_und_prod.dtm_fecha_plan, 
                   viw_servicios_planificados_und_prod.dtm_fecha_inicio,viw_servicios_planificados_und_prod.dtm_fecha_fin, 
                   viw_servicios_planificados_und_prod.str_idsede,viw_servicios_planificados_und_prod.int_criterio_prioridad, 
                   viw_servicios_planificados_und_prod.bol_activo,viw_servicios_planificados_und_prod.int_estsol, 
                   viw_servicios_planificados_und_prod.int_tipsol,viw_servicios_planificados_und_prod.str_nrocon, 
                   viw_servicios_planificados_und_prod.dtm_fecha, viw_servicios_planificados_und_prod.int_idsede, 
                   viw_servicios_planificados_und_prod.sector,viw_servicios_planificados_und_prod.seq_idcliente, 
                   viw_servicios_planificados_und_prod.int_tipocliente, viw_servicios_planificados_und_prod.str_cedurif, 
                   viw_servicios_planificados_und_prod.str_nombre, viw_servicios_planificados_und_prod.str_email, 
                   viw_servicios_planificados_und_prod.tiposolicitud, viw_servicios_planificados_und_prod.id_direccion, 
                   viw_servicios_planificados_und_prod.dbl_hectareas, viw_servicios_planificados_und_prod.dbl_distancia, 
                   viw_servicios_planificados_und_prod.str_parcela,viw_servicios_planificados_und_prod.int_idestter, 
                   viw_servicios_planificados_und_prod.int_idseragu,viw_servicios_planificados_und_prod.int_idserele, 
                   viw_servicios_planificados_und_prod.int_idriego,viw_servicios_planificados_und_prod.int_idvia, 
                   viw_servicios_planificados_und_prod.int_convia,viw_servicios_planificados_und_prod.str_observacion,
                   viw_servicios_planificados_und_prod.str_zona,viw_servicios_planificados_und_prod.int_idestlegter, 
                   viw_servicios_planificados_und_prod.seq_idriego,viw_servicios_planificados_und_prod.str_descripcion, 
                   viw_servicios_planificados_und_prod.conriego,viw_servicios_planificados_und_prod.idrubro
   FROM solicitud.viw_servicios_planificados_und_prod
  ORDER BY viw_servicios_planificados_und_prod.seq_idsolicitud, viw_servicios_planificados_und_prod.dtm_fecha_plan;
     
);

ALTER TABLE administracion.tbl_dem_tipo_forma_pago
add bol_usadocu boolean,
add bol_usacuen boolean,
add bol_usaban boolean;

ALTER TABLE administracion.tbl_dem_cierrediario add dbl_mondes numeric(12,4);

/****************************/

CREATE TABLE administracion.tbl_dem_cierrediarioasiento
(
  seq_idcierreasiento serial NOT NULL,
  int_idcierre integer NOT NULL,
  dte_fecha date,
  str_cuenta character varying(25), 
  dbl_debe numeric(12,4),
  dbl_haber numeric(12,4),
  CONSTRAINT pk_tbl_dem_cierrediarioasiento PRIMARY KEY (seq_idcierreasiento),
  CONSTRAINT fk_tbl_dem_cierrediarioasiento_cierre FOREIGN KEY (int_idcierre)
      REFERENCES administracion.tbl_dem_cierrediario (seq_idcierre) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediarioasiento_cuenta FOREIGN KEY (str_cuenta)
      REFERENCES sigesp.scg_cuentas (sc_cuenta) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediarioasiento UNIQUE (int_idcierre, dte_fecha, str_cuenta)
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_cierrediarioasiento OWNER TO sigesp;




/****************************Script nuevo*************************************/
/*************demeter****************/
ALTER table administracion.tbl_dem_control_sede add str_cuentaCaja character varying(25);
ALTER table administracion.tbl_dem_control_sede drop dte_fecha_cierre_dia;
ALTER table administracion.tbl_dem_control_sede add int_notacredito integer;
ALTER table administracion.tbl_dem_control_sede add int_notadebito integer;
ALTER TABLE administracion.tbl_dem_cierrediario add int_control integer
ALTER TABLE administracion.tbl_dem_cierrediario add int_control integer;
ALTER TABLE administracion.tbl_dem_formapago add bol_verificado boolean default false;


CREATE TABLE administracion.tbl_dem_deposito
(
  seq_iddeposito bigserial NOT NULL,
  int_idformapago bigint NOT NULL,
  str_idcuenta character(25), 
  CONSTRAINT pk_tbl_dem_deposito PRIMARY KEY (seq_iddeposito),
  CONSTRAINT fk_tbl_dem_deposito_pago FOREIGN KEY (int_idformapago)
      REFERENCES administracion.tbl_dem_formapago (seq_idformapago) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_deposito_cuenta FOREIGN KEY (str_idcuenta)
      REFERENCES sigesp.scg_cuentas (sc_cuenta) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_formapago OWNER TO demeter_sa;



CREATE TABLE administracion.tbl_dem_cierrediariodeposito
(
  seq_idcierredeposito serial NOT NULL,
  int_idcierre integer NOT NULL,
  str_idcuenta character(25), 
  dbl_monto    numeric(12,2), 
  CONSTRAINT pk_tbl_dem_cierrediariodeposito PRIMARY KEY (seq_idcierredeposito),
  CONSTRAINT fk_tbl_dem_cierrediariodeposito_cierre FOREIGN KEY (int_idcierre)
      REFERENCES administracion.tbl_dem_cierrediario (seq_idcierre) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediariodeposito_cuenta FOREIGN KEY (str_idcuenta)
      REFERENCES sigesp.scg_cuentas (sc_cuenta) MATCH SIMPLE,
  CONSTRAINT fk_tbl_dem_cierrediariodeposito UNIQUE (int_idcierre, str_idcuenta)
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_cierrediariodeposito OWNER TO sigesp;

/**********************************************Ares**********************************************/
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Bancos', 29, 'cpc.demeter.comando.ComandoBanco', '/iconos/32x32/banco.png', 
            true, '1111000000000000', 8, '');


UPDATE tbl_ares_modulo
   SET str_nombre='Basico'
 WHERE seq_idmodulo = 29;
 
 INSERT INTO tbl_ares_modulo(
            seq_idmodulo, str_nombre, str_icono, int_idsistema, bol_estado)
    VALUES (38, 'Transacciones', '', 6, true);
    
    INSERT INTO tbl_ares_modulo(
            seq_idmodulo, str_nombre, str_icono, int_idsistema, bol_estado)
    VALUES (39, 'Control', '', 6, true);

UPDATE tbl_ares_funcionalidad
   SET int_idmodulo=38, int_orden=0
 WHERE str_nombre='Contrato';
 
 UPDATE tbl_ares_funcionalidad
   SET int_idmodulo=38, int_orden=1
 WHERE str_nombre='Factura';

 UPDATE tbl_ares_funcionalidad
   SET int_idmodulo=38 , int_orden=2
 WHERE str_nombre='Recibo';

 UPDATE tbl_ares_funcionalidad
   SET int_idmodulo=39, int_orden=0
 WHERE str_nombre='Cierre Diario';
 
 
UPDATE tbl_ares_funcionalidad
   SET bol_estado=false
 WHERE str_nombre='Contrato';

UPDATE tbl_ares_funcionalidad
   SET bol_estado=false
 WHERE str_comando='cva.pc.demeter.comando.ComandoFactura';
 
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Cuentas Bancarias', 29, 'cpc.demeter.comando.ComandoCuentaBancaria', '/iconos/32x32/cuentaBancaria.png', 
            true, '1111000000000000', 9, '');
 
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Cuentas Contables', 29, 'cpc.demeter.comando.ComandoCuentaContable', '/iconos/32x32/cuentaContable.png', 
            true, '1111000000000000', 9, '');
            
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Nota Credito', 38, 'cpc.demeter.comando.ComandoNotaCredito', '/iconos/32x32/notaCredito.png', 
            true, '1111000000000000', 3, '');            
            
UPDATE tbl_ares_funcionalidad
   SET str_masaccmax='1001001101000000'
 WHERE str_comando='cpc.demeter.comando.ComandoNotaCredito' or str_comando='cpc.demeter.comando.ComandoFactura'

INSERT INTO tbl_ares_funcionalidad(str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Deposito', 38, 'cpc.demeter.comando.ComandoDeposito', '/iconos/32x32/deposito.png', 
            true, '1111000000000000', 4, '');   
            
INSERT INTO tbl_ares_funcionalidad(str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Nota Debito', 38, 'cpc.demeter.comando.ComandoNotaDebito', '/iconos/32x32/notaDebito.png', 
            true, '1001001101000000', 3, ''); 
            
INSERT INTO tbl_ares_funcionalidad(str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Libro Venta', 39, 'cpc.demeter.comando.ComandoLibroVenta', '/iconos/32x32/libroVenta.png', 
            true, '1011001000000000', 2, '');            
            

INSERT INTO tbl_ares_funcionalidad(str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Correccion Forma Pago', 39, 'cpc.demeter.comando.ComandoFormaPago', '/iconos/32x32/formaPago.png', 
            true, '0101000000000000', 3,'');                 
            
UPDATE tbl_ares_funcionalidad SET str_masaccmax = '1001001101000000' where str_comando = 'cpc.demeter.comando.ComandoFactura';
UPDATE tbl_ares_funcionalidad SET str_masaccmax = '1001001101000000' where str_comando = 'cpc.demeter.comando.ComandoRecibo';
            
            
/***************************************** DEMETER**********************************************/
drop table scb_ctabanco;

CREATE TABLE scb_ctabanco
(
  seq_idcuentabanco serial not null,
  codemp character(4) NOT NULL,
  codban character(3) NOT NULL,
  ctaban character(25) NOT NULL,
  codtipcta character varying(3) NOT NULL,
  dencta character varying(50) NOT NULL,
  sc_cuenta character varying(25) NOT NULL,
  CONSTRAINT pk_scb_ctabanco PRIMARY KEY (seq_idcuentabanco),
  CONSTRAINT fk_scb_ctabanco UNIQUE (codemp, codban, ctaban),
  CONSTRAINT fk_scb_ctab_scb_banco_scb_banc FOREIGN KEY (codemp, codban)
      REFERENCES sigesp.scb_banco (codemp, codban) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_scb_ctab_scb_tipoc_scb_tipo FOREIGN KEY (codtipcta)
      REFERENCES sigesp.scb_tipocuenta (codtipcta) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_scb_ctabanco_fuente FOREIGN KEY (codemp, codfuefin)
      REFERENCES sigesp.sigesp_fuentefinanciamiento (codemp, codfuefin) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_scb_ctabanco_contab FOREIGN KEY (sc_cuenta)
      REFERENCES sigesp.scg_cuentas (sc_cuenta) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE scb_ctabanco OWNER TO sigesp;

INSERT INTO administracion.tbl_dem_tipodocumentolegal(
            str_descripcion, bol_tipfact, bol_haber)
    VALUES ('Factura', true, false);
    INSERT INTO administracion.tbl_dem_tipodocumentolegal(
            str_descripcion, bol_tipfact, bol_haber)
    VALUES ('Nota Credito', false, true);
    INSERT INTO administracion.tbl_dem_tipodocumentolegal(
            seq_idtipfac, str_descripcion, bol_tipfact, bol_haber)
    VALUES ('Nota Debito', false, false);
 

ALTER TABLE administracion.tbl_dem_documentofiscal 
 add int_idtipodocumento integer NOT NULL,
 add CONSTRAINT fk_tbl_dem_documentoimpuesto_tipo FOREIGN KEY (int_idtipodocumento)
      REFERENCES administracion.tbl_dem_tipodocumentolegal (seq_idtipfac) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
    
 
CREATE TABLE administracion.tbl_dem_nota
(
  int_iddocumento integer NOT NULL,
  int_idfactura integer NOT NULL,
  CONSTRAINT pk_tbl_dem_nota PRIMARY KEY (int_iddocumento),
  CONSTRAINT fk_tbl_dem_nota_documento FOREIGN KEY (int_iddocumento)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT fk_tbl_dem_nota_factura FOREIGN KEY (int_idfactura)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION

)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_nota OWNER TO sigesp;


 ALTER TABLE administracion.tbl_dem_documentofiscal 
 add int_nrodocumento integer


CREATE TABLE administracion.tbl_dem_libroventa
(
  seq_idlibvent serial NOT NULL,
  mes integer not null,
  ano integer not null,
  int_iddocumento integer not null,
  CONSTRAINT pk_tbl_dem_tbl_dem_libroventa PRIMARY KEY (seq_idlibvent),
   CONSTRAINT fk_tbl_dem_libroventa_documento FOREIGN KEY (int_iddocumento)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_libroventa OWNER TO demeter_sa;

ALTER TABLE administracion.tbl_dem_estadodocumentofiscal 
add bol_anulada boolean;


ALTER TABLE administracion.tbl_dem_control_sede
  add int_factura integer,
  add int_controlnotadebito integer,
  add int_controlnotacredito integer;
  
  alter TABLE administracion.tbl_dem_tipo_forma_pago add bol_nota boolean default false;

  INSERT INTO administracion.tbl_dem_tipo_forma_pago(
             str_descripcion, bol_usadocu, bol_usacuen, 
            bol_usaban, bol_nota)
    VALUES ('Nota Credito', true, false, false, true);
    
    
CREATE TABLE administracion.tbl_dem_notapago
(
  seq_idpago bigserial NOT NULL,
  int_idnota integer NOT NULL,
  int_idfactura integer NOT NULL,
  dbl_monto numeric(12,4),
  dtm_fecha date,
  str_codemp character(4) DEFAULT '0001'::bpchar,
  str_sede character(4),
  str_concepto character varying(30),
  bol_anulado boolean,
  int_formapago bigint,
  CONSTRAINT pk_tbl_dem_notapago PRIMARY KEY (seq_idpago),
  CONSTRAINT fk_tbl_dem_notapago_nota FOREIGN KEY (int_idnota)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_notapago_fact FOREIGN KEY (int_idfactura)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_notapago_pago FOREIGN KEY (int_formapago)
      REFERENCES  administracion.tbl_dem_formapago (seq_idformapago) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
     
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_notapago OWNER TO sigesp;    


drop administracion.viw_pre_libro_ventas

alter table administracion.tbl_dem_documentoimpuesto alter int_monto type numeric(14,2);


CREATE OR REPLACE VIEW administracion.viw_pre_libro_ventas AS 
 SELECT df.seq_iddocumento, df.dtm_fecha, df.int_nrodocumento, df.str_serie, cli.str_cedurif AS cedurif, cli.str_nombre AS nombrerazon, 
        CASE
            WHEN tbl_dem_tipodocumentolegal.bol_tipfact THEN df.int_nrodocumento
            ELSE 0
        END AS nrofactura, 
        CASE
            WHEN NOT tbl_dem_tipodocumentolegal.bol_tipfact AND tbl_dem_tipodocumentolegal.bol_haber THEN df.int_nrodocumento
            ELSE 0
        END AS nrodebito, 
        CASE
            WHEN NOT tbl_dem_tipodocumentolegal.bol_tipfact AND NOT tbl_dem_tipodocumentolegal.bol_haber THEN df.int_nrodocumento
            ELSE 0
        END AS nrocredito, 
        CASE
            WHEN tbl_dem_tipodocumentolegal.bol_tipfact THEN df.int_nrocontrol
            ELSE 0
        END AS nrocontfactura, 
        CASE
            WHEN NOT tbl_dem_tipodocumentolegal.bol_tipfact AND tbl_dem_tipodocumentolegal.bol_haber THEN df.int_nrocontrol
            ELSE 0
        END AS nrocontdebito, 
        CASE
            WHEN NOT tbl_dem_tipodocumentolegal.bol_tipfact AND NOT tbl_dem_tipodocumentolegal.bol_haber THEN df.int_nrocontrol
            ELSE 0
        END AS nrocontcredito, 
        CASE
            WHEN edf.bol_anulada THEN '03-Anu'::text
            ELSE 
            CASE
                WHEN tbl_dem_tipodocumentolegal.bol_tipfact THEN '01-Reg'::text
                ELSE 
                CASE
                    WHEN tbl_dem_tipodocumentolegal.bol_haber THEN '02-Comp'::text
                    ELSE '04-Aju'::text
                END
            END
        END AS tipotrans, factura.int_nrocontrol AS facturaafectada, df.dbl_montototal 
   FROM administracion.tbl_dem_documentofiscal df
   LEFT JOIN administracion.tbl_dem_nota ON tbl_dem_nota.int_iddocumento = df.seq_iddocumento
   LEFT JOIN administracion.tbl_dem_documentofiscal factura ON tbl_dem_nota.int_idfactura = factura.seq_iddocumento
   LEFT JOIN tbl_dem_clientes cli ON df.int_idpagador = cli.seq_idcliente
   JOIN administracion.tbl_dem_tipodocumentolegal ON df.int_idtipodocumento = tbl_dem_tipodocumentolegal.seq_idtipfac
   JOIN administracion.tbl_dem_estadodocumentofiscal edf ON df.int_estado = edf.seq_idestdocfis;
ALTER TABLE administracion.viw_pre_libro_ventas OWNER TO dba;    


DROP TABLE administracion.tbl_dem_deposito;

CREATE TABLE administracion.tbl_dem_deposito
(
  seq_iddeposito serial NOT NULL,
  int_idcuentabanco integer NOT NULL,
  dbl_montoefectivo numeric(12,4),
  dbl_monto numeric(12,4),
  dtm_fecha date,
  CONSTRAINT pk_tbl_dem_deposito PRIMARY KEY (seq_iddeposito),
  CONSTRAINT fk_tbl_dem_deposito_cuenta FOREIGN KEY (int_idcuentabanco)
      REFERENCES sigesp.scb_ctabanco (seq_idcuentabanco) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_deposito OWNER TO sigesp;


CREATE TABLE administracion.tbl_dem_depositocheque
(
  seq_iddetalledeposito bigserial NOT NULL,
  int_iddeposito integer NOT NULL,
  dtm_fecha date,
  dbl_monto numeric(12,2),
  str_idcodemp character(4) DEFAULT '0001'::bpchar,
  str_idcodban character(3) DEFAULT '---'::bpchar,
  str_numcue character(25),
  str_numdoc character(30),
  CONSTRAINT pk_tbl_dem_depositocheque PRIMARY KEY (seq_iddetalledeposito),
  CONSTRAINT fk_tbl_dem_depositocheque_deposito FOREIGN KEY (int_iddeposito)
      REFERENCES administracion.tbl_dem_deposito (seq_iddeposito) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_depositocheque_banco FOREIGN KEY (str_idcodemp, str_idcodban)
      REFERENCES sigesp.scb_banco (codemp,codban) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_depositocheque OWNER TO sigesp;

Alter TABLE administracion.tbl_dem_formapago
alter int_idrecibo drop NOT NULL;

alter table administracion.tbl_dem_control_sede 
add int_controlcontrato integer default 0;

alter TABLE administracion.tbl_dem_deposito add
str_nrodeposito character varying(25);

ALTER TABLE administracion.tbl_dem_documentofiscaldetalle alter int_idservicio drop not null;

ALTER TABLE administracion.tbl_dem_cierrediariodeposito 
ADD str_nrocuenta character(25);

Alter TABLE administracion.tbl_dem_cierrediariocuentacobro
drop CONSTRAINT fk_tbl_dem_cierrediariocuentacobro_cliente,
add CONSTRAINT fk_tbl_dem_cierrediariocuentacobro_cliente FOREIGN KEY (int_idclienteadm)
      REFERENCES administracion.tbl_dem_clienteadministrativo (seq_idclienteadm) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
drop CONSTRAINT fk_tbl_dem_cierrediariocuentacobro; 

Alter TABLE  administracion.tbl_dem_cierrediariocuentapaga
drop CONSTRAINT fk_tbl_dem_cierrediariocuentapaga_cliente,
add CONSTRAINT fk_tbl_dem_cierrediariocuentapaga_cliente FOREIGN KEY (int_idclienteadm)
      REFERENCES administracion.tbl_dem_clienteadministrativo (seq_idclienteadm) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
drop CONSTRAINT fk_tbl_dem_cierrediariocuentapaga; 


alter TABLE administracion.tbl_dem_cierrediariodeposito
drop CONSTRAINT fk_tbl_dem_cierrediariodeposito_cuenta ,
drop CONSTRAINT fk_tbl_dem_cierrediariodeposito,
add CONSTRAINT fk_tbl_dem_cierrediariodeposito_cuenta FOREIGN KEY (str_idcuenta)
      REFERENCES sigesp.scb_ctabanco (seq_idcuentabanco) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
 add CONSTRAINT fk_tbl_dem_cierrediariodeposito UNIQUE (int_idcierre, str_idcuenta, str_nrocuenta);
 
 

CREATE TABLE administracion.tbl_dem_libroventa
(
  seq_idlibvent serial NOT NULL,
  mes integer NOT NULL,
  ano integer NOT NULL,
  dbl_montobase  numeric(12,4),
  dbl_montototal  numeric(12,4),
  CONSTRAINT pk_tbl_dem_tbl_dem_libroventa PRIMARY KEY (seq_idlibvent),
  CONSTRAINT fk_tbl_dem_libroventao UNIQUE (mes, ano)
);


CREATE TABLE administracion.tbl_dem_libroventadetalle
(
  seq_iddetlibvent serial NOT NULL,
  int_idlibvent integer NOT NULL,
  int_iddocumento integer NOT NULL,
  CONSTRAINT pk_tbl_dem_libroventadetalle PRIMARY KEY (seq_iddetlibvent),
  CONSTRAINT fk_tbl_dem_libroventadetalle_documento FOREIGN KEY (int_iddocumento)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_libroventadetalle_libro FOREIGN KEY (int_idlibvent)
      REFERENCES administracion.tbl_dem_libroventa (seq_idlibvent) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



CREATE OR REPLACE VIEW administracion.viw_pre_libro_ventas AS 
 SELECT df.seq_iddocumento, df.dtm_fecha, df.int_nrodocumento, df.str_serie, cli.str_cedurif AS cedurif, cli.str_nombre AS nombrerazon, 
        CASE
            WHEN tbl_dem_tipodocumentolegal.bol_tipfact THEN df.int_nrodocumento
            ELSE 0
        END AS nrofactura, 
        CASE
            WHEN NOT tbl_dem_tipodocumentolegal.bol_tipfact AND tbl_dem_tipodocumentolegal.bol_haber THEN df.int_nrodocumento
            ELSE 0
        END AS nrodebito, 
        CASE
            WHEN NOT tbl_dem_tipodocumentolegal.bol_tipfact AND NOT tbl_dem_tipodocumentolegal.bol_haber THEN df.int_nrodocumento
            ELSE 0
        END AS nrocredito, 
        CASE
            WHEN tbl_dem_tipodocumentolegal.bol_tipfact THEN df.int_nrocontrol
            ELSE 0
        END AS nrocontfactura, 
        CASE
            WHEN NOT tbl_dem_tipodocumentolegal.bol_tipfact AND tbl_dem_tipodocumentolegal.bol_haber THEN df.int_nrocontrol
            ELSE 0
        END AS nrocontdebito, 
        CASE
            WHEN NOT tbl_dem_tipodocumentolegal.bol_tipfact AND NOT tbl_dem_tipodocumentolegal.bol_haber THEN df.int_nrocontrol
            ELSE 0
        END AS nrocontcredito, 
        CASE
            WHEN edf.bol_anulada THEN '03-Anu'::text
            ELSE 
            CASE
                WHEN tbl_dem_tipodocumentolegal.bol_tipfact THEN '01-Reg'::text
                ELSE 
                CASE
                    WHEN tbl_dem_tipodocumentolegal.bol_haber THEN '02-Comp'::text
                    ELSE '04-Aju'::text
                END
            END
        END AS tipotrans, factura.int_nrocontrol AS facturaafectada, df.dbl_montototal
   FROM administracion.tbl_dem_documentofiscal df
   LEFT JOIN administracion.tbl_dem_nota ON tbl_dem_nota.int_iddocumento = df.seq_iddocumento
   LEFT JOIN administracion.tbl_dem_documentofiscal factura ON tbl_dem_nota.int_idfactura = factura.seq_iddocumento
   LEFT JOIN tbl_dem_clientes cli ON df.int_idpagador = cli.seq_idcliente
   JOIN administracion.tbl_dem_tipodocumentolegal ON df.int_idtipodocumento = tbl_dem_tipodocumentolegal.seq_idtipfac
   JOIN administracion.tbl_dem_estadodocumentofiscal edf ON df.int_estado = edf.seq_idestdocfis;

ALTER TABLE administracion.viw_pre_libro_ventas OWNER TO dba;




CREATE OR REPLACE VIEW administracion.viw_pre_libro_ventas_crosstab AS 
 SELECT date_part('month'::text, viw_pre_libro_ventas.dtm_fecha) = tbl_dem_libroventa.mes::double precision AND date_part('year'::text, viw_pre_libro_ventas.dtm_fecha) = tbl_dem_libroventa.ano::double precision AS sies, viw_pre_libro_ventas.seq_iddocumento, viw_pre_libro_ventas.dtm_fecha, viw_pre_libro_ventas.int_nrodocumento, viw_pre_libro_ventas.str_serie, viw_pre_libro_ventas.cedurif, viw_pre_libro_ventas.nombrerazon, viw_pre_libro_ventas.nrofactura, viw_pre_libro_ventas.nrodebito, viw_pre_libro_ventas.nrocredito, viw_pre_libro_ventas.nrocontfactura, viw_pre_libro_ventas.nrocontdebito, viw_pre_libro_ventas.nrocontcredito, viw_pre_libro_ventas.tipotrans, viw_pre_libro_ventas.facturaafectada, viw_pre_libro_ventas.dbl_montototal, base.idenla, base.base0, base.base2, porcen.idenla1, porcen.porcentaje0, porcen.porcentaje2, impue.idenla2, impue.impuesto0, impue.impuesto2
   FROM administracion.tbl_dem_libroventa
   JOIN administracion.tbl_dem_libroventadetalle ON int_idlibvent = seq_idlibvent
   JOIN administracion.viw_pre_libro_ventas ON tbl_dem_libroventadetalle.int_iddocumento = viw_pre_libro_ventas.seq_iddocumento
   JOIN crosstab('select int_iddocumento, int_idtipoimpuesto, dbl_base from administracion.tbl_dem_documentoimpuesto order by int_iddocumento, int_idtipoimpuesto'::text) base(idenla integer, base0 numeric(14,2), base2 numeric(14,2)) ON base.idenla = viw_pre_libro_ventas.seq_iddocumento
   JOIN crosstab('select int_iddocumento, int_idtipoimpuesto, dbl_pporcentaje from administracion.tbl_dem_documentoimpuesto order by int_iddocumento, int_idtipoimpuesto'::text) porcen(idenla1 integer, porcentaje0 numeric(14,2), porcentaje2 numeric(14,2)) ON porcen.idenla1 = viw_pre_libro_ventas.seq_iddocumento
   JOIN crosstab('select int_iddocumento, int_idtipoimpuesto, int_monto from administracion.tbl_dem_documentoimpuesto order by int_iddocumento, int_idtipoimpuesto'::text) impue(idenla2 integer, impuesto0 numeric(14,2), impuesto2 numeric(14,2)) ON impue.idenla2 = viw_pre_libro_ventas.seq_iddocumento;

ALTER TABLE administracion.viw_pre_libro_ventas_crosstab OWNER TO sigesp;

ALTER TABLE administracion.tbl_dem_libroventa add
int_cantdocu integer;


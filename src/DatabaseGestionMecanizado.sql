- Table: public.tbl_dem_trabajadores

-- DROP TABLE public.tbl_dem_trabajadores;
-- Table: public.tbl_dem_trabajadores

-- DROP TABLE public.tbl_dem_trabajadores;

CREATE TABLE basico.tbl_dem_cicloproductivo
(
  seq_idcicloproductivo serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_cicloproductivo PRIMARY KEY (seq_idcicloproductivo)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_cicloproductivo OWNER TO sigesp;

CREATE TABLE basico.tbl_dem_sectoragricola
(
  seq_idsectoragricola serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_sectoragricola PRIMARY KEY (seq_idsectoragricola)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_sectoragricola OWNER TO sigesp;


CREATE TABLE basico.tbl_dem_tiporubro
(
  seq_idtiporubro integer NOT NULL,
  int_idsectoragricola integer,
  str_descripcion character varying(80),
  CONSTRAINT pk_tbl_dem_tiporubro PRIMARY KEY (seq_idtiporubro),
  CONSTRAINT fk_tbl_dem_tiporubro_sectoragricola FOREIGN KEY (int_idsectoragricola)
      REFERENCES basico.tbl_dem_sectoragricola (seq_idsectoragricola) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_tiporubro OWNER TO sigesp;

CREATE TABLE basico.tbl_dem_rubro
(
  seq_idrubro integer NOT NULL,
  int_idtipo integer,
  str_descripcion character varying(100),
  bol_activo boolean DEFAULT true,
  CONSTRAINT pk_tbl_dem_rubro PRIMARY KEY (seq_idrubro),
  CONSTRAINT fk_tbl_dem_rubro_tipo FOREIGN KEY (int_idtipo)
      REFERENCES  basico.tbl_dem_tiporubro  (seq_idtiporubro) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_rubro OWNER TO sigesp;

INSERT INTO basico.tbl_dem_sectoragricola(str_descripcion)
values('Vegetal');
INSERT INTO basico.tbl_dem_sectoragricola(str_descripcion)
values('Animal');
INSERT INTO basico.tbl_dem_sectoragricola(str_descripcion)
values('Forestal');
INSERT INTO basico.tbl_dem_sectoragricola(str_descripcion)
values('Pesquero');

INSERT INTO basico.tbl_dem_tiporubro(seq_idtiporubro, int_idsectoragricola, str_descripcion)
SELECT seq_idrenglon, int_idexp, str_descripcion
  FROM public.tbl_dem_renglon;

INSERT INTO  basico.tbl_dem_rubro(seq_idrubro, int_idtipo, str_descripcion, bol_activo)
SELECT seq_idrubro, int_idrenglon, str_descripcion, true
  FROM public.tbl_dem_rubros;


CREATE TABLE basico.tbl_dem_trabajador
(
  seq_idtrabajador serial NOT NULL, 
  int_tipo integer,
  str_cedula character varying(10) NOT NULL,
  str_nombres character varying(70),
  str_apellidos character varying(70),
  str_direccion character varying(254),
  str_telfijo character varying(15),
  str_telmovil character varying(15),
  CONSTRAINT pk_tbl_dem_trabajador PRIMARY KEY (seq_idtrabajador),
  CONSTRAINT fk_tbl_dem_trabajador_tipo FOREIGN KEY (int_tipo)
      REFERENCES public.tbl_dem_tipo_trabajador (seq_ser_tipotraba) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT unk_cedula_tipo UNIQUE (str_cedula, int_tipo)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_trabajador OWNER TO demeter_sa;


CREATE TABLE basico.tbl_dem_plan
(
  seq_idplan serial NOT NULL,
  str_nombre character varying(100),
  dtm_desde date,
  dtm_hasta date,
  bol_activo boolean,
  int_tipoplan integer,
  CONSTRAINT pk_plan PRIMARY KEY (seq_idplan),
  CONSTRAINT fk_tbl_dem_plan_tipo FOREIGN KEY (int_tipoplan)
      REFERENCES public.tbl_dem_tipoplan (seq_idtipoplan) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_plan OWNER TO demeter_sa;


CREATE TABLE gestion.tbl_dem_solicitud
(
  seq_idsolicitud bigserial NOT NULL,
  str_codemp character(4) DEFAULT '0001'::bpchar,
  str_codSed character(4) NOT NULL,
  int_idunidadejecutora integer,
  dtm_fecha date,
  str_nrocon character(10) NOT NULL,
  int_idcliente integer NOT NULL,
  int_tipsol integer NOT NULL,
  bol_solicitudprestad boolean NOT NULL DEFAULT false,
  str_observacion character varying,
  bol_activo boolean NOT NULL DEFAULT true,
  int_idplan integer,
  bol_planificada boolean DEFAULT false,
  int_idtrabajador integer,
  CONSTRAINT tbl_dem_solicitud_pkey PRIMARY KEY (seq_idsolicitud),
  CONSTRAINT fk_tbl_dem_solicitud_plan FOREIGN KEY (int_idplan)
      REFERENCES public.tbl_dem_plan (seq_idplan) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitud_sede FOREIGN KEY (str_codemp,str_codSed)
      REFERENCES sigesp.sno_ubicacion_fisica (codemp,codubifis) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitud_tipos FOREIGN KEY (int_tipsol)
      REFERENCES public.tbl_dem_tipo_solicitud (seq_idtiposolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitud_productor FOREIGN KEY (int_idcliente)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitud_unidadejecutora FOREIGN KEY (int_idunidadejecutora)
      REFERENCES ministerio.tbl_dem_unidadfuncional (seq_idunidadfuncional) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitud_trabajador FOREIGN KEY (int_idtrabajador)
      REFERENCES  basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION      
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_solicitud OWNER TO demeter_sa;


CREATE TABLE gestion.tbl_dem_solicitudmepubliccanizado
(
  int_idsolicitud bigint NOT NULL,
  int_idciclo integer,
  int_idrubro integer,
  int_idunidadproduccion integer,
  int_idservicio integer,
  CONSTRAINT pk_tbl_dem_solicitudmecanizado_pkey PRIMARY KEY (int_idsolicitud),
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_ciclo FOREIGN KEY (int_idciclo)
      REFERENCES basico.tbl_dem_cicloproductivo (seq_idcicloproductivo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_servicio FOREIGN KEY (int_idservicio)
      REFERENCES basico.tbl_dem_servicio (seq_idservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_rubro FOREIGN KEY (int_idrubro)
      REFERENCES basico.tbl_dem_rubro (seq_idrubro) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_solicitud FOREIGN KEY (int_idsolicitud)
      REFERENCES gestion.tbl_dem_solicitud (seq_idsolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_unidadproduccion FOREIGN KEY (int_idunidadproduccion)
      REFERENCES ministerio.tbl_dem_unidadproductiva (seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_solicitudmecanizado OWNER TO demeter_sa;


CREATE TABLE gestion.tbl_dem_solicitudmecanizado
(
  int_idsolicitud bigint NOT NULL,public
  int_idciclo integer,
  int_idrubro integer,
  int_idunidadproduccion integer,
  int_idservicio integer,
  CONSTRAINT pk_tbl_dem_solicitudmecanizado_pkey PRIMARY KEY (int_idsolicitud),
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_ciclo FOREIGN KEY (int_idciclo)
      REFERENCES basico.tbl_dem_cicloproductivo (seq_idcicloproductivo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_servicio FOREIGN KEY (int_idservicio)
      REFERENCES basico.tbl_dem_servicio (seq_idservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_rubro FOREIGN KEY (int_idrubro)
      REFERENCES basico.tbl_dem_rubro (seq_idrubro) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_solicitud FOREIGN KEY (int_idsolicitud)
      REFERENCES gestion.tbl_dem_solicitud (seq_idsolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicitudmecanizado_unidadproduccion FOREIGN KEY (int_idunidadproduccion)
      REFERENCES ministerio.tbl_dem_unidadproductiva (seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_solicitudmecanizado OWNER TO demeter_sa;


CREATE TABLE gestion.tbl_dem_solicituddetalle
(
  seq_idrenglon bigserial NOT NULL,
  int_idsolicitud bigint NOT NULL,
  int_idproducto integer,
  dbl_cantidad numeric(10,2) default 1.00,
  dbl_pase numeric(10,2) default 1.00,
  CONSTRAINT pk_tbl_dem_solicituddetalle PRIMARY KEY (seq_idrenglon),
  CONSTRAINT fk_tbl_dem_solicituddetalle_prodcuto FOREIGN KEY (int_idproducto)
      REFERENCES basico.tbl_dem_producto (seq_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicituddetalle_solicitud FOREIGN KEY (int_idsolicitud)
      REFERENCES gestion.tbl_dem_solicitud (seq_idsolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_solicituddetalle OWNER TO demeter_sa;



CREATE TABLE gestion.tbl_dem_solicituddetalleunidad
(
  seq_idrenglonunidad bigserial NOT NULL,
  int_idrenglon bigint NOT NULL,
  int_idunidad integer,
  dbl_cantidad numeric(10,2),
  CONSTRAINT pk_tbl_dem_solicituddetalleunidad PRIMARY KEY (seq_idrenglonunidad),
  CONSTRAINT fk_tbl_dem_solicituddetalleunidad_unidad FOREIGN KEY (int_idunidad)
      REFERENCES public.tbl_dem_unidad_medidas (seq_idumedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_solicituddetalleunidad_detallesolicitud FOREIGN KEY (int_idrenglon)
      REFERENCES gestion.tbl_dem_solicituddetalle (seq_idrenglon) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_solicituddetalleunidad OWNER TO demeter_sa;





ALTER TABLE public.tbl_dem_tipo_servicio
ADD  bol_mecanizado boolean DEFAULT true,
ADD  bol_transporte boolean DEFAULT true,
ADD  bol_transcosecha boolean DEFAULT true,
ADD  bol_lineaamarilla boolean DEFAULT true;


CREATE TABLE gestion.tbl_dem_control_unidad
(
  seq_control serial NOT NULL,
  int_unidad integer NOT NULL,
  str_serie character varying(2) NOT NULL,
  ste_fechagestion date,
  int_control_solicitud integer,
  int_control_seguimiento integer,
  CONSTRAINT pk_tbl_dem_control_unidad PRIMARY KEY (seq_control),
  CONSTRAINT fk_tbl_dem_control_unidad_unidad FOREIGN KEY (int_unidad)
      REFERENCES ministerio.tbl_dem_unidadfuncional (seq_idunidadfuncional) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_control_unidad OWNER TO sigesp;

CREATE TABLE basico.tbl_dem_tipotrabajo
(
  seq_idtipotrabajo serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipotrabajo PRIMARY KEY (seq_idtipotrabajo)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_tipotrabajo OWNER TO sigesp;


ALTER TABLE basico.tbl_dem_tipotrabajo
ADD int_idtiposolictud integer NOT NULL;

ALTER TABLE basico.tbl_dem_tipotrabajo
ADD CONSTRAINT fk_tbl_dem_tipotrabajo_solicitud FOREIGN KEY (int_idtiposolictud)
      REFERENCES tbl_dem_tipo_solicitud (seq_idtiposolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
ADD CONSTRAINT fk_tbl_dem_tipotrabajo_unico UNIQUE (int_idtiposolictud);

ALTER TABLE basico.tbl_dem_servicio
ADD bol_produccion boolean default false,
ADD seq_idtipounidadproduccion integer;

ALTER TABLE basico.tbl_dem_servicio
ADD CONSTRAINT fk_tbl_dem_servicio_tipoproduccion FOREIGN KEY (seq_idtipounidadproduccion)
      REFERENCES public.tbl_dem_tipo_unidad_medida (seq_idtipounidadmedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      

DROP TRIGGER tg_dem_inserta_orden_servicio on gestion.tbl_dem_orden_servicio;

DROP TABLE gestion.tbl_dem_orden_servicio  CASCADE;
DROP TABLE  gestion.tbl_dem_orden_servicio_mecanizado CASCADE;

CREATE TABLE  gestion.tbl_dem_orden_servicio
(
  seq_idordenservicio bigserial NOT NULL,
  str_codemp character(4) NOT NULL,
  str_idsede character(4) NOT NULL,
  
  int_unidadfuncional integer not null, 
  dtm_fecha date,
  int_idtipotrabajo integer not null,
  str_nrocon character(15) NOT NULL,
  int_idtrabajador integer NOT NULL,
  int_idestadoorden integer NOT NULL,
  str_observacion character varying,
  
  str_codempmaq character(4) NOT NULL,
  str_idactivo character(15) NOT NULL,
  str_idejemplar character(15) NOT NULL,
  int_idtipomedidarendimiento integer,
  CONSTRAINT tbl_dem_orden_servicio_pkey PRIMARY KEY (seq_idordenservicio),
  CONSTRAINT fk_tbl_dem_orden_servicio_medida FOREIGN KEY (int_idtipomedidarendimiento)
      REFERENCES bien_produccion.tbl_dem_tipomedidarendimiento (seq_idtipomedidarendimiento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_servicio_unidadfuncional FOREIGN KEY (int_unidadfuncional)
      REFERENCES ministerio.tbl_dem_unidadfuncional (seq_idunidadfuncional) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,    
  CONSTRAINT fk_tbl_dem_orden_servicio_sede FOREIGN KEY (str_codemp, str_idsede)
      REFERENCES sigesp.sno_ubicacion_fisica (codemp,codubifis) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,    
  CONSTRAINT fk_tbl_dem_orden_servicio_operador FOREIGN KEY (int_idtrabajador)
      REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_servicio_maquinaria FOREIGN KEY (str_codempmaq,str_idactivo, str_idejemplar)
      REFERENCES sigesp.saf_dta (codemp, codact, ideact) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_orden_servicio OWNER TO sigesp;

ALTER TABLE  gestion.tbl_dem_orden_servicio
ADD int_idsolicitud integer;

ALTER TABLE  gestion.tbl_dem_orden_servicio
ADD  CONSTRAINT fk_tbl_dem_orden_servicio_solicitud FOREIGN KEY (int_idsolicitud)
      REFERENCES gestion.tbl_dem_solicitud (seq_idsolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


CREATE TABLE  gestion.tbl_dem_orden_servicio_mecanizado
(
  int_idordenservicio bigint NOT NULL,
  int_idrubro integer, 
  str_codempmaq character(4),
  str_idactivo character(15),
  str_idejemplar character(15),
  int_idcicloproductivo integer, 
  int_idunidadproductiva integer, 
  int_idproductor integer, 
  dte_inicio date, 
  int_diasespera integer,
  int_idcontrato integer,
  CONSTRAINT pk_tbl_dem_orden_servicio_mecanizado PRIMARY KEY (int_idordenservicio),
  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_ordentrabajo FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio(seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_rubro FOREIGN KEY (int_idrubro)
      REFERENCES basico.tbl_dem_rubro(seq_idrubro) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_ciclo FOREIGN KEY (int_idcicloproductivo)
      REFERENCES basico.tbl_dem_cicloproductivo(seq_idcicloproductivo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_productor FOREIGN KEY (int_idproductor)
      REFERENCES basico.tbl_dem_producto(seq_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,      
  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_unidadproductiva FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_unidadproductiva(seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_implemento FOREIGN KEY (str_codempmaq,str_idactivo, str_idejemplar)
      REFERENCES sigesp.saf_dta (codemp, codact, ideact) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION)
WITHOUT OIDS;
ALTER TABLE  gestion.tbl_dem_orden_servicio_mecanizado OWNER TO sigesp;
  



CREATE TABLE  gestion.tbl_dem_detalle_orden_servicio
(
  seq_iddetalleoreden bigserial not null, 
  int_idordenservicio bigint NOT NULL,
  int_idlabor integer, 
  int_idunidadmedida integer,
  dbl_cantidadsolicitada numeric(10,2),
  dbl_cantidadejecutada numeric(10,2),
  CONSTRAINT pk_tbl_dem_detalle_orden_servicio PRIMARY KEY (seq_iddetalleoreden),
  CONSTRAINT fk_tbl_dem_detalle_orden_servicio_ordentrabajo FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio(seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      
  CONSTRAINT fk_tbl_dem_detalle_orden_servicio_labor FOREIGN KEY (int_idlabor)
      REFERENCES  basico.tbl_dem_labor(int_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_detalle_orden_servicio_unidad FOREIGN KEY (int_idunidadmedida)
      REFERENCES public.tbl_dem_unidad_medidas(seq_idumedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION)
WITHOUT OIDS;
ALTER TABLE  gestion.tbl_dem_detalle_orden_servicio OWNER TO sigesp;      


CREATE TABLE  gestion.tbl_dem_trabajo_orden_servicio
(
  seq_idtrabajoorden bigserial not null, 
  dte_fecha date, 
  int_idlabor integer, 
  tm_inicio time,
  tm_fin time,
  CONSTRAINT pk_tbl_dem_trabajo_orden_servicio PRIMARY KEY (seq_idtrabajoorden),
  CONSTRAINT fk_tbl_dem_trabajo_orden_servicio_labor FOREIGN KEY (int_idlabor)
      REFERENCES  basico.tbl_dem_labor(int_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION)
WITHOUT OIDS;
ALTER TABLE  gestion.tbl_dem_trabajo_orden_servicio OWNER TO sigesp;     



CREATE TABLE  gestion.tbl_dem_trabajo_orden_servicio_mecanizado
(
  int_idtrabajoorden bigint not null, 
  int_idordenservicio bigint NOT NULL,
  dbl_horometroinicio numeric(10,2), 
  dbl_horometrofinal numeric(10,2), 
  dbl_cantidadtrabajo numeric(10,2), 
  dbl_cantidadproduccion numeric(10,2),
  int_idunidadtrabajo integer not null,
  int_idunidadproduccion integer,
  
  CONSTRAINT pk_tbl_dem_trabajo_orden_servicio_mecanizado PRIMARY KEY (int_idtrabajoorden),
  CONSTRAINT fk_tbl_dem_trabajo_orden_servicio_mecanizado_trabajoorden FOREIGN KEY (int_idtrabajoorden)
      REFERENCES gestion.tbl_dem_trabajo_orden_servicio(seq_idtrabajoorden) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_trabajo_orden_servicio_mecanizado_ordentrabajo FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio(seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_trabajo_orden_servicio_mecanizado_unidadtrabajo FOREIGN KEY (int_idunidadtrabajo)
      REFERENCES public.tbl_dem_unidad_medidas(seq_idumedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,    
  CONSTRAINT fk_tbl_dem_trabajo_orden_servicio_mecanizado_unidadproduccion FOREIGN KEY (int_idunidadproduccion)
      REFERENCES public.tbl_dem_unidad_medidas(seq_idumedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION)
WITHOUT OIDS;
ALTER TABLE  gestion.tbl_dem_trabajo_orden_servicio_mecanizado OWNER TO sigesp;       



ALTER TABLE  gestion.tbl_dem_orden_servicio
drop   int_idtipomedidarendimiento;


ALTER TABLE  gestion.tbl_dem_orden_servicio
ADD bol_cerrada boolean;


ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
  ADD int_idplan integer;

ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado  
  ADD CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_plan FOREIGN KEY (int_idplan)
      REFERENCES basico.tbl_dem_plan (seq_idplan) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
  
      

ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
DROP CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_implemento,
DROP str_codempmaq,
DROP str_idactivo,
DROP str_idejemplar;

ALTER TABLE gestion.tbl_dem_orden_servicio
DROP CONSTRAINT fk_tbl_dem_orden_servicio_maquinaria,
DROP str_codempmaq,
DROP str_idactivo,
DROP str_idejemplar;


ALTER TABLE gestion.tbl_dem_orden_servicio
DROP CONSTRAINT fk_tbl_dem_orden_servicio_operador,
DROP int_idtrabajador;




  
CREATE TABLE gestion.tbl_dem_detalle_maquinaria_orden_trabajo
(
  seq_idmaquinaorden bigserial not null,	
  int_idordenservicio bigint NOT NULL,
  int_idoperador integer NOT NULL,
  str_codempmaq character(4),
  str_idactivomaq character(15),
  str_idejemplarmaq character(15),
  str_codempimp character(4),
  str_idactivoimp character(15),
  str_idejemplaimp character(15),
  CONSTRAINT pk_tbl_dem_detalle_maquinaria_orden_trabajo PRIMARY KEY (seq_idmaquinaorden),  
  CONSTRAINT fk_tbl_dem_detalle_maquinaria_orden_trabajo_ordentrabajo FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio (seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_detalle_maquinaria_orden_trabajo_maquinaria FOREIGN KEY (str_codempmaq, str_idactivomaq, str_idejemplarmaq)
      REFERENCES saf_dta (codemp, codact, ideact) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_detalle_maquinaria_orden_trabajo_implemento FOREIGN KEY (str_codempimp, str_idactivoimp, str_idejemplaimp)
      REFERENCES saf_dta (codemp, codact, ideact) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_detalle_maquinaria_orden_trabajo_operador FOREIGN KEY (int_idoperador)
      REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)    
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_detalle_maquinaria_orden_trabajo OWNER TO sigesp;
      


ALTER TABLE gestion.tbl_dem_trabajo_orden_servicio_mecanizado
ADD dbl_horometroefectivoinicio numeric(10,2),
ADD dbl_horometroefectivofinal numeric(10,2);


CREATE TABLE gestion.tbl_dem_labor_orden_servicio_mecanizado
(
  seq_idlaborordenmecanizado bigserial NOT NULL,
  int_idordenservicio bigint NOT NULL,
  int_idlabor integer,
  int_idunidad integer,
  dbl_fisico numeric(10,2),
  dbl_pase numeric(10,2),
  dbl_cantidad numeric(10,2),
  CONSTRAINT pk_tbl_dem_labor_orden_servicio_mecanizado PRIMARY KEY (seq_idlaborordenmecanizado),
  CONSTRAINT fk_tbl_dem_labor_orden_servicio_mecanizado_labor FOREIGN KEY (int_idlabor)
      REFERENCES basico.tbl_dem_labor (int_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_labor_orden_servicio_mecanizado_orden FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio (seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_labor_orden_servicio_mecanizado_unidad FOREIGN KEY (int_idunidad)
      REFERENCES public.tbl_dem_unidad_medidas (seq_idumedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_labor_orden_servicio_mecanizado OWNER TO sigesp;

ALTER TABLE gestion.tbl_dem_labor_orden_servicio_mecanizado
ADD CONSTRAINT fk_tbl_dem_labor_orden_servicio_mecanizado_ordenunica unique (int_idordenservicio);

ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
ADD int_idfinanciamiento integer;


ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
ADD CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_financiamiento FOREIGN KEY (int_idfinanciamiento)
      REFERENCES ministerio.tbl_dem_institucionfinanciera (seq_idinstitucionf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE gestion.tbl_dem_estadoordentrabajo
ADD bol_detenido boolean;      
      

ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
DROP  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_productor;

ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
ADD  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_productor FOREIGN KEY (int_idproductor)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      

ALTER TABLE gestion.tbl_dem_trabajo_orden_servicio_mecanizado
  DROP int_idordenservicio,
  DROP dbl_horometroinicio,
  DROP dbl_horometrofinal,
  DROP dbl_horometroefectivoinicio,
  DROP dbl_horometroefectivofinal;
  

CREATE TABLE gestion.tbl_dem_maquinaria_unidad
(
  seq_idmaquinaria serial NOT NULL,
  str_codempmaq character(4),
  str_idactivomaq character(15),
  str_idejemplarmaq character(15),
  int_unidadfuncional integer NOT NULL,
  bol_activo boolean not null,
  CONSTRAINT pk_tbl_dem_maquinaria_unidad PRIMARY KEY (seq_idmaquinaria),
  CONSTRAINT fk_tbl_dem_maquinaria_unidad_maquinaria FOREIGN KEY (str_codempmaq, str_idactivomaq, str_idejemplarmaq)
      REFERENCES saf_dta (codemp, codact, ideact) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_maquinaria_unidad_unidad FOREIGN KEY (int_unidadfuncional)
      REFERENCES ministerio.tbl_dem_unidadfuncional (seq_idunidadfuncional) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_maquinaria_unidad_unico UNIQUE (str_codempmaq, str_idactivomaq, str_idejemplarmaq, int_unidadfuncional)       
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_maquinaria_unidad OWNER TO sigesp;



CREATE TABLE gestion.tbl_dem_implemento_unidad
(
  seq_idimplemento serial NOT NULL,
  str_codempmaq character(4),
  str_idactivomaq character(15),
  str_idejemplarmaq character(15),
  int_unidadfuncional integer NOT NULL,
  bol_activo boolean not null,
  CONSTRAINT pk_tbl_dem_implemento_unidad PRIMARY KEY (seq_idimplemento),
  CONSTRAINT fk_tbl_dem_implemento_unidad_maquinaria FOREIGN KEY (str_codempmaq, str_idactivomaq, str_idejemplarmaq)
      REFERENCES saf_dta (codemp, codact, ideact) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_implemento_unidad_unidad FOREIGN KEY (int_unidadfuncional)
      REFERENCES ministerio.tbl_dem_unidadfuncional (seq_idunidadfuncional) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_implemento_unidad_unico UNIQUE (str_codempmaq, str_idactivomaq, str_idejemplarmaq, int_unidadfuncional)   
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_implemento_unidad OWNER TO sigesp;



CREATE TABLE gestion.tbl_dem_detalle_trabajo_mecanizado
(
  seq_iddetalletrabajo bigserial NOT NULL,
  int_idtrabajoorden bigint NOT NULL,
  int_idmaquinaria integer NOT NULL,	
  int_idimplemento integer,
  int_idoperador integer, 
  dbl_horometroinicio numeric(10,2),
  dbl_horometrofinal numeric(10,2),
  dbl_horometroefectivoinicio numeric(10,2),
  dbl_horometroefectivofinal numeric(10,2),
  CONSTRAINT pk_tbl_dem_detalle_trabajo_mecanizado PRIMARY KEY (seq_iddetalletrabajo),
  CONSTRAINT fk_tbl_dem_trabajo_orden_servicio_mecanizado_trabajoorden FOREIGN KEY (int_idtrabajoorden)
      REFERENCES gestion.tbl_dem_trabajo_orden_servicio (seq_idtrabajoorden) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_detalle_trabajo_mecanizado_maquinaria FOREIGN KEY (int_idmaquinaria)
      REFERENCES gestion.tbl_dem_maquinaria_unidad (seq_idmaquinaria) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_detalle_trabajo_mecanizado_implemento FOREIGN KEY (int_idimplemento)
      REFERENCES gestion.tbl_dem_implemento_unidad (seq_idimplemento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_detalle_trabajo_mecanizado_trabajador FOREIGN KEY (int_idoperador)
      REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION    
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_detalle_trabajo_mecanizado OWNER TO sigesp;


CREATE TABLE basico.tbl_dem_tipodocumentotierra
(
  seq_idtipodocumento serial NOT NULL,
  str_descripcion character varying(80),
  bol_provicional boolean,
  CONSTRAINT pk_tbl_dem_tipodocumentotierra PRIMARY KEY (seq_idtipodocumento)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_tipodocumentotierra OWNER TO sigesp;

ALTER TABLE ministerio.tbl_dem_direccion
ADD int_idtipodocumentotierra integer;
ALTER TABLE ministerio.tbl_dem_direccion  
ADD  CONSTRAINT fk_tbl_dem_direccion_tipodocumento FOREIGN KEY (int_idtipodocumentotierra)
      REFERENCES basico.tbl_dem_tipodocumentotierra (seq_idtipodocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
      
INSERT INTO gestion.tbl_dem_implemento_unidad(str_codempmaq,
  str_idactivomaq, str_idejemplarmaq, int_unidadfuncional, bol_activo) 

SELECT codemp, codact, ideact , 4 as unidad, true
  FROM saf_dta
  INNER JOIN saf_activo USING(codemp, codact) 
  INNER JOIN siv_categoria ON codcat = str_idcategoria
WHERE bol_esimplemento = true  ;
  


INSERT INTO gestion.tbl_dem_maquinaria_unidad(str_codempmaq,
  str_idactivomaq, str_idejemplarmaq, int_unidadfuncional, bol_activo) 

SELECT codemp, codact, ideact , 4 as unidad, true
  FROM saf_dta
  INNER JOIN saf_activo USING(codemp, codact) 
  INNER JOIN siv_categoria ON codcat = str_idcategoria
WHERE bol_esimplemento = false ;       




ALTER TABLE gestion.tbl_dem_detalle_maquinaria_orden_trabajo
DROP str_codempmaq,
DROP str_idactivomaq,
DROP str_idejemplarmaq,
DROP str_codempimp,
DROP str_idactivoimp,
DROP str_idejemplaimp,
ADD int_idmaquinaria integer,
ADD int_idimplemento integer;


ALTER TABLE gestion.tbl_dem_detalle_maquinaria_orden_trabajo
ADD  CONSTRAINT fk_tbl_dem_detalle_maquinaria_orden_trabajo_implemento FOREIGN KEY (int_idimplemento)
      REFERENCES gestion.tbl_dem_implemento_unidad (seq_idimplemento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
ADD  CONSTRAINT fk_tbl_dem_detalle_maquinaria_orden_trabajo_maquinaria FOREIGN KEY (int_idmaquinaria)
      REFERENCES gestion.tbl_dem_maquinaria_unidad (seq_idmaquinaria) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      

ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
ADD  int_idtecnico integer,
ADD  bol_produccion boolean,
ADD  bol_actaproduccion boolean;



ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
ADD  CONSTRAINT fk_tbl_dem_orden_servicio_mecanizado_tecnico FOREIGN KEY (int_idtecnico)
      REFERENCES basico.tbl_dem_trabajador(seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;      
      
ALTER TABLE gestion.tbl_dem_trabajo_orden_servicio_mecanizado
ADD  int_idsupervisor integer;



ALTER TABLE gestion.tbl_dem_trabajo_orden_servicio_mecanizado
ADD  CONSTRAINT fk_tbl_dem_trabajo_orden_servicio_mecanizado_supervisor FOREIGN KEY (int_idsupervisor)
      REFERENCES basico.tbl_dem_trabajador(seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;     


ALTER TABLE gestion.tbl_dem_orden_servicio_mecanizado
	ADD  bol_transportado boolean,
	ADD  dbl_produccionreal numeric(10,2),
	ADD  dbl_trabajofisico numeric(10,2);


CREATE TABLE basico.tbl_dem_tiposilo
(
  seq_idtiposilo serial NOT NULL,
  str_descripcion character varying(100),
  bol_activo boolean DEFAULT true,
  CONSTRAINT pk_tbl_dem_tiposilo PRIMARY KEY (seq_idtiposilo)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_tiposilo OWNER TO sigesp;



CREATE TABLE basico.tbl_dem_silo
(
  seq_idsilo serial NOT NULL,
  int_idtipo integer,
  str_descripcion character varying(150),
  int_iddireccion integer,
  bol_publico boolean DEFAULT true,
  CONSTRAINT pk_tbl_dem_silo PRIMARY KEY (seq_idsilo),
  CONSTRAINT fk_tbl_dem_silo_tipo FOREIGN KEY (int_idtipo)
      REFERENCES basico.tbl_dem_tiposilo (seq_idtiposilo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_silo_direccion FOREIGN kEY (int_iddireccion)
      REFERENCES  ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_silo OWNER TO sigesp;	


CREATE TABLE gestion.tbl_dem_orden_transporte_produccion
(
  int_idordenservicio bigint NOT NULL,
  int_idrubro integer,
  int_idunidadproductiva integer,
  int_idproductor integer,
  int_idsilo integer,
  dte_inicio date,
  int_diasespera numeric(10,2),
  int_idconductor integer,
  dbl_produccionreal numeric(10,2),
  dbl_totalrecorrido numeric(10,2),
  int_idordenproduccion integer,
  CONSTRAINT pk_tbl_dem_orden_transporte_produccion PRIMARY KEY (int_idordenservicio),
  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_ordentrabajo FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio (seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_ordenservicio FOREIGN KEY (int_idordenproduccion)
      REFERENCES gestion.tbl_dem_orden_servicio_mecanizado (int_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,    
  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_silo FOREIGN KEY (int_idsilo)
      REFERENCES basico.tbl_dem_silo (seq_idsilo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_productor FOREIGN KEY (int_idproductor)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_rubro FOREIGN KEY (int_idrubro)
      REFERENCES basico.tbl_dem_rubro (seq_idrubro) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_conductor FOREIGN KEY (int_idconductor)
      REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_unidadproductiva FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_unidadproductiva (seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_orden_transporte_produccion OWNER TO sigesp;


ALTER TABLE gestion.tbl_dem_orden_transporte_produccion
DROP CONSTRAINT fk_tbl_dem_orden_transporte_produccion_unidadproductiva,
ADD  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_origen FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      

ALTER TABLE gestion.tbl_dem_orden_transporte_produccion
DROP int_idconductor;     


ALTER TABLE gestion.tbl_dem_trabajo_orden_servicio
ADD int_idordenservicio bigint NOT NULL;


ALTER TABLE basico.tbl_dem_trabajador
ADD  CONSTRAINT fk_tbl_dem_trabajador_tipo FOREIGN KEY (int_tipo)
      REFERENCES public.tbl_dem_tipo_trabajador (seq_ser_tipotraba) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
ADD  CONSTRAINT unk_cedula_tipo UNIQUE (str_cedula, int_tipo);

ALTER TABLE gestion.tbl_dem_trabajo_orden_servicio
ADD CONSTRAINT fk_tbl_dem_trabajo_orden_servicio_ordentrabajo FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio (seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
INSERT INTO gestion.tbl_dem_estadoordentrabajo(
            str_descripcion, bol_activo, bol_finalizada, bol_detenido)
    VALUES ('INICIO LABOR', true, false, false);
INSERT INTO gestion.tbl_dem_estadoordentrabajo(
            str_descripcion, bol_activo, bol_finalizada, bol_detenido)
    VALUES ('EN ESPERA', true, false, true);
INSERT INTO gestion.tbl_dem_estadoordentrabajo(
            str_descripcion, bol_activo, bol_finalizada, bol_detenido)
    VALUES ('TERMINADO', true, true, false);    
INSERT INTO gestion.tbl_dem_estadoordentrabajo(
            str_descripcion, bol_activo, bol_finalizada, bol_detenido)
    VALUES ('ANULADO', false, false, false);    
    
    
INSERT INTO basico.tbl_dem_tipodocumentotierra(
            str_descripcion, bol_provicional)
    VALUES ('Carta Agraria', false);
INSERT INTO basico.tbl_dem_tipodocumentotierra(
            str_descripcion, bol_provicional)
    VALUES ('Titulo de propiedad', false);    
INSERT INTO basico.tbl_dem_tipodocumentotierra(
            str_descripcion, bol_provicional)
    VALUES ('Carta Permanencia', false);      

    
    Alter Table gestion.tbl_dem_solicitudmecanizado      
ADD int_idfinanciamiento integer,
ADD CONSTRAINT fk_tbl_dem_solicitudmecanizado_financiamiento FOREIGN KEY (int_idfinanciamiento)
      REFERENCES ministerio.tbl_dem_institucionfinanciera (seq_idinstitucionf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
Alter TABLE administracion.tbl_dem_contratomecanizado
ADD CONSTRAINT fk_tbl_dem_contratomecanizado_solicitud FOREIGN KEY (int_idsolicitudmecanizado)
      REFERENCES gestion.tbl_dem_solicitudmecanizado (int_idsolicitud) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;      
      
ALTER TABLE gestion.tbl_dem_orden_transporte_produccion
ADD int_cantviajes integer,
ADD dbl_cantkilometraje numeric(8,2),
ADD dtm_fechentrarrime time without time zone,
ADD dbl_pesoacond numeric(8,2),
ADD dbl_gradohumedad numeric(8,2),
ADD bol_usopersona boolean,
ADD int_idsilodestino integer,
ADD CONSTRAINT fk_tbl_dem_orden_transporte_produccion_silodestino FOREIGN KEY (int_idsilodestino)
      REFERENCES basico.tbl_dem_silo (seq_idsilo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION ;
      
ALTER TABLE gestion.tbl_dem_orden_transporte_produccion
ADD dbl_kilometrajeinicio numeric(8,2),
ADD dbl_kilometrajefinal numeric(8,2);     

ALTER TABLE gestion.tbl_dem_orden_transporte_produccion
DROP column dtm_fechentrarrime,
ADD column  dtm_fechentrarrime date;  

ALTER TABLE gestion.tbl_dem_orden_transporte_produccion
DROP column bol_usopersona,
ADD column  int_usopersona integer; 

ALTER TABLE gestion.tbl_dem_orden_transporte_produccion
ADD column  str_observacion character varying(250); 

ALTER TABLE gestion.tbl_dem_orden_transporte_produccion
DROP column str_observacion;

CREATE TABLE gestion.tbl_dem_sustitucion
(
  seq_idsustitucion serial NOT NULL,
  dtm_fecharegistro timestamp without time zone,
 
  int_idoperanterior integer,
  int_idoperactual integer,
  
  int_idmaqanterior integer,
  int_idmaqactual integer, 

  int_idimpanterior integer,
  int_idimpactual integer, 
  
  str_causa character varying(300),
  bol_causaporfallamaq boolean,
  int_idfallaobjeto integer,
  str_observacion character varying(760),
  
  CONSTRAINT pk_tbl_dem_sustitucionbien_pkey PRIMARY KEY (seq_idsustitucion),  


  CONSTRAINT fk_tbl_dem_sustitucion_operbanterior FOREIGN KEY (int_idoperanterior)
    REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

 CONSTRAINT fk_tbl_dem_sustitucion_operactual FOREIGN KEY (int_idoperactual)
    REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      
  CONSTRAINT fk_tbl_dem_sustitucion_maqanterior FOREIGN KEY (int_idmaqanterior)
     REFERENCES gestion.tbl_dem_maquinaria_unidad (seq_idmaquinaria) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT fk_tbl_dem_sustitucion_maqactual FOREIGN KEY (int_idmaqactual)
      REFERENCES gestion.tbl_dem_maquinaria_unidad (seq_idmaquinaria) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  
  CONSTRAINT fk_tbl_dem_sustitucion_impanterior FOREIGN KEY (int_idimpanterior)
      REFERENCES gestion.tbl_dem_implemento_unidad (seq_idimplemento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

 CONSTRAINT fk_tbl_dem_sustitucion_impactual FOREIGN KEY (int_idimpactual)
      REFERENCES gestion.tbl_dem_implemento_unidad (seq_idimplemento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
          
  CONSTRAINT fk_tbl_dem_sustitucion_fallaobjeto FOREIGN KEY (int_idfallaobjeto)
      REFERENCES mantenimiento.tbl_dem_fallaobjeto (seq_idfallaobjeto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE gestion.tbl_dem_sustitucion_ordentrabajo
(
  seq_idsustorden serial, 
  id_idsustitucion integer NOT NULL,  
  int_idordenservicio integer NOT NULL,  
  CONSTRAINT pk_tbl_dem_sustitucion_ordentrabajo_pkey PRIMARY KEY (seq_idsustorden),  
  CONSTRAINT fk_tbl_dem_sustitucion_ordentrabajo_susticion FOREIGN KEY (id_idsustitucion)
      REFERENCES gestion.tbl_dem_sustitucion (seq_idsustitucion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      
  CONSTRAINT fk_tbl_dem_sustitucion_ordentrabajo FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio (seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

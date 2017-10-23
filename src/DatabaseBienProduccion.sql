DROP VIEW gestion.viw_enriqueser_orden_mecanizado;
DROP VIEW gestion.viw_ordenes_servicio_mecanizado CASCADE;

ALTER TABLE bien_produccion.tbl_dem_maquinaria ALTER COLUMN int_idbien TYPE integer;
ALTER TABLE bien_produccion.tbl_dem_maquinaria ALTER COLUMN int_idbien DROP DEFAULT;
ALTER TABLE bien_produccion.tbl_dem_maquinaria ADD CONSTRAINT pk_bien_produccion PRIMARY KEY (int_idbien);

-- DROP TABLE bien_produccion.tbl_dem_maquinaria;
/*CREATE TABLE bien_produccion.tbl_dem_maquinaria
(
  int_idbien integer NOT NULL,
  int_idtipomedidarendimiento integer,
  dbl_caballofuerza numeric(10,4),
  str_serialcarroceria character varying(30),
  str_serialmotor character varying(30),
  str_placa character varying(20),
  CONSTRAINT pk_bien_produccion PRIMARY KEY (int_idbien),
  CONSTRAINT fk_tbl_dem_bien_produccion_marca FOREIGN KEY (int_idtipomedidarendimiento)
      REFERENCES bien_produccion.tbl_dem_tipomedidarendimiento (seq_idtipomedidarendimiento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_maquinaria_bien FOREIGN KEY (int_idbien)
      REFERENCES bien_produccion.tbl_dem_bien_produccion (seq_idbien) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE bien_produccion.tbl_dem_maquinaria OWNER TO dba;*/

CREATE OR REPLACE VIEW gestion.viw_enriqueser_orden_mecanizado AS 
 SELECT tbl_dem_orden_servicio_mecanizado.int_idordenservicio, tbl_dem_orden_servicio_mecanizado.int_idmaquinaria, maquina.str_codact AS str_codactmaquinaria, maquina.str_codigo AS str_codigomaquinaria, maquina.str_nombre AS str_nombremaquinaria, maquinaes.int_idtipomedidarendimiento, maquinaes.dbl_caballofuerza, maquinaes.str_serialcarroceria, maquinaes.str_serialmotor, tbl_dem_tipomedidarendimiento.str_destipomedida, tbl_dem_tipomedidarendimiento.bol_tipohora, tbl_dem_orden_servicio_mecanizado.int_idimplemento, implemento.str_codact AS str_codactimplemento, implemento.str_codigo AS str_codigoimplemento, implemento.str_nombre AS str_nombreimplemento, tbl_dem_orden_servicio_mecanizado.int_idherramienta, herramienta.str_codact AS str_codactherramienta, herramienta.str_codigo AS str_codigoherramienta, herramienta.str_nombre AS str_nombreherramienta
   FROM gestion.tbl_dem_orden_servicio_mecanizado
   LEFT JOIN bien_produccion.tbl_dem_bien_produccion maquina ON tbl_dem_orden_servicio_mecanizado.int_idmaquinaria = maquina.seq_idbien
   LEFT JOIN bien_produccion.tbl_dem_maquinaria maquinaes ON maquinaes.int_idbien = maquina.seq_idbien
   LEFT JOIN bien_produccion.tbl_dem_tipomedidarendimiento ON tbl_dem_tipomedidarendimiento.seq_idtipomedidarendimiento = maquinaes.int_idtipomedidarendimiento
   LEFT JOIN bien_produccion.tbl_dem_bien_produccion implemento ON tbl_dem_orden_servicio_mecanizado.int_idimplemento = implemento.seq_idbien
   LEFT JOIN bien_produccion.tbl_dem_bien_produccion herramienta ON tbl_dem_orden_servicio_mecanizado.int_idherramienta = herramienta.seq_idbien;

ALTER TABLE gestion.viw_enriqueser_orden_mecanizado OWNER TO dba;


CREATE OR REPLACE VIEW gestion.viw_ordenes_servicio_mecanizado AS 
 SELECT viw_ordenes_servico.seq_idordenservicio, viw_ordenes_servico.int_idsede, viw_ordenes_servico.int_estord, viw_ordenes_servico.dtm_fecha, viw_ordenes_servico.str_nrocon, viw_ordenes_servico.seq_idsolicitud, viw_ordenes_servico.str_nroconsol, viw_ordenes_servico.int_tipsol, viw_ordenes_servico.int_idtrabajador, viw_ordenes_servico.str_nombtrab, viw_ordenes_servico.str_apeltrab, viw_ordenes_servico.str_cedutrab, viw_ordenes_servico.str_sedetrab, viw_ordenes_servico.int_idservsoli, viw_ordenes_servico.int_idservicio, viw_ordenes_servico.str_nomserv, viw_ordenes_servico.seq_idcliente, viw_ordenes_servico.int_tipocliente, viw_ordenes_servico.str_cedurif, viw_ordenes_servico.str_nombre, viw_ordenes_servico.int_idtipomedidarendimiento, viw_ordenes_servico.str_destipomedida, viw_ordenes_servico.bol_tipohora, tbl_dem_orden_servicio_mecanizado.int_idmaquinaria, maquina.str_codact AS str_codactmaquinaria, maquina.str_codigo AS str_codigomaquinaria, maquina.str_nombre AS str_nombremaquinaria, maquinaes.int_idtipomedidarendimiento AS tipomaquina, maquinaes.dbl_caballofuerza, maquinaes.str_serialcarroceria, maquinaes.str_serialmotor, tbl_dem_tipomedidarendimiento.str_destipomedida AS desmedimaq, tbl_dem_tipomedidarendimiento.bol_tipohora AS tipohormaq, tbl_dem_orden_servicio_mecanizado.int_idimplemento, implemento.str_codact AS str_codactimplemento, implemento.str_codigo AS str_codigoimplemento, implemento.str_nombre AS str_nombreimplemento, tbl_dem_orden_servicio_mecanizado.int_idherramienta, herramienta.str_codact AS str_codactherramienta, herramienta.str_codigo AS str_codigoherramienta, herramienta.str_nombre AS str_nombreherramienta
   FROM gestion.viw_ordenes_servico
   JOIN gestion.tbl_dem_orden_servicio_mecanizado ON tbl_dem_orden_servicio_mecanizado.int_idordenservicio = viw_ordenes_servico.seq_idordenservicio
   LEFT JOIN bien_produccion.tbl_dem_bien_produccion maquina ON tbl_dem_orden_servicio_mecanizado.int_idmaquinaria = maquina.seq_idbien
   LEFT JOIN bien_produccion.tbl_dem_maquinaria maquinaes ON maquinaes.int_idbien = maquina.seq_idbien
   LEFT JOIN bien_produccion.tbl_dem_tipomedidarendimiento ON tbl_dem_tipomedidarendimiento.seq_idtipomedidarendimiento = maquinaes.int_idtipomedidarendimiento
   LEFT JOIN bien_produccion.tbl_dem_bien_produccion implemento ON tbl_dem_orden_servicio_mecanizado.int_idimplemento = implemento.seq_idbien
   LEFT JOIN bien_produccion.tbl_dem_bien_produccion herramienta ON tbl_dem_orden_servicio_mecanizado.int_idherramienta = herramienta.seq_idbien;

ALTER TABLE gestion.viw_ordenes_servicio_mecanizado OWNER TO dba;

CREATE OR REPLACE VIEW gestion.viw_ordenes_union_operador_ruta AS 
 SELECT viw_ordenes_servicio_mecanizado.seq_idordenservicio, viw_ordenes_servicio_mecanizado.int_idsede, viw_ordenes_servicio_mecanizado.int_estord, viw_ordenes_servicio_mecanizado.dtm_fecha, viw_ordenes_servicio_mecanizado.str_nrocon, viw_ordenes_servicio_mecanizado.seq_idsolicitud, viw_ordenes_servicio_mecanizado.str_nroconsol, viw_ordenes_servicio_mecanizado.int_tipsol, viw_ordenes_servicio_mecanizado.int_idtrabajador, viw_ordenes_servicio_mecanizado.str_nombtrab, viw_ordenes_servicio_mecanizado.str_apeltrab, viw_ordenes_servicio_mecanizado.str_cedutrab, viw_ordenes_servicio_mecanizado.str_sedetrab, viw_ordenes_servicio_mecanizado.int_idservsoli, viw_ordenes_servicio_mecanizado.int_idservicio, viw_ordenes_servicio_mecanizado.str_nomserv, viw_ordenes_servicio_mecanizado.seq_idcliente, viw_ordenes_servicio_mecanizado.int_tipocliente, viw_ordenes_servicio_mecanizado.str_cedurif, viw_ordenes_servicio_mecanizado.str_nombre, viw_ordenes_servicio_mecanizado.int_idtipomedidarendimiento, viw_ordenes_servicio_mecanizado.str_destipomedida, viw_ordenes_servicio_mecanizado.bol_tipohora, viw_ordenes_servicio_mecanizado.int_idmaquinaria, viw_ordenes_servicio_mecanizado.str_codactmaquinaria, viw_ordenes_servicio_mecanizado.str_codigomaquinaria, viw_ordenes_servicio_mecanizado.str_nombremaquinaria, viw_ordenes_servicio_mecanizado.tipomaquina, viw_ordenes_servicio_mecanizado.dbl_caballofuerza, viw_ordenes_servicio_mecanizado.str_serialcarroceria, viw_ordenes_servicio_mecanizado.str_serialmotor, viw_ordenes_servicio_mecanizado.desmedimaq, viw_ordenes_servicio_mecanizado.tipohormaq, viw_ordenes_servicio_mecanizado.int_idimplemento, viw_ordenes_servicio_mecanizado.str_codactimplemento, viw_ordenes_servicio_mecanizado.str_codigoimplemento, viw_ordenes_servicio_mecanizado.str_nombreimplemento, viw_ordenes_servicio_mecanizado.int_idherramienta, viw_ordenes_servicio_mecanizado.str_codactherramienta, viw_ordenes_servicio_mecanizado.str_codigoherramienta, viw_ordenes_servicio_mecanizado.str_nombreherramienta
   FROM gestion.viw_ordenes_servicio_mecanizado
UNION 
 SELECT '0' AS seq_idordenservicio, viw_asignacion_operador_servicio_prestar.idsede_trabajador AS int_idsede, '0' AS int_estord, '1900-01-01' AS dtm_fecha, '0' AS str_nrocon, viw_asignacion_operador_servicio_prestar.int_idsolicitud AS seq_idsolicitud, viw_asignacion_operador_servicio_prestar.str_nrocon AS str_nroconsol, viw_asignacion_operador_servicio_prestar.int_tipsol, viw_asignacion_operador_servicio_prestar.trabajador AS int_idtrabajador, viw_asignacion_operador_servicio_prestar.nombre_trabajador AS str_nombtrab, viw_asignacion_operador_servicio_prestar.apellido_trabajador AS str_apeltrab, viw_asignacion_operador_servicio_prestar.str_cedula AS str_cedutrab, viw_asignacion_operador_servicio_prestar.idsede_trabajador AS str_sedetrab, viw_asignacion_operador_servicio_prestar.seq_idservsoli AS int_idservsoli, viw_asignacion_operador_servicio_prestar.int_idservicio, viw_asignacion_operador_servicio_prestar.str_nomserv, viw_asignacion_operador_servicio_prestar.seq_idcliente, viw_asignacion_operador_servicio_prestar.int_tipocliente, viw_asignacion_operador_servicio_prestar.str_cedurif, viw_asignacion_operador_servicio_prestar.str_nombre, '0' AS int_idtipomedidarendimiento, '' AS str_destipomedida, false AS bol_tipohora, viw_asignacion_operador_servicio_prestar.seq_idbien AS int_idmaquinaria, viw_asignacion_operador_servicio_prestar.str_ideact AS str_codactmaquinaria, viw_asignacion_operador_servicio_prestar.str_codigo AS str_codigomaquinaria, viw_asignacion_operador_servicio_prestar.nombre_bien AS str_nombremaquinaria, viw_asignacion_operador_servicio_prestar.int_idtipo AS tipomaquina, '0.0' AS dbl_caballofuerza, '' AS str_serialcarroceria, '' AS str_serialmotor, '' AS desmedimaq, '0' AS tipohormaq, '0' AS int_idimplemento, '' AS str_codactimplemento, '' AS str_codigoimplemento, '' AS str_nombreimplemento, '0' AS int_idherramienta, '' AS str_codactherramienta, '' AS str_codigoherramienta, '' AS str_nombreherramienta
   FROM solicitud.viw_asignacion_operador_servicio_prestar
  WHERE NOT (viw_asignacion_operador_servicio_prestar.seq_idservsoli IN ( SELECT tbl_dem_orden_servicio.int_idservcont AS int_idservsoli
           FROM gestion.tbl_dem_orden_servicio));

ALTER TABLE gestion.viw_ordenes_union_operador_ruta OWNER TO dba;

/*REYNALDO 03_06_10*/

alter TABLE bien_produccion.tbl_dem_actividad_mantenimiento  

drop CONSTRAINT fk_tbl_dem_actividad_mantenimiento_int_idrespinsu,
drop CONSTRAINT fk_tbl_dem_actividad_mantenimiento_int_idunidmed, 
drop int_idrespinsu; 

CREATE TABLE bien_produccion.tbl_dem_actividad_respuesto
(
  int_respinsu integer NOT NULL,
  int_actimant integer NOT NULL,
  CONSTRAINT pk_tbl_dem_actividad_respuesto PRIMARY KEY (int_respinsu, int_actimant),
  CONSTRAINT fk_tbl_dem_actividad_respuesto_ins FOREIGN KEY (int_respinsu)
      REFERENCES bien_produccion.tbl_dem_respuestos_e_insumos (seq_ser_respinsu) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_dem_actividad_respuesto_act FOREIGN KEY (int_actimant)
      REFERENCES bien_produccion.tbl_dem_actividad_mantenimiento  (seq_ser_actimant) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITHOUT OIDS;

CREATE TABLE bien_produccion.tbl_dem_falla_bienproduccion
(
  seq_idfallabien serial NOT NULL,
  int_idbien integer NOT NULL,
  int_idtipofalla integer NOT NULL,
  int_idmomentofalla integer NOT NULL,
  dtm_fecharegistro date,
  dtm_fechafalla timestamp without time zone,
  int_idtrabajadoreporta integer NULL,
  CONSTRAINT pk_tbl_dem_falla_bienproduccion_pkey PRIMARY KEY (seq_idfallabien),

  CONSTRAINT fk_tbl_dem_falla_bienproduccion_bien FOREIGN KEY (int_idbien)
      REFERENCES bien_produccion.tbl_dem_bien_produccion (seq_idbien) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      
  CONSTRAINT fk_tbl_dem_falla_bienproduccion_tipo_fallas FOREIGN KEY (int_idtipofalla)
      REFERENCES bien_produccion.tbl_dem_tipo_fallas (seq_ser_tipofalla) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT fk_tbl_dem_falla_bienproduccion_trabajador FOREIGN KEY (int_idtrabajadoreporta)
      REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION      
      
);

CREATE TABLE bien_produccion.tbl_dem_sustitucion_bienproduccion
(
   seq_idsustitucionbien serial NOT NULL,
   int_idbienanterior  integer NOT NULL,
   int_idbienactual    integer NOT NULL,
   dtm_fecharegistro   timestamp without time zone,
   int_idordenservicio integer NOT NULL,
   bol_causaporfalla   boolean,
   int_idfallabien     integer NULL,
   str_observacion     character varying(760),
   CONSTRAINT pk_tbl_dem_sustitucionbien_pkey PRIMARY KEY (seq_idsustitucionbien),
   
   CONSTRAINT fk_tbl_dem_sustitucion_bienproduccion_bienanterior FOREIGN KEY (int_idbienanterior)
      REFERENCES bien_produccion.tbl_dem_bien_produccion (seq_idbien) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

   CONSTRAINT fk_tbl_dem_sustitucion_bienproduccion_bienactual FOREIGN KEY (int_idbienactual)
      REFERENCES bien_produccion.tbl_dem_bien_produccion (seq_idbien) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,   
      
   CONSTRAINT fk_tbl_dem_sustitucion_bienproduccion_ordentrabajo FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_servicio (seq_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,   

   CONSTRAINT fk_tbl_dem_sustitucion_bienproduccion_fallabienproduccion FOREIGN KEY (int_idfallabien)
      REFERENCES bien_produccion.tbl_dem_falla_bienproduccion (seq_idfallabien) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE bien_produccion.tbl_dem_bien_produccion
DROP COLUMN codemp,
ADD str_codemp character(4) DEFAULT '0001'::bpchar,
ADD CONSTRAINT fk_tbl_dem_bien_produccion_serieactivo FOREIGN KEY (str_codemp, str_codact, str_ideact)
      REFERENCES sigesp.saf_dta (codemp, codact, ideact) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
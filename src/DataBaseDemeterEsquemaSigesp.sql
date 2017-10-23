ALTER TABLE sigesp.saf_tipo_almacen ADD COLUMN bol_transitorio boolean;

UPDATE sigesp.saf_tipo_almacen SET bol_transitorio = FALSE;

INSERT INTO sigesp.saf_tipo_almacen (str_nombre, str_descripcion, bol_operativo, bol_taller, bol_mobiliario,
            bol_transitorio)
SELECT 'TRANSITORIO' AS nombre, 'ALMACEN QUE CONTIENE ACTIVOS QUE NO SON PARA OPERATIVIDAD DE LA EMPRESA' AS descrip, 
       FALSE AS oper, FALSE AS taller, FALSE as mob, TRUE as trans;

ALTER TABLE sigesp.saf_almacen DROP CONSTRAINT tbl_almacen_unique_key;

ALTER TABLE sigesp.saf_almacen 
  ADD CONSTRAINT tbl_almacen_unique_key 
  UNIQUE (str_codemp, str_idunieje, str_nombre, int_idunidadfuncional);

ALTER TABLE sigesp.saf_tipomovimiento ADD COLUMN bol_prestamo boolean;

UPDATE sigesp.saf_tipomovimiento SET bol_prestamo = FALSE;

INSERT INTO sigesp.saf_tipomovimiento (str_nombre, bol_salida, bol_traslado, bol_entradainicial,bol_prestamo)
SELECT 'PRESTAMO' AS nombre, FALSE AS salida, FALSE AS traslado, FALSE AS entradaini, TRUE AS prestamo;

UPDATE sigesp.saf_tipomovimiento SET str_nombre = 'RECEPCIÓN'
 WHERE str_nombre = 'ENTRADA';

UPDATE sigesp.saf_tipomovimiento SET str_nombre = 'TOMA INICIAL'
 WHERE str_nombre = 'ENTRADA INICIAL';


CREATE TABLE sigesp.saf_prestamos ( 
   int_iddetamovi    integer NOT NULL,
   int_idproductor   integer NOT NULL,
   int_idalmacen     integer NOT NULL,
   CONSTRAINT pk_saf_prestamos  PRIMARY KEY (int_iddetamovi),
   CONSTRAINT fk_saf_prestamos_detamovi FOREIGN KEY (int_iddetamovi) 
   REFERENCES sigesp.saf_detalle_movimiento (seq_ser_iddetamovi) ON UPDATE RESTRICT ON DELETE RESTRICT,
   CONSTRAINT fk_saf_prestamos_productorjuri FOREIGN KEY (int_idproductor) 
   REFERENCES ministerio.tbl_dem_productorjuridico (int_idproductor) ON UPDATE RESTRICT ON DELETE RESTRICT,
   CONSTRAINT fk_saf_prestamos_int_idalmacen FOREIGN KEY (int_idalmacen)
   REFERENCES sigesp.saf_almacen (ser_idalmacen) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT)
WITHOUT OIDS;
ALTER TABLE sigesp.saf_prestamos OWNER TO dba;
GRANT ALL ON TABLE sigesp.saf_prestamos TO dba;


ALTER TABLE sigesp.saf_traslado ADD COLUMN str_cedresptrasl character varying(10);
ALTER TABLE sigesp.saf_traslado ADD COLUMN str_vehiculo character varying(50);
ALTER TABLE sigesp.saf_traslado ADD COLUMN str_placa character varying(10);

INSERT INTO tbl_ares_funcionalidad(str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Prestamos de Activos', 36, 'cpc.demeter.comando.gestion.ComandoPrestamoActivo',
            '/iconos/32x32/salidas_activos.png',TRUE, '1001001001000000', 7, '');

UPDATE tbl_ares_funcionalidad SET str_nombre = 'Reportes de Activos' , int_idmodulo = 35
 WHERE seq_idfuncionalidad = 139;

INSERT INTO tbl_ares_funcionalidad(str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden, str_descripcion)
    VALUES ('Desincorporar Activo', 36, 'cpc.demeter.comando.gestion.ComandoDesincorporacionActivo',
            '/iconos/32x32/salidas_activos.png',TRUE, '0100000000000000', 8, '');

ALTER TABLE sigesp.saf_activoalmacen ADD COLUMN str_motidesincorporacion character varying(200);
ALTER TABLE sigesp.saf_activoalmacen ADD COLUMN bol_desincorporado boolean;

DROP VIEW sigesp.viw_activo;

CREATE OR REPLACE VIEW sigesp.viw_activo AS 
 SELECT DISTINCT dta.codemp, dta.codact, dta.ideact, dta.seract, dta.idchapa, 
        a.denact AS denact, m.str_idmarca AS marca , mo.str_idmodelo AS modelo, actalm.int_idalmacen,
        cat.str_idcategoria As categoria, tip.str_idtipo As tipo, actalm.bol_trasladado, actalm.bol_desincorporado
   FROM sigesp.saf_dta dta
   JOIN sigesp.saf_activo a USING (codemp, codact)
   JOIN sigesp.siv_marca m ON m.str_idmarca = a.codmar
   JOIN sigesp.siv_modelo mo ON m.str_idmarca = mo.str_idmarca AND a.codmod = mo.str_idmodelo
   JOIN sigesp.siv_categoria cat ON a.codcat = cat.str_idcategoria
   JOIN sigesp.siv_tipo tip ON a.codcat = tip.str_idcategoria AND a.codtip = tip.str_idtipo
   LEFT JOIN sigesp.saf_activoalmacen actalm ON dta.codemp = actalm.str_codemp AND dta.codact = actalm.str_codact
        AND dta.ideact = actalm.str_ideact
ORDER BY dta.codemp, dta.codact, dta.ideact, dta.seract, dta.idchapa;
ALTER TABLE sigesp.viw_activo OWNER TO dba;

ALTER TABLE sigesp.saf_almacen DROP COLUMN str_codpai;
ALTER TABLE sigesp.saf_almacen DROP COLUMN str_codest;
ALTER TABLE sigesp.saf_almacen DROP COLUMN str_codmun;
ALTER TABLE sigesp.saf_almacen DROP COLUMN str_codpar;

ALTER TABLE sigesp.saf_almacen ADD COLUMN int_iddireccion integer NOT NULL;

ALTER TABLE  sigesp.saf_almacen 
ADD CONSTRAINT fk_saf_almacen_direccion FOREIGN KEY (int_iddireccion)
    REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
    ON UPDATE RESTRICT ON DELETE RESTRICT;


CREATE TABLE sigesp.spg_ministerio_ua
(
  codemp character(4) NOT NULL,
  coduac character(5) NOT NULL,
  denuac character varying(60) NOT NULL,
  resuac character varying(60),
  tipuac character varying(1),
  hoja boolean NOT NULL DEFAULT true,
  CONSTRAINT pk_spg_ministerio_ua PRIMARY KEY (codemp, coduac)
)
WITHOUT OIDS;
ALTER TABLE sigesp.spg_ministerio_ua OWNER TO dba;
COMMENT ON TABLE sigesp.spg_ministerio_ua IS 'Tabla que contiene las unidades administradoras centrales o desconcentradas de un ministerio';


/* SELECT unidades.codemp, unidades.coduac, unidades.denuac, unidades.resuac, unidades.tipuac, unidades.hoja
   FROM dblink('dbname=db_cvapedrocamejo_2010 hostaddr=127.0.0.1 user=dba password=d84@54 port=5432'::text, 'SELECT spg_ministerio_ua.codemp, spg_ministerio_ua.coduac, spg_ministerio_ua.denuac, spg_ministerio_ua.resuac, 
    spg_ministerio_ua.tipuac, spg_ministerio_ua.hoja FROM spg_ministerio_ua'::text) unidades(codemp character(4), coduac character(5), denuac character varying(60), resuac character varying(60), tipuac character varying(1), hoja boolean);
*/

/* SELECT vint.codemp, vint.codubifis, vint.desubifis, vint.codpai, vint.codest, vint.codmun, vint.codpar, vint.dirubifis
   FROM dblink('dbname=db_cvapedrocamejo_2010 hostaddr=127.0.0.1 user=dba password=d84@54 port=5432'::text, 'SELECT codemp, codubifis, desubifis, codpai, codest, codmun, codpar, dirubifis
               FROM sno_ubicacionfisica '::text) vint(codemp character(4), codubifis character(4), desubifis character varying(100), codpai character varying(3), codest character varying(3), codmun character varying(3), codpar character varying(3), dirubifis character varying(200));
*/
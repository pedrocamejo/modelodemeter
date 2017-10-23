ALTER TABLE gestion.tbl_dem_orden_servicio DROP CONSTRAINT fk_tbl_dem_orden_servicio_serv;
ALTER TABLE gestion.tbl_dem_orden_servicio RENAME int_idservsoli TO int_idservcont;
ALTER TABLE gestion.tbl_dem_orden_servicio DROP int_idcontrato;
ALTER TABLE gestion.tbl_dem_orden_servicio ADD COLUMN str_codemp character(4);


CREATE TABLE gestion.tbl_dem_estadoordentrabajo
(
  seq_idestado serial NOT NULL,
  str_descripcion character varying(30),
  bol_activo boolean,
  bol_finalizada boolean,
  CONSTRAINT pk_tbl_dem_tbl_dem_estadoordentrabajo PRIMARY KEY (seq_idestado)
)
WITHOUT OIDS;


CREATE TABLE gestion.tbl_dem_estadoequipo
(
  seq_idestado serial NOT NULL,
  str_descripcion character varying(30),
  bol_activo boolean,
  CONSTRAINT pk_tbl_dem_tbl_dem_estadoequipo PRIMARY KEY (seq_idestado)
)
WITHOUT OIDS;


CREATE TABLE gestion.tbl_dem_equipo
(
  seq_idequipo serial NOT NULL,
  bol_bien boolean,	
  int_idcategoria integer,
  int_idtipo integer,
  int_idmarca integer,
  int_idmodelo integer,
  int_idcolor integer,
  int_anofab integer,
  str_serialmotor character varying(50),
  str_serialcarroceria character varying(50),
  int_lote integer,
  int_estequ integer, 
  CONSTRAINT pk_tbl_tbl_dem_equipo PRIMARY KEY (seq_idequipo),
  CONSTRAINT fk_tbl_dem_equipo_categoria FOREIGN KEY (int_idcategoria)
      REFERENCES siv_categoria (id_categoria) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_equipo_color FOREIGN KEY (int_idcolor)
      REFERENCES bien_produccion.tbl_dem_color (seq_idcolor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_equipo_marca FOREIGN KEY (int_idmarca)
      REFERENCES siv_marca (id_marca) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_equipo_modelo FOREIGN KEY (int_idmodelo)
      REFERENCES siv_modelo (id_modelo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_equipo_lote FOREIGN KEY (int_lote)
      REFERENCES gestion.tbl_dem_lotes (seq_ser_lote) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_equipo_estado FOREIGN KEY (int_estequ)
      REFERENCES gestion.tbl_dem_estadoequipo (seq_idestado) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;

CREATE TABLE gestion.tbl_dem_equipomantenimiento
(
  int_idequipo 	integer NOT NULL,
  dtm_fecha date,
  str_mantenimiento character varying(30),
  int_medida bigint,
  CONSTRAINT pk_tbl_dem_equipomantenimiento PRIMARY KEY (int_idequipo,dtm_fecha),
  CONSTRAINT fk_tbl_dem_equipomantenimiento_equipo FOREIGN KEY (int_idequipo)
      REFERENCES gestion.tbl_dem_equipo (seq_idequipo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION

)
WITHOUT OIDS;


ALTER TABLE basico.tbl_dem_trabajador
ALTER str_cedula type character varying(15);

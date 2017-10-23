CREATE SCHEMA mantenimiento;

CREATE TABLE mantenimiento.tbl_dem_tipofalla
(
  seq_ser_tipofalla serial NOT NULL,
  str_descripcion text NOT NULL,
  CONSTRAINT pk_tbl_dem_tipofalla PRIMARY KEY (seq_ser_tipofalla)
);

CREATE TABLE mantenimiento.tbl_dem_momentofalla
(
  seq_ser_momenfalla serial NOT NULL,
  str_descripcion text NOT NULL,
  CONSTRAINT pk_tbl_dem_momentofalls PRIMARY KEY (seq_ser_momenfalla)
);

CREATE TABLE mantenimiento.tbl_dem_lote
(
  seq_idlote serial NOT NULL,
  str_deslote character varying(30),
  CONSTRAINT pk_tbl_dem_lote_pkey PRIMARY KEY (seq_idlote)
);

CREATE TABLE mantenimiento.tbl_dem_tipomedidarendimiento
(
  seq_idtipomedidarendimiento serial NOT NULL,
  str_destipomedida character varying(30),
  bol_tipohora boolean,
  CONSTRAINT pk_tbl_dem_tipomedidarendimiento_pkey PRIMARY KEY (seq_idtipomedidarendimiento)
);

CREATE TABLE mantenimiento.tbl_dem_estado
(
  seq_ser_estado serial NOT NULL,
  str_descripcion character varying(100) NOT NULL,
  CONSTRAINT pk_tbl_dem_estado PRIMARY KEY (seq_ser_estado)
);

CREATE TABLE mantenimiento.tbl_dem_fabricante
(
  seq_ser_fabricante serial NOT NULL,
  str_descripcion character varying(350) NOT NULL,
  CONSTRAINT pk_tbl_dem_fabricante PRIMARY KEY (seq_ser_fabricante)
);

CREATE TABLE mantenimiento.tbl_dem_objetomantenimiento
(
  seq_idobjeto serial NOT NULL,
  str_codigo       character varying(15),
  str_nombre 	   character varying(450),
  str_serie        character varying(15),
  bol_propio       boolean,
  str_codact       character varying(15) NOT NULL,
  str_codemp       character varying(4)  NOT NULL,
  str_ideact       character varying(15) NOT NULL,  
  str_idmarca      character varying(8),
  str_idmodelo     character varying(8),
  str_idtipo       character varying(8),
  str_idcategoria  character varying(8),
  str_color        character varying(250),
  str_rgbcolor     character varying(12),
  str_numcolor     character varying(6), 
  
  int_anofab integer,
  int_idfabricante integer,
  
 
  byt_foto   bytea NULL,

  int_idlote integer NULL, 
  int_idestado integer NULL, 
  int_idpropietario integer NULL,
  bol_atomico boolean DEFAULT true,
  int_idpadre integer,
  int_idsismantenimiento integer,
  
  
  CONSTRAINT tbl_dem_objetomantenimiento_pkey PRIMARY KEY (seq_idobjeto),  
  
  CONSTRAINT fk_tbl_dem_dem_objetomantenimiento_modelo FOREIGN KEY (str_idmarca,str_idmodelo)
      REFERENCES sigesp.siv_modelo (str_idmarca,str_idmodelo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  
  CONSTRAINT fk_tbl_dem_dem_objetomantenimiento_serieactivo FOREIGN KEY (str_codemp, str_codact, str_ideact)
      REFERENCES sigesp.saf_dta (codemp, codact, ideact) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_objetomantenimiento_tipo FOREIGN KEY (str_idcategoria,str_idtipo)
      REFERENCES sigesp.siv_tipo (str_idcategoria,str_idtipo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT fk_tbl_dem_objetomantenimiento_lote FOREIGN KEY (int_idlote)
      REFERENCES mantenimiento.tbl_dem_lote (seq_idlote) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

 CONSTRAINT fk_tbl_dem_objetomantenimiento_fabricante FOREIGN KEY (int_idfabricante)
      REFERENCES mantenimiento.tbl_dem_fabricante (seq_ser_fabricante) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT fk_tbl_dem_objetomantenimiento_clientes FOREIGN KEY (int_idpropietario)
      REFERENCES public.tbl_dem_clientes (seq_idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      
  CONSTRAINT fk_tbl_dem_objetomantenimiento_componente FOREIGN KEY (int_idpadre)
      REFERENCES mantenimiento.tbl_dem_objetomantenimiento (seq_idobjeto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT unq_serieactivo UNIQUE (str_codemp, str_codact, str_ideact)
      

      
);

CREATE TABLE mantenimiento.tbl_dem_maquinaria
(
  int_idobjeto serial NOT NULL,
  int_idtipomedidarendimiento integer,
  dbl_caballofuerza numeric(10,4),
  str_serialcarroceria character varying(30),
  str_serialmotor character varying(30),
  str_placa character varying(20),
  CONSTRAINT pk_tbl_dem_maquinaria_pkey PRIMARY KEY (int_idobjeto),
   CONSTRAINT fk_tbl_dem_maquinaria_objeto FOREIGN KEY (int_idobjeto)
      REFERENCES mantenimiento.tbl_dem_objetomantenimiento (seq_idobjeto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_maquinaria_tipomedidarendimiento FOREIGN KEY (int_idtipomedidarendimiento)
      REFERENCES mantenimiento.tbl_dem_tipomedidarendimiento (seq_idtipomedidarendimiento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
 
);

CREATE TABLE mantenimiento.tbl_dem_maquinariaoperador
(
  seq_idasignacion serial NOT NULL,
  int_idobjeto integer NOT NULL,
  int_idtrabajador integer NOT NULL,
  dtm_fechaasignacion date,
  CONSTRAINT pk_tbl_dem_maquinariaoperador_pkey PRIMARY KEY (seq_idasignacion),
  CONSTRAINT fk_tbl_dem_maquinariaoperador_maq FOREIGN KEY (int_idobjeto)
      REFERENCES mantenimiento.tbl_dem_maquinaria (int_idobjeto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_maquinariaoperador_trabajador FOREIGN KEY (int_idtrabajador)
      REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION    
);

CREATE TABLE mantenimiento.tbl_dem_fallaobjeto
(
  seq_idfallaobjeto serial NOT NULL,
  int_idobjeto integer NOT NULL,
  int_idtipofalla integer NOT NULL,
  int_idmomentofalla integer NOT NULL,
  dtm_fecharegistro date,
  dtm_fechafalla timestamp without time zone,
  int_idtrabajadoreporta integer,
  CONSTRAINT pk_tbl_dem_fallaobjeto_pkey PRIMARY KEY (seq_idfallaobjeto),
  CONSTRAINT fk_tbl_dem_fallaobjeto_objeto FOREIGN KEY (int_idobjeto)
      REFERENCES mantenimiento.tbl_dem_objetomantenimiento (seq_idobjeto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_fallaobjeto_tipofalla FOREIGN KEY (int_idtipofalla)
      REFERENCES mantenimiento.tbl_dem_tipofalla (seq_ser_tipofalla) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT fk_tbl_dem_fallaobjeto_momentofalla FOREIGN KEY (int_idtipofalla)
      REFERENCES mantenimiento.tbl_dem_momentofalla (seq_ser_momenfalla) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,    
      
  CONSTRAINT fk_tbl_dem_fallaobjeto_trabajador FOREIGN KEY (int_idtrabajadoreporta)
      REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



CREATE SCHEMA basico;


CREATE TABLE basico.tbl_dem_tipoproducto
(
  seq_idtipoproducto serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipoproducto PRIMARY KEY (seq_idtipoproducto)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_tipoproducto OWNER TO dba;
      
INSERT INTO basico.tbl_dem_tipoproducto(str_descripcion) VALUES ('Articulo');
INSERT INTO basico.tbl_dem_tipoproducto(str_descripcion) VALUES ('Servicio');

CREATE TABLE basico.tbl_dem_producto
(
  seq_idproducto serial NOT NULL,
  str_descripcion character varying(50),
  seq_idtipoproducto integer NOT NULL,
  int_idtipoimpuesto integer DEFAULT 1,	
  CONSTRAINT pk_tbl_dem_producto PRIMARY KEY (seq_idproducto),
  CONSTRAINT fk_tbl_dem_producto_tipo FOREIGN KEY (seq_idtipoproducto)
      REFERENCES basico.tbl_dem_tipoproducto (seq_idtipoproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_producto_imp FOREIGN KEY (int_idtipoimpuesto)
      REFERENCES administracion.tbl_dem_tipo_impuesto (seq_idtipoimpuesto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_producto OWNER TO dba;

CREATE TABLE basico.tbl_dem_precioproducto
(
  seq_idprecio serial not null,	
  int_idproducto integer NOT NULL,
  int_idtipocliente integer NOT NULL,
  dbl_precio numeric(8,4),
  CONSTRAINT pk_tbl_dem_precioproducto PRIMARY KEY (seq_idprecio),
  CONSTRAINT fk_tbl_dem_precioproducto_unico UNIQUE (int_idproducto, int_idtipocliente),
  CONSTRAINT fk_tbl_dem_precioproducto_cliente FOREIGN KEY (int_idtipocliente)
      REFERENCES public.tbl_dem_tipo_clientes (seq_idtipocliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_precioproducto_producto FOREIGN KEY (int_idproducto)
      REFERENCES basico.tbl_dem_producto (seq_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_precioproducto OWNER TO dba;



CREATE TABLE basico.tbl_dem_servicio
(
  seq_idservicio serial NOT NULL,
  str_descripcion character varying(50),
  int_idtiposervicio integer,
  seq_idtipounidadmedida integer,
  CONSTRAINT pk_tbl_dem_servicio PRIMARY KEY (seq_idservicio),
  CONSTRAINT fk_tbl_dem_servicio_tipounidad FOREIGN KEY (seq_idtipounidadmedida)
      REFERENCES public.tbl_dem_tipo_unidad_medida (seq_idtipounidadmedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_servicio_tiposervicio FOREIGN KEY (int_idtiposervicio)
      REFERENCES public.tbl_dem_tipo_servicio (seq_idtiposervicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION    
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_servicio OWNER TO dba;



CREATE TABLE basico.tbl_dem_labor
(
  int_idproducto serial NOT NULL,
  int_idumedida integer,
  int_idservicio integer NOT NULL,
  CONSTRAINT pk_tbl_dem_labor PRIMARY KEY (int_idproducto), 
  CONSTRAINT fk_tbl_dem_labor_producto FOREIGN KEY (int_idproducto)
      REFERENCES basico.tbl_dem_producto (seq_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_labor_servicio FOREIGN KEY (int_idservicio)
      REFERENCES basico.tbl_dem_servicio (seq_idservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_labor OWNER TO dba;    

CREATE TABLE basico.tbl_dem_tipoarticulo
(
  seq_idtipoarticulo serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipoarticulo PRIMARY KEY (seq_idtipoarticulo)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_tipoarticulo OWNER TO dba;



CREATE TABLE basico.tbl_dem_clasearticulo
(
  seq_idclasearticulo serial NOT NULL,
  str_descripcion character varying(50),
  int_idtipoarticulo integer,
  CONSTRAINT pk_tbl_dem_clasearticulo PRIMARY KEY (seq_idclasearticulo),
  CONSTRAINT fk_tbl_dem_clasearticulo_tipounidad FOREIGN KEY (int_idtipoarticulo)
      REFERENCES basico.tbl_dem_tipoarticulo (seq_idtipoarticulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_clasearticulo OWNER TO dba;


CREATE TABLE basico.tbl_dem_articulo
(
  int_idproducto integer NOT NULL,
  int_idclasearticulo integer,
  CONSTRAINT pk_tbl_dem_articulo PRIMARY KEY (int_idproducto),
  CONSTRAINT fk_tbl_dem_articulo_producto FOREIGN KEY (int_idproducto)
      REFERENCES basico.tbl_dem_producto (seq_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_articulo_clase FOREIGN KEY (int_idclasearticulo)
      REFERENCES basico.tbl_dem_clasearticulo (seq_idclasearticulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_articulo OWNER TO dba;



CREATE TABLE basico.tbl_dem_articulosigesp
(
  int_idproducto integer NOT NULL,
  codemp character(4) NOT NULL,
  codart character(20) NOT NULL,
  CONSTRAINT pk_tbl_dem_articulosigesp PRIMARY KEY (int_idproducto),
  CONSTRAINT fk_tbl_dem_articulosigesp_producto FOREIGN KEY (int_idproducto)
      REFERENCES basico.tbl_dem_producto (seq_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_articulosigesp OWNER TO dba;
    

ALTER TABLE basico.tbl_dem_labor
DROP int_idumedida,
ADD int_idumedidaprecio integer,
ADD int_idumedidalabor integer,
ADD CONSTRAINT fk_tbl_dem_labor_serviciol FOREIGN KEY (int_idumedidalabor)
      REFERENCES public.tbl_dem_unidad_medidas (seq_idumedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
ADD  CONSTRAINT fk_tbl_dem_labor_serviciop FOREIGN KEY (int_idumedidaprecio)
      REFERENCES public.tbl_dem_unidad_medidas (seq_idumedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
    TipoUnidadMedida
CREATE TABLE basico.tbl_dem_conceptopago
(
  seq_idconcepto serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_conceptopagoTipoUnidadMedida PRIMARY KEY (seq_idconcepto)
)
WITHOUT OIDS;
ALTER TABLE basico.tbl_dem_conceptopago OWNER TO dba;


insert into basico.tbl_dem_conceptopago(str_descripcion) values('PAGO INICIAL');
insert into basico.tbl_dem_conceptopago(str_descripcion) values('CANCELACION TOTAL');
insert into basico.tbl_dem_conceptopago(str_descripcion) values('ABONO');
insert into basico.tbl_dem_conceptopago(str_descripcion) values('PAGO CUOTA');
insert into basico.tbl_dem_conceptopago(str_descripcion) values('FINALIZACION CREDITO');
      
INSERT INTO basico.tbl_dem_tipoarticulo(str_descripcion)
    VALUES ('Repuesto');
    INSERT INTO basico.tbl_dem_tipoarticulo(str_descripcion)
    VALUES ('Lubricante');
/****************************************************07-12-2010**/

Alter TABLE basico.tbl_dem_servicio
add bol_pases boolean default false,
add bol_cantidades boolean default false;


ALTER TABLE public.tbl_dem_tipo_servicio
ADD bol_mecanizado boolean DEFAULT true,
ADD bol_transporte boolean DEFAULT true,
ADD bol_transcosecha boolean DEFAULT true,
ADD bol_lineaamarilla boolean DEFAULT true;
       
ALTER TABLE basico.tbl_dem_servicio
ADD  bol_produccion boolean DEFAULT false,
ADD  seq_idtipounidadproduccion integer,
ADD  seq_idtipounidadtrabajo integer;

ALTER TABLE basico.tbl_dem_servicio
ADD  CONSTRAINT fk_tbl_dem_servicio_tipounidadtrabajo FOREIGN KEY (seq_idtipounidadtrabajo)
      REFERENCES public.tbl_dem_tipo_unidad_medida (seq_idtipounidadmedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
ADD CONSTRAINT fk_tbl_dem_servicio_tipounidadprodccion FOREIGN KEY (seq_idtipounidadproduccion)
      REFERENCES public.tbl_dem_tipo_unidad_medida (seq_idtipounidadmedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE public.tbl_dem_tipo_unidad_hija
(
  int_idtipounidadpadre integer NOT NULL,
  int_idtipounidadhijo integer NOT NULL,
  CONSTRAINT pk_tbl_dem_tipo_unidad_hija PRIMARY KEY (int_idtipounidadpadre, int_idtipounidadhijo),
  CONSTRAINT fk_tbl_dem_tipo_unidad_hija_padre FOREIGN KEY (int_idtipounidadpadre)
      REFERENCES public.tbl_dem_tipo_unidad_medida (seq_idtipounidadmedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_tipo_unidad_hija_hijo FOREIGN KEY (int_idtipounidadhijo)
      REFERENCES public.tbl_dem_tipo_unidad_medida (seq_idtipounidadmedida) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE public.tbl_dem_tipo_unidad_estandar OWNER TO demeter_sa;

/**OJO LENAR TABLA DE TIPO DE UNIDADES HIJAS**/
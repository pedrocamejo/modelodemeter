ALTER TABLE public.tbl_dem_tipo_clientes add bol_juridico boolean default true;

CREATE SCHEMA ministerio
  AUTHORIZATION dba;
  
CREATE TABLE ministerio.tbl_dem_tiposervicio
(
  seq_idtiposervicio serial NOT NULL,
  str_descripcion character varying(50),
  activo boolean,
  CONSTRAINT tbl_dem_tiposervicio_pkey PRIMARY KEY (seq_idtiposervicio),
  CONSTRAINT tbl_dem_tiposervicio_unique UNIQUE (str_descripcion)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tiposervicio OWNER TO dba;



CREATE TABLE ministerio.tbl_dem_labor
(
  seq_idlabor serial NOT NULL,
  int_idtiposervicio integer,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_labor PRIMARY KEY (seq_idlabor),
  CONSTRAINT fk_tbl_dem_labor_tiposervicio FOREIGN KEY (int_idtiposervicio)
      REFERENCES ministerio.tbl_dem_tiposervicio (seq_idtiposervicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_labor OWNER TO dba;



CREATE TABLE ministerio.tbl_dem_servicio
(
  seq_idservicio serial NOT NULL,
  int_idlabor integer,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_servicio PRIMARY KEY (seq_idservicio),
  CONSTRAINT fk_tbl_dem_servicio_labor FOREIGN KEY (int_idlabor)
      REFERENCES ministerio.tbl_dem_labor (seq_idlabor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_servicio OWNER TO dba;



CREATE TABLE ministerio.tbl_dem_pais
(
  seq_idpais serial NOT NULL,
  str_descripcion character varying(50),
  bol_local boolean,
  CONSTRAINT pk_tbl_dem_pais PRIMARY KEY (seq_idpais)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_pais OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_eje
(
  seq_ideje serial NOT NULL,
  int_idpais integer,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_eje PRIMARY KEY (seq_ideje)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_eje OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_estado
(
  seq_idestado serial NOT NULL,
  int_idpais integer not null,
  int_ideje integer,
  str_descripcion character varying(50),
  
  CONSTRAINT pk_tbl_dem_estado PRIMARY KEY (seq_idestado),
  CONSTRAINT fk_tbl_dem_estado_pais FOREIGN KEY (int_idpais)
      REFERENCES ministerio.tbl_dem_pais (seq_idpais) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_estado_eje FOREIGN KEY (int_ideje)
      REFERENCES ministerio.tbl_dem_eje (seq_ideje) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_estado OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_municipio
(
  seq_idmunicipio serial NOT NULL,
  int_idestado integer not null,
  str_descripcion character varying(50),
  
  CONSTRAINT pk_tbl_dem_municipio PRIMARY KEY (seq_idmunicipio),
  CONSTRAINT fk_tbl_dem_municipio_estado FOREIGN KEY (int_idestado)
      REFERENCES ministerio.tbl_dem_estado(seq_idestado) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_municipio OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_parroquia
(
  seq_idparroquia serial NOT NULL,
  int_idmunicipio integer not null,
  str_descripcion character varying(50),
  
  CONSTRAINT pk_tbl_dem_parroquia PRIMARY KEY (seq_idparroquia),
  CONSTRAINT fk_tbl_dem_parroquia_municipio FOREIGN KEY (int_idmunicipio)
      REFERENCES ministerio.tbl_dem_municipio (seq_idmunicipio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_parroquia OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_sector
(SeqLindero
  seq_idsector serial NOT NULL,
  int_idparroquia integer not null,
  str_descripcion character varying(50),
  
  CONSTRAINT pk_tbl_dem_sector PRIMARY KEY (seq_idsector),
  CONSTRAINT fk_tbl_dem_sector_parroquia FOREIGN KEY (int_idparroquia)
      REFERENCES ministerio.tbl_dem_parroquia (seq_idparroquia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_sector OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_direccion
(
  seq_iddireccion serial NOT NULL,
  int_idsector integer,
  str_descripcion character varying(150),
  str_referencia character varying(150),
  CONSTRAINT pk_tbl_dem_direccion PRIMARY KEY (seq_iddireccion),
  CONSTRAINT fk_tbl_dem_labor_direccion_sector FOREIGN KEY (int_idsector)
      REFERENCES ministerio.tbl_dem_sector (seq_idsector) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_direccion OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_sectoragricola
(SeqLindero
  seq_idsectoragricola serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_sectoragricola PRIMARY KEY (seq_idsectoragricola)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_sectoragricola OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_tipotenencia
(
  seq_idtipotenencia serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipotenencia PRIMARY KEY (seq_idtipotenencia)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tipotenencia OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_tipovialidad
(
  seq_idtipovialidad serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipovialidad PRIMARY KEY (seq_idtipovialidad)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tipovialidad OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_unidadproductiva
(
  seq_idunidadproductiva serial NOT NULL,
  int_iddireccion integer,
  str_nombre character varying(50),
  dbl_superficie numeric(8,4),
  int_idsectoragricola integer,	
  int_idtipotenencia integer,
  int_idtipovialidad integer,	
  CONSTRAINT pk_tbl_dem_unidadproductiva PRIMARY KEY (seq_idunidadproductiva),
  CONSTRAINT fk_tbl_dem_unidadproductiva_direccion FOREIGN KEY (int_iddireccion)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_unidadproductiva_tiposervicio FOREIGN KEY (int_idsectoragricola)
      REFERENCES ministerio.tbl_dem_sectoragricola (seq_idsectoragricola) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_unidadproductiva_tipotenencia FOREIGN KEY (int_idtipotenencia)
      REFERENCES ministerio.tbl_dem_tipotenencia (seq_idtipotenencia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_unidadproductiva_tipovialidad FOREIGN KEY (int_idtipovialidad)
      REFERENCES ministerio.tbl_dem_tipovialidad (seq_idtipovialidad) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_unidadproductiva OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_tipocoordenada
(
  seq_idtipocoordenada serial NOT NULL,
  int_idunidadproductiva integer,
  str_descripcion character varying(50),
  bol_geografica boolean,
  CONSTRAINT pk_tbl_dem_tipocoordenada PRIMARY KEY (seq_idtipocoordenada)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tipocoordenada OWNER TO dba;



CREATE TABLE ministerio.tbl_dem_coordenadageografica
(
  seq_idcoordenada serial NOT NULL,
  int_iddireccion integer not null,
  int_idtipocoordenada integer not null,
  int_husoutm integer,
  bint_ejex bigint,
  bint_ejey bigint,
  CONSTRAINT pk_tbl_dem_coordenadageografica PRIMARY KEY (seq_idcoordenada),
  CONSTRAINT fk_tbl_dem_coordenadageografica_direccion FOREIGN KEY (int_iddireccion)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_coordenadageografica_tipocoordenada FOREIGN KEY (int_idtipocoordenada)
      REFERENCES ministerio.tbl_dem_tipocoordenada(seq_idtipocoordenada) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_coordenadageografica OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_tiposuperficie
(
  seq_idtiposuperficie serial NOT NULL,
  str_descripcion character varying(50),
  bol_usable boolean,
  CONSTRAINT pk_tbl_dem_tiposuperficie PRIMARY KEY (seq_idtiposuperficie)
  
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tiposuperficie OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_superficieunidad
(
  seq_idsuperficieunidad serial NOT NULL,
  int_idunidadproductiva integer,
  int_idtiposuperficie integer,
  dbl_superficie numeric(8,4),
  CONSTRAINT pk_tbl_dem_superficieunidad PRIMARY KEY (seq_idsuperficieunidad),
  CONSTRAINT fk_tbl_dem_superficieunidad_tiposervicio FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_unidadproductiva (seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_labor_superficieunidad_tiposuperficie FOREIGN KEY (int_idtiposuperficie)
      REFERENCES ministerio.tbl_dem_tiposuperficie (seq_idtiposuperficie) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_superficieunidad OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_tipounidad
(
  seq_idtipounidad serial NOT NULL,
  str_descripcion character varying(50),
  bol_administrativo boolean,
  CONSTRAINT pk_tbl_dem_tipounidad PRIMARY KEY (seq_idtipounidad)
  
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tipounidad OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_unidadfuncional
(
  seq_idunidadfuncional serial NOT NULL,
  int_idtipounidad integer,
  int_iddireccion integer,
  CONSTRAINT pk_tbl_dem_unidadfuncional PRIMARY KEY (seq_idunidadfuncional),
  CONSTRAINT fk_tbl_dem_unidadfuncional_tipounidad FOREIGN KEY (int_idtipounidad)
      REFERENCES ministerio.tbl_dem_tipounidad (seq_idtipounidad) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_unidadfuncional_direccion FOREIGN KEY (int_iddireccion)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_unidadfuncional OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_unidadfp
(
  seq_idunidadfp serial NOT NULL,
  int_idunidadfuncional integer,
  int_idunidadproductiva integer,
  CONSTRAINT pk_tbl_dem_unidadfp PRIMARY KEY (seq_idunidadfp),
  CONSTRAINT fk_tbl_dem_unidadfp_funcional FOREIGN KEY (int_idunidadfuncional)
      REFERENCES ministerio.tbl_dem_unidadfuncional (seq_idunidadfuncional) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_unidadfp_productiva FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_unidadproductiva (seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_unidadfp OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_tipofinanciamiento
(
  seq_idtipofinanciamiento serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipofinanciamiento PRIMARY KEY (seq_idtipofinanciamiento)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tipofinanciamiento  OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_institucionfinanciera
(
  seq_idinstitucionf serial NOT NULL,
  int_idtipofinanciamiento integer,
  str_descripcion character varying(50),
  bol_banca boolean,
  bol_privado boolean,
  CONSTRAINT pk_tbl_dem_institucionfinanciera PRIMARY KEY (seq_idinstitucionf),
  CONSTRAINT fk_tbl_dem_institucionfinanciera_tipo FOREIGN KEY (int_idtipofinanciamiento)
      REFERENCES ministerio.tbl_dem_tipofinanciamiento (seq_idtipofinanciamiento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_institucionfinanciera OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_tipoorganizacion
(
  seq_idtipoorganizacion serial NOT NULL,
  str_nombre character varying(50),
  CONSTRAINT pk_tbl_dem_tipoorganizacion PRIMARY KEY (seq_idtipoorganizacion)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tipoorganizacion  OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_organizacion
(
  seq_idorganizacion serial NOT NULL,
  int_idtipoorganizacion integer,
  str_nombre character varying(50),
  CONSTRAINT pk_tbl_dem_organizacion PRIMARY KEY (seq_idorganizacion),
  CONSTRAINT fk_tbl_dem_organizacion_tipo FOREIGN KEY (int_idtipoorganizacion)
      REFERENCES ministerio.tbl_dem_tipoorganizacion (seq_idtipoorganizacion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_organizacion OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_telefono
(
  seq_idtelefono serial NOT NULL,
  int_idcodare integer,
  str_numtel character varying(10) NOT NULL,
  bol_celular boolean DEFAULT true, -- celular o residencial
  CONSTRAINT tbl_dem_telefono_pkey PRIMARY KEY (seq_idtelefono),
  CONSTRAINT fk_tbl_dem_telefono_idcodare FOREIGN KEY (int_idcodare)
      REFERENCES public.tbl_dem_cod_telefono (seq_idcodare) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_telefono OWNER TO dba;
COMMENT ON COLUMN ministerio.tbl_dem_telefono.bol_celular IS 'celular o residencial';


CREATE TABLE ministerio.tbl_dem_productor
(
  seq_idproductor integer NOT NULL,
  int_idunidadfuncional integer,
  bol_organizado boolean,
  dte_ingreso date,
  dte_nacimiento date,
  int_idorganizacion integer,
  CONSTRAINT pk_tbl_dem_productor PRIMARY KEY (seq_idproductor),
  CONSTRAINT fk_tbl_dem_productor_cliente FOREIGN KEY (seq_idproductor)
      REFERENCES tbl_dem_clientes (seq_idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_productor_funcional FOREIGN KEY (int_idunidadfuncional)
      REFERENCES ministerio.tbl_dem_unidadfuncional (seq_idunidadfuncional) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,    
  CONSTRAINT fk_tbl_dem_productor_organizacion FOREIGN KEY (int_idorganizacion)
      REFERENCES ministerio.tbl_dem_organizacion (seq_idorganizacion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_productor OWNER TO dba;



CREATE TABLE ministerio.tbl_dem_productorproduccion
(
  seq_idproductor serial NOT NULL,
  int_idunidadproductiva integer,
  
  CONSTRAINT pk_tbl_dem_productorproduccion PRIMARY KEY (seq_idproductor, int_idunidadproductiva),
  CONSTRAINT fk_tbl_dem_productorproduccion_productor FOREIGN KEY (seq_idproductor)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_productorproduccion_direccion FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_unidadproductiva (seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_productorproduccion OWNER TO dba;





CREATE TABLE ministerio.tbl_dem_productorfinanciamiento
(
  int_idproductor integer NOT NULL,
  int_idinstitucionf integer not null,
 
  CONSTRAINT pk_tbl_dem_productorfinanciamiento PRIMARY KEY (int_idproductor, int_idinstitucionf),
  CONSTRAINT fk_tbl_dem_productorfinanciamiento_productor FOREIGN KEY (int_idproductor)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_productorfinanciamiento_institucion FOREIGN KEY (int_idinstitucionf)
      REFERENCES ministerio.tbl_dem_institucionfinanciera (seq_idinstitucionf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_productorfinanciamiento OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_productortelefono
(
  int_idproductor integer NOT NULL,
  int_idtelefono integer NOT NULL,
  
  CONSTRAINT pk_tbl_dem_productortelefono PRIMARY KEY (int_idproductor, int_idtelefono),
  CONSTRAINT fk_tbl_dem_productortelefono_productor FOREIGN KEY (int_idproductor)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_productortelefono_telefono FOREIGN KEY (int_idtelefono)
      REFERENCES ministerio.tbl_dem_telefono (seq_idtelefono) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_productortelefono OWNER TO dba;

alter TABLE ministerio.tbl_dem_tipocoordenada
drop  int_idunidadproductiva ;



CREATE TABLE ministerio.tbl_dem_tipousotierra
(
  seq_idtipousotierra serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipousotierra PRIMARY KEY (seq_idtipousotierra)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tipousotierra OWNER TO dba;


ALTER TABLE ministerio.tbl_dem_unidadproductiva
add int_idtipousotierra integer,
Add CONSTRAINT fk_tbl_dem_unidadproductiva_usotierra FOREIGN KEY (int_idtipousotierra)
      REFERENCES ministerio.tbl_dem_tipousotierra (seq_idtipousotierra) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
      
CREATE TABLE ministerio.tbl_dem_tiposuelo
(
  seq_idtiposuelo serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tiposuelo PRIMARY KEY (seq_idtiposuelo)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tiposuelo OWNER TO dba;


ALTER TABLE ministerio.tbl_dem_unidadproductiva
add int_idtiposuelo integer,
Add CONSTRAINT fk_tbl_dem_unidadproductiva_suelo FOREIGN KEY (int_idtiposuelo)
      REFERENCES ministerio.tbl_dem_tiposuelo (seq_idtiposuelo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
      
      

CREATE TABLE ministerio.tbl_dem_tiporiego
(
  seq_idtiporiego serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tiporiego PRIMARY KEY (seq_idtiporiego)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tiporiego OWNER TO dba;


ALTER TABLE ministerio.tbl_dem_unidadproductiva
add int_idtiporiego integer,
Add CONSTRAINT fk_tbl_dem_unidadproductiva_riego FOREIGN KEY (int_idtiporiego)
      REFERENCES ministerio.tbl_dem_tiporiego (seq_idtiporiego) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      


ALTER TABLE ministerio.tbl_dem_tiporiego
add bol_tienriego boolean;


CREATE TABLE ministerio.tbl_dem_estadocivil
(
  seq_idestadocivil serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_estadocivil PRIMARY KEY (seq_idestadocivil)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_estadocivil OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_gradoinstruccion
(
  seq_idgradoinstruccion serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_gradoinstruccion PRIMARY KEY (seq_idgradoinstruccion)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_gradoinstruccion OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_nacionalidad
(
  seq_idnacionalidad serial NOT NULL,
  int_idpais integer NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_nacionalidad PRIMARY KEY (seq_idnacionalidad),
  CONSTRAINT fk_seq_idnacionalidad_pais FOREIGN KEY (int_idpais)
      REFERENCES ministerio.tbl_dem_pais (seq_idpais) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_nacionalidad OWNER TO dba;


CREATE TABLE ministerio.tbl_dem_productornatural
(
  int_idproductor integer NOT NULL,
  int_idnacionalidad integer NOT NULL,
  int_idgradoinstruccion integer NOT NULL,
  int_idestadocivil integer NOT NULL,
  str_primernombre character varying(20),
  str_primerapellido character varying(20),
  str_segundonombre character varying(20),
  str_segundoapellido character varying(20),
  bol_militante	boolean, 
  str_email character varying(50),
  
  CONSTRAINT pk_tbl_dem_productornatural PRIMARY KEY (int_idproductor),
  CONSTRAINT fk_seq_productornatural_productor FOREIGN KEY (int_idproductor)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_seq_productornatural_nacionalidad FOREIGN KEY (int_idnacionalidad)
      REFERENCES ministerio.tbl_dem_nacionalidad (seq_idnacionalidad) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_seq_productornatural_gradoinstruccion FOREIGN KEY (int_idgradoinstruccion)
      REFERENCES ministerio.tbl_dem_gradoinstruccion (seq_idgradoinstruccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_seq_productornatural_estadocivil FOREIGN KEY (int_idestadocivil)
      REFERENCES ministerio.tbl_dem_estadocivil (seq_idestadocivil) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_productornatural OWNER TO dba;

ALTER TABLE ministerio.tbl_dem_productornatural
add bol_masculino	boolean;

CREATE TABLE ministerio.tbl_dem_cicloproductivo
(
  seq_idcicloproductivo serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_cicloproductivo PRIMARY KEY (seq_idcicloproductivo)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_cicloproductivo OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_genero
(
  seq_idgenero serial NOT NULL,
  str_descripcion character varying(20),
  CONSTRAINT pk_tbl_dem_genero PRIMARY KEY (seq_idgenero)
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_genero OWNER TO dba;



ALTER TABLE ministerio.tbl_dem_productornatural 
drop bol_masculino,
add int_idgenero integer,
add CONSTRAINT fk_seq_productornatural_genero FOREIGN KEY (int_idgenero)
      REFERENCES ministerio.tbl_dem_genero (seq_idgenero) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      

CREATE TABLE ministerio.tbl_dem_representante
(
  seq_idrepresentante serial NOT NULL,
  str_cedula character varying(10),
  int_idnacionalidad integer NOT NULL,
  int_idgradoinstruccion integer NOT NULL,
  int_idestadocivil integer NOT NULL,
  str_primernombre character varying(20),
  str_primerapellido character varying(20),
  str_segundonombre character varying(20),
  str_segundoapellido character varying(20),
  dte_nacimiento date,
  str_email character varying(50),
  int_idgenero integer,
  CONSTRAINT pk_tbl_dem_representante PRIMARY KEY (seq_idrepresentante),
  CONSTRAINT fk_tbl_dem_representante_estadocivil FOREIGN KEY (int_idestadocivil)
      REFERENCES ministerio.tbl_dem_estadocivil (seq_idestadocivil) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_representante_genero FOREIGN KEY (int_idgenero)
      REFERENCES ministerio.tbl_dem_genero (seq_idgenero) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_representante_gradoinstruccion FOREIGN KEY (int_idgradoinstruccion)
      REFERENCES ministerio.tbl_dem_gradoinstruccion (seq_idgradoinstruccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_representante_nacionalidad FOREIGN KEY (int_idnacionalidad)
      REFERENCES ministerio.tbl_dem_nacionalidad (seq_idnacionalidad) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_representante OWNER TO dba;

CREATE TABLE ministerio.tbl_dem_productorjuridico
(
  int_idproductor integer NOT NULL,
  bol_publico boolean,
  CONSTRAINT pk_tbl_dem_productorjuridico PRIMARY KEY (int_idproductor),
  CONSTRAINT fk_seq_productorjuridico_productor FOREIGN KEY (int_idproductor)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_productorjuridico OWNER TO dba;

   

CREATE TABLE ministerio.tbl_dem_representanteproductorjuridico
(
  int_idproductor integer NOT NULL,
  int_idrepresentante integer NOT NULL,
  CONSTRAINT pk_tbl_dem_representanteproductorjuridico PRIMARY KEY (int_idproductor, int_idrepresentante),
  CONSTRAINT fk_representanteproductorjuridico_productor FOREIGN KEY (int_idproductor)
      REFERENCES ministerio.tbl_dem_productorjuridico (int_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_representanteproductorjuridico_representante FOREIGN KEY (int_idrepresentante)
      REFERENCES ministerio.tbl_dem_representante (seq_idrepresentante) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_representanteproductorjuridico OWNER TO dba;


alter TABLE ministerio.tbl_dem_unidadfuncional
add  str_descripcion character varying(20),
add  bol_sede boolean;

INSERT INTO ministerio.tbl_dem_estadocivil(
             str_descripcion)
    VALUES ( 'Solter@');
INSERT INTO ministerio.tbl_dem_estadocivil(
             str_descripcion)
    VALUES ( 'Casad@');
INSERT INTO ministerio.tbl_dem_estadocivil(
             str_descripcion)
    VALUES ( 'Viud@');
INSERT INTO ministerio.tbl_dem_estadocivil(
             str_descripcion)
    VALUES ( 'Divorsiad@');
    

ALTER TABLE ministerio.tbl_dem_eje
Add CONSTRAINT fk_tbl_dem_eje_pais FOREIGN KEY (int_idpais)
      REFERENCES ministerio.tbl_dem_pais (seq_idpais) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;	
	

ALTER TABLE ministerio.tbl_dem_productortelefono
DROP CONSTRAINT fk_tbl_dem_productortelefono_productor, 
ADD CONSTRAINT fk_tbl_dem_productortelefono_productor FOREIGN KEY (int_idproductor)
      REFERENCES public.tbl_dem_clientes (seq_idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE ministerio.tbl_dem_organizacion
ADD int_idsector integer,
ADD CONSTRAINT fk_tbl_dem_organizacion_sector FOREIGN KEY (int_idsector)
      REFERENCES ministerio.tbl_dem_sector (seq_idsector) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
ADD int_idpadre integer,
ADD str_rif character varying(15);

ALTER TABLE ministerio.tbl_dem_unidadfuncional
ALTER COLUMN  str_descripcion type character varying(40);

ALTER TABLE ministerio.tbl_dem_productorjuridico
ADD str_pagina character varying(100);

/************************25 noviembre 2010****************************************/


CREATE TABLE ministerio.tbl_dem_linderodireccion
(
  seq_idlindero serial NOT NULL,
  int_iddireccion integer NOT NULL,
  int_idorientacion integer NOT NULL,
  str_lindero text,
  CONSTRAINT pk_tbl_dem_linderodireccion PRIMARY KEY (seq_idlindero),
  CONSTRAINT fk_tbl_dem_linderodireccion FOREIGN KEY (int_iddireccion)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_linderodireccion OWNER TO sigesp;


CREATE TABLE ministerio.tbl_dem_tipoubicacion
(
  seq_idtipoubicacion serial NOT NULL,
  str_descripcion character varying(50),
  bol_propietario boolean,
  bol_usointerno  boolean,		
  CONSTRAINT pk_tbl_dem_tipoubicacion PRIMARY KEY (seq_idtipoubicacion)

)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_tipoubicacion OWNER TO sigesp;



ALTER TABLE ministerio.tbl_dem_direccion
ADD str_documentolegal character varying(30),
ADD int_idtipoubicacion integer;

ALTER TABLE ministerio.tbl_dem_direccion
ADD CONSTRAINT fk_tbl_dem_direccion_tipo FOREIGN KEY (int_idtipoubicacion)
      REFERENCES ministerio.tbl_dem_tipoubicacion (seq_idtipoubicacion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION ;
      
      
CREATE TABLE ministerio.tbl_dem_orientacion
(
  seq_idorientacion serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_orientacion PRIMARY KEY (seq_idorientacion)

)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_orientacion OWNER TO sigesp;

INSERT INTO ministerio.tbl_dem_orientacion(str_descripcion) 
VALUES('NORTE');
INSERT INTO ministerio.tbl_dem_orientacion(str_descripcion) 
VALUES('SUR');
INSERT INTO ministerio.tbl_dem_orientacion(str_descripcion) 
VALUES('ESTE');
INSERT INTO ministerio.tbl_dem_orientacion(str_descripcion) 
VALUES('OESTE');

ALTER TABLE ministerio.tbl_dem_linderodireccion
ADD CONSTRAINT fk_tbl_dem_linderodireccion_orientacion FOREIGN KEY (int_idorientacion)
      REFERENCES ministerio.tbl_dem_orientacion (seq_idorientacion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;      
      

ALTER TABLE ministerio.tbl_dem_superficieunidad
DROP CONSTRAINT fk_tbl_dem_superficieunidad_tiposervicio,
ADD CONSTRAINT fk_tbl_dem_superficieunidad_direccion FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;      
      
      
ALTER TABLE ministerio.tbl_dem_superficieunidad
DROP CONSTRAINT fk_tbl_dem_superficieunidad_tiposervicio,
ADD CONSTRAINT fk_tbl_dem_superficieunidad_direccion FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
CREATE TABLE ministerio.tbl_dem_direccionpropietario
(
  seq_iddirpro serial NOT NULL,	
  int_iddireccion integer NOT NULL,
  int_idcliente  integer not null,
  CONSTRAINT pk_tbl_dem_direccionpropietario PRIMARY KEY (seq_iddirpro),
  CONSTRAINT fk_tbl_dem_direccionpropietario_propietario FOREIGN KEY (int_iddireccion)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_direccionpropietario_cliente FOREIGN KEY (int_idcliente)
      REFERENCES public.tbl_dem_clientes (seq_idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE ministerio.tbl_dem_direccionpropietario OWNER TO sigesp;
      

DROP TABLE ministerio.tbl_dem_productorproduccion;

ALTER TABLE ministerio.tbl_dem_unidadproductiva
  ADD int_idproductor integer;

ALTER TABLE ministerio.tbl_dem_unidadproductiva  
  ADD CONSTRAINT fk_tbl_dem_unidadproductiva_productor FOREIGN KEY (int_idproductor)
      REFERENCES ministerio.tbl_dem_productor (seq_idproductor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION  
      

ALTER TABLE ministerio.tbl_dem_unidadproductiva
DROP  CONSTRAINT fk_tbl_dem_unidadproductiva_tiposervicio ;
ALTER TABLE ministerio.tbl_dem_unidadproductiva
ADD  CONSTRAINT fk_tbl_dem_unidadproductiva_tiposervicio FOREIGN KEY (int_idsectoragricola)
      REFERENCES basico.tbl_dem_sectoragricola (seq_idsectoragricola) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
DROP  TABLE ministerio.tbl_dem_sectoragricola;
      

CREATE OR REPLACE FUNCTION fu_numero_letras_entero(numero numeric)
  RETURNS text AS
$BODY$
DECLARE
     lnEntero INTEGER;
     lcRetorno TEXT;
     lnTerna INTEGER;
     lcMiles TEXT;
     lcCadena TEXT;
     lnUnidades INTEGER;
     lnDecenas INTEGER;
     lnCentenas INTEGER;
     lnFraccion INTEGER;
     lnSw INTEGER;
BEGIN
     lnEntero := FLOOR(numero)::INTEGER;--Obtenemos la parte Entera
     lnFraccion := FLOOR(((numero - lnEntero) * 100))::INTEGER;--Obtenemos la Fraccion del Monto
     lcRetorno := '';
     lnTerna := 1;
     IF lnEntero > 0 THEN
     lnSw := LENGTH(lnEntero);
     WHILE lnTerna <= lnSw LOOP
        -- Recorro terna por terna
        lcCadena = '';
        lnUnidades = lnEntero % 10;
        lnEntero = CAST(lnEntero/10 AS INTEGER);
        lnDecenas = lnEntero % 10;
        lnEntero = CAST(lnEntero/10 AS INTEGER);
        lnCentenas = lnEntero % 10;
        lnEntero = CAST(lnEntero/10 AS INTEGER);
    -- Analizo las unidades
       SELECT
         CASE /* UNIDADES */
           WHEN lnUnidades = 1 AND lnTerna = 1 THEN 'UNO ' || lcCadena
           WHEN lnUnidades = 1 AND lnTerna <> 1 THEN 'UN ' || lcCadena
           WHEN lnUnidades = 2 THEN 'DOS ' || lcCadena
           WHEN lnUnidades = 3 THEN 'TRES ' || lcCadena
           WHEN lnUnidades = 4 THEN 'CUATRO ' || lcCadena
           WHEN lnUnidades = 5 THEN 'CINCO ' || lcCadena
           WHEN lnUnidades = 6 THEN 'SEIS ' || lcCadena
           WHEN lnUnidades = 7 THEN 'SIETE ' || lcCadena
           WHEN lnUnidades = 8 THEN 'OCHO ' || lcCadena
           WHEN lnUnidades = 9 THEN 'NUEVE ' || lcCadena
           ELSE lcCadena
          END INTO lcCadena;
          /* UNIDADES */
    -- Analizo las decenas
    SELECT
    CASE /* DECENAS */
      WHEN lnDecenas = 1 THEN
        CASE lnUnidades
          WHEN 0 THEN 'DIEZ '
          WHEN 1 THEN 'ONCE '
          WHEN 2 THEN 'DOCE '
          WHEN 3 THEN 'TRECE '
          WHEN 4 THEN 'CATORCE '
          WHEN 5 THEN 'QUINCE '
          ELSE 'DIECI' || lcCadena
        END
      WHEN lnDecenas = 2 AND lnUnidades = 0 THEN 'VEINTE ' || lcCadena
      WHEN lnDecenas = 2 AND lnUnidades <> 0 THEN 'VEINTI' || lcCadena
      WHEN lnDecenas = 3 AND lnUnidades = 0 THEN 'TREINTA ' || lcCadena
      WHEN lnDecenas = 3 AND lnUnidades <> 0 THEN 'TREINTA Y ' || lcCadena
      WHEN lnDecenas = 4 AND lnUnidades = 0 THEN 'CUARENTA ' || lcCadena
      WHEN lnDecenas = 4 AND lnUnidades <> 0 THEN 'CUARENTA Y ' || lcCadena
      WHEN lnDecenas = 5 AND lnUnidades = 0 THEN 'CINCUENTA ' || lcCadena
      WHEN lnDecenas = 5 AND lnUnidades <> 0 THEN 'CINCUENTA Y ' || lcCadena
      WHEN lnDecenas = 6 AND lnUnidades = 0 THEN 'SESENTA ' || lcCadena
      WHEN lnDecenas = 6 AND lnUnidades <> 0 THEN 'SESENTA Y ' || lcCadena
      WHEN lnDecenas = 7 AND lnUnidades = 0 THEN 'SETENTA ' || lcCadena
      WHEN lnDecenas = 7 AND lnUnidades <> 0 THEN 'SETENTA Y ' || lcCadena
      WHEN lnDecenas = 8 AND lnUnidades = 0 THEN 'OCHENTA ' || lcCadena
      WHEN lnDecenas = 8 AND lnUnidades <> 0 THEN 'OCHENTA Y ' || lcCadena
      WHEN lnDecenas = 9 AND lnUnidades = 0 THEN 'NOVENTA ' || lcCadena
      WHEN lnDecenas = 9 AND lnUnidades <> 0 THEN 'NOVENTA Y ' || lcCadena
      ELSE lcCadena
    END INTO lcCadena; /* DECENAS */
    -- Analizo las centenas
    SELECT
    CASE /* CENTENAS */
      WHEN lnCentenas = 1 AND lnUnidades = 0 AND lnDecenas = 0 THEN 'CIEN ' || lcCadena
      WHEN lnCentenas = 1 AND NOT(lnUnidades = 0 AND lnDecenas = 0) THEN 'CIENTO ' || lcCadena
      WHEN lnCentenas = 2 THEN 'DOSCIENTOS ' || lcCadena
      WHEN lnCentenas = 3 THEN 'TRESCIENTOS ' || lcCadena
      WHEN lnCentenas = 4 THEN 'CUATROCIENTOS ' || lcCadena
      WHEN lnCentenas = 5 THEN 'QUINIENTOS ' || lcCadena
      WHEN lnCentenas = 6 THEN 'SEISCIENTOS ' || lcCadena
      WHEN lnCentenas = 7 THEN 'SETECIENTOS ' || lcCadena
      WHEN lnCentenas = 8 THEN 'OCHOCIENTOS ' || lcCadena
      WHEN lnCentenas = 9 THEN 'NOVECIENTOS ' || lcCadena
      ELSE lcCadena
    END INTO lcCadena;/* CENTENAS */
    -- Analizo la terna
    SELECT
    CASE /* TERNA */
      WHEN lnTerna = 1 THEN lcCadena
      WHEN lnTerna = 2 AND (lnUnidades + lnDecenas + lnCentenas <> 0) THEN lcCadena || ' MIL '
      WHEN lnTerna = 3 AND (lnUnidades + lnDecenas + lnCentenas <> 0) AND
        lnUnidades = 1 AND lnDecenas = 0 AND lnCentenas = 0 THEN lcCadena || ' MILLON '
      WHEN lnTerna = 3 AND (lnUnidades + lnDecenas + lnCentenas <> 0) AND
        NOT (lnUnidades = 1 AND lnDecenas = 0 AND lnCentenas = 0) THEN lcCadena || ' MILLONES '
      WHEN lnTerna = 4 AND (lnUnidades + lnDecenas + lnCentenas <> 0) THEN lcCadena || ' MIL MILLONES '
      ELSE ''
    END INTO lcCadena;/* TERNA */

    --Retornamos los Valores Obtenidos
    lcRetorno = lcCadena  || lcRetorno;
    lnTerna = lnTerna + 1;
    END LOOP;
  END IF;
  IF lnTerna = 1 THEN
    lcRetorno := 'CERO';
  END IF;
  lcRetorno := RTRIM(lcRetorno);
RETURN lcRetorno;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION fu_numero_letras_entero(numeric) OWNER TO dba;
GRANT EXECUTE ON FUNCTION fu_numero_letras_entero(numeric) TO public;
GRANT EXECUTE ON FUNCTION fu_numero_letras_entero(numeric) TO dba;
COMMENT ON FUNCTION fu_numero_letras_entero(numeric) IS 'Funcion para Convertir el Monto Numerico a Letras';
      

ALTER TABLE administracion.tbl_dem_contrato
ALTER int_idplanfinanciamiento DROP NOT NULL;


CREATE TABLE basico.tbl_dem_clasesilo
(
  seq_idclasesilo serial NOT NULL,
  int_idtiposilo integer not null,
  str_descripcion character varying(100),
  CONSTRAINT pk_tbl_dem_clasesilo PRIMARY KEY (seq_idclasesilo),
  CONSTRAINT fk_tbl_dem_clasesilo_tipo FOREIGN KEY (int_idtiposilo)
      REFERENCES basico.tbl_dem_tiposilo (seq_idtiposilo) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE basico.tbl_dem_clasesilo OWNER TO dba;

ALTER TABLE basico.tbl_dem_silo
ADD int_idclase integer,
ADD dbl_capacidadoper numeric(8,2),

ADD CONSTRAINT fk_tbl_dem_silo_clase FOREIGN KEY (int_idclase)
      REFERENCES basico.tbl_dem_clasesilo (seq_idclasesilo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
      
CREATE TABLE basico.tbl_dem_funcion_trabajador
(
  seq_idfunciontraba serial NOT NULL,
  str_descripcion character varying(100) NOT NULL,
  CONSTRAINT pk_tbl_dem_funcion_trabajador PRIMARY KEY (seq_idfunciontraba)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE basico.tbl_dem_funcion_trabajador OWNER TO dba;


CREATE TABLE basico.tbl_dem_cargo_trabajador
(
  seq_idcargotraba serial NOT NULL,
  str_descripcion character varying(100) NOT NULL,
  CONSTRAINT pk_tbl_dem_cargo_trabajador PRIMARY KEY (seq_idcargotraba)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE basico.tbl_dem_cargo_trabajador OWNER TO dba;

TRUNCATE TABLE basico.tbl_dem_trabajador RESTART IDENTITY CASCADE;

DELETE FROM basico.tbl_dem_trabajador;
DELETE FROM tbl_dem_tipo_trabajador;

INSERT INTO tbl_dem_tipo_trabajador(str_descripcion) VALUES('INTERNO');
INSERT INTO tbl_dem_tipo_trabajador(str_descripcion) VALUEs('EVENTUAL');
INSERT INTO tbl_dem_tipo_trabajador(str_descripcion) VALUES('FORANEO');

INSERT INTO basico.tbl_dem_funcion_trabajador(str_descripcion) VALUES('OPERADOR');
INSERT INTO basico.tbl_dem_funcion_trabajador(str_descripcion) VALUES('CHOFER/CONDUCTOR');
INSERT INTO basico.tbl_dem_funcion_trabajador(str_descripcion) VALUES('TECNICO DE CAMPO');
INSERT INTO basico.tbl_dem_funcion_trabajador(str_descripcion) VALUES('MECANICO');
INSERT INTO basico.tbl_dem_funcion_trabajador(str_descripcion) VALUES('ELECTRICISTA');
INSERT INTO basico.tbl_dem_funcion_trabajador(str_descripcion) VALUES('COMUNITARIA');
INSERT INTO basico.tbl_dem_funcion_trabajador(str_descripcion) VALUES('COBRADOR');


ALTER TABLE basico.tbl_dem_trabajador
ADD int_idcargo integer,
ADD CONSTRAINT fk_tbl_dem_trabajador_cargo FOREIGN KEY (int_idcargo)
      REFERENCES basico.tbl_dem_cargo_trabajador (seq_idcargotraba) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE basico.tbl_dem_trabajador_funcion
(
  int_idfunciontraba integer NOT NULL,
  int_idtrabajador integer not null,
  CONSTRAINT pk_tbl_dem_trabajador_funcion PRIMARY KEY (int_idfunciontraba, int_idtrabajador),
  CONSTRAINT fk_tbl_dem_trabajador_funcion_funcion FOREIGN KEY (int_idfunciontraba)
      REFERENCES basico.tbl_dem_funcion_trabajador(seq_idfunciontraba) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_tbl_dem_trabajador_funcion_trabajador FOREIGN KEY (int_idtrabajador)
      REFERENCES basico.tbl_dem_trabajador (seq_idtrabajador) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT  
)
WITH (
  OIDS=FALSE
);
ALTER TABLE basico.tbl_dem_trabajador_funcion OWNER TO dba;

CREATE TABLE basico.tbl_dem_tipoverisuelo
(
  seq_idtipoverisuelo serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipoverisuelo PRIMARY KEY (seq_idtipoverisuelo)
)

ALTER TABLE ministerio.tbl_dem_unidadproductiva

    ADD dbl_distanciaempresa numeric(8,2),
    add dbl_supeactuapro  numeric(8,2),
    add dbl_supepoteapro numeric(8,2),
    add dbl_superesefore numeric(8,2),
    add int_nrolotes integer,
    drop int_idtiporiego,
    add int_idtipoverisuelo integer,
add  CONSTRAINT fk_tbl_dem_unidadproductiva_verisuelo FOREIGN KEY (int_idtipoverisuelo)
      REFERENCES basico.tbl_dem_tipoverisuelo (seq_idtipoverisuelo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
      
      
CREATE TABLE basico.tbl_dem_tipoverisuelo
(
  seq_idtipoverisuelo serial NOT NULL,
  str_descripcion character varying(50),
  CONSTRAINT pk_tbl_dem_tipoverisuelo PRIMARY KEY (seq_idtipoverisuelo)
)

ALTER TABLE ministerio.tbl_dem_unidadproductiva

    ADD dbl_distanciaempresa numeric(8,2),
    add dbl_supeactuapro  numeric(8,2),
    add dbl_supepoteapro numeric(8,2),
    add dbl_superesefore numeric(8,2),
    add int_nrolotes integer,
    drop int_idtiporiego,
    add int_idtipoverisuelo integer,
add  CONSTRAINT fk_tbl_dem_unidadproductiva_verisuelo FOREIGN KEY (int_idtipoverisuelo)
      REFERENCES basico.tbl_dem_tipoverisuelo (seq_idtipoverisuelo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


CREATE TABLE ministerio.tbl_dem_tiporiegounidadproductiva
(
  seq_idtiporiegunidprod serial not null,
  int_idtiporiego integer NOT NULL,
  int_idunidadproductiva integer not null,
  dbl_superficie numeric(8,2),	 
  CONSTRAINT pk_tbl_dem_tiporiegounidadproductiva PRIMARY KEY (seq_idtiporiegunidprod),
  CONSTRAINT fk_tbl_dem_tiporiegounidadproductiva_unico UNIQUE (int_idtiporiego,int_idunidadproductiva),
CONSTRAINT fk_tbl_dem_tiporiegounidadproductiva_tiporiego FOREIGN KEY (int_idtiporiego)
      REFERENCES ministerio.tbl_dem_tiporiego (seq_idtiporiego) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT fk_tbl_dem_tiporiegounidadproductiva_unidadprod FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_unidadproductiva (seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)    


ALTER TABLE ministerio.tbl_dem_productorjuridico
ADD  str_email character varying(100),
ALTER str_pagina type character varying(250);      

CREATE TABLE ministerio.tbl_dem_usos
(
  seq_idusos serial NOT NULL,
  str_descripcion character varying(20),
  CONSTRAINT pk_tbl_dem_usos PRIMARY KEY (seq_idusos)
)
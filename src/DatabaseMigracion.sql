/************CREACION Y MODIFCACUION DE TABLAS****************/
ALTER TABLE ministerio.tbl_dem_productortelefono
DROP CONSTRAINT fk_tbl_dem_productortelefono_productor, 
ADD CONSTRAINT fk_tbl_dem_productortelefono_productor FOREIGN KEY (int_idproductor)
      REFERENCES public.tbl_dem_clientes (seq_idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
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
ALTER TABLE ministerio.tbl_dem_productorjuridico OWNER TO sigesp;

ALTER TABLE ministerio.tbl_dem_organizacion
ADD int_idsector integer,
ADD CONSTRAINT fk_tbl_dem_organizacion_sector FOREIGN KEY (int_idsector)
      REFERENCES ministerio.tbl_dem_sector (seq_idsector) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
ADD int_idpadre integer,
ADD str_rif character varying(15);

ALTER TABLE ministerio.tbl_dem_unidadfuncional
ALTER COLUMN  str_descripcion type character varying(40);

/********************DATOS TEMPORALES****************/

ALTER TABLE ministerio.tbl_dem_pais
ADD str_idmigracion character(3);

ALTER TABLE ministerio.tbl_dem_estado
ADD str_idmigracion character(3);

ALTER TABLE ministerio.tbl_dem_municipio
ADD str_idmigracion character(3);

ALTER TABLE ministerio.tbl_dem_parroquia
ADD str_idmigracion character(3);

ALTER TABLE ministerio.tbl_dem_direccion
ADD int_idmigracion integer;

ALTER TABLE ministerio.tbl_dem_organizacion
ADD int_idmigracion integer;

ALTER TABLE ministerio.tbl_dem_unidadfuncional
ADD str_idmigracion  character(4);

ALTER TABLE ministerio.tbl_dem_telefono
ADD int_idmigracion  integer;


/*************************Migracion***********************/

/**************************LIMPIEZA DE DATOS***************************/

/*
DELETE FROM ministerio.tbl_dem_productortelefono;

DELETE FROM ministerio.tbl_dem_telefono;

DELETE FROM ministerio.tbl_dem_productornatural;

DELETE FROM ministerio.tbl_dem_estadocivil;

DELETE FROM ministerio.tbl_dem_gradoinstruccion;
DELETE FROM ministerio.tbl_dem_nacionalidad;

DELETE FROM ministerio.tbl_dem_productorjuridico;

DELETE FROM ministerio.tbl_dem_productorproduccion;

DELETE FROM ministerio.tbl_dem_unidadproductiva;

DELETE FROM ministerio.tbl_dem_productor;

DELETE FROM ministerio.tbl_dem_organizacion;

DELETE FROM ministerio.tbl_dem_direccion;

DELETE FROM ministerio.tbl_dem_unidadfuncional;

DELETE FROM ministerio.tbl_dem_sector;

DELETE FROM ministerio.tbl_dem_parroquia;

DELETE FROM ministerio.tbl_dem_municipio;

DELETE FROM ministerio.tbl_dem_estado;

DELETE FROM ministerio.tbl_dem_pais;

DELETE FROM ministerio.tbl_dem_tipounidad;*/


TRUNCATE TABLE ministerio.tbl_dem_productortelefono CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_telefono RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_productornatural CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_estadocivil RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_gradoinstruccion RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_nacionalidad RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_productorjuridico RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_productorproduccion CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_unidadproductiva RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_productor RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_organizacion RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_direccion RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_unidadfuncional RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_sector RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_parroquia RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_municipio RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_estado RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_pais RESTART IDENTITY CASCADE;
TRUNCATE TABLE ministerio.tbl_dem_tipounidad RESTART IDENTITY CASCADE;

/*ALTER SEQUENCE ministerio.tbl_dem_unidadfuncional_seq_idunidadfuncional_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_pais_seq_idpais_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_estadocivil_seq_idestadocivil_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_nacionalidad_seq_idnacionalidad_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_estado_seq_idestado_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_pais_seq_idpais_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_municipio_seq_idmunicipio_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_parroquia_seq_idparroquia_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_sector_seq_idsector_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_gradoinstruccion_seq_idgradoinstruccion_seq START WITH 1;
ALTER SEQUENCE ministerio.tbl_dem_tipounidad_seq_idtipounidad_seq START WITH 1;*/

/*************************************************************/

INSERT INTO ministerio.tbl_dem_estadocivil(Str_descripcion)
VALUES ('Soltero');
INSERT INTO ministerio.tbl_dem_estadocivil(str_descripcion)
VALUES ('Casado');
INSERT INTO ministerio.tbl_dem_estadocivil(str_descripcion)
    VALUES ('Divorsiado');        
INSERT INTO ministerio.tbl_dem_estadocivil(str_descripcion)
    VALUES ('Viudo');

INSERT INTO ministerio.tbl_dem_gradoinstruccion(str_descripcion)
    VALUES ('Desconocido');

INSERT INTO ministerio.tbl_dem_pais(str_descripcion, bol_local, str_idmigracion)
VALUES ('VENEZUELA',TRUE,'058');

INSERT INTO ministerio.tbl_dem_nacionalidad(int_idpais, str_descripcion)
    VALUES (1,'VENEZOLANO');


INSERT INTO ministerio.tbl_dem_tipounidad(
           str_descripcion, bol_administrativo)
VALUES ('Sede Administrativa', true);

/**************************INSERCION DE DATOS*******************************/


INSERT INTO ministerio.tbl_dem_pais(str_descripcion, bol_local, str_idmigracion)
select despai, false, codpai from sigesp.sigesp_pais
where not codpai = '---' AND not codpai = '058';



/********************ESTADO********************/

INSERT INTO ministerio.tbl_dem_estado(str_descripcion, int_idpais,  str_idmigracion)
select desest, seq_idpais, codest 
from sigesp.sigesp_estados estado
INNER JOIN sigesp.sigesp_pais pais on pais.codpai = estado.codpai
INNER JOIN ministerio.tbl_dem_pais on pais.codpai = str_idmigracion
where not codest = '---';

/********************MUNICIPIO*********************/

INSERT INTO ministerio.tbl_dem_municipio(str_descripcion, int_idestado,  str_idmigracion)
select denmun, seq_idestado, codmun
from sigesp.sigesp_municipio municipio
INNER JOIN ministerio.tbl_dem_pais pais on municipio.codpai = pais.str_idmigracion
INNER JOIN ministerio.tbl_dem_estado estado on municipio.codest = estado.str_idmigracion and 
pais.seq_idpais = estado.int_idpais
where not codmun = '---';

/********************PARROQUIA********************/

INSERT INTO ministerio.tbl_dem_parroquia(str_descripcion, int_idmunicipio,  str_idmigracion)
SELECT denpar, seq_idmunicipio, codpar
FROM sigesp.sigesp_parroquia parroquia
INNER JOIN ministerio.tbl_dem_pais pais on parroquia.codpai = pais.str_idmigracion
INNER JOIN ministerio.tbl_dem_estado estado on parroquia.codest = estado.str_idmigracion and 
pais.seq_idpais = estado.int_idpais
INNER JOIN ministerio.tbl_dem_municipio municipio on parroquia.codmun = municipio.str_idmigracion
and estado.seq_idestado = municipio.int_idestado
WHERE not codpar = '---';

/********************SECTOR***********************/

INSERT INTO ministerio.tbl_dem_sector(str_descripcion, int_idparroquia)
select distinct trim(str_sector), seq_idparroquia
FROM public.tbl_dem_direccion sector
INNER JOIN ministerio.tbl_dem_pais pais on pais.str_idmigracion = '058'
INNER JOIN ministerio.tbl_dem_estado estado on sector.str_codest = estado.str_idmigracion
and estado.int_idpais = pais.seq_idpais
INNER JOIN ministerio.tbl_dem_municipio municipio on sector.str_codmun = municipio.str_idmigracion
and int_idestado = seq_idestado 
INNER JOIN ministerio.tbl_dem_parroquia parroquia on sector.str_codpar = parroquia.str_idmigracion
and int_idmunicipio = seq_idmunicipio;

INSERT INTO ministerio.tbl_dem_sector(str_descripcion, int_idparroquia)
select distinct str_sector, seq_idparroquia
from tbl_dem_comite_mecanizacion sector
INNER JOIN ministerio.tbl_dem_pais pais on pais.str_idmigracion = '058'
INNER JOIN ministerio.tbl_dem_estado estado on sector.str_codest = estado.str_idmigracion
and estado.int_idpais = pais.seq_idpais
INNER JOIN ministerio.tbl_dem_municipio municipio on sector.str_codmun = municipio.str_idmigracion
and int_idestado = seq_idestado 
INNER JOIN ministerio.tbl_dem_parroquia parroquia on sector.str_codpar = parroquia.str_idmigracion
and int_idmunicipio = seq_idmunicipio
LEFT JOIN ministerio.tbl_dem_sector existe ON existe.str_descripcion = sector.str_sector and existe.int_idparroquia =seq_idparroquia
WHERE existe.str_descripcion is nULL;

/********************DIRECCION*******************/

INSERT INTO ministerio.tbl_dem_direccion(int_idsector,str_descripcion, str_referencia, int_idmigracion)

select distinct seq_idsector, str_infadicional, str_ptoreferencia, seq_iddireccion
FROM public.tbl_dem_direccion direccion
INNER JOIN ministerio.tbl_dem_pais pais on pais.str_idmigracion = '058'
INNER JOIN ministerio.tbl_dem_estado estado on direccion.str_codest = estado.str_idmigracion
and estado.int_idpais = pais.seq_idpais
INNER JOIN ministerio.tbl_dem_municipio municipio on direccion.str_codmun = municipio.str_idmigracion
and int_idestado = seq_idestado 
INNER JOIN ministerio.tbl_dem_parroquia parroquia on direccion.str_codpar = parroquia.str_idmigracion
and int_idmunicipio = seq_idmunicipio
INNER JOIN ministerio.tbl_dem_sector  sector ON str_sector = sector.str_descripcion 
and int_idparroquia = seq_idparroquia;

UPDATE tbl_dem_clientes SET str_email = (str_sector || ' ' || str_infadicional || ', ' || str_ptoreferencia ||', Estado ' || estado)
FROM public.viw_direcccion_prod_rpt
Where int_idcliente=seq_idcliente;

/********************UNIDAD PRODUCCION***/

INSERT INTO ministerio.tbl_dem_unidadproductiva(
            int_iddireccion, str_nombre)
SELECT ministerio.tbl_dem_direccion.seq_iddireccion, 'S/N' as nombre
FROM public.tbl_dem_direccion
INNER JOIN ministerio.tbl_dem_direccion ON int_idmigracion =public.tbl_dem_direccion.seq_iddireccion
where str_codpar <> '---' and int_tipo>1;


/********************ORGANIZACION**********/

INSERT INTO ministerio.tbl_dem_organizacion(
             int_idtipoorganizacion, str_nombre, int_idsector, 
             int_idmigracion)
SELECT 1 as tipo, direccion.str_descripcion, seq_idsector, seq_idcomite 
from tbl_dem_comite_mecanizacion direccion
INNER JOIN ministerio.tbl_dem_pais pais on pais.str_idmigracion = '058'
INNER JOIN ministerio.tbl_dem_estado estado on direccion.str_codest = estado.str_idmigracion
and estado.int_idpais = pais.seq_idpais
INNER JOIN ministerio.tbl_dem_municipio municipio on direccion.str_codmun = municipio.str_idmigracion
and int_idestado = seq_idestado 
INNER JOIN ministerio.tbl_dem_parroquia parroquia on direccion.str_codpar = parroquia.str_idmigracion
and int_idmunicipio = seq_idmunicipio
INNER JOIN ministerio.tbl_dem_sector  sector ON str_sector = sector.str_descripcion 
and int_idparroquia = seq_idparroquia;

/********************UNIDAD FUNCIONAL**********/



INSERT INTO ministerio.tbl_dem_unidadfuncional(str_descripcion, bol_sede, str_idmigracion, int_idtipounidad) 
 SELECT desubifis, true, codubifis, 1 FROM sigesp.sno_ubicacion_fisica;

/***********************************Producto***********************************/
/*****sustituir 3 por id de la sede*****/

INSERT INTO ministerio.tbl_dem_productor(
            seq_idproductor, int_idunidadfuncional, bol_organizado)
Select seq_idcliente, 3, false
FROM public.tbl_dem_clientes;

UPDATE ministerio.tbl_dem_productor
SET bol_organizado = true and ministerio.tbl_dem_productor.int_idorganizacion = seq_idorganizacion 
FROM tbl_dem_cliente_comite
LEFT JOIN ministerio.tbl_dem_organizacion ON int_idmigracion =int_idcomite
Where tbl_dem_cliente_comite.int_idcliente = ministerio.tbl_dem_productor.seq_idproductor;

INSERT INTO ministerio.tbl_dem_productornatural(
            int_idproductor, int_idnacionalidad, int_idgradoinstruccion, 
            int_idestadocivil, str_primernombre, str_primerapellido, str_segundonombre, 
            str_segundoapellido)
SELECT int_idcliente, 1, 1, 1, str_nombre1, str_apellido1, str_nombre2, str_apellido2
  FROM public.tbl_dem_personas;

INSERT INTO ministerio.tbl_dem_productorjuridico(
            int_idproductor, bol_publico)
SELECT int_idcliente, CASE WHEN int_idtipoente =1 then TRUE ELSE FALSE END 
  FROM public.tbl_dem_entes;

UPDATE ministerio.tbl_dem_productor 
SET dte_nacimiento = tbl_dem_cooperativa.dtm_feccre, int_idorganizacion = seq_idorganizacion, bol_organizado = true 
FROM public.tbl_dem_cooperativa
LEFT JOIN ministerio.tbl_dem_organizacion ON int_idmigracion =int_idcomite
Where tbl_dem_cooperativa.int_idcliente = ministerio.tbl_dem_productor.seq_idproductor;

INSERT INTO ministerio.tbl_dem_productorjuridico(
            int_idproductor, bol_publico)
SELECT int_idcliente, FALSE
  FROM public.tbl_dem_cooperativa;

INSERT INTO ministerio.tbl_dem_productorproduccion(seq_idproductor, int_idunidadproductiva)
SELECT int_idcliente, seq_idunidadproductiva
FROM ministerio.tbl_dem_unidadproductiva 
INNER JOIN ministerio.tbl_dem_direccion ON ministerio.tbl_dem_direccion.seq_iddireccion = ministerio.tbl_dem_unidadproductiva.int_iddireccion
INNER JOIN public.tbl_dem_direccion ON ministerio.tbl_dem_direccion.int_idmigracion =public.tbl_dem_direccion.seq_iddireccion;
  
/**************************Telefono********************************/

INSERT INTO ministerio.tbl_dem_telefono(
             int_idcodare, str_numtel, bol_celular, int_idmigracion)
select int_idcodare, str_numtel, case when int_tiptel = 0 then true else false end, seq_idtel
FROM tbl_dem_telefono
INNER JOIN tbl_dem_cod_telefono ON seq_idcodare =int_idcodare;

INSERT INTO ministerio.tbl_dem_productortelefono(int_idproductor, int_idtelefono)
select int_idcliente, seq_idtelefono
FROM tbl_dem_telefono
INNER JOIN tbl_dem_cod_telefono ON seq_idcodare =int_idcodare
INNER JOIN ministerio.tbl_dem_telefono ON int_idmigracion = seq_idtel
INNER JOIN public.tbl_dem_clientes ON int_idcliente=seq_idcliente
WHERE not int_idcliente is null;


/********************LIBERAR TEMPORALES****************/

ALTER TABLE ministerio.tbl_dem_pais
DROP str_idmigracion;

ALTER TABLE ministerio.tbl_dem_estado
DROP str_idmigracion;

ALTER TABLE ministerio.tbl_dem_municipio
DROP str_idmigracion;

ALTER TABLE ministerio.tbl_dem_parroquia
DROP str_idmigracion;

ALTER TABLE ministerio.tbl_dem_direccion
DROP int_idmigracion;

ALTER TABLE ministerio.tbl_dem_organizacion
DROP int_idmigracion;

ALTER TABLE ministerio.tbl_dem_unidadfuncional
DROP str_idmigracion;

ALTER TABLE ministerio.tbl_dem_telefono
DROP int_idmigracion;



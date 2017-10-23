
CREATE TABLE public.tbl_dem_tipo_consumo
(
  seq_idtipcon serial,
  str_descripcion character varying(30) NOT NULL,
  bol_abono boolean,
  CONSTRAINT tbl_dem_tipo_consumo_pkey PRIMARY KEY (seq_idtipcon)
)
WITHOUT OIDS;
ALTER TABLE public.tbl_dem_tipo_consumo OWNER TO sigesp;


CREATE TABLE administracion.tbl_dem_consumocredito
(
  seq_idconcre serial NOT NULL,
  int_idnota integer NOT NULL,
  int_idtipcon integer NOT NULL,
  str_control character varying(5),
  int_iddocumento integer,
  dbl_monto numeric(12,4),
  dtm_fecha date,
  str_codemp character(4) DEFAULT '0001'::bpchar,
  str_sede character(4),
  str_concepto character varying(30),
  bol_anulado boolean,
  CONSTRAINT pk_tbl_dem_consumocredito PRIMARY KEY (seq_idconcre),
  CONSTRAINT fk_tbl_dem_consumocredito_cons FOREIGN KEY (int_idtipcon)
      REFERENCES public.tbl_dem_tipo_consumo (seq_idtipcon) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_consumocredito_fact FOREIGN KEY (int_iddocumento)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_consumocredito_nota FOREIGN KEY (int_idnota)
      REFERENCES administracion.tbl_dem_documentofiscal (seq_iddocumento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_consumocredito OWNER TO sigesp;



DELETE FROM administracion.tbl_dem_documentofiscaldetalle;
DELETE FROM administracion.tbl_dem_notapago;
DELETE FROM administracion.tbl_dem_consumocredito;
DELETE FROM administracion.tbl_dem_nota;
DELETE FROM administracion.tbl_dem_documentoimpuesto;

DELETE FROM administracion.tbl_dem_cierrediariodocumento;
DELETE FROM administracion.tbl_dem_formapago;
DELETE FROM administracion.tbl_dem_libroventadetalle;
DELETE FROM administracion.tbl_dem_libroventa;
DELETE FROM administracion.tbl_dem_recibo;
DELETE FROM administracion.tbl_dem_debitointerno;

DELETE FROM administracion.tbl_dem_documentofiscal;


ALTER TABLE administracion.tbl_dem_deposito
ADD dtm_fecharecepcion date;
  

ALTER TABLE administracion.tbl_dem_documentofiscaldetalle
DROP CONSTRAINT fk_tbl_dem_documentofiscaldetalle_servicio;

ALTER TABLE administracion.tbl_dem_documentofiscaldetalle
ADD CONSTRAINT fk_tbl_dem_documentofiscaldetalle_producto FOREIGN KEY (int_idservicio)
      REFERENCES basico.tbl_dem_producto (seq_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE administracion.tbl_dem_documentofiscal
DROP CONSTRAINT fk_tbl_dem_documentofiscal_direccion;


ALTER TABLE administracion.tbl_dem_documentofiscal
DROP int_iddireccion;

ALTER TABLE administracion.tbl_dem_documentofiscal
ADD str_direccion character varying(50);


ALTER TABLE administracion.tbl_dem_formapago
ALTER str_numcue type character varying(25),
ALTER str_numdoc type character varying(30);

ALTER TABLE sigesp.scb_ctabanco
ALTER ctaban TYPE  character varying(25); 

DELETE FROM administracion.tbl_dem_tipo_forma_pago where str_descripcion like '%Nota Credito%';

INSERT INTO administracion.tbl_dem_tipo_forma_pago(
            str_descripcion, bol_usadocu, bol_usacuen, 
            bol_usaban, bol_nota)
    VALUES ('Retención IVA',true, false,false, false);
            

INSERT INTO administracion.tbl_dem_tipo_forma_pago(
            str_descripcion, bol_usadocu, bol_usacuen, 
            bol_usaban, bol_nota)
    VALUES ('Retención ISLR',true, false,false, false);
    
//*********Cambios en Contrato ****************//
    
ALTER TABLE administracion.tbl_dem_consumocredito
ALTER str_control DROP NOT NULL;
      
ALTER TABLE administracion.tbl_dem_contrato
  DROP CONSTRAINT fk_tbl_dem_contrato_direccion,
  DROP CONSTRAINT fk_tbl_dem_contrato_solicitud;

ALTER TABLE administracion.tbl_dem_contrato
  DROP bol_pagado,
  DROP int_idsolicitud,
  DROP int_idtiposolicitud,
  DROP int_iddireccion;

ALTER TABLE administracion.tbl_dem_contrato
  ADD CONSTRAINT fk_tbl_dem_contrato_solicitud FOREIGN KEY (int_idsolicitud)
      REFERENCES gestion.tbl_dem_solicitud (seq_idsolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;  

ALTER TABLE administracion.tbl_dem_detallecontrato
DROP CONSTRAINT fk_tbl_dem_contrato_servicio_ser,
ADD int_idproducto integer;

ALTER TABLE administracion.tbl_dem_detallecontrato
DROP  int_idservicio;

ALTER TABLE administracion.tbl_dem_detallecontrato
ADD CONSTRAINT fk_tbl_dem_contrato_prodcuto FOREIGN KEY (int_idproducto)
      REFERENCES basico.tbl_dem_producto (seq_idproducto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;   
      
CREATE TABLE administracion.tbl_dem_contratomecanizado
(
  int_idcontrato integer NOT NULL,
  int_idunidadproductiva integer NOT NULL,
  int_idsolicitudmecanizado integer NOT NULL,
  CONSTRAINT pk_tbl_dem_contratomecanizado PRIMARY KEY (int_idcontrato),
  CONSTRAINT fk_tbl_dem_contratomecanizado_contrato FOREIGN KEY (int_idcontrato)
      REFERENCES administracion.tbl_dem_contrato(seq_idcontrato) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_contratomecanizado_unidadprod FOREIGN KEY (int_idunidadproductiva)
      REFERENCES ministerio.tbl_dem_unidadproductiva (seq_idunidadproductiva) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_contratomecanizado_solicitud FOREIGN KEY (int_idsolicitudmecanizado)
      REFERENCES gestion.tbl_dem_solicitudmecanizado (int_idsolicitud) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_contratomecanizado OWNER TO sigesp;


ALTER TABLE administracion.tbl_dem_contratomecanizado
ALTER int_idsolicitudmecanizado DROP NOT NULL;


ALTER TABLE administracion.tbl_dem_contratomecanizado
ALTER int_idsolicitudmecanizado DROP NOT NULL;


DROP TRIGGER trg_actualiza_estado_servicio_en_contrato ON administracion.tbl_dem_detallecontrato;  

ALTER TABLE administracion.tbl_dem_clienteadministrativo
  ADD CONSTRAINT fk_tbl_dem_contratomecanizado_unico UNIQUE (int_idcliente);
      

}
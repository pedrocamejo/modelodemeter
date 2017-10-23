
ALTER TABLE gestion.tbl_dem_solicituddetalle
ADD bol_prestado boolean default false;

ALTER TABLE administracion.tbl_dem_detallecontrato
ADD bol_prestado boolean default false;

ALTER TABLE gestion.tbl_dem_detalle_maquinaria_orden_trabajo 
ADD bol_operativo boolean default true; 


ALTER TABLE administracion.tbl_dem_contrato
ADD dbl_montoconiva numeric(12,2),
ADD dbl_saldo numeric(12,2);



ALTER TABLE administracion.tbl_dem_recibo
ALTER int_iddocumento DROP  NOT NULL;

ALTER TABLE administracion.tbl_dem_recibo
ADD int_idcontrato integer,
ADD CONSTRAINT fk_tbl_dem_recibo_ctto FOREIGN KEY (int_idcontrato)
      REFERENCES administracion.tbl_dem_contrato (seq_idcontrato) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE administracion.tbl_dem_documentofiscal
ADD CONSTRAINT fk_tbl_dem_documentofiscal_uniquectto UNIQUE (int_idcontrato);

ALTER TABLE administracion.tbl_dem_contrato
ADD bol_facturado boolean;

ALTER TABLE administracion.tbl_dem_documentofiscal
ADD bol_postservicio boolean;

/*********************************************************************/

ALTER TABLE administracion.tbl_dem_detallecontrato
ADD dbl_cantidadreal numeric(12,4);


Alter TABLE gestion.tbl_dem_orden_servicio_mecanizado
ADD dbl_trabajolabor numeric(10,2);

ALTER TABLE administracion.tbl_dem_formapago
ADD  dtm_fecharecep date;

ALTER TABLE administracion.tbl_dem_deposito
ADD bol_cierre boolean,
ADD dtm_fechacierre date;
  
/****************************************************************/


ALTER TABLE administracion.tbl_dem_tipo_forma_pago
ADD bol_deposito boolean DEFAULT false;

UPDATE administracion.tbl_dem_tipo_forma_pago set bol_deposito = true where seq_idtipoformapago = 3;
UPDATE administracion.tbl_dem_tipo_forma_pago set bol_usacuen = true where seq_idtipoformapago = 2;
UPDATE administracion.tbl_dem_tipo_forma_pago set bol_usadocu = true where seq_idtipoformapago >4;
UPDATE administracion.tbl_dem_tipo_forma_pago set bol_nota = true where seq_idtipoformapago >4;

ALTER TABLE administracion.tbl_dem_control_sede
ADD str_cuentaadelanto character varying(25);


ALTER TABLE administracion.tbl_dem_cierrediario
ADD  dbl_monade numeric(12,4);


CREATE TABLE administracion.tbl_dem_cierrediarioadelanto
(
  seq_idcierreadelanto serial NOT NULL,
  int_idcierre integer NOT NULL,
  int_idcliente integer NOT NULL,
  dbl_monto numeric(12,4),
  CONSTRAINT pk_tbl_dem_cierrediarioadelanto PRIMARY KEY (seq_idcierreadelanto),
  CONSTRAINT fk_tbl_dem_cierrediarioadelanto_cierre FOREIGN KEY (int_idcierre)
      REFERENCES administracion.tbl_dem_cierrediario (seq_idcierre) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_cierrediarioadelanto_cliente FOREIGN KEY (int_idcliente)
      REFERENCES public.tbl_dem_clientes (seq_idcliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE administracion.tbl_dem_cierrediarioadelanto OWNER TO dba;

ALTER TABLE public.tbl_dem_tipo_servicio
ADD str_cuentaingreso character varying(50),
ADD str_presupuestoingreso character varying(50);



CREATE TABLE gestion.tbl_dem_orden_transporte_detalle
(
  seq_iddetalletransporte bigserial NOT NULL,
  int_idordenservicio bigint NOT NULL,
  int_origen integer,
  int_destino integer,
  bol_exitoso integer,
  int_causafalla integer,
  dbl_distancia numeric(10,2),
  int_diasespera integer,
  CONSTRAINT pk_tbl_dem_orden_transporte_detalle PRIMARY KEY (seq_iddetalletransporte),
  CONSTRAINT fk_tbl_dem_orden_transporte_detalle_ordenservicio FOREIGN KEY (int_idordenservicio)
      REFERENCES gestion.tbl_dem_orden_transporte_produccion (int_idordenservicio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_transporte_detalle_origen FOREIGN KEY (int_origen)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_transporte_detalle_destino FOREIGN KEY (int_destino)
      REFERENCES ministerio.tbl_dem_direccion (seq_iddireccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tbl_dem_orden_transporte_produccion_falla FOREIGN KEY (int_causafalla)
      REFERENCES gestion.tbl_dem_fallas_recepcion_silo (seq_idfallarecepcionsilo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;
ALTER TABLE gestion.tbl_dem_orden_transporte_detalle OWNER TO dba;


ALTER TABLE gestion.tbl_dem_orden_transporte_detalle
ADD  int_orden integer;

ALTER TABLE gestion.tbl_dem_orden_transporte_detalle
drop  bol_exitoso, 
ADD   bol_exitoso boolean;


ALTER TABLE ministerio.tbl_dem_direccionpropietario
ADD seq_iddireprop serial NOT NULL, 
  DROP CONSTRAINT pk_tbl_dem_direccionpropietario,
  ADD CONSTRAINT pk_tbl_dem_direccionpropietario PRIMARY KEY (seq_iddireprop),
  ADD CONSTRAINT fk_tbl_dem_direccionpropietario_unico UNIQUE (int_iddireccion, int_idcliente);
  
ALTER TABLE administracion.tbl_dem_tipo_forma_pago
ADD str_cuentacontable character varying(25);

INSERT INTO administracion.tbl_dem_tipo_forma_pago(
            str_descripcion, bol_usadocu, bol_usacuen, 
            bol_usaban, bol_nota, bol_deposito, str_cuentacontable)
    VALUES ('Retencion 1x1000', true, false, false, true, 
            false, '112040101010013');

UPDATE administracion.tbl_dem_tipo_forma_pago SET str_cuentacontable = '112040101010011' where seq_idtipoformapago = 5;
UPDATE administracion.tbl_dem_tipo_forma_pago SET str_cuentacontable = '112040101010012'where seq_idtipoformapago = 6;   

ALTER TABLE basico.tbl_dem_tipoarticulo
ADD str_cuentaingreso character varying(50),
ADD str_presupuestoingreso character varying(50);

ALTER TABLE administracion.tbl_dem_cierrediarioasiento
ADD str_codemp character(4) NOT NULL DEFAULT '0001'::bpchar,
ADD str_sede character(4) NOT NULL, 
ADD CONSTRAINT fk_tbl_dem_cierrediarioasiento_sede FOREIGN KEY (str_codemp, str_sede)
       REFERENCES spg_unidadadministrativa (codemp, coduniadm) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
      


ALTER TABLE administracion.tbl_dem_cierrediarioasiento
ADD str_renglon character varying(120);

CREATE OR REPLACE VIEW administracion.viw_detalle_asiento_ingresos AS 
 SELECT sum(tbl_dem_documentofiscaldetalle.dbl_cantidad * tbl_dem_documentofiscaldetalle.dbl_preciounitario) AS ingreso, tbl_dem_tipodocumentolegal.bol_haber, tbl_dem_documentofiscal.dtm_fecha, tbl_dem_tipo_servicio.str_cuentaingreso, tbl_dem_tipo_servicio.str_presupuestoingreso, tbl_dem_tipo_servicio.str_nombre
   FROM administracion.tbl_dem_documentofiscaldetalle
   JOIN administracion.tbl_dem_documentofiscal ON tbl_dem_documentofiscal.seq_iddocumento = tbl_dem_documentofiscaldetalle.int_iddocumento
   LEFT JOIN administracion.tbl_dem_tipodocumentolegal ON tbl_dem_tipodocumentolegal.seq_idtipfac = tbl_dem_documentofiscal.int_idtipodocumento
   LEFT JOIN basico.tbl_dem_labor ON tbl_dem_documentofiscaldetalle.int_idservicio = tbl_dem_labor.int_idproducto
   LEFT JOIN basico.tbl_dem_servicio ON tbl_dem_labor.int_idservicio = tbl_dem_servicio.seq_idservicio
   LEFT JOIN tbl_dem_tipo_servicio ON tbl_dem_tipo_servicio.seq_idtiposervicio = tbl_dem_servicio.int_idtiposervicio
   WHERE int_estado != 3
  GROUP BY tbl_dem_tipodocumentolegal.bol_haber, tbl_dem_documentofiscal.dtm_fecha, tbl_dem_tipo_servicio.str_cuentaingreso, tbl_dem_tipo_servicio.str_presupuestoingreso, tbl_dem_tipo_servicio.str_nombre
UNION 
 SELECT sum(tbl_dem_documentofiscaldetalle.dbl_cantidad * tbl_dem_documentofiscaldetalle.dbl_preciounitario) AS ingreso, tbl_dem_tipodocumentolegal.bol_haber, tbl_dem_documentofiscal.dtm_fecha, tbl_dem_tipoarticulo.str_cuentaingreso, tbl_dem_tipoarticulo.str_presupuestoingreso, 'VENTA MAQUINARIA' AS str_nombre
   FROM administracion.tbl_dem_documentofiscaldetalle
   JOIN administracion.tbl_dem_documentofiscal ON tbl_dem_documentofiscal.seq_iddocumento = tbl_dem_documentofiscaldetalle.int_iddocumento
   LEFT JOIN administracion.tbl_dem_tipodocumentolegal ON tbl_dem_tipodocumentolegal.seq_idtipfac = tbl_dem_documentofiscal.int_idtipodocumento
   LEFT JOIN basico.tbl_dem_articulo ON tbl_dem_documentofiscaldetalle.int_idservicio = tbl_dem_articulo.int_idproducto
   LEFT JOIN basico.tbl_dem_clasearticulo ON tbl_dem_articulo.int_idclasearticulo = tbl_dem_clasearticulo.seq_idclasearticulo
   LEFT JOIN basico.tbl_dem_tipoarticulo ON tbl_dem_tipoarticulo.seq_idtipoarticulo = tbl_dem_clasearticulo.int_idtipoarticulo
   WHERE int_estado != 3
  GROUP BY tbl_dem_tipodocumentolegal.bol_haber, tbl_dem_documentofiscal.dtm_fecha, tbl_dem_tipoarticulo.str_cuentaingreso, tbl_dem_tipoarticulo.str_presupuestoingreso;

ALTER TABLE administracion.viw_detalle_asiento_ingresos OWNER TO sigesp;

CREATE OR REPLACE VIEW administracion.viw_detalle_asiento_impuestos AS 
 SELECT sum(tbl_dem_documentofiscaldetalle.dbl_cantidad * tbl_dem_documentofiscaldetalle.dbl_preciounitario * tbl_dem_tipo_impuesto.dbl_porcentaje / 100::numeric) AS ingreso, tbl_dem_tipodocumentolegal.bol_haber, tbl_dem_documentofiscal.dtm_fecha, tbl_dem_tipo_impuesto.str_descripcion
   FROM administracion.tbl_dem_documentofiscaldetalle
   JOIN administracion.tbl_dem_documentofiscal ON tbl_dem_documentofiscal.seq_iddocumento = tbl_dem_documentofiscaldetalle.int_iddocumento
   JOIN administracion.tbl_dem_tipodocumentolegal ON tbl_dem_tipodocumentolegal.seq_idtipfac = tbl_dem_documentofiscal.int_idtipodocumento
   JOIN administracion.tbl_dem_tipo_impuesto ON tbl_dem_documentofiscaldetalle.int_idtipoimpuesto = tbl_dem_tipo_impuesto.seq_idtipoimpuesto
  WHERE int_estado != 3
  GROUP BY tbl_dem_tipodocumentolegal.bol_haber, tbl_dem_documentofiscal.dtm_fecha, tbl_dem_tipo_impuesto.str_descripcion;

ALTER TABLE administracion.viw_detalle_asiento_impuestos OWNER TO sigesp;


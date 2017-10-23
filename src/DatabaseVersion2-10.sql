
ALTER TABLE gestion.tbl_dem_orden_servicio
ADD CONSTRAINT fk_tbl_dem_orden_servicio_estado FOREIGN KEY (int_idestadoorden)
      REFERENCES gestion.tbl_dem_estadoordentrabajo (seq_idestado) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      


CREATE OR REPLACE FUNCTION operadores_orden(bigint)
  RETURNS text AS
$BODY$
  DECLARE
	proceden ALIAS FOR $1;
	mirecord basico.tbl_dem_trabajador%ROWTYPE;
	salida TEXT := '';

BEGIN
	FOR mirecord IN (SELECT basico.tbl_dem_trabajador.* FROM gestion.tbl_dem_detalle_maquinaria_orden_trabajo
		INNER JOIN basico.tbl_dem_trabajador ON seq_idtrabajador = int_idoperador
		WHERE int_idordenservicio = proceden) LOOP
		IF salida = '' THEN
			salida = mirecord.str_nombres || ' ' || mirecord.str_apellidos;		
		ELSE
			salida = salida || ', ' || mirecord.str_nombres || ' ' || mirecord.str_apellidos;		
		END IF;
	END LOOP;
	return salida;

END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION operadores_orden(bigint) OWNER TO dba;
GRANT EXECUTE ON FUNCTION operadores_orden(bigint) TO dba;
GRANT EXECUTE ON FUNCTION operadores_orden(bigint) TO postgres;
GRANT EXECUTE ON FUNCTION operadores_orden(bigint) TO public;      


CREATE OR REPLACE FUNCTION maquinas_orden(bigint)
  RETURNS text AS
$BODY$
  DECLARE
	proceden ALIAS FOR $1;
	mirecord record;
	salida TEXT := '';

BEGIN
	FOR mirecord IN (SELECT A.*, B.denact  FROM gestion.tbl_dem_detalle_maquinaria_orden_trabajo
		INNER JOIN gestion.tbl_dem_maquinaria_unidad ON seq_idmaquinaria =int_idmaquinaria
		INNER JOIN sigesp.saf_dta  A ON A.codemp =str_codempmaq and A.codact = str_idactivomaq and ideact=str_idejemplarmaq
		INNER JOIN sigesp.saf_activo B ON B.codemp = str_codempmaq and B.codact =str_idactivomaq
		WHERE int_idordenservicio = proceden) LOOP
		IF salida = '' THEN
			salida = mirecord.denact || ' ' || mirecord.seract;		
		ELSE
			salida = salida || ', ' || mirecord.denact || ' ' || mirecord.seract;		
		END IF;
	END LOOP;
	return salida;

END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION maquinas_orden(bigint) OWNER TO dba;
GRANT EXECUTE ON FUNCTION maquinas_orden(bigint) TO dba;
GRANT EXECUTE ON FUNCTION maquinas_orden(bigint) TO postgres;
GRANT EXECUTE ON FUNCTION maquinas_orden(bigint) TO public;




CREATE TYPE nota_result AS (notas text, cantnontacre numeric(12,4),  cantnotadeb numeric(12,4), montobasenotas numeric(12,4), montototanotas numeric(12,4));


CREATE OR REPLACE FUNCTION notas_orden(integer, integer)
  RETURNS nota_result  AS
$BODY$
  DECLARE
	proceden ALIAS FOR $1;
	servicio ALIAS FOR $2;
	salida	nota_result;
	mirecord record;

BEGIN
	select '', 0.0,0.0, 0.0, 0.0 INTO salida;

	FOR mirecord IN (SELECT cast(administracion.tbl_dem_documentofiscal.int_nrocontrol AS TEXT) as nota, dbl_cantidad, dbl_preciounitario*dbl_cantidad as precio,dbl_preciounitario*dbl_cantidad * (1+ dbl_porcentaje) as preciototal, int_idtipodocumento, bol_haber
   	   FROM  administracion.tbl_dem_nota
           INNER JOIN administracion.tbl_dem_documentofiscaldetalle as detallenota ON administracion.tbl_dem_nota.int_iddocumento = detallenota.int_iddocumento 
           INNER JOIN administracion.tbl_dem_documentofiscal ON seq_iddocumento = detallenota.int_iddocumento
           INNER JOIN administracion.tbl_dem_tipodocumentolegal ON seq_idtipfac = int_idtipodocumento
	   LEFT JOIN administracion.tbl_dem_tipo_impuesto ON seq_idtipoimpuesto = detallenota.int_idtipoimpuesto	   
	

   	   WHERE int_idfactura = proceden and detallenota.int_idservicio = servicio and not (int_estado = 3)) LOOP
		IF  salida.notas = '' then
			salida.notas = mirecord.nota;
		else
			salida.notas = salida.notas || ', ' || mirecord.nota;
		END IF;
		IF mirecord.bol_haber then
			salida.cantnontacre = salida.cantNontaCre - mirecord.dbl_cantidad;
			salida.montobasenotas = salida.montobasenotas - mirecord.precio;
			salida.montototanotas = salida.montototanotas - mirecord.preciototal;
		else
			salida.cantnotadeb = salida.cantnotadeb + mirecord.dbl_cantidad;
			salida.montobasenotas = salida.montobasenotas + mirecord.precio;
			salida.montototanotas = salida.montototanotas + mirecord.preciototal;
		end if;
		
		
	END LOOP;
	return salida;

END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION notas_orden(integer, integer) OWNER TO dba;
GRANT EXECUTE ON FUNCTION notas_orden(integer, integer) TO dba;
GRANT EXECUTE ON FUNCTION notas_orden(integer, integer) TO postgres;
GRANT EXECUTE ON FUNCTION notas_orden(integer, integer) TO public;

select notas_orden(1,16);






CREATE OR REPLACE VIEW viw_sabana_labores AS 
SELECT tbl_dem_clientes.str_nombre, str_cedurif, basico.tbl_dem_rubro.str_descripcion as rubro, 
ministerio.tbl_dem_sector.str_descripcion as sector,  ministerio.tbl_dem_parroquia.str_descripcion as parroquia, 
ministerio.tbl_dem_municipio.str_descripcion as municipio, 
basico.tbl_dem_producto.str_descripcion as labor, 
basico.tbl_dem_servicio.str_descripcion as servicio,
tbl_dem_unidad_medidas.str_descripcion as unidadmedida,  str_abreviatura, 

gestion.tbl_dem_solicitud.str_nrocon as nrosol,
gestion.tbl_dem_solicitud.dtm_fecha as fechasol, 
gestion.tbl_dem_solicituddetalleunidad.dbl_cantidad as cantfisisol, dbl_pase as cantpassol, 
gestion.tbl_dem_solicituddetalleunidad.dbl_cantidad * dbl_pase as cantlabsol,

gestion.tbl_dem_orden_servicio.str_nrocon as nroorden, dte_inicio, gestion.tbl_dem_estadoordentrabajo.str_descripcion as estadoorden,
dbl_cantidadsolicitada as cantsolordserv, 

administracion.tbl_dem_contrato.str_serie as seriectto, administracion.tbl_dem_contrato.int_nrocontrol contctto,
administracion.tbl_dem_documentofiscal.str_serie as seriefact, administracion.tbl_dem_documentofiscal.int_nrocontrol as nroconfac,
administracion.tbl_dem_documentofiscal.dtm_fecha as fechafact, 
administracion.tbl_dem_documentofiscaldetalle.dbl_cantidad as cantfact, 
administracion.tbl_dem_documentofiscaldetalle.dbl_preciounitario * administracion.tbl_dem_documentofiscaldetalle.dbl_cantidad as basefact,
administracion.tbl_dem_documentofiscaldetalle.dbl_preciounitario * administracion.tbl_dem_documentofiscaldetalle.dbl_cantidad *(1+dbl_porcentaje/100) as montofact,

dbl_produccionreal, dbl_trabajofisico,   dbl_trabajolabor,

  str_nombres || ', ' || str_apellidos as tecnico,

notas_orden(seq_iddocumento, int_idlabor) as notasgeneradas,

operadores_orden(seq_idordenservicio)  as operadores, maquinas_orden(seq_idordenservicio) as maquinarias,

seq_idsector, seq_idparroquia, seq_idmunicipio, seq_idcliente, seq_idrubro, int_unidadfuncional, int_idestadoorden,
gestion.tbl_dem_solicituddetalle.int_idsolicitud, gestion.tbl_dem_orden_servicio_mecanizado.int_idordenservicio, seq_idservicio, int_idlabor,
 int_idunidadmedida,seq_idcontrato, seq_iddocumento,

int_idcicloproductivo, int_idunidadproductiva, 
       int_diasespera, 
       int_idfinanciamiento, int_idtecnico, gestion.tbl_dem_orden_servicio_mecanizado.bol_produccion, 
       bol_actaproduccion, bol_transportado
  FROM gestion.tbl_dem_orden_servicio_mecanizado
  INNER JOIN gestion.tbl_dem_orden_servicio ON seq_idordenservicio = int_idordenservicio
  INNER JOIN basico.tbl_dem_rubro ON seq_idrubro = int_idrubro
  INNER JOIN tbl_dem_clientes on seq_idcliente = int_idproductor
  INNER JOIN  ministerio.tbl_dem_unidadproductiva ON seq_idunidadproductiva = int_idunidadproductiva
  INNER JOIN ministerio.tbl_dem_direccion ON seq_iddireccion = int_iddireccion
  INNER JOIN ministerio.tbl_dem_sector ON seq_idsector = int_idsector
  INNER JOIN ministerio.tbl_dem_parroquia ON seq_idparroquia = int_idparroquia
  INNER JOIN ministerio.tbl_dem_municipio ON seq_idmunicipio = int_idmunicipio
  INNER JOIN gestion.tbl_dem_estadoordentrabajo ON seq_idestado = int_idestadoorden
  LEFT JOIN gestion.tbl_dem_solicitud on seq_idsolicitud = gestion.tbl_dem_orden_servicio.int_idsolicitud
  
  INNER JOIN gestion.tbl_dem_detalle_orden_servicio ON seq_idordenservicio = gestion.tbl_dem_detalle_orden_servicio.int_idordenservicio
  INNER JOIN basico.tbl_dem_labor ON int_idproducto = int_idlabor
  INNER JOIN basico.tbl_dem_producto ON seq_idproducto = int_idproducto
  INNER JOIN basico.tbl_dem_servicio ON seq_idservicio = int_idservicio
  INNER JOIN tbl_dem_unidad_medidas  ON seq_idumedida = int_idunidadmedida
  LEFT JOIN gestion.tbl_dem_solicituddetalle ON seq_idsolicitud =gestion.tbl_dem_solicituddetalle.int_idsolicitud and gestion.tbl_dem_solicituddetalle.int_idproducto =int_idlabor
  LEFT JOIN gestion.tbl_dem_solicituddetalleunidad ON int_idrenglon = seq_idrenglon and int_idunidad = seq_idumedida
  LEFT JOIN administracion.tbl_dem_contrato ON seq_idcontrato = int_idcontrato
  LEFT JOIN  administracion.tbl_dem_documentofiscal ON seq_idcontrato = administracion.tbl_dem_documentofiscal.int_idcontrato
  LEFT JOIN administracion.tbl_dem_documentofiscaldetalle ON seq_iddocumento = administracion.tbl_dem_documentofiscaldetalle.int_iddocumento 
	and administracion.tbl_dem_documentofiscaldetalle.int_idservicio = int_idlabor
  LEFT JOIN administracion.tbl_dem_tipo_impuesto ON seq_idtipoimpuesto = administracion.tbl_dem_documentofiscaldetalle.int_idtipoimpuesto
  LEFT JOIN basico.tbl_dem_trabajador on seq_idtrabajador = int_idtecnico
LEFT JOIN administracion.tbl_dem_nota ON int_idfactura = seq_iddocumento
LEFT JOIN administracion.tbl_dem_documentofiscaldetalle as detallenota ON administracion.tbl_dem_nota.int_iddocumento = detallenota.int_iddocumento 
	and detallenota.int_idservicio = int_idlabor;
	

CREATE OR REPLACE FUNCTION extraer_notaorden(nota_result)
  RETURNS text AS
$BODY$
  DECLARE
	dato ALIAS FOR $1;
	salida TEXT := '';
BEGIN
	return dato.notas;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION extraer_notaorden(nota_result) OWNER TO dba;
GRANT EXECUTE ON FUNCTION extraer_notaorden(nota_result) TO public;
GRANT EXECUTE ON FUNCTION extraer_notaorden(nota_result) TO dba;
GRANT EXECUTE ON FUNCTION extraer_notaorden(nota_result) TO postgres;





CREATE OR REPLACE FUNCTION extraer_notaorden(nota_result, integer )
  RETURNS numeric(12,4) AS
$BODY$
  DECLARE
	dato ALIAS FOR $1;
	campo ALIAS FOR $2;
	salida TEXT := '';
BEGIN
	if campo = 1 then
		return CAST(dato.cantnontacre as TEXT);
	elsif campo = 2 then
		return CAST(dato.cantnotadeb as TEXT);
	elsif campo = 3 then
		return CAST(dato.montobasenotas as TEXT);		
	elsif campo = 4 then
		return CAST(dato.montototanotas as TEXT);	
	end if;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION extraer_notaorden(nota_result, integer) OWNER TO dba;
GRANT EXECUTE ON FUNCTION extraer_notaorden(nota_result, integer) TO public;
GRANT EXECUTE ON FUNCTION extraer_notaorden(nota_result, integer) TO dba;
GRANT EXECUTE ON FUNCTION extraer_notaorden(nota_result, integer) TO postgres;
	
CREATE OR REPLACE VIEW gestion.viw_sabana_labores AS 
SELECT str_nombre, str_cedurif, rubro, sector, parroquia, municipio, 
       labor, servicio, unidadmedida, str_abreviatura, nrosol, fechasol, 
       cantfisisol, cantpassol, cantlabsol, nroorden, dte_inicio, estadoorden, 
       cantsolordserv, seriectto, contctto, seriefact, nroconfac, fechafact, 
       cantfact, basefact, montofact, dbl_produccionreal, dbl_trabajofisico, 
       dbl_trabajolabor, tecnico, 

       extraer_notaorden(notasgeneradas) as notasasociadas, 
       extraer_notaorden(notasgeneradas, 1) as cantCredito, 
       extraer_notaorden(notasgeneradas, 2) as cantdebito, 
       extraer_notaorden(notasgeneradas, 3) as montobasenotas,
       extraer_notaorden(notasgeneradas, 4) as montototalnotas,
	
       operadores, maquinarias, 
       seq_idsector, seq_idparroquia, seq_idmunicipio, seq_idcliente, 
       seq_idrubro, int_unidadfuncional, int_idestadoorden, int_idsolicitud, 
       int_idordenservicio, seq_idservicio, int_idlabor, int_idunidadmedida, 
       seq_idcontrato, seq_iddocumento, int_idcicloproductivo, int_idunidadproductiva, 
       int_diasespera, int_idfinanciamiento, int_idtecnico, bol_produccion, 
       bol_actaproduccion, bol_transportado
  FROM viw_sabana_labores;
